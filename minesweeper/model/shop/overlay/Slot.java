package com.javarush.games.minesweeper.model.shop.overlay;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.*;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.DrawableObject;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.shop.ShopItem;

/**
 * Clicking on this slot will sell the player the corresponding item.
 */

public class Slot extends DrawableObject {
    private static final int PRESSED_DURATION = 5;
    private static final int PRESS_DEPTH = 1;
    private int pressedCountDown;
    private final ShopItem item;
    private Image frame;

    public ShakeHelper activatedShakeHelper = new ShakeHelper();

    public Slot(int x, int y, ShopItem item) {
        this.item = item;
        frame = Cache.get(ImageType.SHOP_ITEM_FRAME);
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
        final int bottom = y + 11;

        // Select frame color depending on item status
        pickFrameColor();

        // Draw icon on frame
        frame.draw(fx, fy);
        item.icon.draw(fx + 1, fy + 1);

        // Write price, activation markers, expiration time, availability etc.
        if (item.inStock > 0 && !item.isActivated()) {
            Printer.print("" + item.cost, Color.YELLOW, right, bottom, true);
        } else if (item.isActivated()) {
            if (item.canExpire) {
                Printer.print(Integer.toString(item.remainingMoves()), Color.MAGENTA, right, top, true);
            }
            Printer.print("АКТ", Color.YELLOW, right + activatedShakeHelper.getShift(), bottom, true);
        } else {
            Printer.print("НЕТ", Theme.SHOP_SIGN_NO.getColor(), right, bottom, true);
        }
    }

    private boolean shiftFrame() {
        if (pressedCountDown > 0) {
            frame = Cache.get(ImageType.SHOP_ITEM_FRAME_PRESSED);
            pressedCountDown--;
            return true;
        } else {
            frame = Cache.get(ImageType.SHOP_ITEM_FRAME);
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
    protected void onLeftTouch() {
        pressedCountDown = PRESSED_DURATION;

        if (item.isActivated()) {
            activatedShakeHelper.startShaking();
            PopUpMessage.show("Уже активировано");
            return;
        }

        if (item.inStock <= 0) {
            PopUpMessage.show("Недоступно");
            return;
        }

        if (item.isUnaffordable()) {
            PopUpMessage.show("Не хватает золота");
            game.shop.showCase.header.moneyShakeHelper.startShaking();
            return;
        }

        game.shop.sell(item);
    }

    @Override
    protected void onRightTouch() {
        game.shop.helpDisplayItem = item;
        Phase.setActive(Phase.ITEM_HELP);
    }
}
