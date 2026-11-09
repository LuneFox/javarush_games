package com.javarush.games.minesweeper.model.shop;

import com.javarush.games.minesweeper.Text;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.ShakeHelper;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.shop.items.ShopItem;

/** Интерактивная ячейка витрины магазина. */
public class ShopSlot extends InteractiveObject {
    /** Длительность нажатия. */
    private static final int PRESS_DURATION = 5;
    /** Глубина нажатия. */
    private static final int PRESS_DEPTH = 1;
    /** Оставшееся время нажатия. */
    private int pressedCountDown;

    /** Предмет магазина. */
    private ShopItem item;
    /** Рамка прицела или ячейки. */
    private Image frame;
    /** Анимация дрожания активного предмета. */
    private final ShakeHelper activatedShaker = new ShakeHelper();

    /** Создаёт экземпляр ShopSlot. */
    public ShopSlot(int x, int y) {
        frame = Image.cache.get(ImageType.SHOP_SHOWCASE_FRAME);
        this.x = x;
        this.y = y;
        this.height = 20;
        this.width = 20;
    }

    /** Рисует объект на экране. */
    @Override
    public void draw() {
        final int shift = shiftFrame() ? PRESS_DEPTH : 0;
        final int frameX = x + shift;
        final int frameY = y + shift;
        changeFrameColor();
        frame.draw(frameX, frameY);
        item.drawIcon(frameX + 1, frameY + 1);
        printInfoOverlay();
    }

    /** Сдвигает рамку при нажатии. */
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

    /** Меняет цвет рамки по состоянию предмета. */
    private void changeFrameColor() {
        Color frameColor;
        frameColor = (item.isUnobtainable()) ? Color.RED : Theme.SHOP_ITEM_FRAME_AVAILABLE.getColor();
        frameColor = (item.isActivated()) ? Color.BLUE : frameColor;
        frame.changeColor(frameColor, 3);
    }

    /** Печатает стоимость или статус предмета. */
    private void printInfoOverlay() {
        if (item.getInStock() > 0 && !item.isActivated()) {
            printItemCost();
        } else if (item.isActivated()) {
            printActivatedStatus();
        } else {
            printOutOfStockMarker();
        }
    }

    /** Печатает стоимость предмета. */
    private void printItemCost() {
        Printer.print("<" + item.getCost() + ">", Color.YELLOW, x + 18, y + 10, Printer.Align.RIGHT);
    }

    /** Печатает статус активного предмета. */
    private void printActivatedStatus() {
        Printer.print(Text.SHOP_ACTIVE.getText(), Color.YELLOW, x + 18 + activatedShaker.getShift(), y + 10, Printer.Align.RIGHT);
        Printer.print(item.getRemainingMovesText(), Color.MAGENTA, x + 18, y, Printer.Align.RIGHT);
    }

    /** Печатает отметку об отсутствии товара. */
    private void printOutOfStockMarker() {
        Printer.print(Text.SHOP_OUT_OF_STOCK.getText(), Theme.SHOP_SIGN_NO.getColor(), x + 18, y + 10, Printer.Align.RIGHT);
    }

    /** Обрабатывает левый клик. */
    @Override
    public void onLeftClick() {
        pressedCountDown = PRESS_DURATION;

        if (item.isActivated()) {
            activatedShaker.startShaking();
            game.showMessage(Text.SHOP_ALREADY_ACTIVE.getText());
            return;
        }

        if (item.getInStock() <= 0) {
            game.showMessage(Text.SHOP_NOT_FOR_SALE.getText());
            return;
        }

        if (item.isUnaffordable()) {
            game.shakeShopMoney();
            game.showMessage(Text.SHOP_NOT_ENOUGH_GOLD.getText());
            return;
        }

        game.getShop().sell(item);
    }

    /** Обрабатывает правый клик. */
    @Override
    public void onRightClick() {
        game.openItemHelp(item);
    }

    /** Задаёт предмет. */
    public void setItem(ShopItem item) {
        this.item = item;
    }
}
