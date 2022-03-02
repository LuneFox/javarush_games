package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.model.*;
import com.javarush.games.minesweeper.model.Timer;
import com.javarush.games.minesweeper.view.graphics.*;
import com.javarush.games.minesweeper.model.Cell.Filter;

import java.util.*;

/**
 * Main game class
 */

public class MinesweeperGame extends Game {
    private static MinesweeperGame instance;
    private Controller controller;
    public Display display;
    public Inventory inventory;
    public Shop shop;
    public Player player;
    public Timer timer;
    public Field field;
    public int difficulty = 10;                // current difficulty
    public int difficultySetting = difficulty; // in the options, applied for the new game
    private boolean allowCountingPlayerMoves;  // sometimes cells are opened by recursion, don't count it as moves
    public boolean allowFlagExplosion;         // allows destroying flags during chain explosions
    public boolean isStopped = true;
    public boolean isFirstMove = true;
    public boolean isVictory = false;

    @Override
    public void initialize() {
        instance = this;            // most declarations below use this instance
        display = new Display();
        controller = new Controller();
        field = new Field();
        timer = new Timer();
        shop = new Shop();
        player = new Player();
        inventory = new Inventory();

        showGrid(false);
        setScreenSize(100, 100);
        isStopped = true;
        Screen.set(Screen.MAIN);
        setTurnTimer(30);
    }

    @Override
    public void onTurn(int step) {
        Screen.updateView();
        display.draw();
    }

    public void startNewGame() {
        difficulty = difficultySetting;            // impacts the number of created mines
        timer.isEnabled = timer.isEnabledSetting;
        field.create();
        isStopped = false;
        isFirstMove = true;
        player.reset();
        shop.reset();
        inventory.reset();
        timer.restart();
        setScore(player.score.getCurrentScore());
    }

    // WIN AND LOSE

    private void lose() {
        finish(false);
        shop.dice.hide();
        field.revealMines();
    }

    private void win() {
        finish(true);
        player.score.registerTopScore();
    }

    private void finish(boolean isVictory) {
        this.isStopped = true;
        this.isVictory = isVictory;
        Screen.gameOver.setDelay();
        Screen.set(Screen.GAME_OVER);
        setScore(player.score.getTotalScore());
        Score.Table.update();
    }

    public void checkVictory() {
        if (field.countAllCells(Filter.CLOSED) == field.countAllCells(Filter.DANGEROUS)) {
            win();
        }
    }

    public void checkTimeOut() {
        if (!isStopped && timer.isZero()) {
            Screen.set(Screen.BOARD);
            lose();
        } else {
            timer.countDown();
        }
    }

    private void explode(Cell cell) {
        cell.isGameOverCause = true; // highlight mine that caused game over
        lose();
    }

    // ACTIVE CELL OPERATIONS

    public void openCell(int x, int y) {
        Cell cell = field.getCell(x, y);

        if (shop.miniBomb.use(cell) || shop.scanner.use(cell)) return; // try using items and return if they were used
        if (allowCountingPlayerMoves) shop.dice.appearCell = cell;
        if (isStopped || cell.isFlagged || cell.isOpen) return;

        if (isFirstMove) {
            makeClickOnBlank(cell);       // rebuilds the level until the cell at this position is blank
            cell = field.getCell(x, y);   // continue with a new cell
        }

        cell.isOpen = true;
        onManualClick();                  // do things that happen during real click only

        boolean survived = trySurviving(cell);
        if (!survived) {
            explode(cell);
            return;
        }

        inventory.money += cell.countMinedNeighbors * (shop.goldenShovel.isActivated() ? 2 : 1); // player gets gold
        cell.makeNumberGold();            // make golden if the shovel is in use

        addScore(shop.dice.appearCell.x, shop.dice.appearCell.y); // x,y = dice display position

        if (cell.isEmpty()) {             // recursive opening
            allowCountingPlayerMoves = false;
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
            if (cell.countMinedNeighbors == field.getNeighborCells(cell, Filter.SUSPECTED, false).size()) {
                field.getNeighborCells(cell, Filter.NONE, false).forEach(neighbor -> openCell(neighbor.x, neighbor.y));
            }
        }
    }

