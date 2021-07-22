package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Printer;
import com.javarush.games.minesweeper.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main game class
 */

public class MinesweeperGame extends Game {

    // FINAL OBJECTS
    public final Printer printer = new Printer(this);
    public final Display display = new Display(this);
    public final View view = new View(this);
    public final Inventory inventory = new Inventory();
    public final Shop shop = new Shop(this);
    public final Player player = new Player(this);
    public final Timer timer = new Timer(this);
    public final Cell[][] field = new Cell[10][10];
    private final InputEvent ie = new InputEvent(this);

    // GAME STATE
    public static int staticDifficulty;
    public int difficulty = 10;
    public int difficultyInOptionsScreen = 10;

    // FLAGS
    public boolean evenTurn; // is now even turn or odd turn? helps with animation of certain elements
    private boolean isFirstMove;
    public boolean allowCountMoves; // user clicked with mouse = not recursive action = allow to count as a move
    public boolean allowFlagExplosion;
    public boolean lastResultIsVictory;
    public boolean isStopped;

    public enum Filter {CLOSED, DANGEROUS, MINED, NONE, NUMERABLE, OPEN, SAFE, SUSPECTED}

    // NEW GAME

    @Override
    public void initialize() {
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
        switch (Screen.getType()) {
            case MAIN_MENU:
                View.main.display();
                break;
            case GAME_OVER:
                if (View.gameOver.displayDelay <= 0) {
                    View.board.display();
                    View.gameOver.display(lastResultIsVictory, 0);
                } else {
                    View.gameOver.displayDelay--;
                }
                break;
            case GAME_BOARD:
                if (timeOutCheck()) return;
                View.board.display();
                break;
            case SHOP:
                if (timeOutCheck()) return;
                View.shop.display();
                break;
            default:
                break;
        }
        displayDice();
    }

    void createGame() {
        applyOptions();    // difficulty impacts the number of mines created below
        createField();
        plantMines();      // number of mines define the number of flags given below
        resetValues();
        assignMineNumbersToCells();
        View.board.display();
        setScore(player.score);
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
        staticDifficulty = difficultyInOptionsScreen;
        difficulty = difficultyInOptionsScreen;
        timer.isOn = timer.optionIsOn;
    }

    public static int getDifficulty() {
        return staticDifficulty;
    }

