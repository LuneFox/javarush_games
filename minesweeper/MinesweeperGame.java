package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.controller.Click;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.gui.Display;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.*;
import com.javarush.games.minesweeper.model.board.BoardManager;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.player.Player;
import com.javarush.games.minesweeper.model.player.Score;
import com.javarush.games.minesweeper.model.shop.Shop;
import com.javarush.games.minesweeper.model.shop.items.Bomb;
import com.javarush.games.minesweeper.model.shop.items.Scanner;
import com.javarush.games.minesweeper.model.shop.items.ShopItem;
import com.javarush.games.minesweeper.view.View;
import com.javarush.games.minesweeper.view.impl.ViewShop;

import java.util.List;
import java.util.function.Predicate;

/**
 * Точка входа JavaRush и фасад игры: связывает модель, контроллер и представление,
 * а также отдаёт контроллеру и представлению единый доступ к игровым объектам.
 */
public class MinesweeperGame extends Game {
    /** Версия игры. */
    public static final String VERSION = "1.27";

    /** Контроллер игры. */
    private Controller controller;
    /** Виртуальный экран. */
    private Display display;
    /** Менеджер поля. */
    private BoardManager boardManager;
    /** Магазин. */
    private Shop shop;
    /** Игрок. */
    private Player player;
    /** Результат партии. */
    private Result result;
    /** Признак остановки игры. */
    private boolean isStopped;

    /** Инициализирует игру и создаёт её объекты. */
    @Override
    public void initialize() {
        super.showGrid(false);
        super.setScreenSize(Display.SIZE, Display.SIZE);
        super.setTurnTimer(30);

        Phase.setUp(this);
        InteractiveObject.setGame(this);
        GameStatistics.setGame(this);
        Score.setGame(this);
        display = new Display();
        controller = new Controller(this);
        boardManager = new BoardManager(this);
        shop = new Shop(this);
        Phase.linkShopSlots(shop.getShowCaseSlots());
        player = new Player(this);
        isStopped = true;

        Phase.setActive(Phase.MAIN);
    }

    /** Обновляет взрывы, представление и выводит кадр на экран. */
    @Override
    public void onTurn(int step) {
        if (display == null || boardManager == null) return;
        boardManager.updateExplosions();
        Phase.updateView();
        display.draw();
    }

    /**
     * Начинает новую партию с текущими настройками.
     */
    public void startNewGame() {
        Options.apply();
        resetAssets();
        Phase.setActive(Phase.BOARD);
        showMessage(Text.GAME_NEW.getText());
    }

    /** Сбрасывает поле, игрока и магазин перед новой партией. */
    private void resetAssets() {
        boardManager.reset();
        player.reset();
        shop.reset();
        isStopped = false;
        result = Result.UNDEFINED;
    }

    /**
     * Завершает игру победой, если она ещё не завершена.
     */
    public void win() {
        if (!finish(Result.WIN)) return;
        player.getScore().registerTopScore();
    }

    /**
     * Завершает игру поражением, если она ещё не завершена.
     */
    public void lose() {
        if (!finish(Result.LOSE)) return;
        shop.getDice().hide();
        boardManager.revealMines();
    }

    /**
     * Фиксирует результат один раз и сообщает, был ли он принят.
     */
    private boolean finish(Result result) {
        if (isStopped) return false;

        this.isStopped = true;
        this.result = result;
        View.setGameOverShowDelay(30);
        Phase.setActive(Phase.GAME_OVER);
        GameStatistics.update();
        return true;
    }

    /*
     * Фасад
     */

    /** Обрабатывает левый клик по игровому полю. */
    public void leftClickOnBoard(int x, int y) {
        boardManager.handleLeftClick(x, y);
        shop.checkExpiredItems();
    }

    /** Обрабатывает правый клик по игровому полю. */
    public void rightClickOnBoard(int x, int y) {
        boardManager.handleRightClick(x, y);
        shop.checkExpiredItems();
    }

    /**
     * Наводит активный предмет магазина на клетку.
     */
    public void aimAtCell(Cell cell) {
        if (isStopped()) return;
        shop.aimWithScanner(cell);
        shop.aimWithBomb(cell);
    }

    /**
     * Показывает короткое сообщение поверх игры.
     */
    public void showMessage(String message) {
        PopUpMessage.show(message);
    }

    /**
     * Открывает экран магазина.
     */
    public void openShop() {
        Phase.setActive(Phase.SHOP);
    }

    /**
     * Открывает справку по предмету магазина.
     */
    public void openItemHelp(ShopItem item) {
        shop.setHelpDisplayItem(item);
        Phase.setActive(Phase.ITEM_HELP);
    }

