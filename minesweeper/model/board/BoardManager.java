package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.Text;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.board.field.Field;
import com.javarush.games.minesweeper.model.board.field.FieldDao;
import com.javarush.games.minesweeper.model.board.field.MineFieldValidator;
import com.javarush.games.minesweeper.model.player.Player;
import com.javarush.games.minesweeper.model.player.Score;
import com.javarush.games.minesweeper.model.shop.Shop;
import com.javarush.games.minesweeper.model.shop.items.Dice;
import com.javarush.games.minesweeper.model.shop.items.Shield;

import java.util.List;
import java.util.function.Predicate;

/** Управляет полем, ходами и правилами сапёра. */
public class BoardManager {
    /** Ссылка на игру. */
    private final MinesweeperGame game;
    /** Объект доступа к клеткам поля. */
    private final FieldDao fieldDao;
    /** Менеджер флажков. */
    private final FlagManager flagManager;
    /** Таймер партии. */
    private final Timer timer;
    /** Инструменты режима разработчика. */
    private final DeveloperTools developerTools;
    /** Генератор полей без угадывания. */
    private final NoGuessGenerator noGuessGenerator;

    /** Разрешён ли взрыв флажков. */
    private boolean isFlagExplosionAllowed;
    /** Идёт ли ещё первый ход. */
    private boolean isFirstMove;
    /** Идёт ли каскадное открытие. */
    private boolean isRecursiveMove;
    /** Попала ли мини-бомба в мину. */
    private boolean miniBombHitMine;
    /** Число попыток разложить мины без изолированных мин. */
    private static final int MINE_PLACEMENT_ATTEMPTS = 1000;

    /** Создаёт экземпляр BoardManager. */
    public BoardManager(MinesweeperGame game) {
        this.game = game;
        this.fieldDao = new FieldDao();
        this.timer = new Timer();
        this.flagManager = new FlagManager(game);
        this.developerTools = new DeveloperTools(game, fieldDao, flagManager);
        this.noGuessGenerator = new NoGuessGenerator(game, fieldDao);
    }

    /*
     * Создание
     */

    /** Сбрасывает состояние в начальное. */
    public void reset() {
        fieldDao.createNewField();
        placeMines();
        updateAllCountMinedNeighbors();
        timer.reset();
        isRecursiveMove = false;
        isFirstMove = true;
    }

    /** Расставляет мины по полю, избегая мин без безопасных соседей. */
    private void placeMines() {
        int numberOfMinesToPlant = countMines();

        for (int attempt = 0; attempt < MINE_PLACEMENT_ATTEMPTS; attempt++) {
            clearMines();

            while (fieldDao.count(Cell::isMined) < numberOfMinesToPlant) {
                int randomX = game.getRandomNumber(Field.SIZE);
                int randomY = game.getRandomNumber(Field.SIZE);

                Cell cell = getCell(randomX, randomY);
                if (!cell.isMined()) cell.setMined(true);
            }

            if (!MineFieldValidator.hasBuriedMine(fieldDao)) return;
        }
    }

    /** Убирает мины с поля перед новой попыткой раскладки. */
    private void clearMines() {
        for (Cell cell : fieldDao.getAllCells()) {
            cell.setMined(false);
        }
    }

    /** Возвращает число мин для текущей сложности. */
    private int countMines() {
        return Options.getMineCount(Options.getDifficulty());
    }

    /** Пересчитывает число мин вокруг всех клеток. */
    public void updateAllCountMinedNeighbors() {
        for (Cell cell : fieldDao.getAllCells()) {
            if (!cell.isCountable()) continue;
            cell.setCountMinedNeighbors(fieldDao.countNeighborCells(cell, Cell::isMined));
        }
    }

    /*
     * Отрисовка
     */

    /** Рисует объект на экране. */
    public void draw() {
        drawField();
        timer.draw();
        game.getShop().drawItemAssets();
    }

    /** Рисует игровое поле. */
    private void drawField() {
        fieldDao.getAllCells().forEach(Cell::draw);
    }

    /*
     * Взаимодействие
     */

    /** Обрабатывает левый клик по полю. */
    public void handleLeftClick(int x, int y) {
        Cell cell = fieldDao.getCellByCoordinates(x, y);

        if (cell.isShop()) {
            game.openShop();
            return;
        }

        if (game.isAiming()) {
            game.aimAtCell(cell);
            return;
        }

        openCell(cell);
    }

    /** Обрабатывает правый клик по полю. */
    public void handleRightClick(int x, int y) {
        Cell cell = fieldDao.getCellByCoordinates(x, y);

        if (cell.isShop()) {
            game.showMessage(Text.BOARD_SHOP_DOORS.getText());
            return;
        }

        if (cell.isClosed()) {
            flagManager.toggleFlag(cell);
        } else {
            openSurroundingCells(cell);
        }
    }

    /*
     * Открытие клетки
     */

