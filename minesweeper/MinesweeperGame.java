package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.gui.Display;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.SwitchSelector;
import com.javarush.games.minesweeper.model.*;
import com.javarush.games.minesweeper.model.board.Dice;
import com.javarush.games.minesweeper.model.board.Timer;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.board.Field;
import com.javarush.games.minesweeper.model.player.Inventory;
import com.javarush.games.minesweeper.model.player.Player;
import com.javarush.games.minesweeper.model.player.Score;
import com.javarush.games.minesweeper.model.shop.Shop;
import com.javarush.games.minesweeper.model.shop.ShopItem;
import com.javarush.games.minesweeper.model.board.Cell.Filter;

import java.util.*;

/**
 * Main game class
 */

public class MinesweeperGame extends Game {
    private static MinesweeperGame instance;
    private Controller controller;
    public Display display;
    public Shop shop;
    public Player player;
    public Timer timer;
    public Field field;
    public boolean allowFlagExplosion;         // allows destroying flags during chain explosions
    public boolean isStopped = true;
    public boolean isFirstMove = true;
    private boolean isRecursiveMove = false;    // sometimes cells are opened by recursion, don't count it as moves
    public boolean isVictory = false;
    public int gameOverShowDelay;
    public boolean autoStop;                   // Game can't continue playing automatically

    @Override
    public void initialize() {
        instance = this;            // most declarations below use this instance, leave before everything else
        Options.initialize();
        display = new Display();
        controller = new Controller();
        field = new Field();
        timer = new Timer();
        shop = new Shop();
        player = new Player();

        showGrid(false);
        setScreenSize(100, 100);
        isStopped = true;
        Phase.setActive(Phase.MAIN);
        setTurnTimer(30);
    }

    @Override
    public void onTurn(int step) {
        Phase.updateView(); // view elements are drawn layer by layer over the virtual invisible display to save time
        display.draw();     // copy all pixels from virtual display to real display
    }

    public void startNewGame() {
        Options.apply();
        field.create();
        isStopped = false;
        isFirstMove = true;
        player.reset();
        shop.reset();
        player.inventory.reset();
        timer.restart();
        setScore(player.score.getCurrentScore());
        Phase.setActive(Phase.BOARD);
        PopUpMessage.show("Новая игра");
    }


    // WIN AND LOSE

    private void lose() {
        finish(false);
        field.dice.hide();
        field.revealMines();
    }

    private void win() {
        finish(true);
        player.score.registerTopScore();
    }

    private void finish(boolean isVictory) {
        this.isStopped = true;
        this.isVictory = isVictory;
        gameOverShowDelay = 30;
        Phase.setActive(Phase.GAME_OVER);
        setScore(player.score.getTotalScore());
        Score.Table.update();
    }

    public void checkVictory() {
        if (field.countAllCells(Filter.CLOSED) == field.countAllCells(Filter.DANGEROUS)) {
            win();
        }
    }

    public void timerTick() {
        if (!isStopped && timer.isZero()) {
            Phase.setActive(Phase.BOARD);
            PopUpMessage.show("Время вышло!");
            lose();
        } else {
            timer.countDown();
        }
    }

    private void explode(Cell cell) {
        cell.isGameOverCause = true; // mine that caused game over
        lose();
    }

    // BASE ACTIONS

    public void openCell(int x, int y) {
        Cell cell = field.getCell(x, y);

        if (shop.miniBomb.use(cell) || shop.scanner.use(cell)) return; // try using items and return if they were used
        if (!isRecursiveMove) field.dice.appearCell = cell;
        if (isStopped || cell.isFlagged || cell.isOpen) return;

        if (isFirstMove) {
            forceClickOnBlank(cell);       // rebuilds the level until the cell at this position is blank
            cell = field.getCell(x, y);    // continue with a new cell
        }

        cell.open();
        onManualTurn();                  // do things that happen during real click only

        boolean survived = trySurviving(cell);
        if (!survived) {
            explode(cell);
            return;
        }

        int moneyEarned = cell.countMinedNeighbors * (shop.goldenShovel.isActivated() ? 2 : 1);
        player.inventory.money += moneyEarned;

        cell.makeNumberYellow();            // make golden if the shovel is in use

        addScore(field.dice.appearCell.x, field.dice.appearCell.y); // x,y = dice display position

        if (cell.isEmpty()) {             // recursive opening
            isRecursiveMove = true;
            List<Cell> neighbors = field.getNeighborCells(cell, Filter.NONE, false);
            neighbors.forEach(neighbor -> openCell(neighbor.x, neighbor.y));
        }

        checkVictory();
    }

