package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.utility.Util;
import com.javarush.games.minesweeper.view.graphics.*;
import com.javarush.games.minesweeper.view.View;
import com.javarush.games.minesweeper.Cell.Filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main game class
 */

public class MinesweeperGame extends Game {

    private static MinesweeperGame instance;

    public Display display;
    public View view;
    public Inventory inventory;
    public Shop shop;
    public Player player;
    public Timer timer;
    public Cell[][] field = new Cell[10][10];
    private Controller controller;

    // GAME STATE
    public int difficulty = 10;

    // FLAGS
    public boolean evenTurn;        // is now even turn or odd turn? helps with animation of certain elements
    public boolean allowCountMoves; // user clicked with mouse = not recursive action = allow counting as a move
    public boolean allowFlagExplosion;
    public boolean lastResultIsVictory;
    public boolean isStopped = true;
    public boolean isFirstMove = true;

    // NEW GAME

    @Override
    public void initialize() {
        instance = this;            // must come first, new objects before use this instance at creation time
        display = new Display();
        controller = new Controller();
        view = new View();
        timer = new Timer();
        shop = new Shop();
        player = new Player();
        inventory = new Inventory();

        showGrid(false);
        setScreenSize(100, 100);
        isStopped = true;
        view.createSubViews();
        View.main.display();
        setTurnTimer(30);
    }

    @Override
    public void onTurn(int step) {
        evenTurn = (!evenTurn);
        onTurnAction();
        display.draw();
    }

    private void onTurnAction() {
        // everything that happens with the flow of time on different screens
        switch (Screen.get()) {
            case MAIN:
                View.main.display();
                break;
            case OPTIONS:
                View.options.display();
                break;
            case RECORDS:
                View.records.display();
                break;
            case ABOUT:
                View.about.display();
                break;
            case SCORE:
                View.score.display();
                break;
            case ITEM_HELP:
                View.itemHelp.display();
                break;
            case GAME_OVER:
                View.gameOver.display();
                break;
            case BOARD:
                if (timeOut()) {
                    loseByTimeOut();
                    return;
                } else {
                    View.board.display();
                }
                break;
            case SHOP:
                if (timeOut()) {
                    loseByTimeOut();
                    return;
                } else {
                    View.shop.display();
                }
                break;
            default:
                break;
        }
        displayDice();
    }

    public void createGame() {
        applyOptions();    // difficulty impacts the number of mines created below
        createField();
        plantMines();      // number of mines define the number of flags given out below
        resetValues();
        enumerateCells();
        View.board.display();
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
        difficulty = View.options.difficultySetting;
        timer.enabled = View.options.timerEnabledSetting;
    }

