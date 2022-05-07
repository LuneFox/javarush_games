package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.interactive.SwitchSelector;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.board.field.CellFilter;
import com.javarush.games.minesweeper.model.board.field.FieldDAO;
import com.javarush.games.minesweeper.model.player.Inventory;
import com.javarush.games.minesweeper.model.player.Player;
import com.javarush.games.minesweeper.model.shop.Shop;
import com.javarush.games.minesweeper.model.shop.item.Shield;

import java.util.List;

public class BoardManager {
    private final MinesweeperGame game;
    private final FieldDAO fieldDAO;
    private final FlagManager flagManager;
    private final Timer timer;

    private boolean isUnableToCheatMore;
    private boolean isFlagExplosionAllowed;
    private boolean isFirstMove;
    private boolean isRecursiveMove;
    private boolean miniBombHitMine;

    public BoardManager(MinesweeperGame game) {
        this.game = game;
        this.fieldDAO = new FieldDAO();
        this.timer = new Timer();
        this.flagManager = new FlagManager(game);
    }

    /*
     * Creation
     */

    public void reset() {
        fieldDAO.createNewField();
        plantMines();
        enumerateMinedCells();
        timer.reset();
        isRecursiveMove = false;
        isFirstMove = true;
    }

    private void plantMines() {
        double numberOfMinesToPlant = Options.difficulty / 1.5;
        while (fieldDAO.countAllCells(CellFilter.MINED) < numberOfMinesToPlant) {
            int randomX = game.getRandomNumber(10);
            int randomY = game.getRandomNumber(10);
            Cell cell = getCell(randomX, randomY);
            if (!cell.isMined()) {
                cell.setMined(true);
            }
        }
    }

    public void enumerateMinedCells() {
        fieldDAO.getAllCells(CellFilter.NUMERABLE).forEach(cell -> {
            final int minesCount = fieldDAO.getNeighborCells(cell, CellFilter.MINED).size();
            cell.setCountMinedNeighbors(minesCount);
        });
    }

    /*
     * Display
     */

    public void draw() {
        drawField();
        timer.draw();
        game.getShop().drawItemAssets();
    }

    private void drawField() {
        fieldDAO.getAllCells().forEach(Cell::draw);
    }

    /*
     * Open cell
     */

    public void openCell(Cell cell) {
        if (cell.cannotBeOpened()) return;

        if (isFirstMove) {
            cell = transformToEmptyShopCell(cell);
            isFirstMove = false;
        }

        if (cell.isMined()) {
            tryUsingShieldOnMinedCell(cell);
            if (!cell.isShielded()) {
                gameOverFromCellExplosion(cell);
                return;
            }
        }

        cell.open();

        if (cell.isEmpty()) {
            isRecursiveMove = true;
            List<Cell> closedNeighbors = fieldDAO.getNeighborCells(cell, CellFilter.CLOSED);
            closedNeighbors.forEach(this::openCell);
        }

        registerScoreAndMoney(cell);
        checkVictory();
    }

    private Cell transformToEmptyShopCell(Cell cell) {
        List<Cell> flaggedCells = fieldDAO.getAllCells(CellFilter.FLAGGED);
        flaggedCells.forEach(flagManager::returnFlagToInventory);
        cell = restartUntilCellIsEmpty(cell);
        flaggedCells.forEach(flagManager::placeFlagFromInventory);
        cell.setShop(true);
        return cell;
    }

    private Cell restartUntilCellIsEmpty(Cell cell) {
        while (!cell.isEmpty()) {
            reset();
            cell = getCell(cell.x, cell.y);
        }
        return cell;
    }

    private void tryUsingShieldOnMinedCell(Cell cell) {
        Shield shield = game.getShop().getShield();
        if (shield.isActivated()) shield.use(cell);
    }

    private void gameOverFromCellExplosion(Cell cell) {
        cell.setGameOverCause(true);
        game.lose();
    }

    private void registerScoreAndMoney(Cell cell) {
        addPlayerMove();
        addTimerScore();
        useDice(cell);
        collectMoneyFromCell(cell);
    }

    private void addPlayerMove() {
        if (isRecursiveMove()) return;
        game.getPlayer().addMove();
    }

    private void addTimerScore() {
        if (isRecursiveMove) return;
        game.getPlayer().getScore().addTimerScore();
        timer.reset();
    }

    private void useDice(Cell cell) {
        game.getShop().getDice().use(cell);
    }

