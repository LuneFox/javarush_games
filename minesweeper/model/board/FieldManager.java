package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.interactive.SwitchSelector;
import com.javarush.games.minesweeper.model.Options;

import java.util.List;

public class FieldManager {
    private final Field field;
    private final FlagManager flagManager;
    private final MinesweeperGame game;
    private boolean isUnableToCheatMore;
    private boolean isFlagExplosionAllowed;
    private boolean isRecursiveMove;

    public FieldManager(MinesweeperGame game) {
        this.game = game;
        this.flagManager = new FlagManager(game);
        this.field = new Field();
    }

    public void createField() {
        field.createNewLayout();
        flagManager.setField(field);
        isRecursiveMove = false;
    }

    public void openCell(int x, int y) {
        if (game.isStopped) return;

        Cell cell = game.isFirstMove ? restartUntilCellIsEmpty(field.getCell(x, y)) : field.getCell(x, y);

        if (game.shop.miniBomb.use(cell) || game.shop.scanner.use(cell)) return;
        if (cell.isFlagged || cell.isOpen) return;

        boolean survived = tryOpening(cell);
        if (!survived) return;

        onManualMove(cell);
        game.player.score.addScore(cell);
        game.player.inventory.addMoney(cell);
        recursiveOpen(cell);
        checkVictory();
    }

    private void recursiveOpen(Cell cell) {
        if (cell.isEmpty()) {
            isRecursiveMove = true;
            List<Cell> neighbors = field.getNeighborCells(cell, Cell.Filter.NONE, false);
            neighbors.forEach(neighbor -> openCell(neighbor.x, neighbor.y));
        }
    }

    // Quick bruteforce implementation, but it makes first move very convenient
    private Cell restartUntilCellIsEmpty(Cell cell) {
        List<Cell> flaggedCells = field.getAllCells(Cell.Filter.FLAGGED);
        flaggedCells.forEach(fc -> flagManager.returnFlagToInventory(field.getCell(fc.x, fc.y)));
        while (!cell.isEmpty()) {
            field.createNewLayout();
            cell = field.getCell(cell.x, cell.y);
        }
        flaggedCells.forEach(fc -> flagManager.setFlag(fc.x, fc.y, false));
        game.isFirstMove = false;
        return cell;
    }

    // Open and return true if survived, false if mine has exploded
    private boolean tryOpening(Cell cell) {
        cell.open();

        if (!cell.isMined) {
            return true;
        }

        if (game.shop.shield.isActivated()) {
            return game.shop.shield.use(cell);
        }

        cell.isGameOverCause = true;
        game.lose();

        return false;
    }

    // Attempt to open cells around if number of flags nearby equals the number on the cell
    public void openSurroundingCells(int x, int y) {
        if (game.shop.scanner.isActivated()) return;
        if (game.shop.miniBomb.isActivated()) return;
        Cell cell = field.getCell(x, y);
        if (cell.isOpen && !cell.isMined) {
            // If mined neighbors = number of neighbor flags + opened mines
            if (cell.countMinedNeighbors == field.getNeighborCells(cell, Cell.Filter.SUSPECTED, false).size()) {
                field.getNeighborCells(cell, Cell.Filter.NONE, false).forEach(neighbor -> openCell(neighbor.x, neighbor.y));
            }
        }
    }

    public void onManualMove(Cell cell) {
        if (isRecursiveMove()) return;
        field.dice.appearCell = cell;
        game.player.incMoves();
        game.player.score.addTimerScore();
        game.timer.reset();
    }

    public void checkVictory() {
        if (field.countAllCells(Cell.Filter.CLOSED) == field.countAllCells(Cell.Filter.DANGEROUS)) {
            game.win();
        }
    }

    public void setFlag(int x, int y, boolean flagIsRemovable) {
        flagManager.setFlag(x, y, flagIsRemovable);
    }

    // Scanner action
    public void scanNeighbors(int x, int y) {  // action for Scanner
        List<Cell> safeNeighbors = field.getNeighborCells(field.getCell(x, y), Cell.Filter.SAFE, true);

        if (safeNeighbors.size() == 0) {       // no safe cells, place free flags over closed ones
            PopUpMessage.show("Нечего сканировать");
            field.getNeighborCells(field.getCell(x, y), Cell.Filter.CLOSED, true).forEach(closedCell -> {
                if (game.player.inventory.hasNoFlags()) game.shop.give(game.shop.flag);
                flagManager.setFlag(closedCell.x, closedCell.y, false);
            });
        } else {                               // open random safe cell
            Cell cell = safeNeighbors.get(game.getRandomNumber(safeNeighbors.size()));
            if (cell.isFlagged) {
                flagManager.setFlag(cell.x, cell.y, true); // remove flag if it was placed wrong
            }
            openCell(cell.x, cell.y);
            PopUpMessage.show("Сканирование...");
            cell.scan();
        }
    }