    private void createField() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                field[y][x] = new Cell(VisualElement.CELL_CLOSED, x, y, false);
            }
        }
    }

    private void plantMines() {
        while (countAllCells(Filter.MINED) < difficulty / 1.5) { // fixed number of mines on field
            int x = getRandomNumber(10);
            int y = getRandomNumber(10);
            if (!field[y][x].isMined && !field[y][x].isOpen)
                field[y][x] = new Cell(VisualElement.CELL_CLOSED, x, y, true);
        }
    }


    // WIN AND LOSE

    private void lose() {
        lastResultIsVictory = false;
        isStopped = true;
        View.gameOver.display(false, 30);
    }

    private void win() {
        lastResultIsVictory = true;
        player.score.registerTopScore();
        isStopped = true;
        View.gameOver.display(true, 30);
    }

    public boolean timeOut() {
        if (!isStopped) {
            if (timer.isZero()) {
                return true;
            } else {
                timer.countDown();
            }
        }
        return false;
    }

    public void loseByTimeOut() {
        View.board.display();
        revealAllMines();
        lose();
        View.gameOver.display(false, 30);
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

        pushCell(cell);                 // change visuals, set isOpen flag
        onManualClick();                // do things that happen during real click only
        if (!surviveMine(cell)) return; // stop processing if the player didn't survive
        drawNumberOnCell(cell);         // show number since we know it's not a bomb (survived)

        inventory.money += cell.countMinedNeighbors * (shop.goldenShovel.isActivated() ? 2 : 1); // player gets gold

        addScore(shop.dice.appearCell.x, shop.dice.appearCell.y); // cell.x, cell.y = dice display position

        recursiveOpenEmpty(cell);                  // for surrounding empty cells, moved don't count
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
        if (shop.scanner.isActivated() || shop.miniBomb.isActivated()) return;
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

        cell.setSprite(VisualElement.SPR_BOARD_NONE);
        cell.isDestroyed = true;
        pushCell(cell);
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
            redrawAllCells();
        }
    }

    public void setFlag(int x, int y, boolean canRemove) {
        if (isStopped) return;

        Cell cell = field[y][x];

        if (cell.isOpen) return;

        if (cell.isFlagged && canRemove) {
            returnFlagToInventory(cell);
        } else {
            if (canPlaceFlag()) {
                placeFlagFromInventory(cell);
            }
        }
    }

    private void returnFlagToInventory(Cell cell) {
        inventory.add(ShopItem.ID.FLAG);
        cell.isFlagged = false;
        cell.eraseSprite();
    }

    private void placeFlagFromInventory(Cell cell) {
        if (cell.isFlagged) return;
        inventory.remove(ShopItem.ID.FLAG);
        cell.isFlagged = true;
        cell.setSprite(VisualElement.SPR_BOARD_FLAG);
        cell.drawSprite();
    }

    private boolean canPlaceFlag() {
        if (inventory.hasNoFlags()) {
            if (shop.autoBuyFlagsEnabled) {
                if (shop.flag.isUnobtainable()) {
                    return false;
                }
                shop.sell(shop.flag);
                return true;
            } else {
                View.shop.display();
                return false;
            }
        } else {
            return true;
        }
    }

    private boolean surviveMine(Cell cell) {        // did the player survive the mine?
        if (cell.isMined) {
            if (shop.shield.isActivated()) {
                shop.shield.use(cell);              // shield has worked - YES
                return true;
            } else {
                explodeAndGameOver(cell);           // nothing else saved the player - NO
                return false;
            }
        }
        return true;
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

    private List<Cell> getAllCells(Filter filter) {
        List<Cell> all = new ArrayList<>();
        for (int y = 0; y < 10; y++) {
            all.addAll(Arrays.asList(field[y]).subList(0, 10));
        }
        return Cell.filterCells(all, filter);
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

    private void enumerateCells() {
        getAllCells(Filter.NUMERABLE).forEach(cell -> {
            cell.countMinedNeighbors = getNeighborCells(cell, Filter.MINED, false).size();
            cell.setSprite(cell.countMinedNeighbors);
        });
    }

    public int countAllCells(Filter filter) {
        return getAllCells(filter).size();
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

        List<Cell> flaggedCells = getAllCells(Filter.FLAGGED); // get flagged items
        flaggedCells.forEach(fc -> returnFlagToInventory(field[fc.y][fc.x])); // collect flags

        while (!cell.isEmpty()) { // create new game until the clicked cell is empty
            createField();
            plantMines();
            enumerateCells();
            cell = field[y][x];
        }

        flaggedCells.forEach(oldFlaggedCell -> { // put flags back
            setFlag(oldFlaggedCell.x, oldFlaggedCell.y, false);
        });

        isFirstMove = false;
        openCell(x, y);
    }

    private void explodeAndGameOver(Cell cell) {
        revealAllMines();
        cell.drawBackground(Color.RED); // highlight mine that caused game over
        cell.drawSprite();
        shop.dice.hide();
        lose();
    }

    private void displayDice() {
        if (shop.luckyDice == null) return;
        int remainingTurns = shop.luckyDice.expireMove - player.getMoves();
        if (Util.inside(remainingTurns, 0, 2) && player.getMoves() != 0) shop.dice.draw();
    }

    // VARIOUS CHECKS WITH CORRESPONDING ACTIONS

    public void checkVictory() {
        if (countAllCells(Filter.CLOSED) == countAllCells(Filter.DANGEROUS))
            win();
    }

    private boolean cellDestructionImpossible(Cell cell) {
        boolean activated = (cell.isOpen || cell.isDestroyed);
        boolean noFlagDestruction = (cell.isFlagged && !allowFlagExplosion);
        return (isStopped || activated || noFlagDestruction);
    }

    private void onManualClick() {
        if (allowCountMoves) {
            player.incMoves();
            player.score.setTimerScore(player.score.getTimerScore() + timer.getScore());
            timer.restart();
        }
    }

    public void deactivateExpiredItems() {
        shop.goldenShovel.expireCheck();
        shop.luckyDice.expireCheck();
    }

    // ANIMATIONS

    private void drawNumberOnCell(Cell cell) {
        if (!cell.isFlagged && !cell.isMined) {
            cell.setSprite(cell.countMinedNeighbors);
            if (shop.goldenShovel.isActivated()) {
                cell.changeSpriteColor(Color.YELLOW);
            }
        }
        cell.drawSprite();
    }

    private void pushCell(Cell cell) {
        cell.isOpen = true;
        cell.push();
    }

    public void redrawAllCells() { // redraws all cells in current state to hide whatever was drawn over them
        getAllCells(Filter.NONE).forEach(cell -> {
            cell.drawBackground(Color.NONE);
            if (cell.isOpen || cell.isFlagged) {
                cell.drawSprite();
            }
        });
    }

    private void revealAllMines() {
        for (int posY = 0; posY < 10; posY++) {
            for (int posX = 0; posX < 10; posX++) {
                Cell showCell = field[posY][posX];
                if (showCell.isMined) {
                    showCell.isOpen = true;
                    showCell.setSprite(VisualElement.SPR_BOARD_MINE);
                    showCell.push();
                }
            }
        }
        timer.draw();
    }

    public void recolorAllCells() {
        if (isStopped) return;
        for (int posY = 0; posY < 10; posY++) {
            for (int posX = 0; posX < 10; posX++) {
                field[posY][posX].fullRecolor();
            }
        }
    }

    public static MinesweeperGame getInstance() {
        return instance;
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
}