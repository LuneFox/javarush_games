package com.javarush.games.minesweeper.view.impl;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.ShakeHelper;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.board.field.CellFilter;
import com.javarush.games.minesweeper.model.player.Player;
import com.javarush.games.minesweeper.view.View;

public class ViewShop extends View {
    private static final ShakeHelper moneyShakeHelper = new ShakeHelper();
    private final Image headerFooterPanel = new Image(ImageType.SHOP_HEADER_PANEL);
    private final Image showCasePanel = new Image(ImageType.SHOP_SHOWCASE_PANEL);
    private int displayedMoney;

    private final Image headerMine = new Image(ImageType.BOARD_MINE, this) {
        @Override
        public void onLeftClick() {
            PopUpMessage.show("Мины на поле");
        }

        public void onRightClick() {
            onLeftClick();
        }
    };

    private final Image headerFlag = new Image(ImageType.BOARD_FLAG, this) {
        @Override
        public void onLeftClick() {
            PopUpMessage.show("Ваши флажки");
        }

        public void onRightClick() {
            onLeftClick();
        }
    };

    private final Image headerCoin = new Image(ImageType.SHOP_HEADER_COIN, this) {
        @Override
        public void onLeftClick() {
            PopUpMessage.show("Ваше золото");
        }

        @Override
        public void onRightClick() {
            onLeftClick();
        }
    };


    public ViewShop(MinesweeperGame game) {
        super(game);
    }

    @Override
    public void update() {
        drawField();
        drawShowCase();
        drawHeader();
        drawFooter();
        super.update();
    }

    private void drawField() {
        game.drawGameBoard();
    }

    private void drawShowCase() {
        showCasePanel.draw(10, 10);
        game.getShop().getShowCaseSlots().forEach(InteractiveObject::draw);
        Printer.print("*** магазин ***", Theme.SHOP_TITLE.getColor(), Printer.CENTER, 22);
    }

    private void drawHeader() {
        Player player = game.getPlayer();
        headerFooterPanel.draw(10, 10);
        headerMine.draw(13, 10);
        headerFlag.draw(42, 11);
        headerCoin.draw(70, 13);
        Printer.print("" + game.countAllCells(CellFilter.DANGEROUS), 25, 12);
        Printer.print("" + player.countFlags(), 52, 12);
        Printer.print("" + shiftDisplayedMoney(), 76 + moneyShakeHelper.getShift(), 12);
    }

    private int shiftDisplayedMoney() {
        Player player = game.getPlayer();
        if (displayedMoney < player.getMoneyBalance()) displayedMoney++;
        else if (displayedMoney > player.getMoneyBalance()) displayedMoney--;
        return displayedMoney;
    }

    public void skipMoneyAnimation() {
        Player player = game.getPlayer();
        displayedMoney = player.getMoneyBalance();
    }

    private void drawFooter() {
        headerFooterPanel.draw(10, 78);
        final int currentScore = game.getPlayer().getScore().getCurrentScore();
        final int countMoves = game.getPlayer().getMoves();
        Printer.print("Очки:" + currentScore, Theme.SHOP_SCORE.getColor(), 13, 80);
        Printer.print("Шаги:" + countMoves, Theme.SHOP_MOVES.getColor(), 83, 80, Printer.Align.RIGHT);
    }

    public static void shakeMoney() {
        moneyShakeHelper.startShaking();
    }
}