    /** Открывает клетку и запускает каскад при необходимости. */
    private void openCell(Cell cell) {
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

        cell.reveal();

        registerMove();
        awardMoney(cell);
        awardTimerBonus();
        awardDiceBonus(cell);

        if (cell.isEmpty()) {
            isRecursiveMove = true;
            fieldDao.getNeighborCells(cell, Cell::isClosed).forEach(this::openCell);
        }

        checkVictory();
    }

    /** Превращает первую клетку в дверь магазина. */
    private Cell transformToEmptyShopCell(Cell cell) {
        List<Cell> oldFlaggedCells = collectFlagsFromField();
        cell = createFirstClickCell(cell);
        cell.setShop(true);
        placeCollectedFlagsOnNewCells(oldFlaggedCells);
        return cell;
    }

    /** Создаёт поле вокруг первого клика. */
    private Cell createFirstClickCell(Cell cell) {
        if (NoGuessGenerator.isEnabled()
                && noGuessGenerator.generate(cell.x, cell.y, countMines())) {
            updateAllCountMinedNeighbors();
            return getCell(cell.x, cell.y);
        }
        return restartUntilCellIsEmpty(cell);
    }

    /** Пересоздаёт поле, пока выбранная клетка не станет пустой. */
    private Cell restartUntilCellIsEmpty(Cell cell) {
        while (!cell.isEmpty()) {
            reset();
            cell = getCell(cell.x, cell.y);
        }

        return cell;
    }

    /** Собирает флажки с поля. */
    private List<Cell> collectFlagsFromField() {
        List<Cell> flaggedCellsBefore = fieldDao.getAllCells(Cell::isFlagged);
        flaggedCellsBefore.forEach(flagManager::returnFlagToPlayer);
        return flaggedCellsBefore;
    }

    /** Расставляет собранные флажки на новые клетки. */
    private void placeCollectedFlagsOnNewCells(List<Cell> oldFLaggedCells) {
        oldFLaggedCells.forEach(oldFlaggedCell -> {
            Cell newCellToFlag = fieldDao.getCell(oldFlaggedCell.x, oldFlaggedCell.y);
            flagManager.placeFlag(newCellToFlag);
        });
    }

    /** Применяет щит к заминированной клетке. */
    private void tryUsingShieldOnMinedCell(Cell cell) {
        Shield shield = game.getShop().getShield();
        if (shield.isActivated()) shield.use(cell);
    }

    /** Завершает игру из-за взрыва клетки. */
    private void gameOverFromCellExplosion(Cell cell) {
        cell.setGameOverCause(true);
        game.lose();
    }

    /** Регистрирует ход игрока. */
    private void registerMove() {
        if (isRecursiveMove()) return;
        game.getPlayer().registerMove();
    }

    /** Начисляет бонус за оставшееся время. */
    private void awardTimerBonus() {
        if (isRecursiveMove) return;
        final Score score = game.getScore();
        score.addTimerScore();
        timer.reset();
    }

    /** Начисляет бонус кубика за открытую клетку. */
    private void awardDiceBonus(Cell cell) {
        final Shop shop = game.getShop();
        final Dice dice = shop.getDice();
        dice.use(cell);
    }

    /** Начисляет золото за открытую клетку. */
    private void awardMoney(Cell cell) {
        final Player player = game.getPlayer();
        player.gainMoney(cell.calculateMoney());
    }

    /** Проверяет условие победы. */
    private void checkVictory() {
        if (game.isStopped()) return;

        int closedCells = 0;
        int dangerousCells = 0;
        for (Cell cell : fieldDao.getAllCells()) {
            if (cell.isClosed()) closedCells++;
            if (cell.isDangerousToOpen()) dangerousCells++;
        }

        if (closedCells == dangerousCells) {
            game.win();
        }
    }

    /*
     * Уничтожение клетки (мини-бомба)
     */

    /**
     * Уничтожает клетку и сообщает, удалось ли это.
     */
    public boolean destroyCell(Cell cell) {
        if (game.isStopped()) return false;
        if (cell.isIndestructible() && !isRecursiveMove) {
            game.showMessage(Text.BOARD_SCAN_FAILED.getText());
            return false;
        }

        registerMove();
        awardTimerBonus();
        flagManager.returnFlagToShop(cell);

        if (cell.isMined()) {
            game.showMessage(isRecursiveMove ? Text.BOARD_MINES_EXPLODED.getText() : Text.BOARD_MINE_EXPLODED.getText());

            miniBombHitMine = true;
            isRecursiveMove = true;
            isFlagExplosionAllowed = true;

            cell.destroy();

            fieldDao.getNeighborCells(cell, Cell::isMined).forEach(neighbor -> destroyCell(neighbor));
        } else {
            cell.destroy();
        }
        updateAllCountMinedNeighbors();
        checkVictory();
        return true;
    }

    /** Открывает соседей после взрыва мин. */
    public void cleanUpAfterMineDestruction() {
        if (!miniBombHitMine) return;

        for (Cell explodedMine : fieldDao.getAllCells()) {
            if (!explodedMine.isDestroyed() || !explodedMine.wasMinedBeforeDestruction()) continue;
            for (Cell neighbor : fieldDao.getNeighborCells(explodedMine, Cell::isClosed)) {
                openCell(neighbor);
            }
        }

        refreshOpenedCellsGraphics();
        miniBombHitMine = false;
    }