    public void openSurroundingCells(int x, int y) {
        // attempts to open cells around if number of flags nearby equals the number on the cell
        if (shop.scanner.isActivated()) return;
        if (shop.miniBomb.isActivated()) return;
        Cell cell = field.getCell(x, y);
        if (cell.isOpen && !cell.isMined) {
            // If mined neighbors = number of neighbor flags + opened mines
            if (cell.countMinedNeighbors == field.getNeighborCells(cell, Filter.SUSPECTED, false).size()) {
                field.getNeighborCells(cell, Filter.NONE, false).forEach(neighbor -> openCell(neighbor.x, neighbor.y));
            }
        }
    }

    public void setFlag(int x, int y, boolean flagIsRemovable) {
        if (isStopped) return;
        Cell cell = field.get()[y][x];
        if (cell.isOpen) return;
        if (cell.isFlagged && flagIsRemovable) {
            returnFlagToInventory(cell);
        } else {
            placeFlagFromInventory(cell);
        }
    }

    private void returnFlagToInventory(Cell cell) {
        player.inventory.add(ShopItem.ID.FLAG);
        cell.isFlagged = false;
        cell.setSprite(cell.isMined ? ImageType.BOARD_MINE : ImageType.NONE);
    }

    private void placeFlagFromInventory(Cell cell) {
        Inventory inventory = player.inventory;
        if (inventory.hasNoFlags()) shop.offerFlag();
        if (inventory.hasNoFlags()) return;
        if (cell.isFlagged) return;
        inventory.remove(ShopItem.ID.FLAG);
        cell.isFlagged = true;
        cell.setSprite(ImageType.BOARD_FLAG);
    }

    private void retrieveFlag(Cell cell) {
        if (cell.isFlagged) {
            shop.restock(shop.flag, 1);
            cell.isFlagged = false;
        }
    }

    private boolean trySurviving(Cell cell) {   // did the player survive the mine?
        if (!cell.isMined) return true;         // cell isn't mined - YES
        if (shop.shield.isActivated()) {
            shop.shield.use(cell);              // shield has worked - YES
            PopUpMessage.show("Щит разрушен!");
            return true;
        } else {
            return false;
        }
    }

    private void addScore(int x, int y) {
        int randomNumber = getRandomNumber(6) + 1;
        Dice dice = field.dice;
        dice.setImage(randomNumber, x, y);

        if (field.getCell(x, y).isMined) return;
        if (shop.luckyDice.isActivated()) {
            player.score.setDiceScore(player.score.getDiceScore() + Options.difficulty * randomNumber);
            dice.totalCells++;
            dice.totalBonus += randomNumber;
        }
        setScore(player.score.getCurrentScore());
    }

    private void forceClickOnBlank(Cell cell) {
        List<Cell> flaggedCells = field.getAllCells(Filter.FLAGGED);                  // get flagged cells
        flaggedCells.forEach(fc -> returnFlagToInventory(field.getCell(fc.x, fc.y))); // collect flags
        while (!cell.isEmpty()) {                                                     // until cell is empty here
            field.create();                                                           // recreate field
            cell = field.getCell(cell.x, cell.y);
        }
        flaggedCells.forEach(fc -> setFlag(fc.x, fc.y, false));                       // put flags back
        isFirstMove = false;
        openCell(cell.x, cell.y);
    }

    private void onManualTurn() {
        if (isRecursiveMove) return;
        player.incMoves();
        player.score.addTimerScore(timer.getScore());
        timer.restart();
    }

    // Scanner action
    public void scanNeighbors(int x, int y) {  // action for Scanner
        List<Cell> safeNeighbors = field.getNeighborCells(field.getCell(x, y), Filter.SAFE, true);

        if (safeNeighbors.size() == 0) {       // no safe cells, place free flags over closed ones
            PopUpMessage.show("Нечего открывать");
            field.getNeighborCells(field.getCell(x, y), Filter.CLOSED, true).forEach(closedCell -> {
                if (player.inventory.hasNoFlags()) shop.give(shop.flag);
                setFlag(closedCell.x, closedCell.y, false);
            });
        } else {                               // open random safe cell
            Cell cell = safeNeighbors.get(getRandomNumber(safeNeighbors.size()));
            cell.isScanned = true;
            if (cell.isFlagged) {
                setFlag(cell.x, cell.y, true); // remove flag if it was placed wrong
            }
            openCell(cell.x, cell.y);
            cell.setBackgroundColor(Theme.CELL_SCANNED.getColor());
            PopUpMessage.show("Ячейка открыта");
        }
    }

