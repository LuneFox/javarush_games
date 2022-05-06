package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.interactive.SwitchSelector;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.player.Inventory;
import com.javarush.games.minesweeper.model.shop.Shop;
import com.javarush.games.minesweeper.model.shop.item.Shield;

import java.util.List;

public class BoardManager {
    private final MinesweeperGame game;
    private final Field field;
    private final FlagManager flagManager;
    private final Timer timer;

    private boolean isUnableToCheatMore;
    private boolean isFlagExplosionAllowed;
    private boolean isFirstMove;
    private boolean isRecursiveMove;
    private boolean miniBombHitMine;

    public BoardManager(MinesweeperGame game) {
        this.game = game;
        this.field = new Field();
        this.timer = new Timer();
        this.flagManager = new FlagManager(game, field);
    }

    public void reset() {
        field.createNewLayout();
        timer.reset();
        isRecursiveMove = false;
        isFirstMove = true;
    }

    public void drawGameBoard() {
        field.draw();
        timer.draw();
        drawShopItems();
    }

    private void drawShopItems() {
        Shop shop = game.getShop();
        shop.getScanner().drawFrame();
        shop.getBomb().drawFrame();
        shop.getShovel().drawStatusBar();
        shop.getDice().drawStatusBar();
    }

    public void useItemOnCell(int x, int y) {
        if (game.isStopped()) return;
        Cell cell = field.getCell(x, y);
        Shop shop = game.getShop();
        shop.getScanner().aimOrUse(cell);
        shop.getBomb().aimOrUse(cell);
    }

    public void openCell(int x, int y) {
        if (game.isStopped()) return;

        Cell cell = field.getCell(x, y);
        if (cell.isFlagged() || cell.isOpen()) return;

        if (isFirstMove) {
            cell = transformToEmptyShopCell(cell);
            isFirstMove = false;
        }

        if (cell.isMined()) {
            tryUsingShield(cell);
            if (!cell.isShielded()) {
                gameOver(cell);
                return;
            }
        }

        cell.open();

        if (cell.isEmpty()) {
            isRecursiveMove = true;
            List<Cell> neighbors = field.getNeighborCells(cell, CellFilter.CLOSED, false);
            neighbors.forEach(neighbor -> openCell(neighbor.x, neighbor.y));
        }

        registerScoreAndMoney(cell);
        checkVictory();
    }

    private void gameOver(Cell cell) {
        cell.setGameOverCause(true);
        game.lose();
    }

    private void tryUsingShield(Cell cell) {
        Shield shield = game.getShop().getShield();
        if (shield.isActivated()) shield.use(cell);
    }

    private void registerScoreAndMoney(Cell cell) {
        addPlayerMove();
        addTimerScore();
        useDice(cell);
        collectMoneyFromCell(cell);
    }

    private void useDice(Cell cell) {
        game.getShop().getDice().use(cell);
    }

    private void collectMoneyFromCell(Cell cell) {
        int collectedMoney = cell.getCountMinedNeighbors();
        Shop shop = game.getShop();

        if (shop.getShovel().isActivated()) {
            collectedMoney *= 2;
            cell.makeSpriteYellow();
        }

        Inventory inventory = game.getPlayer().getInventory();
        inventory.addMoney(collectedMoney);
    }

    private Cell transformToEmptyShopCell(Cell cell) {
        // A little costly but quick bruteforce implementation, makes first move very convenient
        List<Cell> flaggedCells = field.getAllCells(CellFilter.FLAGGED);
        collectExistingFlags(flaggedCells);
        cell = recreateLayoutUntilEmpty(cell);
        putFlagsBack(flaggedCells);
        cell.setShop(true);
        return cell;
    }

    private Cell recreateLayoutUntilEmpty(Cell cell) {
        while (!cell.isEmpty()) {
            field.createNewLayout();
            cell = field.getCell(cell.x, cell.y);
        }
        return cell;
    }

    private void putFlagsBack(List<Cell> flaggedCells) {
        flaggedCells.forEach(fc -> flagManager.setFlag(fc.x, fc.y));
    }

    private void collectExistingFlags(List<Cell> flaggedCells) {
        flaggedCells.forEach(fc -> flagManager.swapFlag(fc.x, fc.y));
    }

