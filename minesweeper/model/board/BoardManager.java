package com.javarush.games.minesweeper.model.board;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.SwitchSelector;
import com.javarush.games.minesweeper.model.Options;

import java.util.List;

public class BoardManager {
    private final MinesweeperGame game;
    private final Field field;
    private final FlagManager flagManager;
    private final Timer timer;
    private Dice dice;

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
        dice = new Dice();
        timer.reset();
        isRecursiveMove = false;
        isFirstMove = true;
    }

    public void drawField() {
        field.draw();
        timer.draw();
        drawActivatedToolFrame();
        game.shop.goldenShovel.statusBar.draw();
        game.shop.luckyDice.statusBar.draw();
        dice.draw();
    }

    private void drawActivatedToolFrame() {
        Image frame = Image.cache.get(ImageType.GUI_SURROUND_FRAME);
        if (game.shop.allItems.get(1).isActivated()) {          // scanner
            frame.replaceColor(Color.BLUE, 3);
        } else if (game.shop.allItems.get(5).isActivated()) {   // mini bomb
            frame.replaceColor(Color.RED, 3);
        } else {
            return;
        }
        frame.draw();
    }

    public void openCell(int x, int y) {
        if (game.isStopped) return;
        Cell cell;

        if (isFirstMove) {
            cell = rebuildUntilEmpty(field.getCell(x, y));
            cell.setShop(true);
        } else {
            cell = field.getCell(x, y);
        }

        if (game.shop.miniBomb.use(cell) || game.shop.scanner.use(cell)) return;
        if (cell.isFlagged() || cell.isOpen()) return;

        boolean survived = tryOpening(cell);
        if (!survived) return;

        onManualMove();
        dice.roll(cell);
        game.player.inventory.addMoney(cell);
        recursiveOpen(cell);
        checkVictory();
    }

    private void recursiveOpen(Cell cell) {
        if (cell.isEmpty()) {
            isRecursiveMove = true;
            List<Cell> neighbors = field.getNeighborCells(cell, Cell.Filter.CLOSED, false);
            neighbors.forEach(neighbor -> openCell(neighbor.x, neighbor.y));
        }
    }

    // Quick bruteforce implementation, but it makes first move very convenient
    private Cell rebuildUntilEmpty(Cell cell) {
        List<Cell> flaggedCells = field.getAllCells(Cell.Filter.FLAGGED);
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
    private boolean tryOpening(Cell cell) {
        cell.open();

        if (!cell.isMined()) {
            return true;
        }

        if (game.shop.shield.isActivated()) {
            return game.shop.shield.use(cell);
        }

        cell.setGameOverCause(true);
        game.lose();

        return false;
    }

    // Attempt to open cells around if number of flags nearby equals the number on the cell
    public void openSurroundingCells(int x, int y) {
        if (game.shop.scanner.isActivated()) return;
        if (game.shop.miniBomb.isActivated()) return;
        Cell cell = field.getCell(x, y);
        if (cell.isEmpty()) return;
        if (!cell.isOpen()) return;
        if (cell.isMined()) return;
        // If mined neighbors = number of neighbor flags + opened mines
        if (cell.getCountMinedNeighbors() == field.getNeighborCells(cell, Cell.Filter.SUSPECTED, false).size()) {
            field.getNeighborCells(cell, Cell.Filter.NONE, false).forEach(neighbor -> openCell(neighbor.x, neighbor.y));
        }
    }

    public void onManualMove() {
        if (isRecursiveMove()) return;
        game.player.incMoves();
        game.player.score.addTimerScore();
        timer.reset();
    }

    public void checkVictory() {
        if (field.countAllCells(Cell.Filter.CLOSED) == field.countAllCells(Cell.Filter.DANGEROUS)) {
            game.win();
        }
    }

    public void swapFlag(int x, int y) {
        flagManager.swapFlag(x, y);
    }

    // Scanner action
    public void scanNeighbors(int x, int y) {  // action for Scanner
        List<Cell> safeNeighbors = field.getNeighborCells(field.getCell(x, y), Cell.Filter.SAFE, true);
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
        openCell(cell.x, cell.y);
        cell.setScanned();
    }

    private void placeFlagsForPlayer(int x, int y) {
        field.getNeighborCells(field.getCell(x, y), Cell.Filter.CLOSED, true).forEach(closedCell -> {
            if (game.player.inventory.hasNoFlags()) game.shop.give(game.shop.flag);
            flagManager.setFlag(closedCell.x, closedCell.y);
        });
    }

    // Mini Bomb action
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
            field.getNeighborCells(cell, Cell.Filter.NONE, false).forEach(neighbor -> {
                if (neighbor.isMined()) {
                    destroyCell(neighbor.x, neighbor.y); // recursive call
                }
            });
        }

        cell.destroy();
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
        field.getAllCells(Cell.Filter.NUMERABLE).forEach(cell -> {
            if (!cell.isOpen()) return;
            List<Cell> dangerousNeighbors = field.getNeighborCells(cell, Cell.Filter.DANGEROUS, false);
            List<Cell> closedNeighbors = field.getNeighborCells(cell, Cell.Filter.CLOSED, false);
            if (dangerousNeighbors.size() == closedNeighbors.size()) {
                dangerousNeighbors.forEach(dangerousNeighbor -> {
                    if (dangerousNeighbor.isFlagged()) return;
                    if (game.player.inventory.hasNoFlags()) game.shop.sell(game.shop.flag);
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

        int closedCells = field.countAllCells(Cell.Filter.CLOSED);
        if (isFirstMove) {
            List<Cell> allCells = field.getAllCells(Cell.Filter.NONE);
            Cell randomCell = allCells.get(game.getRandomNumber(allCells.size()));
            game.onMouseLeftClick(randomCell.x * 10, randomCell.y * 10);
        } else {
            for (Cell cell : field.getAllCells(Cell.Filter.NUMERABLE)) {
                if (cell.isOpen()) game.onMouseRightClick(cell.x * 10, cell.y * 10);
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
            autoOpen();
            autoFlag();
            if (limit++ > closedCells) {
                // Limit is set to prevent accidental infinite loops
                break;
            }
        }
        autoOpen();
        PopUpMessage.show(field.countAllCells(Cell.Filter.CLOSED) == closedCells ? "DEV: TOO RISKY!" : "DEV: SKIP EASY PART");
    }

    public void updateOpenedCellsColors() {
        if (game.isStopped) return;
        field.getAllCells(Cell.Filter.NONE).forEach(Cell::updateOpenedColors);
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

    public Dice getDice() {
        return dice;
    }

    public Timer getTimer() {
        return timer;
    }
}
