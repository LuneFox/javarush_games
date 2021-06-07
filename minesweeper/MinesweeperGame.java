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
    int inventoryFlags;
    int inventoryMoney;
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

    public enum Filter {
        ALL, CLOSED, SAFE, MUST_CONTAIN_NUMBERS, FLAGGED_OR_REVEALED, MINED
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
        assignMineNumbersToCells();
        createShopItems();
        menu.displayGameBoard();
    }

    private void resetAllValues() {
        isStopped = false;
        isFirstMove = true;
        countMinesOnField = 0;
        inventoryMoney = 0;
        countMoves = 0;
        inventoryFlags = INITIAL_NUMBER_OF_FLAGS;
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
        score += (countMinesOnField * 20 * difficulty) + (inventoryMoney * difficulty);
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
        if (useMiniBomb(x, y) || useScanner(x, y)) {   // if bomb or scanner are active, do their actions instead
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
        inventoryMoney += cell.countMinedNeighbors * (shopGoldenShovel.isActivated() ? 2 : 1); // player gets gold
        addScore(clickedCell.x, clickedCell.y); // x and y of the clicked cell define where the dice will be drawn
        setScore(score);         // JavaRushTV score
        deactivateExpiredItems();
        checkVictory();

        isFirstMove = false; // protects from counting moves when opening cells in recursion
        recursiveOpenCell(cell); // if there are empty cells around, start over this opening process without counting moves
    }

    private void recursiveOpenCell(Cell cell) {
        // empty cells open cells near them, does not count as a move made by player
        if (cell.countMinedNeighbors == 0 && !cell.isMined) {
            allowCountMoves = false;
            getNeighborCells(cell, Filter.ALL, false).forEach(neighbor -> {
                if (!neighbor.isOpen) {
                    openCell(neighbor.x, neighbor.y);
                }
            });
        }
    }

    void openRest(int x, int y) {
        // attempts to open cells around if number of flags nearby equals the number on the cell
        if (shopScanner.isActivated() || shopMiniBomb.isActivated()) {
            // don't do anything if item usage is pending
            return;
        }
        Cell cell = field[y][x];
        if (cell.isOpen && !cell.isMined) {
            if (cell.countMinedNeighbors == getNeighborCells(cell, Filter.FLAGGED_OR_REVEALED, false).size()) {
                getNeighborCells(cell, Filter.ALL, false).forEach(neighbor -> openCell(neighbor.x, neighbor.y));
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
            shopFlag.restock(1);
            cell.isFlagged = false;
        }

        if (cell.isMined) { // recursive explosions
            cell.isMined = false;
            allowCountMoves = false;
            countMinesOnField--;
            allowFlagExplosion = true;
            getNeighborCells(cell, Filter.ALL, false).forEach(neighbor -> {
                if (neighbor.isMined) {
                    destroyCell(neighbor.x, neighbor.y); // recursive call
                }
            });
            assignMineNumbersToCells();
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
            inventoryFlags++;
            cell.eraseSprite();
        } else { // set
            if (inventoryFlags == 0) {
                // if there are no flags left (happens only in manual mode, scanner gives free flag before coming here)
                if (allowAutoBuyFlags) {
                    // buy flags automatically if you can
                    if (inventoryMoney < shopFlag.cost || shopFlag.getCount() <= 0) {
                        return;
                    }
                    inventoryFlags++;
                    shopFlag.sell();
                    inventoryMoney -= shopFlag.cost;
                } else {
                    // otherwise proceed to the shop and stop
                    menu.displayShop();
                    return;
                }
            }
            cell.isFlagged = true;
            inventoryFlags--;
            cell.assignSprite(Bitmap.BOARD_FLAG);
            cell.drawSprite();
        }
    }

    private boolean surviveMine(Cell cell) {        // did the player survive the mine?
        if (cell.isMined) {
            if (isFirstMove) {
                replantMine(cell);                  // mine was replanted on first move - YES
            } else if (shopShield.isActivated()) {
                useShield(cell);                 // shield has worked - YES
            } else {
                explodeAndGameOver(cell);           // nothing else saved the player - NO
                return false;
            }
        }
        return true;
    }

    private void openRandomNeighbor(int x, int y) {
        // scanner action
        List<Cell> neighbors = getNeighborCells(field[y][x], Filter.SAFE, true);
        if (neighbors.size() == 0) {
            // if there are no cell that scanner can open, it means that they're all mined
            // so we can start force-flagging them all
            getNeighborCells(field[y][x], Filter.CLOSED, true).forEach(closedCell -> {
                if (inventoryFlags == 0) { // ran out of flags? no problem, here are some freebies from the shop
                    shopFlag.sell();
                    inventoryFlags++;
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

    private List<Cell> getAllCells(Filter filter) {
        List<Cell> result = new ArrayList<>();
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Cell cell = field[y][x];
                switch (filter) {
                    case CLOSED:
                        if (!cell.isOpen) {
                            result.add(cell);
                        }
                        break;
                    case MINED:
                        if (cell.isMined) {
                            result.add(cell);
                        }
                        break;
                    case MUST_CONTAIN_NUMBERS:
                        if (!cell.isDestroyed && !cell.isFlagged && !cell.isMined) {
                            result.add(cell);
                        }
                        break;
                    case ALL:
                    default:
                        result.add(cell);
                        break;
                }
            }
        }
        return result;
    }

    private List<Cell> getNeighborCells(Cell cell, Filter filter, boolean includeSelf) {
        List<Cell> result = new ArrayList<>();
        for (int y = cell.y - 1; y <= cell.y + 1; y++) {
            for (int x = cell.x - 1; x <= cell.x + 1; x++) {
                if (y < 0 || y >= 10) continue;     // skip out of borders
                if (x < 0 || x >= 10) continue;     // skip out of borders
                if (field[y][x] == cell && !includeSelf) continue;  // skip center if not included
                switch (filter) {
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
                    case FLAGGED_OR_REVEALED:
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

    private void assignMineNumbersToCells() {
        getAllCells(Filter.MUST_CONTAIN_NUMBERS).forEach(cell -> {
            cell.countMinedNeighbors = getNeighborCells(cell, Filter.MINED, false).size();
            cell.assignSprite(cell.countMinedNeighbors);
        });
    }

    // ITEM ACTIONS

    private boolean useMiniBomb(int x, int y) {
        if (shopMiniBomb.isActivated()) {
            shopMiniBomb.deactivate();
            shopMiniBomb.restock(1);
            destroyCell(x, y);
            redrawAllCells();
            checkVictory();
            return true;
        } else {
            return false;
        }
    }

    private boolean useScanner(int x, int y) {
        if (shopScanner.isActivated()) {
            shopScanner.deactivate();
            shopScanner.restock(1);
            shopMiniBomb.restock(1);
            openRandomNeighbor(x, y);
            redrawAllCells();
            return true;
        } else {
            return false;
        }
    }

    private void useShield(Cell cell) {
        countMinesOnField--; // exploded mine isn't a mine anymore
        cell.assignSprite(Bitmap.BOARD_MINE);
        cell.replaceColor(Color.YELLOW, 3);
        cell.draw();
        cell.drawSprite();
        cell.isShielded = true;
        shopShield.deactivate();
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
        score += difficulty * (shopLuckyDice.isActivated() ? randomNumber : 1);
        if (shopLuckyDice.isActivated()) {
            scoreDice += (score - scoreBeforeDice) - difficulty;
        }
    }

    private void replantMine(Cell cell) {
        cell.eraseSprite();
        cell.isMined = false;
        countMinesOnField--;
        plantMines();
        assignMineNumbersToCells();
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
            dice.isHidden = shopMiniBomb.isActivated();
        }
    }


    // VARIOUS CHECKS WITH CORRESPONDING ACTIONS

    private void checkVictory() {
        // everything except bombs is open = win
        if (getAllCells(Filter.CLOSED).size() == countMinesOnField) {
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
            shopGoldenShovel.deactivate();
        }
        if (countMoves >= shopLuckyDice.expireMove) {
            shopLuckyDice.deactivate();
        }
    }

    // ANIMATIONS

    private void drawNumberOnCell(Cell cell) {
        if (!cell.isFlagged && !cell.isMined) {
            cell.assignSprite(cell.countMinedNeighbors);
            if (shopGoldenShovel.isActivated()) {
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
        getAllCells(Filter.ALL).forEach(cell -> {
            cell.draw();
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
                    showCell.assignSprite(Bitmap.BOARD_MINE);
                    showCell.push();
                }
            }
        }
    }


    // SHOP ACTIONS

    void buyFlag() {
        if (inventoryMoney < shopFlag.cost || shopFlag.getCount() <= 0) {
            return;
        }
        inventoryFlags++;
        shopFlag.sell();
        inventoryMoney -= shopFlag.cost;
        menu.displayShop();
    }

    void buyShield() {
        if (inventoryMoney < shopShield.cost || shopShield.isActivated()) {
            return;
        }
        shopShield.activate();
        inventoryMoney -= shopShield.cost;
        menu.displayShop();
    }

    void buyScanner() {
        if (inventoryMoney < shopScanner.cost || shopScanner.isActivated() || shopMiniBomb.isActivated()) {
            return;
        }
        shopScanner.activate();
        inventoryMoney -= shopScanner.cost;
        shopMiniBomb.removeFromShop();
        Menu.IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).replaceColor(Color.BLUE, 3);
        Menu.IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).draw();
        menu.displayShop();
    }

    void buyGoldenShovel() {
        if (inventoryMoney < shopGoldenShovel.cost || shopGoldenShovel.isActivated()) {
            return;
        }
        shopGoldenShovel.activate();
        shopGoldenShovel.expireMove = countMoves + 5;
        inventoryMoney -= shopGoldenShovel.cost;
        menu.displayShop();
    }

    void buyLuckyDice() {
        if (inventoryMoney < shopLuckyDice.cost || shopLuckyDice.isActivated()) {
            return;
        }
        shopLuckyDice.activate();
        shopLuckyDice.expireMove = countMoves + 3;
        inventoryMoney -= shopLuckyDice.cost;
        menu.displayShop();
    }

    void buyMiniBomb() {
        if (inventoryMoney < shopMiniBomb.cost || shopMiniBomb.isActivated() || shopScanner.isActivated()) {
            return;
        }
        shopMiniBomb.activate();
        inventoryMoney -= shopMiniBomb.cost;
        shopScanner.removeFromShop();
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