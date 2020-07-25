package com.javarush.games.minesweeper;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.graphics.*;
import com.javarush.games.minesweeper.Screen.*;

import java.util.*;

/**
 * Main game class
 */

public class MinesweeperGame extends Game {

    // FINAL OBJECTS
    static final String VERSION = "1.06";
    public final Display display = new Display(this);
    final private Text text_writer = new Text(Bitmap.NONE, this);
    final private Menu menu = new Menu(this);
    final private InputEvent ie = new InputEvent(this);
    final Tile[][] field = new Tile[10][10];
    private final int INIT_FLAGS = 3;

    // SHOP ITEMS
    private ShopItem shopShield;
    private ShopItem shopScanner;
    private ShopItem shopFlag;
    private ShopItem shopGoldenShovel;
    private ShopItem shopLuckyDice;
    private ShopItem shopMiniBomb;
    private LinkedList<ShopItem> allShopItems = new LinkedList<>();

    // GAME STATE
    private int countClosedTiles = 100;
    int difficulty = 10;
    int difficultySetting = 10;
    int countMinesOnField;
    int countFlags;
    int countMoney;
    int countMoves;

    // SCORE RELATED
    String topScoreTitle = "";
    int topScore = 0;
    int score;
    int scoreLost;
    int scoreDice;
    int scoreCell;
    int openedCellsCount;

    // FLAGS
    private boolean isFirstMove;
    boolean isStopped;
    boolean lastResultIsVictory;
    boolean allowAutoBuyFlags;
    boolean allowCountMoves;
    boolean allowFlagExplosion;


