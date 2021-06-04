package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Picture;
import com.javarush.games.minesweeper.graphics.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Main game class
 */

public class MinesweeperGame extends Game {

    // FINAL OBJECTS
    public final Display display = new Display(this);
    final private Text text_writer = new Text(Bitmap.NONE, this);
    final private Menu menu = new Menu(this);
    final private InputEvent ie = new InputEvent(this);
    final Cell[][] field = new Cell[10][10];
    private final int INITIAL_NUMBER_OF_FLAGS = 3;

    // SHOP ITEMS
    private ShopItem shopShield;
    private ShopItem shopScanner;
    private ShopItem shopFlag;
    private ShopItem shopGoldenShovel;
    private ShopItem shopLuckyDice;
    private ShopItem shopMiniBomb;
    private final LinkedList<ShopItem> allShopItems = new LinkedList<>();

    // DICE DISPLAY
    private Dice dice;
    private Cell clickedCell;

    // GAME STATE
    int difficulty = 10;
    int difficultyInOptionsScreen = 10;
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
    int countOpenCells;

    // FLAGS
    private boolean isFirstMove;
    boolean isStopped;
    boolean lastResultIsVictory;
    boolean allowAutoBuyFlags;
    boolean allowCountMoves;
    boolean allowFlagExplosion;