    private void collectMoneyFromCell(Cell cell) {
        final Player player = game.getPlayer();
        final Inventory inventory = player.getInventory();
        int collectedMoney = cell.produceMoney();
        inventory.addMoney(collectedMoney);
    }

    public void checkVictory() {
        final int countClosedCells = fieldDAO.countAllCells(CellFilter.CLOSED);
        final int countMinedClosedCells = fieldDAO.countAllCells(CellFilter.DANGEROUS);
        if (countClosedCells == countMinedClosedCells) {
            game.win();
        }
    }

    /*
     * Destroy cell (mini bomb action)
     */

    public void destroyCell(Cell cell) {
        if (cell.isIndestructible() && !isRecursiveMove) {
            PopUpMessage.show("Не получилось!");
            return;
        }

        addPlayerMove();
        addTimerScore();
        flagManager.returnFlagToShop(cell);

        if (cell.isMined()) {
            PopUpMessage.show(isRecursiveMove ? "Взорвались мины!" : "Взорвалась мина!");
            miniBombHitMine = true;
            isRecursiveMove = true;
            isFlagExplosionAllowed = true;
            cell.destroy();
            List<Cell> minedNeighbors = fieldDAO.getNeighborCells(cell, CellFilter.MINED);
            minedNeighbors.forEach(this::destroyCell);
        } else {
            cell.destroy();
        }
        enumerateMinedCells();
        checkVictory();
    }

    public void cleanUpAfterMineDestruction() {
        if (!miniBombHitMine) return;
        fieldDAO.getAllCells(CellFilter.DESTROYED).stream()
                .filter(Cell::wasMinedBeforeDestruction)
                .map(cell -> fieldDAO.getNeighborCells(cell, CellFilter.CLOSED))
                .forEach(closedNeighbors -> closedNeighbors.forEach(this::openCell));
        reapplyOpenedCellsVisuals();
        miniBombHitMine = false;
    }

    /*
     * Open surrounding cells automatically
     */

    public void openSurroundingCells(int x, int y) {
        if (game.isScannerOrBombActivated()) return;

        Cell cell = getCell(x, y);
        if (cell.isEmpty()) return;
        if (!cell.isOpen()) return;
        if (cell.isMined()) return;

        int countMinedNeighbors = cell.getCountMinedNeighbors();
        int countNeighborFlagsAndRevealedMines = fieldDAO.getNeighborCells(cell, CellFilter.SUSPECTED).size();

        if (countMinedNeighbors == countNeighborFlagsAndRevealedMines) {
            List<Cell> allNeighbors = fieldDAO.getNeighborCells(cell, CellFilter.NONE);
            allNeighbors.forEach(game::openCell);
        }
    }

    /*
     * Scan neighbors (scanner action)
     */


    public void scanNeighbors(Cell cell) {  // action for Scanner
        List<Cell> safeCells = fieldDAO.getCellsIn3x3area(cell, CellFilter.SAFE);
        if (safeCells.size() != 0) {
            PopUpMessage.show("Сканирование...");
            scanRandomCell(safeCells);
        } else {
            PopUpMessage.show("Нечего сканировать");
            placeFlagsForPlayer(cell);
        }
    }

    private void scanRandomCell(List<Cell> safeNeighbors) {
        Cell cell = safeNeighbors.get(game.getRandomNumber(safeNeighbors.size()));
        if (cell.isFlagged()) { // by mistake
            flagManager.swapFlag(cell);
        }
        cell.setScanned(true);
        openCell(cell);
    }

    private void placeFlagsForPlayer(Cell cell) {
        final List<Cell> closedCells = fieldDAO.getCellsIn3x3area(cell, CellFilter.CLOSED);

        closedCells.forEach(closedCell -> {
            final Inventory inventory = game.getPlayer().getInventory();
            if (inventory.countFlags() == 0) {
                Shop shop = game.getShop();
                shop.give(shop.getFlag());
            }
            flagManager.placeFlagFromInventory(closedCell);
        });
    }

    /*
     * Other
     */

    public void revealMines() {
        fieldDAO.getAllCells().forEach(cell -> {
            if (cell.isMined()) cell.open();
        });
    }

    public void reapplyOpenedCellsVisuals() {
        if (game.isStopped()) return;
        fieldDAO.getAllCells(CellFilter.OPEN).forEach(Cell::setGraphicsForOpenedState);
    }

    /*
     * Cheats
     */