    private void createField() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                field[y][x] = new Cell(Bitmap.CELL_CLOSED, this, x, y, false);
            }
        }
    }

    private void plantMines() {
        while (countAllCells(Filter.MINED) < difficulty / 1.5) { // fixed number of mines on field
            int x = getRandomNumber(10);
            int y = getRandomNumber(10);
            if (!field[y][x].isMined && !field[y][x].isOpen)
                field[y][x] = new Cell(Bitmap.CELL_CLOSED, this, x, y, true);
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
        player.score += (countAllCells(Filter.MINED) * 20 * difficulty) + (inventory.money * difficulty);
        setScore(player.score);
        player.registerTopScore();
        isStopped = true;
        View.gameOver.display(true, 30);
    }

    private boolean timeOutCheck() {
        if (timer.isZero() && !isStopped) {
            View.board.display();
            revealAllMines();
            lose();
            View.gameOver.display(false, 30);
            return true;
        }
        if (!isStopped) {
            timer.countDown();
        }
        return false;
    }


    // ACTIVE CELL OPERATIONS

    void openCell(int x, int y) {
        Cell cell = field[y][x];

        if (shop.miniBomb.use(cell) || shop.scanner.use(cell)) return;
        if (allowCountMoves) shop.dice.cell = cell;
        if (isStopped || cell.isFlagged || cell.isOpen) return;

        pushCell(cell);                 // change visuals, set isOpen flag
        onManualClick();                // do things that happen during real click only
        if (!surviveMine(cell)) return; // stop processing if the player didn't survive
        drawNumberOnCell(cell);         // show number since we know it's not a bomb (survived)

        player.openedCells++;
        inventory.money += cell.countMinedNeighbors * (shop.goldenShovel.isActivated() ? 2 : 1); // player gets gold
        addScore(shop.dice.cell.x, shop.dice.cell.y); // cell.x, cell.y = dice display position
        setScore(player.score);                       // JavaRushTV
        deactivateExpiredItems();

        checkVictory();
        isFirstMove = false;
        recursiveOpenEmpty(cell);                  // for surrounding empty cells, moved don't count
    }

    private void recursiveOpenEmpty(Cell cell) {
        allowCountMoves = false;               // automatic opening doesn't count as a player move
        if (cell.isEmpty()) {
            getNeighborCells(cell, Filter.NONE, false).forEach(neighbor -> openCell(neighbor.x, neighbor.y));
        }
    }

    void openRest(int x, int y) {
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
            allowFlagExplosion = true;
            getNeighborCells(cell, Filter.NONE, false).forEach(neighbor -> {
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
                if (shop.autoBuyFlagsOptionOn) {             // if auto-buy is on
                    if (shop.flag.isUnobtainable()) {        //    but you can't buy
                        return;                              //        do nothing and return
                    }                                        //    but if you can
                    shop.sell(shop.flag);                    //        shop sells you one
                } else {                                     // if auto-buy is off,
                    View.shop.display();                     //    proceed to shop
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
                if (inventory.getCount(ShopItem.ID.FLAG) == 0) {
                    shop.give(shop.flag); // get a free flag from the shop
                }
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

    // COLLECTING SURROUNDING CELLS & INFO

    private List<Cell> getAllCells(Filter filter) {
        List<Cell> all = new ArrayList<>();
        for (int y = 0; y < 10; y++) {
            all.addAll(Arrays.asList(field[y]).subList(0, 10));
        }
        return filterCells(all, filter);
    }

    private List<Cell> getNeighborCells(Cell cell, Filter filter, boolean includeSelf) {
        List<Cell> neighbors = new ArrayList<>();
        for (int y = cell.y - 1; y <= cell.y + 1; y++) {
            for (int x = cell.x - 1; x <= cell.x + 1; x++) {
                if (y < 0 || y >= 10) continue;     // skip out of borders
                if (x < 0 || x >= 10) continue;     // skip out of borders
                if (field[y][x] == cell && !includeSelf) continue;  // skip center if not included
                neighbors.add(field[y][x]);
            }
        }
        return filterCells(neighbors, filter);
    }

    private List<Cell> filterCells(List<Cell> list, Filter filter) {
        List<Cell> result = new ArrayList<>();
        list.forEach(cell -> {
            switch (filter) {
                case CLOSED:
                    if (!cell.isOpen) result.add(cell);
                    break;
                case OPEN:
                    if (cell.isOpen) result.add(cell);
                    break;
                case MINED:
                    if (cell.isMined) result.add(cell);
                    break;
                case DANGEROUS:
                    if (cell.isDangerous()) result.add(cell);
                    break;
                case NUMERABLE:
                    if (cell.isNumerable()) result.add(cell);
                    break;
                case SUSPECTED:
                    if (cell.isSuspected()) result.add(cell);
                    break;
                case SAFE:
                    if (cell.isSafe()) result.add(cell);
                    break;
                case NONE:
                default:
                    result.add(cell);
                    break;
            }
        });
        return result;
    }

    private void assignMineNumbersToCells() {
        getAllCells(Filter.NUMERABLE).forEach(cell -> {
            cell.countMinedNeighbors = getNeighborCells(cell, Filter.MINED, false).size();
            cell.assignSprite(cell.countMinedNeighbors);
        });
    }

    public int countAllCells(Filter filter) {
        return getAllCells(filter).size();
    }

    // UTILITIES

    private void addScore(int x, int y) {
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
        diceTurns = (shop.luckyDice == null) ? -1 : shop.luckyDice.expireMove - player.countMoves;
        if (diceTurns > -1 && diceTurns < 3 && player.countMoves != 0)
            shop.dice.draw();
    }

    void hideDice() {
        if (shop.miniBomb != null)
            shop.dice.isHidden = shop.miniBomb.isActivated();
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
            player.countMoves++;
            player.score += timer.getScore();
            player.scoreTimer += timer.getScore();
            timer.restart();
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
                cell.makeSpriteYellow();
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
        timer.draw();
    }

    // PRINT

    public void print(String text, Color color, int x, int y, boolean right) {
        printer.print(text, color, x, y, right);
    }

    public void print(String text, Color color, int x, int y) {
        print(text, color, x, y, false);
    }

    public void print(String text, int x, int y) {
        print(text, Color.WHITE, x, y, false);
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