    // Mini Bomb action
    public void destroyCell(int x, int y) {
        Cell cell = field.getCell(x, y);
        onManualMove(cell);

        if (cell.isIndestructible()) {
            PopUpMessage.show("Не получилось!");
            return;
        }

        if (cell.isMined) { // recursive explosions
            PopUpMessage.show("Взорвалась мина!");
            cell.isMined = false;
            isRecursiveMove = true;
            isFlagExplosionAllowed = true;
            field.getNeighborCells(cell, Cell.Filter.NONE, false).forEach(neighbor -> {
                if (neighbor.isMined) {
                    destroyCell(neighbor.x, neighbor.y); // recursive call
                }
            });
        }

        cell.destroy();
        flagManager.retrieveFlag(cell);
        field.setNumbers();
        checkVictory();
    }

    // CHEATS

    @DeveloperOption
    public void autoFlag() {
        if (!Options.developerMode) return;

        SwitchSelector selector = Options.autoBuyFlagsSelector;
        if (!selector.isEnabled()) {
            selector.tryClick(selector.x, selector.y); // click self
        }

        boolean[] success = new boolean[1];
        field.getAllCells(Cell.Filter.NUMERABLE).forEach(cell -> {
            if (!cell.isOpen) return;
            List<Cell> dangerousNeighbors = field.getNeighborCells(cell, Cell.Filter.DANGEROUS, false);
            List<Cell> closedNeighbors = field.getNeighborCells(cell, Cell.Filter.CLOSED, false);
            if (dangerousNeighbors.size() == closedNeighbors.size()) {
                dangerousNeighbors.forEach(dangerousNeighbor -> {
                    if (dangerousNeighbor.isFlagged) return;
                    if (game.player.inventory.hasNoFlags()) game.shop.sell(game.shop.flag);
                    flagManager.setFlag(dangerousNeighbor.x, dangerousNeighbor.y, false);
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

        int closedCells = field.countAllCells(Cell.Filter.CLOSED);
        if (game.isFirstMove) {
            List<Cell> allCells = field.getAllCells(Cell.Filter.NONE);
            Cell randomCell = allCells.get(game.getRandomNumber(allCells.size()));
            game.onMouseLeftClick(randomCell.x * 10, randomCell.y * 10);
        } else {
            for (Cell cell : field.getAllCells(Cell.Filter.NUMERABLE)) {
                if (cell.isOpen) game.onMouseRightClick(cell.x * 10, cell.y * 10);
            }
        }
        PopUpMessage.show(field.countAllCells(Cell.Filter.CLOSED) == closedCells ? "DEV: CANNOT OPEN" : "DEV: AUTO OPEN");
    }

    @DeveloperOption
    public void autoScan() {
        if (!Options.developerMode) return;

        List<Cell> allCells = field.getAllCells(Cell.Filter.SAFE);
        if (allCells.isEmpty()) return;
        Cell randomCell = allCells.get(game.getRandomNumber(allCells.size()));
        game.shop.scanner.activate();
        game.onMouseLeftClick(randomCell.x * 10, randomCell.y * 10);
        PopUpMessage.show("DEV: RANDOM SCAN");
    }

    @DeveloperOption
    public void skipEasyPart() {
        if (!Options.developerMode) return;

        int closedCells = field.countAllCells(Cell.Filter.CLOSED);
        isUnableToCheatMore = false;
        int limit = 0;
        while (!isUnableToCheatMore) {
            // Limit is set to prevent accidental infinite loops
            autoOpen();
            autoFlag();
            limit++;
            if (limit > closedCells) {
                break;
            }
        }
        autoOpen();
        PopUpMessage.show(field.countAllCells(Cell.Filter.CLOSED) == closedCells ? "DEV: TOO RISKY!" : "DEV: SKIP EASY PART");
    }

    @DeveloperOption
    public void cheatMoreMoney() {
        if (!Options.developerMode) return;

        game.player.inventory.money += 50;
        PopUpMessage.show("DEV: 50 GOLD");
    }

    @DeveloperOption
    public void cheatMoreTools() {
        if (!Options.developerMode) return;

        game.shop.goldenShovel.activate();
        game.shop.luckyDice.activate();
        game.shop.goldenShovel.expireMove += 10;
        game.shop.luckyDice.expireMove += 10;
        PopUpMessage.show("DEV: 10 TOOLS");
    }

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
}
