package com.javarush.games.minesweeper;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Picture;
import com.javarush.games.minesweeper.graphics.Text;
import com.javarush.games.minesweeper.screens.Screen;
import com.javarush.games.minesweeper.screens.ScreenType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Main game class
 */

public class MinesweeperGame extends Game {
    static final String VERSION = "1.03";
    final private Text TEXT_WRITER = new Text(Bitmap.NONE, this);
    final private Menu MENU = new Menu(this);
    final private InputEvent IE = new InputEvent(this);
    final Tile[][] GAME_TILES = new Tile[10][10];
    private ShopItem shopShield;
    private ShopItem shopScanner;
    private ShopItem shopFlag;
    private ShopItem shopGoldenShovel;
    private ShopItem shopLuckyDice;
    private ShopItem shopMiniBomb;
    private LinkedList<ShopItem> allShopItems = new LinkedList<>();
    private int countClosedTiles = 100;
    private boolean isFirstMove;
    int difficulty = 10;
    int difficultySetting = 10;
    boolean autoBuyFlags;
    private int initialFlags = 3;
    int countMinesOnField;
    int countFlagsInInventory;
    int countMoney;
    int countMoves;
    int score;
    int scoreLost;
    int scoreDice;
    int scoreCell;
    int openedCellCount;
    int scoreTop = 0;
    String topScoreTitle = "";
    boolean isStopped;
    boolean lastResultIsVictory;
    boolean allowCountMoves;
    boolean flagExplosionPossible;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(100, 100);
        loadResources();
        isStopped = true;
        MENU.displayMain();
    }

    private void loadResources() {
        TEXT_WRITER.loadAlphabet();
        MENU.loadImages();
        MENU.loadButtons();
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        IE.leftClick(x, y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        IE.rightClick(x, y);
    }

    @Override
    public void onKeyPress(Key key) {
        IE.keyPress(key);
    }

    // CREATING AND RESETTING THE GAME

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
        openedCellCount = 0;
        countFlagsInInventory = initialFlags;
        difficulty = difficultySetting;

        // create assets
        setScore(score);
        createField();
        plantMines();
        countMineNeighbors();
        createShopItems();
        Screen.set(ScreenType.GAME_BOARD);
    }

    // WIN AND LOSE SCENARIOS

    private void gameOver() {
        lastResultIsVictory = false;
        isStopped = true;
        MENU.displayGameOver(false);

    }

    private void win() {
        lastResultIsVictory = true;
        score += (countMinesOnField * 20 * difficulty) + (countMoney * difficulty);
        setScore(score);
        if (score > scoreTop) {
            scoreTop = score;
            topScoreTitle = Menu.TITLE_NAMES.get(difficulty / 5 - 1);
        }
        isStopped = true;
        MENU.displayGameOver(true);
    }

    // GAME MECHANICS

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
                if (GAME_TILES[y][x] == tile) {
                    continue;
                }
                result.add(GAME_TILES[y][x]);
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
                if (GAME_TILES[y][x] == tile) {
                    continue;
                }
                if (GAME_TILES[y][x].isFlag || (GAME_TILES[y][x].isOpen && GAME_TILES[y][x].isMine)) {
                    result.add(GAME_TILES[y][x]);
                }
            }
        }
        return result;

    }

    private List<Tile> getTilesForScanning(Tile tile) { // get closed not mined tiles from a 3x3 area
        List<Tile> result = new ArrayList<>();
        for (int y = tile.y - 1; y <= tile.y + 1; y++) {
            for (int x = tile.x - 1; x <= tile.x + 1; x++) {
                if (y < 0 || y >= 10) {
                    continue;
                }
                if (x < 0 || x >= 10) {
                    continue;
                }
                if (!GAME_TILES[y][x].isOpen && !GAME_TILES[y][x].isMine) {
                    result.add(GAME_TILES[y][x]);
                }
            }
        }
        return result;
    }

    private List<Tile> getTilesForFlagging(Tile tile) { // get closed tiles from a 3x3 area
        List<Tile> result = new ArrayList<>();
        for (int y = tile.y - 1; y <= tile.y + 1; y++) {
            for (int x = tile.x - 1; x <= tile.x + 1; x++) {
                if (y < 0 || y >= 10) {
                    continue;
                }
                if (x < 0 || x >= 10) {
                    continue;
                }
                if (!GAME_TILES[y][x].isOpen) {
                    result.add(GAME_TILES[y][x]);
                }
            }
        }
        return result;
    }

    private void countMineNeighbors() { // counts mines in surrounding tiles
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Tile cell = GAME_TILES[y][x];
                cell.countMineNeighbors = 0;
                if (!cell.isMine) {
                    List<Tile> neighbors = getNeighbors(cell);
                    for (Tile neighbor : neighbors) {
                        if (neighbor.isMine) {
                            cell.countMineNeighbors++;
                        }
                    }
                    if (!cell.isDestroyed && !cell.isFlag) {
                        cell.assignSprite(cell.countMineNeighbors);
                    }
                }
            }
        }
    }

    void openTile(int x, int y) { // mouse click or recursive
        if (miniBombAction(x, y) || scannerAction(x, y)) {
            return;
        }
        Tile cell = GAME_TILES[y][x];
        if (isStopped || cell.isFlag || cell.isOpen) {
            return;
        }
        pushCell(cell);
        countMoves();
        if (!surviveMine(cell)) {
            return;
        }
        drawNumberOnCell(cell);
        countMoney += cell.countMineNeighbors * (shopGoldenShovel.isActivated ? 2 : 1);
        addScore();
        setScore(score);
        deactivateExpiredItems();
        checkVictory();
        isFirstMove = false; // first move ends here, doesn't affect recursion
        recursiveOpen(cell);
    }

    private void recursiveOpen(Tile cell) {
        if (cell.countMineNeighbors == 0 && !cell.isMine) {
            allowCountMoves = false;
            List<Tile> neighbors = getNeighbors(cell);
            for (Tile neighbor : neighbors) {
                if (!neighbor.isOpen) {
                    openTile(neighbor.x, neighbor.y);
                }
            }
        }
    }

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
        countClosedTiles++; // don't count as open, even though it's physically open
        cell.assignSprite(Bitmap.BOARD_MINE);
        cell.replaceColor(Color.YELLOW, 3); // highlight mine that was blocked
        cell.draw();
        cell.drawSprite();
        cell.isShielded = true;
        shopShield.isActivated = false;
        int scoreBefore = score;
        score = Math.max(score - 150 * (difficulty / 5), 0);
        scoreLost -= scoreBefore - score;
        setScore(score);
    }

    private boolean surviveMine(Tile cell) {
        if (cell.isMine) {
            if (isFirstMove) {
                replantMine(cell);
            } else if (shopShield.isActivated) {
                shieldAction(cell);
            } else {
                triggerGameOver(cell);
                return false;
            }
        }
        return true;
    }

    private void addScore() {
        openedCellCount++; // for score detail
        scoreCell += difficulty;
        int scoreBeforeDice = score;
        score += difficulty * (shopLuckyDice.isActivated ? getRandomNumber(6) + 1 : 1);
        if (shopLuckyDice.isActivated) {
            scoreDice += (score - scoreBeforeDice) - difficulty;
        }
    }

    private void drawNumberOnCell(Tile cell) {
        if (!cell.isFlag && !cell.isMine) {
            cell.assignSprite(cell.countMineNeighbors);
        }
        cell.drawSprite();
    }

    private void replantMine(Tile cell) {
        cell.eraseSprite();
        cell.isMine = false;
        plantMines();
        countMineNeighbors();
    }

    private void triggerGameOver(Tile cell) {
        revealAllMines();
        cell.replaceColor(Color.RED, 3); // highlight mine that caused game over
        cell.draw();
        cell.drawSprite();
        gameOver();
    }

    void openRest(int x, int y) {
        Tile cell = GAME_TILES[y][x];
        if (!cell.isOpen || cell.isMine) {
            return;
        }
        List<Tile> flaggedNeighbors = getFlaggedNeighbors(cell);
        if (cell.countMineNeighbors == flaggedNeighbors.size()) {
            List<Tile> neighbors = getNeighbors(cell);
            for (Tile tile : neighbors) {
                openTile(tile.x, tile.y);
            }
        }
    }

    void markTile(int x, int y) { // sets or removes a flag
        if (isStopped) {
            return;
        }
        Tile cell = GAME_TILES[y][x];
        if (cell.isOpen) {
            return;
        }
        if (cell.isFlag) { // remove
            cell.isFlag = false;
            countFlagsInInventory++;
            cell.eraseSprite();
        } else { // set
            if (countFlagsInInventory == 0) {
                if (autoBuyFlags) {
                    if (countMoney < shopFlag.cost || shopFlag.count <= 0) {
                        return;
                    }
                    countFlagsInInventory++;
                    shopFlag.count--;
                    countMoney -= shopFlag.cost;
                } else {
                    MENU.displayShop();
                    return;
                }
            }
            cell.isFlag = true;
            countFlagsInInventory--;
            cell.assignSprite(Bitmap.BOARD_FLAG);
            cell.drawSprite();
        }
    }

    private void destroyTile(int x, int y) {
        countMoves();
        Tile cell = GAME_TILES[y][x];

        if (tileDestructionImpossible(cell)) {
            return;
        }

        cell.assignSprite(Bitmap.BOARD_NONE);
        cell.isDestroyed = true;
        pushCell(cell);
        deactivateExpiredItems();

        if (cell.isFlag) {
            shopFlag.count++;   // returning exploded flag to the shop
            cell.isFlag = false;
        }
        if (cell.isMine) { // recursive explosions
            cell.isMine = false;
            allowCountMoves = false;
            countMinesOnField--;
            flagExplosionPossible = true;
            List<Tile> neighbors = getNeighbors(cell);
            for (Tile neighbor : neighbors) { // recursion
                if (neighbor.isMine) {
                    destroyTile(neighbor.x, neighbor.y);
                }
            }
            countMineNeighbors();
            redrawAllTiles();
            if (countClosedTiles == countMinesOnField) {
                win();
            }
        }
    }

    private boolean tileDestructionImpossible(Tile cell) {
        boolean activated = (cell.isOpen || cell.isDestroyed);
        boolean noFlagDestruction = (cell.isFlag && !flagExplosionPossible);
        return (isStopped || activated || noFlagDestruction);
    }

    private void countMoves() {
        if (allowCountMoves) { // counts moves only after physical click
            countMoves++;
        }
    }

    private void checkVictory() {
        if (countClosedTiles == countMinesOnField) { // everything except bombs is open = win
            win();
        }
    }

    private void pushCell(Tile cell) {
        cell.isOpen = true;
        cell.push();
        countClosedTiles--;
    }

    private void deactivateExpiredItems() {
        if (countMoves >= shopGoldenShovel.expireMove) {
            shopGoldenShovel.isActivated = false;
        }
        if (countMoves >= shopLuckyDice.expireMove) {
            shopLuckyDice.isActivated = false;
        }
    }

    private void forceMarkTile(int x, int y) { // doesn't unmark if it's already a flag
        if (isStopped) {
            return;
        }
        Tile cell = GAME_TILES[y][x];
        if (cell.isOpen) {
            return;
        }
        if (!cell.isFlag) { // set
            if (countFlagsInInventory == 0) {
                return;
            }
            cell.isFlag = true;
            countFlagsInInventory--;
            cell.assignSprite(Bitmap.BOARD_FLAG);
            cell.drawSprite();
        }
    }

    private void openRandomNeighbor(int x, int y) {
        List<Tile> neighbors = getTilesForScanning(GAME_TILES[y][x]);
        if (neighbors.size() == 0) {
            List<Tile> neighborsToMark = getTilesForFlagging(GAME_TILES[y][x]);
            for (Tile tile : neighborsToMark) {
                if (countFlagsInInventory == 0) {
                    shopFlag.count--;
                    countFlagsInInventory++;   // free flags, yay!
                }
                forceMarkTile(tile.x, tile.y); // doesn't unmark what was already flagged
            }
            return;
        }
        Tile cell = neighbors.get(getRandomNumber(neighbors.size()));
        cell.isScanned = true;
        if (cell.isFlag) {
            markTile(cell.x, cell.y);
        } // remove wrong flag
        openTile(cell.x, cell.y);
    }

    void redrawAllTiles() { // redraws all tiles in current state to hide whatever was drawn over them
        Tile cell;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                cell = GAME_TILES[y][x];
                cell.draw();
                if (cell.isOpen || cell.isFlag) {
                    cell.drawSprite();
                }
            }
        }
    }

    private void createField() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                GAME_TILES[y][x] = new Tile(Bitmap.TILE_CLOSED, this, x, y, false);
            }
        }
    }

    private void createShopItems() {
        this.shopShield = new ShopItem(0, 13 + difficulty / 5, 1,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_SHIELD), this);
        this.shopScanner = new ShopItem(1, 8 + difficulty / 5, 1,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_SCANNER), this);
        this.shopFlag = new ShopItem(2, 1, countMinesOnField - initialFlags,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_FLAG), this);
        this.shopGoldenShovel = new ShopItem(3, 9, 1,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_SHOVEL), this);
        this.shopLuckyDice = new ShopItem(4, 6, 1,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_DICE), this);
        this.shopMiniBomb = new ShopItem(5, 6 + difficulty / 10, 1,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_BOMB), this);
        this.allShopItems.clear();
        this.allShopItems.addAll(Arrays.asList(
                shopShield,
                shopScanner,
                shopFlag,
                shopGoldenShovel,
                shopLuckyDice,
                shopMiniBomb
        ));
    }

    private void plantMines() {
        while (countMinesOnField < difficulty / 1.5) { // fixed number of mines on field
            int x = getRandomNumber(10);
            int y = getRandomNumber(10);
            if (!GAME_TILES[y][x].isMine && !GAME_TILES[y][x].isOpen) {
                GAME_TILES[y][x] = new Tile(Bitmap.TILE_CLOSED, this, x, y, true);
                countMinesOnField++;
            }
        }
    }

    private void revealAllMines() {
        for (int posY = 0; posY < 10; posY++) {
            for (int posX = 0; posX < 10; posX++) {
                Tile showCell = GAME_TILES[posY][posX];
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
        countFlagsInInventory++;
        shopFlag.count--;
        countMoney -= shopFlag.cost;
        MENU.displayShop();
    }

    void buyShield() {
        if (countMoney < shopShield.cost || shopShield.isActivated) {
            return;
        }
        shopShield.isActivated = true;
        countMoney -= shopShield.cost;
        MENU.displayShop();
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
        MENU.displayShop();
    }

    void buyGoldenShovel() {
        if (countMoney < shopGoldenShovel.cost || shopGoldenShovel.isActivated) {
            return;
        }
        shopGoldenShovel.isActivated = true;
        shopGoldenShovel.expireMove = countMoves + 5;
        countMoney -= shopGoldenShovel.cost;
        MENU.displayShop();
    }

    void buyLuckyDice() {
        if (countMoney < shopLuckyDice.cost || shopLuckyDice.isActivated) {
            return;
        }
        shopLuckyDice.isActivated = true;
        shopLuckyDice.expireMove = countMoves + 3;
        countMoney -= shopLuckyDice.cost;
        MENU.displayShop();
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
        MENU.displayShop();
    }

    // GETTERS

    ShopItem getShopScanner() {
        return shopScanner;
    }

    LinkedList<ShopItem> getAllShopItems() {
        return allShopItems;
    }

    Menu getMenu() {
        return MENU;
    }

    Text getTextWriter() {
        return TEXT_WRITER;
    }
}