    // NEW GAME

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(100, 100);
        loadResources();
        isStopped = true;
        menu.displayMain();
        setTurnTimer(30);
    }

    @Override
    public void onTurn(int step) {
        display.draw();
        if (Screen.get() == ScreenType.GAME_OVER) {
            if (menu.showDelay > 0) {
                menu.decreaseShowDelay();
            }
            menu.displayGameOver(lastResultIsVictory);
        }
    }

    void createGame() {
        // reset values
        isStopped = false;
        isFirstMove = true;
        countClosedTiles = 100;
        countMinesOnField = 0;
        countMoney = 0;
        countMoves = 0;
        score = 0;
        scoreLost = 0;
        scoreDice = 0;
        scoreCell = 0;
        openedCellsCount = 0;
        countFlags = INIT_FLAGS;
        difficulty = difficultySetting;

        // create assets
        setScore(score);
        createField();
        plantMines();
        countMineNeighbors();
        createShopItems();
        Screen.set(ScreenType.GAME_BOARD);
        menu.displayGameBoard();
    }

    private void loadResources() {
        text_writer.loadAlphabet();
        menu.loadImages();
        menu.loadButtons();
    }

    private void createField() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                field[y][x] = new Tile(Bitmap.TILE_CLOSED, this, x, y, false);
            }
        }
    }

    private void createShopItems() {
        this.shopShield = new ShopItem(0, 13 + difficulty / 5, 1,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_SHIELD), this);
        this.shopScanner = new ShopItem(1, 8 + difficulty / 5, 1,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_SCANNER), this);
        this.shopFlag = new ShopItem(2, 1, countMinesOnField - INIT_FLAGS,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_FLAG), this);
        this.shopGoldenShovel = new ShopItem(3, 9, 1,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_SHOVEL), this);
        this.shopLuckyDice = new ShopItem(4, 6, 1,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_DICE), this);
        this.shopMiniBomb = new ShopItem(5, 6 + difficulty / 10, 1,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_BOMB), this);
        this.allShopItems.clear();
        this.allShopItems.addAll(Arrays.asList(shopShield, shopScanner, shopFlag,
                shopGoldenShovel, shopLuckyDice, shopMiniBomb));
    }

    private void plantMines() {
        while (countMinesOnField < difficulty / 1.5) { // fixed number of mines on field
            int x = getRandomNumber(10);
            int y = getRandomNumber(10);
            if (!field[y][x].isMine && !field[y][x].isOpen) {
                field[y][x] = new Tile(Bitmap.TILE_CLOSED, this, x, y, true);
                countMinesOnField++;
            }
        }
    }


    // WIN AND LOSE

    private void gameOver() {
        lastResultIsVictory = false;
        isStopped = true;
        menu.showDelay = 30;
        menu.displayGameOver(false);
    }

    private void win() {
        lastResultIsVictory = true;
        score += (countMinesOnField * 20 * difficulty) + (countMoney * difficulty);
        setScore(score);
        if (score > topScore) {
            topScore = score;
            topScoreTitle = Menu.TITLE_NAMES.get(difficulty / 5 - 1);
        }
        isStopped = true;
        menu.showDelay = 30;
        menu.displayGameOver(true);
    }


    // ACTIVE TILE OPERATIONS

    void openTile(int x, int y) { // mouse click or recursive
        if (miniBombAction(x, y) || scannerAction(x, y)) {
            // attempt to perform bomb action or scanner action before normal turn
            // if any of them succeed, don't do anything else
            return;
        }

        Tile cell = field[y][x];

        if (isStopped || cell.isFlag || cell.isOpen) {
            // don't do anything else if the game is stopped or if user clicks on flag or open cell
            return;
        }

        pushTile(cell);
        countMoves();

        if (!surviveMine(cell)) {
            // check if the player survived the mine, and stop doing things below if so
            return;
        }

        drawNumberOnCell(cell);
        countMoney += cell.countMineNeighbors * (shopGoldenShovel.isActivated ? 2 : 1);
        addScore();
        setScore(score);
        deactivateExpiredItems();
        checkVictory();

        isFirstMove = false; // protects from counting moves when opening cells in recursion
        recursiveOpen(cell);
    }

    private void recursiveOpen(Tile cell) {
        // empty cells open cells near them, does not count as a move made by player
        if (cell.countMineNeighbors == 0 && !cell.isMine) {
            allowCountMoves = false;
            getNeighbors(cell).forEach(neighbor -> {
                if (!neighbor.isOpen) {
                    openTile(neighbor.x, neighbor.y);
                }
            });
        }
    }

    void openRest(int x, int y) {
        // attempts to open cells around if number of flags nearby equals the number on the cell
        Tile cell = field[y][x];
        if (cell.isOpen && !cell.isMine) {
            if (cell.countMineNeighbors == getFlaggedNeighbors(cell).size()) {
                getNeighbors(cell).forEach(tile -> openTile(tile.x, tile.y));
            }
        }
    }

    private void destroyTile(int x, int y) {
        countMoves();
        Tile cell = field[y][x];

        if (tileDestructionImpossible(cell)) {
            return;
        }

        cell.assignSprite(Bitmap.BOARD_NONE);
        cell.isDestroyed = true;
        pushTile(cell);
        deactivateExpiredItems();

        if (cell.isFlag) {
            shopFlag.count++;   // returning exploded flag to the shop
            cell.isFlag = false;
        }

        if (cell.isMine) { // recursive explosions
            cell.isMine = false;
            allowCountMoves = false;
            countMinesOnField--;
            allowFlagExplosion = true;
            getNeighbors(cell).forEach(neighbor -> {
                if (neighbor.isMine) {
                    destroyTile(neighbor.x, neighbor.y); // recursive call
                }
            });
            countMineNeighbors();
            redrawAllTiles();
            if (countClosedTiles == countMinesOnField) {
                win();
            }
        }
    }

    void markTile(int x, int y) { // sets or removes a flag
        if (isStopped) {
            return;
        }
        Tile cell = field[y][x];
        if (cell.isOpen) {
            return;
        }
        if (cell.isFlag) { // remove
            cell.isFlag = false;
            countFlags++;
            cell.eraseSprite();
        } else { // set
            if (countFlags == 0) {
                // if there are no flags left
                if (allowAutoBuyFlags) {
                    // buy flags automatically if you can
                    if (countMoney < shopFlag.cost || shopFlag.count <= 0) {
                        return;
                    }
                    countFlags++;
                    shopFlag.count--;
                    countMoney -= shopFlag.cost;
                } else {
                    // otherwise proceed to the shop and stop
                    menu.displayShop();
                    return;
                }
            }
            cell.isFlag = true;
            countFlags--;
            cell.assignSprite(Bitmap.BOARD_FLAG);
            cell.drawSprite();
        }
    }

    private void markTileWithoutUndo(int x, int y) { // doesn't unmark if it's already a flag
        if (isStopped) {
            return;
        }
        Tile cell = field[y][x];
        if (cell.isOpen) {
            return;
        }
        if (!cell.isFlag) { // set
            if (countFlags == 0) {
                return;
            }
            cell.isFlag = true;
            countFlags--;
            cell.assignSprite(Bitmap.BOARD_FLAG);
            cell.drawSprite();
        }
    }

    private boolean surviveMine(Tile cell) {
        if (cell.isMine) {
            if (isFirstMove) {
                // on the very first move, clicking on a mine moves it secretly to another place, returns true
                replantMine(cell);
            } else if (shopShield.isActivated) {
                // if the shield is bought, it saves from death, returns true
                shieldAction(cell);
            } else {
                // if nothing from above happened, triggers game over, returns false
                explodeAndGameOver(cell);
                return false;
            }
        }
        return true;
    }

    private void openRandomNeighbor(int x, int y) {
        List<Tile> neighbors = getTilesForScanner(field[y][x]);
        if (neighbors.size() == 0) {
            getTilesForAutoFlagging(field[y][x]).forEach(tile -> {
                if (countFlags == 0) {
                    shopFlag.count--;
                    countFlags++;   // free flags, yay!
                }
                markTileWithoutUndo(tile.x, tile.y); // doesn't unmark what was already flagged
            });
            return;
        }
        Tile cell = neighbors.get(getRandomNumber(neighbors.size()));
        cell.isScanned = true;
        if (cell.isFlag) {
            // remove flag with simple "mark" action
            markTile(cell.x, cell.y);
        }
        openTile(cell.x, cell.y);
    }


    // COLLECTING SURROUNDING TILES & INFO

    private void countMineNeighbors() { // counts mines in surrounding tiles
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Tile cell = field[y][x];
                cell.countMineNeighbors = 0;
                if (!cell.isMine) {
                    getNeighbors(cell).forEach(neighbor -> {
                        if (neighbor.isMine) {
                            cell.countMineNeighbors++;
                        }
                    });
                    if (!cell.isDestroyed && !cell.isFlag) {
                        cell.assignSprite(cell.countMineNeighbors);
                    }
                }
            }
        }
    }

    private List<Tile> getNeighbors(Tile tile) { // gets a list of surrounding tiles
        List<Tile> result = new ArrayList<>();
        for (int y = tile.y - 1; y <= tile.y + 1; y++) {
            for (int x = tile.x - 1; x <= tile.x + 1; x++) {
                if (y < 0 || y >= 10) {
                    continue;
                }
                if (x < 0 || x >= 10) {
                    continue;
                }
                if (field[y][x] == tile) {
                    continue;
                }
                result.add(field[y][x]);
            }
        }
        return result;
    }

    private List<Tile> getFlaggedNeighbors(Tile tile) {
        List<Tile> result = new ArrayList<>();
        for (int y = tile.y - 1; y <= tile.y + 1; y++) {
            for (int x = tile.x - 1; x <= tile.x + 1; x++) {
                if (y < 0 || y >= 10) {
                    continue;
                }
                if (x < 0 || x >= 10) {
                    continue;
                }
                if (field[y][x] == tile) {
                    continue;
                }
                if (field[y][x].isFlag || (field[y][x].isOpen && field[y][x].isMine)) {
                    result.add(field[y][x]);
                }
            }
        }
        return result;

    }

    private List<Tile> getTilesForScanner(Tile tile) { // closed and not mined, 3x3 area
        List<Tile> result = new ArrayList<>();
        for (int y = tile.y - 1; y <= tile.y + 1; y++) {
            for (int x = tile.x - 1; x <= tile.x + 1; x++) {
                if (y < 0 || y >= 10) {
                    continue;
                }
                if (x < 0 || x >= 10) {
                    continue;
                }
                if (!field[y][x].isOpen && !field[y][x].isMine) {
                    result.add(field[y][x]);
                }
            }
        }
        return result;
    }

    private List<Tile> getTilesForAutoFlagging(Tile tile) { // closed tiles from 3x3 area
        List<Tile> result = new ArrayList<>();
        for (int y = tile.y - 1; y <= tile.y + 1; y++) {
            for (int x = tile.x - 1; x <= tile.x + 1; x++) {
                if (y < 0 || y >= 10) {
                    continue;
                }
                if (x < 0 || x >= 10) {
                    continue;
                }
                if (!field[y][x].isOpen) {
                    result.add(field[y][x]);
                }
            }
        }
        return result;
    }


    // ITEM ACTIONS

    private boolean miniBombAction(int x, int y) {
        if (shopMiniBomb.isActivated) {
            shopMiniBomb.isActivated = false;
            shopScanner.count = 1; // allow to buy
            destroyTile(x, y);
            redrawAllTiles();
            return true;
        } else {
            return false;
        }
    }

    private boolean scannerAction(int x, int y) {
        if (shopScanner.isActivated) {
            shopScanner.isActivated = false;
            shopMiniBomb.count = 1; // allow to buy
            openRandomNeighbor(x, y);
            redrawAllTiles();
            return true;
        } else {
            return false;
        }
    }

    private void shieldAction(Tile cell) {
        countClosedTiles++; // revert opening, don't count opened mines
        cell.assignSprite(Bitmap.BOARD_MINE);
        cell.replaceColor(Color.YELLOW, 3);
        cell.draw();
        cell.drawSprite();
        cell.isShielded = true;
        shopShield.isActivated = false;
        int scoreBefore = score;
        score = Math.max(score - 150 * (difficulty / 5), 0);
        scoreLost -= scoreBefore - score;
        setScore(score);
    }


    // UTILITIES

    private void addScore() {
        openedCellsCount++; // for score detail
        scoreCell += difficulty;
        int scoreBeforeDice = score;
        score += difficulty * (shopLuckyDice.isActivated ? getRandomNumber(6) + 1 : 1);
        if (shopLuckyDice.isActivated) {
            scoreDice += (score - scoreBeforeDice) - difficulty;
        }
    }

    private void replantMine(Tile cell) {
        cell.eraseSprite();
        cell.isMine = false;
        plantMines();
        countMineNeighbors();
    }

    private void explodeAndGameOver(Tile cell) {
        revealAllMines();
        cell.replaceColor(Color.RED, 3); // highlight mine that caused game over
        cell.draw();
        cell.drawSprite();
        gameOver();
    }


    // VARIOUS CHECKS WITH CORRESPONDING ACTIONS

    private void checkVictory() {
        if (countClosedTiles == countMinesOnField) { // everything except bombs is open = win
            win();
        }
    }

    private boolean tileDestructionImpossible(Tile cell) {
        boolean activated = (cell.isOpen || cell.isDestroyed);
        boolean noFlagDestruction = (cell.isFlag && !allowFlagExplosion);
        return (isStopped || activated || noFlagDestruction);
    }

    private void countMoves() {
        if (allowCountMoves) { // counts moves only after physical click
            countMoves++;
        }
    }

    private void deactivateExpiredItems() {
        if (countMoves >= shopGoldenShovel.expireMove) {
            shopGoldenShovel.isActivated = false;
        }
        if (countMoves >= shopLuckyDice.expireMove) {
            shopLuckyDice.isActivated = false;
        }
    }


    // ANIMATIONS

    private void drawNumberOnCell(Tile cell) {
        if (!cell.isFlag && !cell.isMine) {
            cell.assignSprite(cell.countMineNeighbors);
        }
        cell.drawSprite();
    }

    private void pushTile(Tile tile) {
        tile.isOpen = true;
        tile.push();
        countClosedTiles--;
    }

    void redrawAllTiles() { // redraws all tiles in current state to hide whatever was drawn over them
        Tile cell;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                cell = field[y][x];
                cell.draw();
                if (cell.isOpen || cell.isFlag) {
                    cell.drawSprite();
                }
            }
        }
    }

    private void revealAllMines() {
        for (int posY = 0; posY < 10; posY++) {
            for (int posX = 0; posX < 10; posX++) {
                Tile showCell = field[posY][posX];
                if (showCell.isMine) {
                    showCell.isOpen = true;
                    showCell.assignSprite(Bitmap.BOARD_MINE);
                    showCell.push();
                }
            }
        }
    }


    // SHOP ACTIONS

    void buyFlag() {
        if (countMoney < shopFlag.cost || shopFlag.count <= 0) {
            return;
        }
        countFlags++;
        shopFlag.count--;
        countMoney -= shopFlag.cost;
        menu.displayShop();
    }

    void buyShield() {
        if (countMoney < shopShield.cost || shopShield.isActivated) {
            return;
        }
        shopShield.isActivated = true;
        countMoney -= shopShield.cost;
        menu.displayShop();
    }

    void buyScanner() {
        if (countMoney < shopScanner.cost || shopScanner.isActivated || shopMiniBomb.isActivated) {
            return;
        }
        shopScanner.isActivated = true;
        countMoney -= shopScanner.cost;
        shopMiniBomb.count = 0; // disallow buying
        Menu.IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).replaceColor(Color.BLUE, 3);
        Menu.IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).draw();
        menu.displayShop();
    }

    void buyGoldenShovel() {
        if (countMoney < shopGoldenShovel.cost || shopGoldenShovel.isActivated) {
            return;
        }
        shopGoldenShovel.isActivated = true;
        shopGoldenShovel.expireMove = countMoves + 5;
        countMoney -= shopGoldenShovel.cost;
        menu.displayShop();
    }

    void buyLuckyDice() {
        if (countMoney < shopLuckyDice.cost || shopLuckyDice.isActivated) {
            return;
        }
        shopLuckyDice.isActivated = true;
        shopLuckyDice.expireMove = countMoves + 3;
        countMoney -= shopLuckyDice.cost;
        menu.displayShop();
    }

    void buyMiniBomb() {
        if (countMoney < shopMiniBomb.cost || shopMiniBomb.isActivated || shopScanner.isActivated) {
            return;
        }
        shopMiniBomb.isActivated = true;
        countMoney -= shopMiniBomb.cost;
        shopScanner.count = 0; // disallow buying
        Menu.IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).replaceColor(Color.RED, 3);
        Menu.IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).draw();
        menu.displayShop();
    }


    // CONTROLS

    @Override
    public void onMouseLeftClick(int x, int y) {
        ie.leftClick(x, y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        ie.rightClick(x, y);
    }

    @Override
    public void onKeyPress(Key key) {
        ie.keyPress(key);
    }


    // GETTERS

    ShopItem getShopScanner() {
        return shopScanner;
    }

    LinkedList<ShopItem> getAllShopItems() {
        return allShopItems;
    }

    Menu getMenu() {
        return menu;
    }

    public Text getTextWriter() {
        return text_writer;
    }
}