package com.javarush.games.minesweeper.view;

import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.shop.ShopItem;

public class ViewShop extends View {
    public static ShakeHelper moneyShakeHelper = new ShakeHelper();
    public static ShakeHelper activatedShakeHelper = new ShakeHelper();

    private final Image headerFooterPanel = new Image(ImageType.WIN_SHOP_HEADER_FOOTER);
    private final Image headerMine = new Image(ImageType.SPR_BOARD_MINE, this) {
        @Override
        public void onLeftClick() {
            PopUpMessage.show("Мины на поле");
        }

        public void onRightClick() {
            onLeftClick();
        }
    };
    private final Image headerFlag = new Image(ImageType.SPR_BOARD_FLAG, this) {
        @Override
        public void onLeftClick() {
            PopUpMessage.show("Ваши флажки");
        }

        public void onRightClick() {
            onLeftClick();
        }
    };
    private final Image headerCoin = new Image(ImageType.SHOP_COIN, this) {
        @Override
        public void onLeftClick() {
            PopUpMessage.show("Ваше золото");
        }

        @Override
        public void onRightClick() {
            onLeftClick();
        }
    };
    private final Image showCasePanel = new Image(ImageType.WIN_SHOP_SHOWCASE);

    private boolean slotsAreLinked;

    public ViewShop(Phase phase) {
        super(phase);
        slotsAreLinked = false;
    }

    @Override
    public void update() {
        // Linking shop slots to this view. Cannot link in constructor because shop isn't created at that time
        if (!slotsAreLinked) {
            game.shop.shopSlots.forEach(shopSlot -> shopSlot.linkView(this));
            slotsAreLinked = true;
        }

        game.field.draw();
        game.checkTimeOut();

        // Draw showcase
        showCasePanel.draw(10, 10);
        game.shop.shopSlots.forEach(InteractiveObject::draw);
        Printer.print("*** магазин ***", Theme.SHOP_TITLE.getColor(), Printer.CENTER, 22);

        // Draw header
        headerFooterPanel.draw(10, 10);
        headerMine.draw(10, 10);
        headerFlag.draw(39, 11);
        headerCoin.draw(69, 13);
        Printer.print("" + game.field.countAllCells(Cell.Filter.DANGEROUS), 22, 12);
        Printer.print("" + game.player.inventory.getCount(ShopItem.ID.FLAG), 49, 12);
        Printer.print("" + game.player.inventory.displayMoney, 75 + moneyShakeHelper.getShift(), 12);
        moneyApproach();

        // Draw footer
        headerFooterPanel.draw(10, 78);
        Printer.print("Очки:" + game.player.score.getCurrentScore(), Theme.SHOP_SCORE.getColor(), 13, 80);
        Printer.print("Шаги:" + game.player.getMoves(), Theme.SHOP_MOVES.getColor(), 83, 80, true);

        // Draw field elements
        game.timer.draw();
        game.shop.goldenShovel.statusBar.draw();
        game.shop.luckyDice.statusBar.draw();

        super.update();
    }

    // Animation. With each view update money on display slowly approaches the real amount that player has
    private void moneyApproach() {
        if (game.player.inventory.displayMoney < game.player.inventory.money) {
            game.player.inventory.displayMoney++;
        } else if (game.player.inventory.displayMoney > game.player.inventory.money) {
            game.player.inventory.displayMoney--;
        }
    }

    public static class ShakeHelper {
        private static final int SHAKE_DURATION = 10;
        private int shakeCountDown;
        private int shakeShift;

        public int getShift() {
            if (shakeCountDown == 0) return 0;
            shakeShift = (shakeShift == 0) ? 1 : 0;
            shakeCountDown--;
            return shakeShift;
        }

        public void startShaking() {
            shakeCountDown = SHAKE_DURATION;
        }
    }
}
