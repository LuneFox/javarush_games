package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Printer;

import java.util.ArrayList;
import java.util.List;

/**
 * Main game class
 */

public class MinesweeperGame extends Game {

    // FINAL OBJECTS
    public final Printer printer = new Printer(this);
    public final Display display = new Display(this);
    public final Menu menu = new Menu(this);
    public final Inventory inventory = new Inventory();
    public final Shop shop = new Shop(this);
    public final Player player = new Player(this);
    public final Cell[][] field = new Cell[10][10];
    final private InputEvent ie = new InputEvent(this);

    // GAME STATE
    public static int shieldPenalty;
    int difficulty = 10;
    int difficultyInOptionsScreen = 10;
    int countMinesOnField;

    // FLAGS
    private boolean isFirstMove;
    boolean isStopped;
    boolean lastResultIsVictory;
    boolean optionAutoBuyFlagsOn;
    boolean allowCountMoves; // user clicked with mouse = not recursive action = allow to count as a move
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
        createField();
        plantMines();
        assignMineNumbersToCells();
        resetValues();
        menu.displayGameBoard();
        setScore(player.score);
    }

    private void resetValues() {
        isStopped = false;
        isFirstMove = true;
        difficulty = difficultyInOptionsScreen;
        player.reset();
        shop.reset();
        inventory.reset();
        shieldPenalty = (difficulty / 5);
        System.out.println(shieldPenalty);
    }

    private void loadResources() {
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

    private void plantMines() {
        countMinesOnField = 0;
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
        player.score += (countMinesOnField * 20 * difficulty) + (inventory.money * difficulty);
        setScore(player.score);
        player.registerTopScore();
        isStopped = true;
        menu.displayGameOver(true, 30);
    }


    // ACTIVE CELL OPERATIONS

    void openCell(int x, int y) {
        Cell cell = field[y][x];
        if (shop.miniBomb.use(cell) || shop.scanner.use(cell)) {
            return;
        }

        if (allowCountMoves) { // non-recursive (manual) openCell
            shop.dice.cell = cell;
        }

        if (isStopped || cell.isFlagged || cell.isOpen) {
            return;
        }

        pushCell(cell);
        countMoves();   // count moves if opened not recursively (manually)

        if (!surviveMine(cell)) { // check if the player survived the mine, and stop doing things below if so
            return;
        }

        drawNumberOnCell(cell);  // show number since we know it's not a bomb (survived)
        inventory.money += cell.countMinedNeighbors * (shop.goldenShovel.isActivated() ? 2 : 1); // player gets gold
        addScore(shop.dice.cell.x, shop.dice.cell.y); // x and y of the clicked cell define where the dice will be drawn
        setScore(player.score);         // JavaRushTV score
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
        if (shop.scanner.isActivated() || shop.miniBomb.isActivated()) {
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

    public void destroyCell(int x, int y) {
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
            shop.restock(shop.flag, 1);
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

    void setFlag(int x, int y, boolean canRemove) {
        if (isStopped) return;
        Cell cell = field[y][x];
        if (cell.isOpen) return;

        if (cell.isFlagged && canRemove) {
            inventory.add(ShopItem.ID.FLAG);
            cell.isFlagged = false;
            cell.eraseSprite();
        } else {
            if (inventory.getCount(ShopItem.ID.FLAG) == 0) { // IN CASE OF NO FLAGS IN THE INVENTORY:
                if (optionAutoBuyFlagsOn) {                  // if auto-buy is on
                    if (shop.flag.isUnobtainable()) {        //    but you can't buy
                        return;                              //        do nothing and return
                    }                                        //    but if you can
                    shop.sell(shop.flag);                    //        shop sells you one
                } else {                                     // if auto-buy is off,
                    menu.displayShop();                      //    proceed to shop
                    return;
                }
            }

            if (!cell.isFlagged) { // don't do anything to cells that have flags
                inventory.remove(ShopItem.ID.FLAG);
                cell.isFlagged = true;
                cell.assignSprite(Bitmap.BOARD_FLAG);
                cell.drawSprite();
            }
        }
    }

    private boolean surviveMine(Cell cell) {        // did the player survive the mine?
        if (cell.isMined) {
            if (isFirstMove) {
                replantMine(cell);                  // mine was replanted on first move - YES
            } else if (shop.shield.isActivated()) {
                shop.shield.use(cell);              // shield has worked - YES
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
                if (inventory.getCount(ShopItem.ID.FLAG) == 0) { // get a free flag from the shop
                    shop.give(shop.flag);
                }
                setFlag(closedCell.x, closedCell.y, false); // set flag forcefully
            });
            return;
        }
        Cell cell = neighbors.get(getRandomNumber(neighbors.size()));
        cell.isScanned = true;
        if (cell.isFlagged) {
            // remove flag if it was placed wrong
            setFlag(cell.x, cell.y, true);
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

    // UTILITIES

    private void addScore(int x, int y) {
        player.openCells++; // for score detail
        player.scoreCell += difficulty;
        int scoreBeforeDice = player.score;
        int randomNumber = getRandomNumber(6) + 1;
        shop.dice.setImage(randomNumber, x, y);
        player.score += difficulty * (shop.luckyDice.isActivated() ? randomNumber : 1);
        if (shop.luckyDice.isActivated()) {
            player.scoreDice += (player.score - scoreBeforeDice) - difficulty;
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
        shop.dice.isHidden = true;
        lose();
    }

    private void displayDice() {
        int diceTurns;
        if (shop.luckyDice == null) {
            diceTurns = -1;
        } else {
            diceTurns = shop.luckyDice.expireMove - player.countMoves;
        }
        if (diceTurns > -1 && diceTurns < 3 && player.countMoves != 0) {
            shop.dice.draw();
        }
    }

    void hideDice() {
        if (shop.miniBomb != null) {
            shop.dice.isHidden = shop.miniBomb.isActivated();
        }
    }


    // VARIOUS CHECKS WITH CORRESPONDING ACTIONS

    public void checkVictory() {
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
        if (allowCountMoves) {
            player.countMoves++;
        }
    }

    private void deactivateExpiredItems() {
        shop.goldenShovel.expireCheck();
        shop.luckyDice.expireCheck();
    }

    // ANIMATIONS

    private void drawNumberOnCell(Cell cell) {
        if (!cell.isFlagged && !cell.isMined) {
            cell.assignSprite(cell.countMinedNeighbors);
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

    // TEXT OUTPUT

    public void out(String text, Color color, int x, int y, boolean right) {
        printer.out(text, color, x, y, right);
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
}