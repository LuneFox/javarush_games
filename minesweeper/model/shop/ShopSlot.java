package com.javarush.games.minesweeper.model.shop;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.ShakeHelper;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.shop.item.ShopItem;
import com.javarush.games.minesweeper.view.impl.ViewShop;

/**
 * Clicking on this slot will sell the player the corresponding item.
 */

public class ShopSlot extends InteractiveObject {
    private static final int PRESSED_DURATION = 5;
    private static final int PRESS_DEPTH = 1;
    private int pressedCountDown;

    private ShopItem item;
    private Image frame;
    private final ShakeHelper activatedShaker = new ShakeHelper();

    public ShopSlot(int x, int y) {
        frame = Image.cache.get(ImageType.SHOP_SHOWCASE_FRAME);
        this.x = x;
        this.y = y;
        this.height = 20;
        this.width = 20;
    }

    @Override
    public void draw() {
        final int shift = shiftFrame() ? PRESS_DEPTH : 0;
        final int fx = x + shift;
        final int fy = y + shift;
        changeFrameColor();
        frame.draw(fx, fy);
        item.getIcon().draw(fx + 1, fy + 1);
        printInfo();
    }

    private void printInfo() {
        final int top = y;
        final int right = x + 14;
        final int bottom = y + 10;

        if (item.inStock() > 0 && !item.isActivated()) {
            Printer.print("<" + item.getCost() + ">", Color.YELLOW, right, bottom, true);
        } else if (item.isActivated()) {
            Printer.print("<АКТ>", Color.YELLOW, right + activatedShaker.getShift(), bottom, true);
            Printer.print(item.getRemainingMovesText(), Color.MAGENTA, right, top, true);
        } else {
            Printer.print("<НЕТ>", Theme.SHOP_SIGN_NO.getColor(), right, bottom, true);
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

    private void changeFrameColor() {
        Color frameColor;
        frameColor = (item.isUnobtainable()) ? Color.RED : Theme.SHOP_ITEM_FRAME_AVAILABLE.getColor();
        frameColor = (item.isActivated()) ? Color.BLUE : frameColor;
        frame.replaceColor(frameColor, 3);
    }

    @Override
    public void onLeftClick() {
        pressedCountDown = PRESSED_DURATION;

        if (item.isActivated()) {
            activatedShaker.startShaking();
            PopUpMessage.show("Уже активировано");
            return;
        }

        if (item.inStock() <= 0) {
            PopUpMessage.show("Нет в продаже");
            return;
        }

        if (item.isUnaffordable()) {
            ViewShop.shakeMoney();
            PopUpMessage.show("Не хватает золота");
            return;
        }

        game.getShop().sell(item);
    }

    public void setItem(ShopItem item) {
        this.item = item;
    }

    @Override
    public void onRightClick() {
        game.getShop().setHelpDisplayItem(item);
        Phase.setActive(Phase.ITEM_HELP);
    }
}
