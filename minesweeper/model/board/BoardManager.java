package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.interactive.SwitchSelector;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.player.Inventory;
import com.javarush.games.minesweeper.model.shop.Shop;
import com.javarush.games.minesweeper.model.shop.item.Dice;
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

    public void drawField() {
        field.draw();
        timer.draw();
        final Shop shop = game.getShop();
        shop.getScanner().drawFrame();
        shop.getBomb().drawFrame();
        shop.getShovel().draw();
        shop.getDice().draw();
    }

    public void openCell(int x, int y) {
        if (game.isStopped()) return;
        Cell cell;

        if (isFirstMove) {
            cell = rebuildUntilEmpty(field.getCell(x, y));
            cell.setShop(true);
        } else {
            cell = field.getCell(x, y);
        }

        final Shop shop = game.getShop();
        if (shop.getBomb().tryToUseOrMoveFrame(cell) || shop.getScanner().tryToUseOrMoveFrame(cell)) return;
        if (cell.isFlagged() || cell.isOpen()) return;

        boolean survived = tryToOpen(cell);
        if (!survived) return;

        onManualMove();
        final Dice dice = shop.getDice();
        dice.use(cell);
        collectMoney(cell);
        recursiveOpen(cell);
        checkVictory();
    }

    private void recursiveOpen(Cell cell) {
        if (cell.isEmpty()) {
            isRecursiveMove = true;
            List<Cell> neighbors = field.getNeighborCells(cell, CellFilter.CLOSED, false);
            neighbors.forEach(neighbor -> openCell(neighbor.x, neighbor.y));
        }
    }

    private void collectMoney(Cell cell) {
        int collectedMoney = cell.getCountMinedNeighbors();
        if (game.getShop().getShovel().isActivated()) {
            collectedMoney *= 2;
            cell.makeSpriteYellow();
        }
        Inventory inventory = game.getPlayer().getInventory();
        inventory.addMoney(collectedMoney);
    }

    // Quick bruteforce implementation, but it makes first move very convenient
    private Cell rebuildUntilEmpty(Cell cell) {
        List<Cell> flaggedCells = field.getAllCells(CellFilter.FLAGGED);

        flaggedCells.forEach(fc -> flagManager.swapFlag(fc.x, fc.y));
        while (!cell.isEmpty()) {
            field.createNewLayout();
            cell = field.getCell(cell.x, cell.y);
        }
        flaggedCells.forEach(fc -> flagManager.setFlag(fc.x, fc.y));

        isFirstMove = false;
        return cell;
    }

    // Open and return true if survived, false if mine has exploded
    private boolean tryToOpen(Cell cell) {
        cell.open();

        if (!cell.isMined()) {
            return true;
        }

        Shield shield = game.getShop().getShield();
        if (shield.isActivated()) {
            return shield.tryToUse(cell);
        }

        cell.setGameOverCause(true);
        game.lose();

        return false;
    }

    // Attempt to open cells around if number of flags nearby equals the number on the cell
    public void openSurroundingCells(int x, int y) {

        final Shop shop = game.getShop();
        if (shop.getScanner().isActivated()) return;
        if (shop.getBomb().isActivated()) return;

        Cell cell = field.getCell(x, y);
        if (cell.isEmpty()) return;
        if (!cell.isOpen()) return;
        if (cell.isMined()) return;
        // If mined neighbors = number of neighbor flags + opened mines
        if (cell.getCountMinedNeighbors() == field.getNeighborCells(cell, CellFilter.SUSPECTED, false).size()) {
            field.getNeighborCells(cell, CellFilter.NONE, false).forEach(neighbor -> openCell(neighbor.x, neighbor.y));
        }
    }

    public void onManualMove() {
        if (isRecursiveMove()) return;
        game.getPlayer().addMove();
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
        if (cell.isFlagged()) {
            flagManager.swapFlag(cell.x, cell.y); // return wrong flag
        }
        cell.setScanned();
        openCell(cell.x, cell.y);
    }

    private void placeFlagsForPlayer(int x, int y) {
        field.getNeighborCells(field.getCell(x, y), CellFilter.CLOSED, true).forEach(closedCell -> {
            final Inventory inventory = game.getPlayer().getInventory();
            if (inventory.countFlags() == 0) {
                final Shop shop = game.getShop();
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
        onManualMove();

        if (cell.isIndestructible()) {
            PopUpMessage.show("Не получилось!");
            return;
        }

        if (cell.isMined()) { // recursive explosions
            PopUpMessage.show("Взорвалась мина!");
            cell.setMined(false);
            isRecursiveMove = true;
            isFlagExplosionAllowed = true;
            field.getNeighborCells(cell, CellFilter.NONE, false).forEach(neighbor -> {
                if (neighbor.isMined()) {
                    destroyCell(neighbor.x, neighbor.y); // recursive call
                }
            });
        }

        cell.destroy();
        cell.open();
        flagManager.returnFlagToShop(cell);
        field.setNumbers();
        checkVictory();
    }


    // Cheats

    @DeveloperOption
    public void autoFlag() {
        if (!Options.developerMode) return;

        SwitchSelector selector = Options.autoBuyFlagsSelector;
        if (!selector.isEnabled()) {
            selector.tryClick(selector.x, selector.y); // click self
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
                        game.getShop().sellFlag();
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
        if (!Options.developerMode) return;

        int closedCells = field.countAllCells(CellFilter.CLOSED);
        if (isFirstMove) {
            List<Cell> allCells = field.getAllCells(CellFilter.NONE);
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
        if (!Options.developerMode) return;

        List<Cell> allCells = field.getAllCells(CellFilter.SAFE);
        if (allCells.isEmpty()) return;
        Cell randomCell = allCells.get(game.getRandomNumber(allCells.size()));
        game.getShop().getScanner().activate();
        game.onMouseLeftClick(randomCell.x * 10, randomCell.y * 10);
        PopUpMessage.show("DEV: RANDOM SCAN");
    }

    @DeveloperOption
    public void autoSolve() {
        if (!Options.developerMode) return;

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

    public void updateOpenedCellsVisuals() {
        if (game.isStopped()) return;
        field.getAllCells(CellFilter.NONE).forEach(Cell::updateOpenedCellVisuals);
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