    public enum CellFilterOption {
        ALL, CLOSED, SAFE, FLAGGED_OR_MINE_REVEALED, MINED
    }

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
        onTurnAction();
        display.draw();
    }

    private void onTurnAction() {
        // everything that happens with the flow of time on different screens
        switch (Screen.getType()) {
            case MAIN_MENU:
                menu.displayMain();
                break;
            case GAME_OVER:
                if (menu.gameOverDisplayDelay <= 0) {
                    menu.displayGameBoard();
                    menu.displayGameOver(lastResultIsVictory, 0);
                } else {
                    menu.gameOverDisplayDelay--;
                }
                break;
            case GAME_BOARD:
                menu.displayGameBoard();
                break;
            default:
                break;
        }
        displayDice();
    }

    void createGame() {
        resetAllValues();
        setScore(score); // score on JavaRush TV
        createField();
        plantMines();
        countMineNeighbors();
        createShopItems();
        menu.displayGameBoard();
    }

    private void resetAllValues() {
        isStopped = false;
        isFirstMove = true;
        countMinesOnField = 0;
        countMoney = 0;
        countMoves = 0;
        countFlags = INITIAL_NUMBER_OF_FLAGS;
        difficulty = difficultyInOptionsScreen;

        score = 0;
        scoreLost = 0;
        scoreDice = 0;
        scoreCell = 0;
        countOpenCells = 0;
    }

    private void loadResources() {
        text_writer.loadAlphabet();
        menu.loadImages();
        menu.loadButtons();
    }

    private void createField() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                field[y][x] = new Cell(Bitmap.CELL_CLOSED, this, x, y, false);
            }
        }
    }

    private void createShopItems() {
        this.shopShield = new ShopItem(0, 13 + difficulty / 5, 1,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_SHIELD), this);
        this.shopScanner = new ShopItem(1, 8 + difficulty / 5, 1,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_SCANNER), this);
        this.shopFlag = new ShopItem(2, 1, countMinesOnField - INITIAL_NUMBER_OF_FLAGS,
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
        this.dice = new Dice(1);
    }

    private void plantMines() {
        while (countMinesOnField < difficulty / 1.5) { // fixed number of mines on field
            int x = getRandomNumber(10);
            int y = getRandomNumber(10);
            if (!field[y][x].isMined && !field[y][x].isOpen) {
                field[y][x] = new Cell(Bitmap.CELL_CLOSED, this, x, y, true);
                countMinesOnField++;
            }
        }
    }


    // WIN AND LOSE

    private void lose() {
        lastResultIsVictory = false;
        isStopped = true;
        menu.displayGameOver(false, 30);
    }

    private void win() {
        lastResultIsVictory = true;
        score += (countMinesOnField * 20 * difficulty) + (countMoney * difficulty);
        setScore(score);
        registerTopScore();
        isStopped = true;
        menu.displayGameOver(true, 30);
    }

    private void registerTopScore() {
        if (score > topScore) {
            topScore = score;
            topScoreTitle = Menu.DIFFICULTY_NAMES.get(difficulty / 5 - 1);
        }
    }


    // ACTIVE CELL OPERATIONS

    void openCell(int x, int y) { // cell can be opened by mouse click or recursively
        if (miniBombAction(x, y) || scannerAction(x, y)) {   // if bomb or scanner are active, do their actions instead
            return;
        }

        Cell cell = field[y][x]; // locking on target cell

        if (allowCountMoves) {  // if the opening is performed by mouse (counts as a move)
            clickedCell = cell; // remember this cell as the one that the player interacted with
        }

        if (isStopped || cell.isFlagged || cell.isOpen) { // don't react if player clicks on flag or open cell
            return;
        }

        pushCell(cell); // flag as opened, push inside
        countMoves();   // count moves if opened not recursively (manually)

        if (!surviveMine(cell)) { // check if the player survived the mine, and stop doing things below if so
            return;
        }

        drawNumberOnCell(cell);  // show number since we know it's not a bomb (survived)
        countMoney += cell.countMineNeighbors * (shopGoldenShovel.isActivated ? 2 : 1); // player gets gold
        addScore(clickedCell.x, clickedCell.y); // x and y of the clicked cell define where the dice will be drawn
        setScore(score);         // JavaRushTV score
        deactivateExpiredItems();
        checkVictory();

        isFirstMove = false; // protects from counting moves when opening cells in recursion
        recursiveOpenCell(cell); // if there are empty cells around, start over this opening process without counting moves
    }

    private void recursiveOpenCell(Cell cell) {
        // empty cells open cells near them, does not count as a move made by player
        if (cell.countMineNeighbors == 0 && !cell.isMined) {
            allowCountMoves = false;
            getListOfNeighbors(cell, CellFilterOption.ALL).forEach(neighbor -> {
                if (!neighbor.isOpen) {
                    openCell(neighbor.x, neighbor.y);
                }
            });
        }
    }

    void openRest(int x, int y) {
        // attempts to open cells around if number of flags nearby equals the number on the cell
        if (shopScanner.isActivated || shopMiniBomb.isActivated) {
            // don't do anything if item usage is pending
            return;
        }
        Cell cell = field[y][x];
        if (cell.isOpen && !cell.isMined) {
            if (cell.countMineNeighbors == getListOfNeighbors(cell, CellFilterOption.FLAGGED_OR_MINE_REVEALED).size()) {
                getListOfNeighbors(cell, CellFilterOption.ALL).forEach(neighbor -> openCell(neighbor.x, neighbor.y));
            }
        }
    }

    private void destroyCell(int x, int y) {
        countMoves();
        Cell cell = field[y][x];

        if (cellDestructionImpossible(cell)) {
            return;
        }

        cell.assignSprite(Bitmap.BOARD_NONE);
        cell.isDestroyed = true;
        pushCell(cell);
        deactivateExpiredItems();

        if (cell.isFlagged) {
            shopFlag.count++;   // returning exploded flag to the shop
            cell.isFlagged = false;
        }

        if (cell.isMined) { // recursive explosions
            cell.isMined = false;
            allowCountMoves = false;
            countMinesOnField--;
            allowFlagExplosion = true;
            getListOfNeighbors(cell, CellFilterOption.ALL).forEach(neighbor -> {
                if (neighbor.isMined) {
                    destroyCell(neighbor.x, neighbor.y); // recursive call
                }
            });
            countMineNeighbors();
            redrawAllCells();
        }
    }

    void markCell(int x, int y, boolean isRevertible) { // sets or removes a flag
        if (isStopped) {
            return;
        }
        Cell cell = field[y][x];
        if (cell.isOpen) {
            return;
        }
        if (cell.isFlagged && isRevertible) { // remove
            cell.isFlagged = false;
            countFlags++;
            cell.eraseSprite();
        } else { // set
            if (countFlags == 0) {
                // if there are no flags left (happens only in manual mode, scanner gives free flag before coming here)
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
            cell.isFlagged = true;
            countFlags--;
            cell.assignSprite(Bitmap.BOARD_FLAG);
            cell.drawSprite();
        }
    }

    private boolean surviveMine(Cell cell) {        // did the player survive the mine?
        if (cell.isMined) {
            if (isFirstMove) {
                replantMine(cell);                  // mine was replanted on first move - YES
            } else if (shopShield.isActivated) {
                shieldAction(cell);                 // shield has worked - YES
            } else {
                explodeAndGameOver(cell);           // nothing else saved the player - NO
                return false;
            }
        }
        return true;
    }

    private void openRandomNeighbor(int x, int y) {
        // scanner action
        List<Cell> neighbors = getListOfNeighbors(field[y][x], CellFilterOption.SAFE);
        if (neighbors.size() == 0) {
            // if there are no cell that scanner can open, it means that they're all mined
            // so we can start force-flagging them all
            getListOfNeighbors(field[y][x], CellFilterOption.CLOSED).forEach(closedCell -> {
                if (countFlags == 0) { // ran out of flags? no problem, here are some freebies from the shop
                    shopFlag.count--;
                    countFlags++;
                }
                // force a flag mark to the cell, un-marking not allowed
                markCell(closedCell.x, closedCell.y, false);
            });
            return;
        }
        Cell cell = neighbors.get(getRandomNumber(neighbors.size()));
        cell.isScanned = true;
        if (cell.isFlagged) {
            // remove flag if it was placed wrong
            markCell(cell.x, cell.y, true);
        }
        openCell(cell.x, cell.y);
    }


    // COLLECTING SURROUNDING CELLS & INFO

    private void countMineNeighbors() { // counts mines in surrounding cells
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Cell cell = field[y][x];
                cell.countMineNeighbors = 0;
                if (!cell.isMined) {
                    getListOfNeighbors(cell, CellFilterOption.ALL).forEach(neighbor -> {
                        if (neighbor.isMined) {
                            cell.countMineNeighbors++;
                        }
                    });
                    if (!cell.isDestroyed && !cell.isFlagged) {
                        cell.assignSprite(cell.countMineNeighbors);
                    }
                }
            }
        }
    }

    private List<Cell> getListOfNeighbors(Cell cell, CellFilterOption cellFilterOption) {
        List<Cell> result = new ArrayList<>();
        for (int y = cell.y - 1; y <= cell.y + 1; y++) {
            for (int x = cell.x - 1; x <= cell.x + 1; x++) {
                if (y < 0 || y >= 10) { // skip out of borders
                    continue;
                }
                if (x < 0 || x >= 10) { // skip out of borders
                    continue;
                }
                if (field[y][x] == cell) { // skip center
                    continue;
                }
                switch (cellFilterOption) {
                    case SAFE: // safe to open: closed and not mined
                        if (!field[y][x].isOpen && !field[y][x].isMined) {
                            result.add(field[y][x]);
                        }
                        break;
                    case CLOSED:
                        if (!field[y][x].isOpen) {
                            result.add(field[y][x]);
                        }
                        break;
                    case FLAGGED_OR_MINE_REVEALED:
                        if (field[y][x].isFlagged || (field[y][x].isOpen && field[y][x].isMined)) {
                            result.add(field[y][x]);
                        }
                        break;
                    case MINED:
                        if (field[y][x].isMined) {
                            result.add(field[y][x]);
                        }
                        break;
                    case ALL:
                    default:
                        result.add(field[y][x]);
                        break;
                }

            }
        }
        return result;
    }

    private int countClosedCells() {
        int count = 0;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (!field[y][x].isOpen) {
                    count++;
                }
            }
        }
        return count;
    }


    // ITEM ACTIONS

    private boolean miniBombAction(int x, int y) {
        if (shopMiniBomb.isActivated) {
            shopMiniBomb.isActivated = false;
            shopScanner.count = 1; // allow to buy
            destroyCell(x, y);
            redrawAllCells();
            checkVictory();
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
            redrawAllCells();
            return true;
        } else {
            return false;
        }
    }

    private void shieldAction(Cell cell) {
        countMinesOnField--; // exploded mine isn't a mine anymore
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

    private void addScore(int x, int y) {
        countOpenCells++; // for score detail
        scoreCell += difficulty;
        int scoreBeforeDice = score;
        int randomNumber = getRandomNumber(6) + 1;
        this.dice.setImage(randomNumber, x, y);
        score += difficulty * (shopLuckyDice.isActivated ? randomNumber : 1);
        if (shopLuckyDice.isActivated) {
            scoreDice += (score - scoreBeforeDice) - difficulty;
        }
    }

    private void replantMine(Cell cell) {
        cell.eraseSprite();
        cell.isMined = false;
        countMinesOnField--;
        plantMines();
        countMineNeighbors();
    }

    private void explodeAndGameOver(Cell cell) {
        revealAllMines();
        cell.replaceColor(Color.RED, 3); // highlight mine that caused game over
        cell.draw();
        cell.drawSprite();
        dice.isHidden = true;
        lose();
    }

    private void displayDice() {
        int diceTurns;
        if (shopLuckyDice == null) {
            diceTurns = -1;
        } else {
            diceTurns = shopLuckyDice.expireMove - countMoves;
        }
        if (diceTurns > -1 && diceTurns < 3 && countMoves != 0) {
            dice.draw();
        }
    }

    void hideDice() {
        if (shopMiniBomb != null) {
            dice.isHidden = shopMiniBomb.isActivated;
        }
    }


    // VARIOUS CHECKS WITH CORRESPONDING ACTIONS

    private void checkVictory() {
        // everything except bombs is open = win
        if (countClosedCells() == countMinesOnField) {
            win();
        }
    }

    private boolean cellDestructionImpossible(Cell cell) {
        boolean activated = (cell.isOpen || cell.isDestroyed);
        boolean noFlagDestruction = (cell.isFlagged && !allowFlagExplosion);
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

    private void drawNumberOnCell(Cell cell) {
        if (!cell.isFlagged && !cell.isMined) {
            cell.assignSprite(cell.countMineNeighbors);
            if (shopGoldenShovel.isActivated) {
                cell.changeSpriteColor(Color.YELLOW);
            }
        }
        cell.drawSprite();
    }

    private void pushCell(Cell cell) {
        cell.isOpen = true;
        cell.push();
    }

    void redrawAllCells() { // redraws all cells in current state to hide whatever was drawn over them
        Cell cell;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                cell = field[y][x];
                cell.draw();
                if (cell.isOpen || cell.isFlagged) {
                    cell.drawSprite();
                }
            }
        }
    }

    private void revealAllMines() {
        for (int posY = 0; posY < 10; posY++) {
            for (int posX = 0; posX < 10; posX++) {
                Cell showCell = field[posY][posX];
                if (showCell.isMined) {
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
        ie.keyPressAction(key);
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