    public void destroyCell(int x, int y) {
        onManualClick();
        Cell cell = field.getCell(x, y);
        if (cell.isIndestructible()) return;

        cell.setSprite(VisualElement.NONE);
        cell.isDestroyed = true;
        cell.isOpen = true;
        shop.deactivateExpiredItems();

        if (cell.isFlagged) {
            shop.restock(shop.flag, 1);
            cell.isFlagged = false;
        }

        if (cell.isMined) { // recursive explosions
            cell.isMined = false;
            allowCountingPlayerMoves = false;
            allowFlagExplosion = true;
            field.getNeighborCells(cell, Filter.NONE, false).forEach(neighbor -> {
                if (neighbor.isMined) {
                    destroyCell(neighbor.x, neighbor.y); // recursive call
                }
            });
            field.attachNumbers();
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
        inventory.add(ShopItem.ID.FLAG);
        cell.isFlagged = false;
        cell.setSprite(cell.isMined ? VisualElement.SPR_BOARD_MINE : VisualElement.NONE);
    }

    private void placeFlagFromInventory(Cell cell) {
        if (inventory.hasNoFlags()) shop.offerFlag();
        if (inventory.hasNoFlags()) return;
        if (cell.isFlagged) return;
        inventory.remove(ShopItem.ID.FLAG);
        cell.isFlagged = true;
        cell.setSprite(VisualElement.SPR_BOARD_FLAG);
    }

    private boolean trySurviving(Cell cell) {   // did the player survive the mine?
        if (!cell.isMined) return true;         // cell isn't mined - YES
        if (shop.shield.isActivated()) {
            shop.shield.use(cell);              // shield has worked - YES
            return true;
        } else {
            return false;
        }
    }

    public void scanNeighbors(int x, int y) {  // action for Scanner
        List<Cell> safeNeighbors = field.getNeighborCells(field.getCell(x, y), Filter.SAFE, true);

        if (safeNeighbors.size() == 0) {       // no safe cells, place free flags over closed ones
            field.getNeighborCells(field.getCell(x, y), Filter.CLOSED, true).forEach(closedCell -> {
                if (inventory.hasNoFlags()) shop.give(shop.flag);
                setFlag(closedCell.x, closedCell.y, false);
            });
        } else {                               // open random safe cell
            Cell cell = safeNeighbors.get(getRandomNumber(safeNeighbors.size()));
            cell.isScanned = true;
            if (cell.isFlagged) {
                setFlag(cell.x, cell.y, true); // remove flag if it was placed wrong
            }
            openCell(cell.x, cell.y);
        }
    }

    private void addScore(int x, int y) {
        int randomNumber = getRandomNumber(6) + 1;
        shop.dice.setImage(randomNumber, x, y);

        if (field.getCell(x, y).isMined) return;
        if (shop.luckyDice.isActivated()) {
            player.score.setDiceScore(player.score.getDiceScore() + difficulty * randomNumber);
            shop.dice.totalCells++;
            shop.dice.totalBonus += randomNumber;
        }
        setScore(player.score.getCurrentScore());
    }

    private void makeClickOnBlank(Cell cell) {
        List<Cell> flaggedCells = field.getAllCells(Filter.FLAGGED);                  // get flagged cells
        flaggedCells.forEach(fc -> returnFlagToInventory(field.getCell(fc.x, fc.y))); // collect flags

        while (!cell.isEmpty()) { // create new game until the clicked cell is empty
            field.create();
            cell = field.getCell(cell.x, cell.y);
        }

        flaggedCells.forEach(oldFlaggedCell -> {                                      // put flags back
            setFlag(oldFlaggedCell.x, oldFlaggedCell.y, false);
        });

        isFirstMove = false;
        openCell(cell.x, cell.y);
    }

    private void onManualClick() {
        if (!allowCountingPlayerMoves) return;
        player.incMoves();
        player.score.addTimerScore(timer.getScore());
        timer.restart();
    }

    // OPTIONS

    public void changeDifficultySetting(boolean makeHarder) {
        if (makeHarder && difficultySetting < 45) {
            difficultySetting += 5;
            Screen.options.animateRightArrow();
        } else if (!makeHarder && difficultySetting > 5) {
            difficultySetting -= 5;
            Screen.options.animateLeftArrow();
        }
    }

    public void switchAutoBuyFlags() {
        shop.autoBuyFlagsEnabled = !shop.autoBuyFlagsEnabled;
    }

    public void switchTimerSetting() {
        timer.isEnabledSetting = !timer.isEnabledSetting;
    }

    // CONTROLS

    @Override
    public void onMouseLeftClick(int x, int y) {
        allowCountingPlayerMoves = true;
        allowFlagExplosion = false;
        controller.leftClick(x, y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        allowCountingPlayerMoves = true;
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