    /**
     * Запускает анимацию тряски золота в магазине.
     */
    public void shakeShopMoney() {
        ViewShop.shakeMoney();
    }

    /**
     * Уничтожает клетку бомбой и сообщает, удалось ли это.
     */
    public boolean destroyCell(Cell cell) {
        return boardManager.destroyCell(cell);
    }

    /**
     * Сообщает, наводится ли сейчас сканер или бомба.
     */
    public boolean isAiming() {
        Scanner scanner = shop.getScanner();
        Bomb bomb = shop.getBomb();
        return scanner.isActivated() || bomb.isActivated();
    }

    /*
     * Короткие методы
     */

    /** Возвращает клетку по координатам. */
    public Cell getCell(int x, int y) {
        return boardManager.getCell(x, y);
    }

    /**
     * Перерисовывает все клетки поля в актуальных цветах темы.
     */
    public void refreshAllCellsGraphics() {
        boardManager.refreshAllCellsGraphics();
    }

    /** Сканирует область вокруг клетки. */
    public void scanNeighbors(Cell cell) {
        boardManager.scanNeighbors(cell);
    }

    /** Рисует игровое поле. */
    public void drawGameBoard() {
        boardManager.draw();
    }

    /** Пытается доиграть партию автоходами. */
    @DeveloperOption
    public void autoSolve() {
        boardManager.autoSolve();
    }

    /** Автоматически ставит флажки. */
    @DeveloperOption
    public void autoFlag() {
        boardManager.autoFlag();
    }

    /** Автоматически открывает клетки. */
    @DeveloperOption
    public void autoOpen() {
        boardManager.autoOpen();
    }

    /** Применяет сканер к случайной безопасной клетке. */
    @DeveloperOption
    public void autoScan() {
        boardManager.autoScan();
    }

    /** Возвращает количество флажков у игрока. */
    public int countPlayerFlags() {
        return player.countFlags();
    }

    /** Запрещает дальнейшее каскадное открытие. */
    public void stopRecursion() {
        boardManager.setRecursiveMove(false);
    }

    /** Запрещает взрыв флажков. */
    public void restrictFlagExplosion() {
        boardManager.setFlagExplosionAllowed(false);
    }

    /*
     * Обычные геттеры
     */

    /** Возвращает все клетки поля. */
    public List<Cell> getAllCells(Predicate<Cell> predicate) {
        return boardManager.getAllCells(predicate);
    }

    /**
     * Считает клетки, удовлетворяющие условию, без создания промежуточного списка.
     */
    public int countCells(Predicate<Cell> predicate) {
        return boardManager.countCells(predicate);
    }

    /** Возвращает магазин. */
    public Shop getShop() {
        return shop;
    }

    /**
     * Сообщает, активна ли золотая лопата.
     */
    public boolean isShovelActivated() {
        return shop.getShovel().isActivated();
    }

    /** Возвращает игрока. */
    public Player getPlayer() {
        return player;
    }

    /** Возвращает счёт игрока. */
    public Score getScore() {
        return player.getScore();
    }

    /** Сообщает, остановлена ли игра. */
    public boolean isStopped() {
        return isStopped;
    }

    /** Возвращает бонус за время. */
    public int getTimerBonus() {
        return boardManager.getTimer().getScoreBonus();
    }

    /** Сообщает, победа ли это. */
    public boolean isWon() {
        return result == Result.WIN;
    }

    /** Сообщает, идёт ли каскадное открытие. */
    public boolean isRecursiveMove() {
        return boardManager.isRecursiveMove();
    }

    /** Сообщает, разрешён ли взрыв флажков. */
    public boolean isFlagExplosionAllowed() {
        return boardManager.isFlagExplosionAllowed();
    }

    /** Сообщает, идёт ли ещё первый ход. */
    public boolean isFirstMove() {
        return boardManager.isFirstMove();
    }

    /*
     * Обычные сеттеры
     */

    /** Включает или выключает чересстрочный эффект. */
    public void setInterlacedEffect(boolean enabled) {
        display.setInterlaceEnabled(enabled);
    }

    /** Рисует пиксель. */
    public void drawPixel(int x, int y, Color color) {
        display.drawPixel(x, y, color);
    }

    /*
     * Управление
     */

    /** Обрабатывает левый клик мышью. */
    @Override
    public void onMouseLeftClick(int x, int y) {
        controller.click(x, y, Click.LEFT);
    }

    /** Обрабатывает правый клик мышью. */
    @Override
    public void onMouseRightClick(int x, int y) {
        controller.click(x, y, Click.RIGHT);
    }

    /** Обрабатывает нажатие клавиши. */
    @Override
    public void onKeyPress(Key key) {
        controller.pressKey(key);
    }
}