    public void openSurroundingCells(int x, int y) {
        if (game.isScannerOrBombActivated()) return;

        Cell cell = field.getCell(x, y);
        if (cell.isEmpty()) return;
        if (!cell.isOpen()) return;
        if (cell.isMined()) return;

        int countMinedNeighbors = cell.getCountMinedNeighbors();
        int countNeighborFlagsAndRevealedMines = field.getNeighborCells(cell, CellFilter.SUSPECTED, false).size();

        if (countMinedNeighbors == countNeighborFlagsAndRevealedMines) {
            List<Cell> allNeighbors = field.getNeighborCells(cell, CellFilter.NONE, false);
            allNeighbors.forEach(game::open);
        }
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


    public void checkVictory() {
        if (field.countAllCells(CellFilter.CLOSED) == field.countAllCells(CellFilter.DANGEROUS)) {
            game.win();
        }
    }

    public void swapFlag(int x, int y) {
        flagManager.swapFlag(x, y);
    }

    /**
     * Scanner action
     */
    public void scanNeighbors(int x, int y) {  // action for Scanner
        List<Cell> safeNeighbors = field.getNeighborCells(field.getCell(x, y), CellFilter.SAFE, true);
        if (safeNeighbors.size() != 0) {
            PopUpMessage.show("Сканирование...");
            scanRandomCell(safeNeighbors);
        } else {
            PopUpMessage.show("Нечего сканировать");
            placeFlagsForPlayer(x, y);
        }
    }

    private void scanRandomCell(List<Cell> safeNeighbors) {
        Cell cell = safeNeighbors.get(game.getRandomNumber(safeNeighbors.size()));
        if (cell.isFlagged()) { // by mistake
            flagManager.swapFlag(cell.x, cell.y);
        }
        cell.setScanned(true);
        openCell(cell.x, cell.y);
    }

    private void placeFlagsForPlayer(int x, int y) {
        field.getNeighborCells(field.getCell(x, y), CellFilter.CLOSED, true).forEach(closedCell -> {
            final Inventory inventory = game.getPlayer().getInventory();
            if (inventory.countFlags() == 0) {
                Shop shop = game.getShop();
                shop.give(shop.getFlag());
            }
            flagManager.setFlag(closedCell.x, closedCell.y);
        });
    }

    /**
     * Mini-bomb action
     */
    public void destroyCell(int x, int y) {
        Cell cell = field.getCell(x, y);
        addPlayerMove();
        addTimerScore();

        if (cell.isIndestructible()) {
            PopUpMessage.show("Не получилось!");
            return;
        }

        if (cell.isMined()) { // recursive explosions
            miniBombHitMine = true;
            PopUpMessage.show("Взорвалась мина!");
            cell.destroy();
            isRecursiveMove = true;
            isFlagExplosionAllowed = true;
            field.getNeighborCells(cell, CellFilter.NONE, false).forEach(neighbor -> {
                if (neighbor.isMined()) {
                    destroyCell(neighbor.x, neighbor.y); // recursive call
                }
            });
        } else {
            cell.destroy();
        }
        flagManager.returnFlagToShop(cell);
        field.applyNumbersToCellsNearMines();
        checkVictory();
    }

    public void cleanUpAfterMineDestruction() {
        if (!miniBombHitMine) return;
        field.getAllCells(CellFilter.DESTROYED).forEach(cell -> {
            if (cell.wasMinedBeforeDestruction()) {
                openClosedNeighbors(cell);
            }
        });
        reapplyOpenedCellsVisuals();
        miniBombHitMine = false;
    }

    private void openClosedNeighbors(Cell cell) {
        field.getNeighborCells(cell, CellFilter.CLOSED, false)
                .forEach(closedNeighbor -> openCell(closedNeighbor.x, closedNeighbor.y));
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
        field.getAllCells(CellFilter.NUMERABLE).forEach(cell -> {
            if (!cell.isOpen()) return;
            List<Cell> dangerousNeighbors = field.getNeighborCells(cell, CellFilter.DANGEROUS, false);
            List<Cell> closedNeighbors = field.getNeighborCells(cell, CellFilter.CLOSED, false);
            if (dangerousNeighbors.size() == closedNeighbors.size()) {
                dangerousNeighbors.forEach(dangerousNeighbor -> {
                    if (dangerousNeighbor.isFlagged()) return;
                    if (game.getPlayer().getInventory().countFlags() == 0) {
                        Shop shop = game.getShop();
                        shop.sellFlag();
                    }
                    flagManager.swapFlag(dangerousNeighbor.x, dangerousNeighbor.y);
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

        int closedCells = field.countAllCells(CellFilter.CLOSED);
        if (isFirstMove) {
            List<Cell> allCells = field.getAllCells();
            Cell randomCell = allCells.get(game.getRandomNumber(allCells.size()));
            game.onMouseLeftClick(randomCell.x * 10, randomCell.y * 10);
        } else {
            for (Cell cell : field.getAllCells(CellFilter.NUMERABLE)) {
                if (cell.isOpen()) game.onMouseRightClick(cell.x * 10, cell.y * 10);
            }
        }
        PopUpMessage.show(field.countAllCells(CellFilter.CLOSED) == closedCells ? "DEV: CANNOT OPEN" : "DEV: AUTO OPEN");
    }

    @DeveloperOption
    public void autoScan() {
        if (!Options.developerModeEnabled) return;

        List<Cell> allCells = field.getAllCells(CellFilter.SAFE);
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

        int closedCells = field.countAllCells(CellFilter.CLOSED);
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
        PopUpMessage.show(field.countAllCells(CellFilter.CLOSED) == closedCells ? "DEV: CANNOT SOLVE!" : "DEV: SOLVING...");
    }

    public void reapplyOpenedCellsVisuals() {
        if (game.isStopped()) return;
        field.getAllCells(CellFilter.OPEN).forEach(Cell::setGraphicsForOpenedState);
    }

    // Setters, getters

    public Field getField() {
        return field;
    }

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
