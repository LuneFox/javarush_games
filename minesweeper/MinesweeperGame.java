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
    public com.javarush.games.minesweeper.model.Timer timer;
    public Cell[][] field = new Cell[10][10];
    public int difficulty = 10;                // current difficulty
    public int difficultySetting = difficulty; // in the options, applied for the new game
    private boolean allowCountMoves; // user clicked with mouse = not recursive action = allow counting as a move
    private boolean allowFlagExplosion;
    public boolean isStopped = true;
    public boolean isFirstMove = true;
    public boolean isVictory = false;

    // NEW GAME

    @Override
    public void initialize() {
        instance = this;            // must come first, new objects before use this instance at creation time
        display = new Display();
        controller = new Controller();
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

    public void createGame() {
        applyOptions();    // difficulty impacts the number of mines created below
        createField();
        plantMines();      // number of mines define the number of flags given out below
        resetValues();
        enumerateCells();
        setScore(player.score.getCurrentScore());
    }

    private void resetValues() {
        isStopped = false;
        isFirstMove = true;
        player.reset();
        shop.reset();
        inventory.reset();
        timer.restart();
    }

    private void applyOptions() {
        difficulty = difficultySetting;
        timer.isEnabled = timer.isEnabledSetting;
    }

    private void createField() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                field[y][x] = new Cell(VisualElement.CELL_CLOSED, x, y, false);
                field[y][x].setSprite(VisualElement.NONE);
            }
        }
    }

    private void plantMines() {
        while (countAllCells(Filter.MINED) < difficulty / 1.5) { // fixed number of mines on field
            int x = getRandomNumber(10);
            int y = getRandomNumber(10);
            if (!field[y][x].isMined && !field[y][x].isOpen) {
                field[y][x] = new Cell(VisualElement.CELL_CLOSED, x, y, true);
                field[y][x].setSprite(VisualElement.SPR_BOARD_MINE);
            }
        }
    }


    // WIN AND LOSE

    private void lose() {
        isStopped = true;
        isVictory = false;
        Screen.gameOver.setShowDelay();
        Screen.set(Screen.GAME_OVER);
    }

    private void win() {
        player.score.registerTopScore();
        isStopped = true;
        isVictory = true;
        Screen.gameOver.setShowDelay();
        Screen.set(Screen.GAME_OVER);
    }

    public void timerAction() {
        if (!isStopped && timer.isZero()) {
            Screen.set(Screen.BOARD);
            revealAllMines();
            lose();
        } else {
            timer.countDown();
        }
    }

    // ACTIVE CELL OPERATIONS

    public void openCell(int x, int y) {
        Cell cell = field[y][x];
        if (shop.miniBomb.use(cell) || shop.scanner.use(cell)) return;
        if (allowCountMoves) shop.dice.appearCell = cell;
        if (isStopped || cell.isFlagged || cell.isOpen) return;
        if (isFirstMove) {
            ensureClickingOnBlankSpace(x, y);
            cell = field[y][x];
        }
        cell.isOpen = true;
        onManualClick();                  // do things that happen during real click only
        if (!trySurviving(cell)) return;  // stop processing if the player didn't survive
        applyNumberToCell(cell);          // set number since we know it's not a bomb (survived)
        inventory.money += cell.countMinedNeighbors * (shop.goldenShovel.isActivated() ? 2 : 1); // player gets gold
        addScore(shop.dice.appearCell.x, shop.dice.appearCell.y); // x,y = dice display position
        recursiveOpenEmpty(cell);         // for surrounding empty cells, moves don't count
        checkVictory();
    }

    private void recursiveOpenEmpty(Cell cell) {
        allowCountMoves = false;               // automatic opening doesn't count as a player move
        if (cell.isEmpty()) {
            List<Cell> neighbors = getNeighborCells(cell, Filter.NONE, false);
            neighbors.forEach(neighbor -> openCell(neighbor.x, neighbor.y));
        }
    }

    public void openRest(int x, int y) {
        // attempts to open cells around if number of flags nearby equals the number on the cell
        if (shop.scanner.isActivated()) return;
        if (shop.miniBomb.isActivated()) return;
        Cell cell = field[y][x];
        if (cell.isOpen && !cell.isMined) {
            if (cell.countMinedNeighbors == getNeighborCells(cell, Filter.SUSPECTED, false).size()) {
                getNeighborCells(cell, Filter.NONE, false).forEach(neighbor -> openCell(neighbor.x, neighbor.y));
            }
        }
    }

    public void destroyCell(int x, int y) {
        onManualClick();
        Cell cell = field[y][x];
        if (cellDestructionImpossible(cell)) return;

        cell.setSprite(VisualElement.NONE);
        cell.isDestroyed = true;
        cell.isOpen = true;
        deactivateExpiredItems();

        if (cell.isFlagged) {
            shop.restock(shop.flag, 1);
            cell.isFlagged = false;
        }

        if (cell.isMined) { // recursive explosions
            cell.isMined = false;
            allowCountMoves = false;
            allowFlagExplosion = true;
            getNeighborCells(cell, Filter.NONE, false).forEach(neighbor -> {
                if (neighbor.isMined) {
                    destroyCell(neighbor.x, neighbor.y); // recursive call
                }
            });
            enumerateCells();
        }
    }

    public void setFlag(int x, int y, boolean flagIsRemovable) {
        if (isStopped) return;
        Cell cell = field[y][x];
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
        if (!cell.isMined) return true;
        if (shop.shield.isActivated()) {
            shop.shield.use(cell);              // shield has worked - YES
            return true;
        } else {
            explodeAndGameOver(cell);           // nothing else saved the player - NO
            return false;
        }
    }

    public void scanNeighbors(int x, int y) { // to use with a scanner item
        List<Cell> neighbors = getNeighborCells(field[y][x], Filter.SAFE, true);

        if (neighbors.size() == 0) { // no safe cells
            getNeighborCells(field[y][x], Filter.CLOSED, true).forEach(closedCell -> {
                if (inventory.hasNoFlags()) shop.give(shop.flag);
                setFlag(closedCell.x, closedCell.y, false); // set flag forcefully
            });
            return;
        }

        Cell cell = neighbors.get(getRandomNumber(neighbors.size()));
        cell.isScanned = true;
        if (cell.isFlagged) {
            setFlag(cell.x, cell.y, true); // remove flag if it was placed wrong
        }
        openCell(cell.x, cell.y);
    }

    private void enumerateCells() {
        getAllCells(Filter.NUMERABLE).forEach(cell -> {
            cell.countMinedNeighbors = getNeighborCells(cell, Filter.MINED, false).size();
            cell.setSprite(cell.countMinedNeighbors);
        });
    }

    // CELL COLLECTIONS

    private List<Cell> getAllCells(Filter filter) {
        List<Cell> all = new ArrayList<>();
        for (int y = 0; y < 10; y++) {
            all.addAll(Arrays.asList(field[y]));
        }
        return Cell.filterCells(all, filter);
    }

    public int countAllCells(Filter filter) {
        return getAllCells(filter).size();
    }

    private List<Cell> getNeighborCells(Cell cell, Filter filter, boolean includeSelf) {
        List<Cell> neighbors = new ArrayList<>();
        for (int y = cell.y - 1; y <= cell.y + 1; y++) {
            for (int x = cell.x - 1; x <= cell.x + 1; x++) {
                if (y < 0 || y >= 10) continue;
                if (x < 0 || x >= 10) continue;
                if (field[y][x] == cell && !includeSelf) continue;  // skip center if not included
                neighbors.add(field[y][x]);
            }
        }
        return Cell.filterCells(neighbors, filter);
    }

    // UTILITIES

    private void addScore(int x, int y) {
        int randomNumber = getRandomNumber(6) + 1;
        shop.dice.setImage(randomNumber, x, y);

        if (field[y][x].isMined) return;
        if (shop.luckyDice.isActivated()) {
            player.score.setDiceScore(player.score.getDiceScore() + difficulty * randomNumber);
            shop.dice.totalCells++;
            shop.dice.totalBonus += randomNumber;
        }
        setScore(player.score.getCurrentScore());
    }

    private void ensureClickingOnBlankSpace(int x, int y) {
        Cell cell = field[y][x];

        List<Cell> flaggedCells = getAllCells(Filter.FLAGGED);                // get flagged cells
        flaggedCells.forEach(fc -> returnFlagToInventory(field[fc.y][fc.x])); // collect flags

        while (!cell.isEmpty()) { // create new game until the clicked cell is empty
            createField();
            plantMines();
            enumerateCells();
            cell = field[y][x];
        }

        flaggedCells.forEach(oldFlaggedCell -> {                             // put flags back
            setFlag(oldFlaggedCell.x, oldFlaggedCell.y, false);
        });

        isFirstMove = false;
        openCell(x, y);
    }

    private void explodeAndGameOver(Cell cell) {
        revealAllMines();
        cell.isGameOverCause = true; // highlight mine that caused game over
        shop.dice.hide();
        lose();
    }

    public void displayDice() {
        if (shop.luckyDice == null) return;
        int remainingTurns = shop.luckyDice.expireMove - player.getMoves();
        if (Util.inside(remainingTurns, 0, 2) && player.getMoves() != 0) shop.dice.draw();
    }

    // OPTION SETTINGS

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

    // VARIOUS CHECKS WITH CORRESPONDING ACTIONS

    public void checkVictory() {
        if (countAllCells(Filter.CLOSED) == countAllCells(Filter.DANGEROUS)) win();
    }

    private boolean cellDestructionImpossible(Cell cell) {
        boolean activated = (cell.isOpen || cell.isDestroyed);
        boolean noFlagDestruction = (cell.isFlagged && !allowFlagExplosion);
        return (isStopped || activated || noFlagDestruction);
    }

    private void onManualClick() {
        if (!allowCountMoves) return;
        player.incMoves();
        player.score.setTimerScore(player.score.getTimerScore() + timer.getScore());
        timer.restart();
    }

    public void deactivateExpiredItems() {
        shop.goldenShovel.expireCheck();
        shop.luckyDice.expireCheck();
    }

    // ANIMATIONS

    private void applyNumberToCell(Cell cell) {
        if (cell.isFlagged) return;
        if (cell.isMined) return;

        cell.setSprite(cell.countMinedNeighbors);
        if (shop.goldenShovel.isActivated()) {
            cell.makeSpriteYellow();
        }
    }

    public void drawAllCells() {
        getAllCells(Filter.NONE).forEach(Cell::draw);
    }

    private void revealAllMines() {
        getAllCells(Filter.NONE).forEach(cell -> {
            if (cell.isMined) cell.isOpen = true;
        });
    }

    public final void recolorInterface() {
        Cache.fill();
        if (isStopped) return; // No cells to color yet
        getAllCells(Filter.NONE).forEach(Cell::updateColors);
    }

    // CONTROLS

    @Override
    public void onMouseLeftClick(int x, int y) {
        allowCountMoves = true;
        allowFlagExplosion = false;
        controller.leftClick(x, y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        allowCountMoves = true;
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