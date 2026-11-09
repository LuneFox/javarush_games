package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.Text;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.interactive.SwitchSelector;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.board.field.FieldDao;

import java.util.List;

/**
 * Автоматические ходы и подсказки для режима разработчика.
 */
final class DeveloperTools {
    /** Ссылка на игру. */
    private final MinesweeperGame game;
    /** Объект доступа к клеткам поля. */
    private final FieldDao fieldDao;
    /** Менеджер флажков. */
    private final FlagManager flagManager;
    /** Признак остановки авторешения. */
    private boolean stopAutoSolve;

    /** Создаёт экземпляр DeveloperTools. */
    DeveloperTools(MinesweeperGame game, FieldDao fieldDao, FlagManager flagManager) {
        this.game = game;
        this.fieldDao = fieldDao;
        this.flagManager = flagManager;
    }

    /**
     * Ставит флажки там, где все закрытые соседи уже определены как мины.
     */
    void autoFlag() {
        if (!Options.isDeveloperModeEnabled()) return;

        forceEnableAutoBuyFlags();
        boolean success = false;

        for (Cell cell : fieldDao.getAllCells()) {
            if (!cell.isOpen() || !cell.canShowNumber()) continue;

            List<Cell> dangerousNeighbors = fieldDao.getNeighborCells(cell, Cell::isDangerousToOpen);
            List<Cell> closedNeighbors = fieldDao.getNeighborCells(cell, Cell::isClosed);

            if (dangerousNeighbors.size() != closedNeighbors.size()) continue;

            for (Cell dangerousNeighbor : dangerousNeighbors) {
                if (!dangerousNeighbor.isNotFlagged()) continue;
                buyFlagIfAbsent();
                flagManager.toggleFlag(dangerousNeighbor);
                success = true;
            }
        }

        showAutoFlagResult(success);
    }

    /**
     * Открывает случайную клетку или все безопасные открытые числа.
     */
    void autoOpen() {
        if (!Options.isDeveloperModeEnabled()) return;

        int closedCells = fieldDao.count(Cell::isClosed);

        if (game.isFirstMove()) {
            List<Cell> allCells = fieldDao.getAllCells();
            Cell randomCell = allCells.get(game.getRandomNumber(allCells.size()));
            game.onMouseLeftClick(randomCell.x * Cell.PIXEL_SIZE, randomCell.y * Cell.PIXEL_SIZE);
        } else {
            for (Cell cell : fieldDao.getAllCells()) {
                if (cell.canShowNumber() && cell.isOpen()) {
                    game.onMouseRightClick(cell.x * Cell.PIXEL_SIZE, cell.y * Cell.PIXEL_SIZE);
                }
            }
        }
        game.showMessage(fieldDao.count(Cell::isClosed) == closedCells ? Text.DEV_CANNOT_OPEN.getText() : Text.DEV_AUTO_OPEN.getText());
    }

    /**
     * Применяет сканер к случайной безопасной клетке.
     */
    void autoScan() {
        if (!Options.isDeveloperModeEnabled()) return;

        List<Cell> safeCells = fieldDao.getAllCells(Cell::isSafeToOpen);
        if (safeCells.isEmpty()) return;

        Cell randomCell = safeCells.get(game.getRandomNumber(safeCells.size()));
        game.getShop().getScanner().activate();
        game.onMouseLeftClick(randomCell.x * Cell.PIXEL_SIZE, randomCell.y * Cell.PIXEL_SIZE);
        game.onMouseLeftClick(randomCell.x * Cell.PIXEL_SIZE, randomCell.y * Cell.PIXEL_SIZE);
        game.showMessage(Text.DEV_RANDOM_SCAN.getText());
    }

    /**
     * Пытается доиграть партию автоходами.
     */
    void autoSolve() {
        if (!Options.isDeveloperModeEnabled()) return;

        int closedCells = fieldDao.count(Cell::isClosed);
        stopAutoSolve = false;
        int limit = 0;

        while (!stopAutoSolve) {
            autoOpen();
            autoFlag();
            if (limit++ > closedCells) break; // Защита от бесконечного цикла.
        }
        autoOpen();

        game.showMessage(fieldDao.count(Cell::isClosed) == closedCells
                ? Text.DEV_CANNOT_SOLVE.getText()
                : Text.DEV_SOLVING.getText());
    }

    /**
     * Покупает флажок, если у игрока не осталось ни одного.
     */
    private void buyFlagIfAbsent() {
        if (game.getPlayer().countFlags() == 0) {
            game.getShop().sellFlag();
        }
    }

    /**
     * Включает авто-покупку флажков для авторешения.
     */
    private void forceEnableAutoBuyFlags() {
        SwitchSelector selector = Options.getAutoBuyFlagsSelector();
        if (!selector.isEnabled()) {
            selector.click(selector.x, selector.y);
        }
    }

    /**
     * Показывает итог автофлага и при неудаче останавливает авторешение.
     */
    private void showAutoFlagResult(boolean success) {
        if (!success) {
            stopAutoSolve = true;
            game.showMessage(Text.DEV_CANNOT_FLAG.getText());
        } else {
            game.showMessage(Text.DEV_AUTO_FLAG.getText());
        }
    }
}
