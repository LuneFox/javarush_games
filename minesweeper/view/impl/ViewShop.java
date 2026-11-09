package com.javarush.games.minesweeper.view.impl;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.ShakeHelper;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.player.Player;
import com.javarush.games.minesweeper.view.View;

/** Экран магазина. */
public class ViewShop extends View {
    /** Анимация тряски золота. */
    private static final ShakeHelper moneyShakeHelper = new ShakeHelper();
    /** Панель шапки и подвала магазина. */
    private final Image headerFooterPanel = new Image(ImageType.SHOP_HEADER_PANEL);
    /** Панель витрины магазина. */
    private final Image showCasePanel = new Image(ImageType.SHOP_SHOWCASE_PANEL);
    /** Показанный баланс золота. */
    private int displayedMoney;

    /** Иконка мин в шапке магазина. */
    private final Image headerMine = new Image(ImageType.BOARD_MINE, this) {
        /** Обрабатывает левый клик. */
        @Override
        public void onLeftClick() {
            PopUpMessage.show("Мины на поле");
        }

        /** Обрабатывает правый клик. */
        public void onRightClick() {
            onLeftClick();
        }
    };

    /** Иконка флажков в шапке магазина. */
    private final Image headerFlag = new Image(ImageType.BOARD_FLAG, this) {
        /** Обрабатывает левый клик. */
        @Override
        public void onLeftClick() {
            PopUpMessage.show("Ваши флажки");
        }

        /** Обрабатывает правый клик. */
        public void onRightClick() {
            onLeftClick();
        }
    };

    /** Иконка золота в шапке магазина. */
    private final Image headerCoin = new Image(ImageType.SHOP_HEADER_COIN, this) {
        /** Обрабатывает левый клик. */
        @Override
        public void onLeftClick() {
            PopUpMessage.show("Ваше золото");
        }

        /** Обрабатывает правый клик. */
        @Override
        public void onRightClick() {
            onLeftClick();
        }
    };

    /** Создаёт экземпляр ViewShop. */
    public ViewShop(MinesweeperGame game) {
        super(game);
    }

    /** Обновляет состояние. */
    @Override
    public void update() {
        drawField();
        drawShowCase();
        drawHeader();
        drawFooter();
        super.update();
    }

    /** Рисует игровое поле. */
    private void drawField() {
        game.drawGameBoard();
    }

    /** Рисует витрину магазина. */
    private void drawShowCase() {
        showCasePanel.draw(10, 10);
        game.getShop().getShowCaseSlots().forEach(InteractiveObject::draw);
        Printer.print("*** магазин ***", Theme.SHOP_TITLE.getColor(), Printer.CENTER, 22);
    }

    /** Рисует шапку магазина. */
    private void drawHeader() {
        Player player = game.getPlayer();
        headerFooterPanel.draw(10, 10);
        headerMine.draw(13, 10);
        headerFlag.draw(42, 11);
        headerCoin.draw(70, 13);
        Printer.print("" + game.countCells(Cell::isDangerousToOpen), 25, 12);
        Printer.print("" + player.countFlags(), 52, 12);
        Printer.print("" + shiftDisplayedMoney(), 76 + moneyShakeHelper.getShift(), 12);
    }

    /** Плавно показывает баланс золота. */
    private int shiftDisplayedMoney() {
        Player player = game.getPlayer();
        if (displayedMoney < player.getMoneyBalance()) displayedMoney++;
        else if (displayedMoney > player.getMoneyBalance()) displayedMoney--;
        return displayedMoney;
    }

    /** Пропускает анимацию золота. */
    public void skipMoneyAnimation() {
        Player player = game.getPlayer();
        displayedMoney = player.getMoneyBalance();
    }

    /** Рисует подвал магазина. */
    private void drawFooter() {
        headerFooterPanel.draw(10, 78);
        final long currentScore = game.getPlayer().getScore().getCurrentScore();
        final long countMoves = game.getPlayer().getMoves();
        Printer.print("Очки:" + currentScore, Theme.SHOP_SCORE.getColor(), 13, 80);
        Printer.print("Шаги:" + countMoves, Theme.SHOP_MOVES.getColor(), 88, 80, Printer.Align.RIGHT);
    }

    /** Запускает тряску золота. */
    public static void shakeMoney() {
        moneyShakeHelper.startShaking();
    }
}
