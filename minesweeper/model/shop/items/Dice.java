package com.javarush.games.minesweeper.model.shop.items;

import com.javarush.games.minesweeper.Text;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Util;
import com.javarush.games.minesweeper.gui.ShopItemStatusBar;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.field.Cell;

/** Кубик удачи, добавляющий бонус к очкам. */
public class Dice extends ShopItem {
    /** Полоса состояния предмета. */
    protected final ShopItemStatusBar statusBar;
    /** Изображение кубика на поле. */
    private Image onBoardImage;
    /** Оставшееся время показа кубика. */
    private int onBoardImageTimeToLive;
    /** Результат последнего броска. */
    private int rollResult;
    /** Сумма выпавших значений кубика. */
    private int rollsSum = 0;
    /** Число затронутых кубиком клеток. */
    private int rollsCount = 0;

    /** Создаёт экземпляр Dice. */
    public Dice(MinesweeperGame game) {
        super(game);
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_DICE);
        name = Text.ITEM_DICE_NAME.getText();
        description = Text.ITEM_DICE_DESCRIPTION.getText();
        cost = 6;
        inStock = 1;
        effectDuration = 3;
        statusBar = new ShopItemStatusBar(89, Color.GREEN, this);
    }

    /** Использует предмет. */
    public void use(Cell cell) {
        if (cell.isMined() || !isActivated) return;
        rollOnce(cell);
        registerResult();
    }

    /** Активирует предмет. */
    @Override
    public void activate() {
        lease();
    }

    /** Рисует полосу состояния. */
    public void drawStatusBar() {
        if (game.getPlayer().getMoves() > expirationMove) return;
        statusBar.draw();
        drawOnBoardImage();
    }

    /** Рисует изображение кубика на поле. */
    private void drawOnBoardImage() {
        if (onBoardImageTimeToLive <= 0) return;
        onBoardImage.draw();
        onBoardImageTimeToLive--;
    }

    /** Скрывает изображение. */
    public void hide() {
        onBoardImageTimeToLive = 0;
    }

    /** Возвращает среднюю удачу. */
    public double getAverageLuck() {
        return (Util.round((double) rollsSum / rollsCount, 2));
    }

    /** Бросает кубик один раз. */
    private void rollOnce(Cell cell) {
        if (game.isRecursiveMove()) return;
        rollResult = game.getRandomNumber(6) + 1;
        setBoardImage(rollResult, cell.x, cell.y);
    }

    /** Начисляет бонус за клетку. */
    private void registerResult() {
        game.getPlayer().getScore().addDiceScore(Options.getDifficulty() * rollResult);
        rollsCount++;
        rollsSum += rollResult;
    }

    /** Задаёт изображение кубика на поле. */
    private void setBoardImage(int number, int x, int y) {
        this.onBoardImage = Image.cache.get(ImageType.valueOf("BOARD_DICE_" + number));
        this.onBoardImage.setPosition(x * Cell.PIXEL_SIZE + 2, y * Cell.PIXEL_SIZE + 2);
        this.onBoardImageTimeToLive = 20;
    }

    /** Возвращает число затронутых клеток. */
    public int getRollsCount() {
        return rollsCount;
    }

}
