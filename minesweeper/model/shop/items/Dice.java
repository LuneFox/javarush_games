package com.javarush.games.minesweeper.model.shop.items;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Util;
import com.javarush.games.minesweeper.gui.ShopItemStatusBar;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.field.Cell;

public class Dice extends ShopItem {
    protected final ShopItemStatusBar statusBar;
    private Image onBoardImage;
    private int onBoardImageTimeToLive;
    private int rollResult;
    private int rollsSum = 0;
    private int rollsCount = 0;

    public Dice(MinesweeperGame game) {
        super(game);
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_DICE);
        name = "Кубик удачи";
        description = getDiceDescription();
        cost = 6;
        inStock = 1;
        effectDuration = 3;
        statusBar = new ShopItemStatusBar(89, Color.GREEN, this);
    }

    public void use(Cell cell) {
        if (cell.isMined() || !isActivated) return;
        rollOnce(cell);
        registerResult();
    }

    @Override
    public void activate() {
        lease();
    }

    public void drawStatusBar() {
        if (game.getPlayer().getMoves() > expirationMove) return;
        statusBar.draw();
        drawOnBoardImage();
    }

    private void drawOnBoardImage() {
        if (onBoardImageTimeToLive <= 0) return;
        onBoardImage.draw();
        onBoardImageTimeToLive--;
    }

    public void hide() {
        onBoardImageTimeToLive = 0;
    }

    public double getAverageLuck() {
        return (Util.round((double) rollsSum / rollsCount, 2));
    }

    private void rollOnce(Cell cell) {
        if (game.isRecursiveMove()) return;
        rollResult = game.getRandomNumber(6) + 1;
        setBoardImage(rollResult, cell.x, cell.y);
    }

    private void registerResult() {
        game.getPlayer().getScore().addDiceScore(Options.getDifficulty() * rollResult);
        rollsCount++;
        rollsSum += rollResult;
    }

    private void setBoardImage(int number, int x, int y) {
        this.onBoardImage = Image.cache.get(ImageType.valueOf("BOARD_DICE_" + number));
        this.onBoardImage.setPosition(x * 10 + 2, y * 10 + 2);
        this.onBoardImageTimeToLive = 20;
    }

    public int getRollsCount() {
        return rollsCount;
    }

    private String getDiceDescription() {
        return "Следующие 3 шага\n" +
                "вы можете получить\n" +
                "от 1 до 6 раз больше\n" +
                "очков. Базовое\n" +
                "количество зависит\n" +
                "от сложности игры.";
    }
}