    // Mini-bomb action
    public void destroyCell(int x, int y) {
        onManualTurn();
        Cell cell = field.getCell(x, y);
        if (cell.isIndestructible()) {
            PopUpMessage.show("Не получилось!");
            return;
        }

        if (cell.isMined) { // recursive explosions
            PopUpMessage.show("Взорвалась мина!");
            cell.isMined = false;
            isRecursiveMove = true;
            allowFlagExplosion = true;
            field.getNeighborCells(cell, Filter.NONE, false).forEach(neighbor -> {
                if (neighbor.isMined) {
                    destroyCell(neighbor.x, neighbor.y); // recursive call
                }
            });
        }

        cell.destroy();
        retrieveFlag(cell);
        field.setNumbers();
    }


    @DeveloperOption
    public void autoFlag() {
        if (!Options.developerMode) return;

        SwitchSelector selector = Options.autoBuyFlagsSelector;
        if (!selector.isEnabled()) {
            selector.tryClick(selector.x, selector.y); // click self
        }

        boolean[] success = new boolean[1];
        field.getAllCells(Filter.NUMERABLE).forEach(cell -> {
            if (!cell.isOpen) return;
            List<Cell> dangerousNeighbors = field.getNeighborCells(cell, Filter.DANGEROUS, false);
            List<Cell> closedNeighbors = field.getNeighborCells(cell, Filter.CLOSED, false);
            if (dangerousNeighbors.size() == closedNeighbors.size()) {
                dangerousNeighbors.forEach(dangerousNeighbor -> {
                    if (dangerousNeighbor.isFlagged) return;
                    if (player.inventory.hasNoFlags()) shop.sell(shop.flag);
                    setFlag(dangerousNeighbor.x, dangerousNeighbor.y, false);
                    success[0] = true;
                });
            }
        });
        if (!success[0]) {
            PopUpMessage.show("DEV: CANNOT FLAG");
            autoStop = true;
        } else {
            PopUpMessage.show("DEV: AUTO FLAG");
        }
    }

    @DeveloperOption
    public void autoOpen() {
        if (!Options.developerMode) return;

        int closedCells = field.countAllCells(Filter.CLOSED);
        if (isFirstMove) {
            List<Cell> allCells = field.getAllCells(Filter.NONE);
            Cell randomCell = allCells.get(getRandomNumber(allCells.size()));
            onMouseLeftClick(randomCell.x * 10, randomCell.y * 10);
        } else {
            for (Cell cell : field.getAllCells(Filter.NUMERABLE)) {
                if (cell.isOpen) onMouseRightClick(cell.x * 10, cell.y * 10);
            }
        }
        PopUpMessage.show(field.countAllCells(Filter.CLOSED) == closedCells ? "DEV: CANNOT OPEN" : "DEV: AUTO OPEN");
    }

    @DeveloperOption
    public void autoScan() {
        if (!Options.developerMode) return;

        List<Cell> allCells = field.getAllCells(Filter.SAFE);
        if (allCells.isEmpty()) return;
        Cell randomCell = allCells.get(getRandomNumber(allCells.size()));
        shop.scanner.activate();
        onMouseLeftClick(randomCell.x * 10, randomCell.y * 10);
        PopUpMessage.show("DEV: RANDOM SCAN");
    }

    @DeveloperOption
    public void skipEasyPart() {
        if (!Options.developerMode) return;

        int closedCells = field.countAllCells(Filter.CLOSED);
        autoStop = false;
        int limit = 0;
        while (!autoStop) {
            // Limit is set to prevent accidental infinite loops
            autoOpen();
            autoFlag();
            limit++;
            if (limit > closedCells) {
                break;
            }
        }
        autoOpen();
        PopUpMessage.show(field.countAllCells(Filter.CLOSED) == closedCells ? "DEV: TOO RISKY!" : "DEV: SKIP EASY PART");
    }

    @DeveloperOption
    public void cheatMoreMoney() {
        if (!Options.developerMode) return;

        player.inventory.money += 50;
        PopUpMessage.show("DEV: 50 GOLD");
    }

    @DeveloperOption
    public void cheatMoreTools() {
        if (!Options.developerMode) return;

        shop.goldenShovel.activate();
        shop.luckyDice.activate();
        shop.goldenShovel.expireMove += 10;
        shop.luckyDice.expireMove += 10;
        PopUpMessage.show("DEV: 10 TOOLS");
    }

    // CONTROLS

    @Override
    public void onMouseLeftClick(int x, int y) {
        isRecursiveMove = false;
        allowFlagExplosion = false;
        controller.leftClick(x, y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        isRecursiveMove = false;
        controller.rightClick(x, y);
    }

    @Override
    public void onKeyPress(Key key) {
        controller.pressKey(key);
    }

    // GETTERS

    public static MinesweeperGame getInstance() {
        return instance;
    }
}