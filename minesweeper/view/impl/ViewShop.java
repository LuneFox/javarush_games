package com.javarush.games.minesweeper.view.impl;

import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.ShakeHelper;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.player.Inventory;
import com.javarush.games.minesweeper.model.player.Player;
import com.javarush.games.minesweeper.model.shop.Shop;
import com.javarush.games.minesweeper.model.shop.ShopItem;
import com.javarush.games.minesweeper.view.View;

public class ViewShop extends View {
    private static final ShakeHelper moneyShakeHelper = new ShakeHelper();

    private final Image headerFooterPanel = new Image(ImageType.SHOP_HEADER_PANEL);

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

    private final Image showCasePanel = new Image(ImageType.SHOP_SHOWCASE_PANEL);
    private boolean slotsAreLinked;

    @Override
    public void update() {
        linkShowCaseSlots(game.shop);
        drawField(game.shop);
        drawShowCase(game.shop);
        drawHeader(game.player.inventory);
        drawFooter(game.player);
        super.update();
    }

    private void drawField(Shop shop) {
        game.boardManager.drawField();
    }

    private void drawShowCase(Shop shop) {
        showCasePanel.draw(10, 10);
        shop.slots.forEach(InteractiveObject::draw);
        Printer.print("*** магазин ***", Theme.SHOP_TITLE.getColor(), Printer.CENTER, 22);
    }

    private void drawHeader(Inventory inventory) {
        headerFooterPanel.draw(10, 10);
        headerMine.draw(13, 10);
        headerFlag.draw(42, 11);
        headerCoin.draw(70, 13);
        Printer.print("" + game.boardManager.getField().countAllCells(Cell.Filter.DANGEROUS), 25, 12);
        Printer.print("" + inventory.getCount(ShopItem.ID.FLAG), 52, 12);
        Printer.print("" + inventory.displayMoney, 76 + moneyShakeHelper.getShift(), 12);
        inventory.moneyApproach();
    }

    private void drawFooter(Player player) {
        headerFooterPanel.draw(10, 78);
        Printer.print("Очки:" + player.score.getCurrentScore(), Theme.SHOP_SCORE.getColor(), 13, 80);
        Printer.print("Шаги:" + player.getMoves(), Theme.SHOP_MOVES.getColor(), 83, 80, true);
    }

    // Cannot link at creation time because they don't exist yet
    private void linkShowCaseSlots(Shop shop) {
        if (slotsAreLinked) return;
        shop.slots.forEach(this::linkObject);
        slotsAreLinked = true;
    }

    public static void shakeMoney() {
        moneyShakeHelper.startShaking();
    }
}
