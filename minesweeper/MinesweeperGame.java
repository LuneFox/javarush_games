package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.gui.*;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.SwitchSelector;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.board.*;
import com.javarush.games.minesweeper.model.board.Timer;
import com.javarush.games.minesweeper.model.board.Cell.Filter;
import com.javarush.games.minesweeper.model.player.*;
import com.javarush.games.minesweeper.model.shop.*;

import java.util.List;

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
    public int gameOverShowDelay;              // delay after which game over screen appears
    public boolean isFlagExplosionAllowed;     // allows destroying flags during chain explosions
    public boolean isStopped;                  // board game hasn't started or is already finished
    public boolean isFirstMove;                // state before first click was made on the field
    public boolean isVictory;                  // last game result
    private boolean isRecursiveMove;           // doesn't count as a move made by player
    private boolean autoStop;                  // game can't continue playing automatically

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(100, 100);
        setTurnTimer(30);

        instance = this;            // most declarations below use this instance, leave before everything else
        Options.initialize();
        display = new Display();
        controller = new Controller();
        field = new Field();
        timer = new Timer();
        shop = new Shop();
        player = new Player();
        isStopped = true;

        Phase.setActive(Phase.MAIN);
    }

    @Override
    public void onTurn(int step) {
        Phase.updateView(); // view elements are drawn layer by layer over the virtual invisible display to save time
        display.draw();     // copy all pixels from virtual display to real display
    }

    public void startNewGame() {
        resetValues();
        Phase.setActive(Phase.BOARD);
        PopUpMessage.show("Новая игра");
    }

    private void resetValues() {
        Options.apply();
        field.create();
        player.reset();
        shop.reset();
        timer.reset();
        isStopped = false;
        isFirstMove = true;
        isRecursiveMove = false;
        isVictory = false;
        setScore(player.score.getCurrentScore());
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
        if (isStopped) return;

        // Ensure that first click hits empty cell, all others clicks are normal without magic
        Cell cell = isFirstMove ? restartUntilCellIsEmpty(field.getCell(x, y)) : field.getCell(x, y);

        // Check if bomb or scanner can be used, stop processing if they were used
        if (shop.miniBomb.use(cell) || shop.scanner.use(cell)) return;
        if (cell.isFlagged || cell.isOpen) return;

        boolean survived = tryOpening(cell);
        if (!survived) return;

        onManualClick(cell);  // do things that happen during real click only
        addScore(cell);       // dice will be displayed over the passed cell
        addMoney(cell);       // money will be given according to the number on the cell
        recursiveOpen(cell);  // neighbors of the cell will be opened recursively
        checkVictory();
    }

    private boolean tryOpening(Cell cell) {     // did the player survive the mine while opening?
        cell.open();
        if (!cell.isMined) {
            return true;                        // cell isn't mined - YES
        }
        if (shop.shield.isActivated()) {
            return shop.shield.use(cell);       // shield has worked - YES
        }
        explode(cell);                          // nothing helped - NO
        return false;
    }

    private void recursiveOpen(Cell cell) {
        if (cell.isEmpty()) {
            isRecursiveMove = true;
            List<Cell> neighbors = field.getNeighborCells(cell, Filter.NONE, false);
            neighbors.forEach(neighbor -> openCell(neighbor.x, neighbor.y));
        }
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
        if (cell.isMined) {
            cell.setSprite(ImageType.BOARD_MINE);
        } else if (cell.isNumerable()) {
            cell.setSprite(cell.countMinedNeighbors);
        } else {
            cell.setSprite(ImageType.NONE);
        }
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

    private void addMoney(Cell cell) {
        int moneyEarned = cell.countMinedNeighbors * (shop.goldenShovel.isActivated() ? 2 : 1);
        player.inventory.money += moneyEarned;
        if (shop.goldenShovel.isActivated()) {
            cell.makeNumberYellow();
        }
    }

    private void addScore(Cell cell) {
        int randomNumber = getRandomNumber(6) + 1;
        Dice dice = field.dice;
        dice.setImage(randomNumber, dice.appearCell.x, dice.appearCell.y);

        if (cell.isMined) return;
        if (shop.luckyDice.isActivated()) {
            player.score.setDiceScore(player.score.getDiceScore() + Options.difficulty * randomNumber);
            dice.totalCells++;
            dice.totalBonus += randomNumber;
        }
        setScore(player.score.getCurrentScore());
    }

    private Cell restartUntilCellIsEmpty(Cell cell) {
        List<Cell> flaggedCells = field.getAllCells(Filter.FLAGGED);                  // get flagged cells
        flaggedCells.forEach(fc -> returnFlagToInventory(field.getCell(fc.x, fc.y))); // collect flags
        while (!cell.isEmpty()) {                                                     // until cell is empty here
            field.create();                                                           // recreate field
            cell = field.getCell(cell.x, cell.y);
        }
        flaggedCells.forEach(fc -> setFlag(fc.x, fc.y, false));                       // put flags back
        isFirstMove = false;
        return cell;                                                                  // this cell is empty for sure
    }

    private void onManualClick(Cell cell) {
        if (isRecursiveMove) return;
        field.dice.appearCell = cell;
        player.incMoves();
        player.score.addTimerScore();
        timer.reset();
    }

    // Scanner action
    public void scanNeighbors(int x, int y) {  // action for Scanner
        List<Cell> safeNeighbors = field.getNeighborCells(field.getCell(x, y), Filter.SAFE, true);

        if (safeNeighbors.size() == 0) {       // no safe cells, place free flags over closed ones
            PopUpMessage.show("Нечего сканировать");
            field.getNeighborCells(field.getCell(x, y), Filter.CLOSED, true).forEach(closedCell -> {
                if (player.inventory.hasNoFlags()) shop.give(shop.flag);
                setFlag(closedCell.x, closedCell.y, false);
            });
        } else {                               // open random safe cell
            Cell cell = safeNeighbors.get(getRandomNumber(safeNeighbors.size()));
            if (cell.isFlagged) {
                setFlag(cell.x, cell.y, true); // remove flag if it was placed wrong
            }
            openCell(cell.x, cell.y);
            PopUpMessage.show("Сканирование...");
            cell.scan();
        }
    }

    // Mini-bomb action
    public void destroyCell(int x, int y) {
        Cell cell = field.getCell(x, y);
        onManualClick(cell);

        if (cell.isIndestructible()) {
            PopUpMessage.show("Не получилось!");
            return;
        }

        if (cell.isMined) { // recursive explosions
            PopUpMessage.show("Взорвалась мина!");
            cell.isMined = false;
            isRecursiveMove = true;
            isFlagExplosionAllowed = true;
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


    // CHEATS

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
        isFlagExplosionAllowed = false;
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