    /*
     * Сканирование соседей (сканер)
     */

    /** Сканирует область вокруг клетки. */
    public void scanNeighbors(Cell cell) {
        List<Cell> safeCells = fieldDao.getCellsIn3x3area(cell, Cell::isSafeToOpen);
        if (!safeCells.isEmpty()) {
            game.showMessage(Text.BOARD_SCANNING.getText());
            scanRandomCell(safeCells);
        } else {
            game.showMessage(Text.BOARD_NOTHING_TO_SCAN.getText());
            placeFlagsForPlayer(cell);
        }
    }

    /** Открывает случайную безопасную клетку. */
    private void scanRandomCell(List<Cell> safeNeighbors) {
        Cell cell = safeNeighbors.get(game.getRandomNumber(safeNeighbors.size()));
        if (cell.isFlagged()) { // флажок поставлен ошибочно
            flagManager.toggleFlag(cell);
        }
        cell.setScanned(true);
        openCell(cell);
    }

    /** Расставляет флажки вокруг клетки. */
    private void placeFlagsForPlayer(Cell cell) {
        fieldDao.getCellsIn3x3area(cell, Cell::isClosed).forEach(closedCell -> {
            final Player player = game.getPlayer();
            Shop shop = game.getShop();

            if (player.countFlags() == 0) {
                shop.give(shop.getFlag());
            }

            flagManager.placeFlag(closedCell);
        });
    }

    /*
     * Прочее
     */

    /** Открывает соседние клетки при совпадении флажков. */
    private void openSurroundingCells(Cell cell) {
        if (game.isAiming()) return;
        if (!cell.isOpen() || cell.isEmpty() || cell.isMined()) return;

        int countMinedNeighbors = cell.getCountMinedNeighbors();
        int countShieldedNeighbors = fieldDao.countNeighborCells(cell, Cell::isShielded);
        int countFlaggedNeighbors = fieldDao.countNeighborCells(cell, Cell::isFlagged);

        if (countMinedNeighbors == countFlaggedNeighbors + countShieldedNeighbors) {
            fieldDao.getNeighborCells(cell, cellToOpen -> true).forEach(this::openCell);
        }
    }

    /** Открывает все мины. */
    public void revealMines() {
        for (Cell cell : fieldDao.getAllCells()) {
            if (cell.isMined()) cell.reveal();
        }
    }

    /** Перерисовывает открытые клетки. */
    public void refreshOpenedCellsGraphics() {
        if (game.isStopped()) return;
        for (Cell cell : fieldDao.getAllCells()) {
            if (cell.isOpen()) cell.setRevealedContent();
        }
    }

    /**
     * Перерисовывает все клетки поля в актуальных цветах темы.
     */
    public void refreshAllCellsGraphics() {
        for (Cell cell : fieldDao.getAllCells()) {
            cell.refreshTheme();
        }
    }

    /*
     * ---------------------------------------------- Читы ----------------------------------------------
     */

    /** Автоматически ставит флажки. */
    @DeveloperOption
    public void autoFlag() {
        developerTools.autoFlag();
    }

    /** Автоматически открывает клетки. */
    @DeveloperOption
    public void autoOpen() {
        developerTools.autoOpen();
    }

    /** Применяет сканер к случайной безопасной клетке. */
    @DeveloperOption
    public void autoScan() {
        developerTools.autoScan();
    }

    /** Пытается доиграть партию автоходами. */
    @DeveloperOption
    public void autoSolve() {
        developerTools.autoSolve();
    }

    /** Возвращает клетку по координатам. */
    public Cell getCell(int x, int y) {
        return fieldDao.getCell(x, y);
    }

    /** Возвращает все клетки поля. */
    public List<Cell> getAllCells(Predicate<Cell> predicate) {
        return fieldDao.getAllCells(predicate);
    }

    /** Считает клетки, удовлетворяющие условию. */
    public int countCells(Predicate<Cell> predicate) {
        return fieldDao.count(predicate);
    }

    /** Сообщает, разрешён ли взрыв флажков. */
    public boolean isFlagExplosionAllowed() {
        return isFlagExplosionAllowed;
    }

    /** Разрешает или запрещает взрыв флажков. */
    public void setFlagExplosionAllowed(boolean flagExplosionAllowed) {
        isFlagExplosionAllowed = flagExplosionAllowed;
    }

    /** Сообщает, идёт ли каскадное открытие. */
    public boolean isRecursiveMove() {
        return isRecursiveMove;
    }

    /** Задаёт режим каскадного открытия. */
    public void setRecursiveMove(boolean recursiveMove) {
        isRecursiveMove = recursiveMove;
    }

    /** Сообщает, идёт ли ещё первый ход. */
    public boolean isFirstMove() {
        return isFirstMove;
    }

    /** Возвращает таймер партии. */
    public Timer getTimer() {
        return timer;
    }
}
