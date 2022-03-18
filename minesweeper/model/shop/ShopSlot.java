package com.javarush.games.minesweeper.model.shop;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.*;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.ViewShop;

/**
 * Clicking on this slot will sell the player the corresponding item.
 */

public class ShopSlot extends InteractiveObject {
    private static final int PRESSED_DURATION = 5;
    private static final int PRESS_DEPTH = 1;
    private int pressedCountDown;

    private ShopItem item;
    private Image frame;

    public ShopSlot(int x, int y) {
        frame = Image.cache.get(ImageType.SHOP_SHOWCASE_FRAME);
        this.x = x;
        this.y = y;
        this.height = 20;
        this.width = 20;
    }

    @Override
    public void draw() {
        // Define "press" depth when it's touched
        final int shift = shiftFrame() ? PRESS_DEPTH : 0;
        final int fx = x + shift;
        final int fy = y + shift;

        // Anchors for text
        final int top = y;
        final int right = x + 14;
        final int bottom = y + 10;

        // Select frame color depending on item status
        pickFrameColor();

        // Draw icon on frame
        frame.draw(fx, fy);
        item.icon.draw(fx + 1, fy + 1);

        // Write price, activation markers, expiration time, availability etc.
        if (item.inStock > 0 && !item.isActivated()) {
            Color strokeColor = Color.BLACK;
            Printer.print("" + item.cost, strokeColor, right + 1, bottom, true);
            Printer.print("" + item.cost, strokeColor, right - 1, bottom, true);
            Printer.print("" + item.cost, strokeColor, right, bottom + 1, true);
            Printer.print("" + item.cost, strokeColor, right, bottom - 1, true);
            Printer.print("" + item.cost, Color.YELLOW, right, bottom, true);

        } else if (item.isActivated()) {
            if (item.canExpire) {
                Printer.print(Integer.toString(item.remainingMoves()), Color.MAGENTA, right, top, true);
            }
            Printer.print("АКТ", Color.YELLOW, right + ViewShop.activatedShakeHelper.getShift(), bottom, true);
        } else {
            Printer.print("НЕТ", Theme.SHOP_SIGN_NO.getColor(), right, bottom, true);
        }
    }

    private boolean shiftFrame() {
        if (pressedCountDown > 0) {
            frame = Image.cache.get(ImageType.SHOP_SHOWCASE_FRAME_PRESSED);
            pressedCountDown--;
            return true;
        } else {
            frame = Image.cache.get(ImageType.SHOP_SHOWCASE_FRAME);
            return false;
        }
    }

    private void pickFrameColor() {
        Color frameColor;
        frameColor = (item.isUnobtainable()) ? Color.RED : Theme.SHOP_ITEM_FRAME_AVAILABLE.getColor();
        frameColor = (item.isActivated()) ? Color.BLUE : frameColor;
        frame.replaceColor(frameColor, 3);
    }

    @Override
    public void onLeftClick() {
        pressedCountDown = PRESSED_DURATION;

        if (item.isActivated()) {
            ViewShop.activatedShakeHelper.startShaking();
            PopUpMessage.show("Уже активировано");
            return;
        }

        if (item.inStock <= 0) {
            PopUpMessage.show("Недоступно");
            return;
        }

        if (item.isUnaffordable()) {
            ViewShop.moneyShakeHelper.startShaking();
            PopUpMessage.show("Не хватает золота");
            return;
        }

        game.shop.sell(item);
    }

    public void setItem(ShopItem item) {
        this.item = item;
    }

    @Override
    public void onRightClick() {
        game.shop.helpDisplayItem = item;
        Phase.setActive(Phase.ITEM_HELP);
    }
}