    @DeveloperOption
    public void autoFlag() {
        if (!Options.developerModeEnabled) return;

        SwitchSelector selector = Options.autoBuyFlagsSelector;
        if (!selector.isEnabled()) {
            selector.click(selector.x, selector.y); // click self
        }

        boolean[] success = new boolean[1];
        fieldDAO.getAllCells(CellFilter.NUMERABLE).forEach(cell -> {
            if (!cell.isOpen()) return;
            List<Cell> dangerousNeighbors = fieldDAO.getNeighborCells(cell, CellFilter.DANGEROUS);
            List<Cell> closedNeighbors = fieldDAO.getNeighborCells(cell, CellFilter.CLOSED);
            if (dangerousNeighbors.size() == closedNeighbors.size()) {
                dangerousNeighbors.forEach(dangerousNeighbor -> {
                    if (dangerousNeighbor.isFlagged()) return;
                    if (game.getPlayer().getInventory().countFlags() == 0) {
                        Shop shop = game.getShop();
                        shop.sellFlag();
                    }
                    flagManager.swapFlag(dangerousNeighbor);
                    success[0] = true;
                });
            }
        });
        if (!success[0]) {
            PopUpMessage.show("DEV: CANNOT FLAG");
            isUnableToCheatMore = true;
        } else {
            PopUpMessage.show("DEV: AUTO FLAG");
        }
    }

    @DeveloperOption
    public void autoOpen() {
        if (!Options.developerModeEnabled) return;

        int closedCells = fieldDAO.countAllCells(CellFilter.CLOSED);
        if (isFirstMove) {
            List<Cell> allCells = fieldDAO.getAllCells();
            Cell randomCell = allCells.get(game.getRandomNumber(allCells.size()));
            game.onMouseLeftClick(randomCell.x * 10, randomCell.y * 10);
        } else {
            for (Cell cell : fieldDAO.getAllCells(CellFilter.NUMERABLE)) {
                if (cell.isOpen()) game.onMouseRightClick(cell.x * 10, cell.y * 10);
            }
        }
        PopUpMessage.show(fieldDAO.countAllCells(CellFilter.CLOSED) == closedCells ? "DEV: CANNOT OPEN" : "DEV: AUTO OPEN");
    }

    @DeveloperOption
    public void autoScan() {
        if (!Options.developerModeEnabled) return;

        List<Cell> allCells = fieldDAO.getAllCells(CellFilter.SAFE);
        if (allCells.isEmpty()) return;
        Cell randomCell = allCells.get(game.getRandomNumber(allCells.size()));
        Shop shop = game.getShop();
        shop.getScanner().activate();
        game.onMouseLeftClick(randomCell.x * 10, randomCell.y * 10);
        game.onMouseLeftClick(randomCell.x * 10, randomCell.y * 10);
        PopUpMessage.show("DEV: RANDOM SCAN");
    }

    @DeveloperOption
    public void autoSolve() {
        if (!Options.developerModeEnabled) return;

        int closedCells = fieldDAO.countAllCells(CellFilter.CLOSED);
        isUnableToCheatMore = false;
        int limit = 0;
        while (!isUnableToCheatMore) {
            autoOpen();
            autoFlag();
            if (limit++ > closedCells) {
                // Limit is set to prevent accidental infinite loops
                break;
            }
        }
        autoOpen();
        PopUpMessage.show(fieldDAO.countAllCells(CellFilter.CLOSED) == closedCells ? "DEV: CANNOT SOLVE!" : "DEV: SOLVING...");
    }

    /*
     * Delegations
     */

    public Cell getCell(int x, int y) {
        return fieldDAO.getCell(x, y);
    }

    public Cell getCellByCoordinates(int x, int y) {
        return fieldDAO.getCellByCoordinates(x, y);
    }

    public int countAllCells(CellFilter filter) {
        return fieldDAO.countAllCells(filter);
    }

    public void swapFlag(Cell cell) {
        flagManager.swapFlag(cell);
    }

    /*
     * Getters, setters
     */

    public boolean isFlagExplosionAllowed() {
        return isFlagExplosionAllowed;
    }

    public void setFlagExplosionAllowed(boolean flagExplosionAllowed) {
        isFlagExplosionAllowed = flagExplosionAllowed;
    }

    public boolean isRecursiveMove() {
        return isRecursiveMove;
    }

    public void setRecursiveMove(boolean recursiveMove) {
        isRecursiveMove = recursiveMove;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public Timer getTimer() {
        return timer;
    }
}
