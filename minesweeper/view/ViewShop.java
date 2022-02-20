package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.*;
import com.javarush.games.minesweeper.view.graphics.*;

import java.util.Date;

/**
 * Shows the shop over the game board.
 */

public final class ViewShop extends View {
    public int moneyOnDisplay;                                            // for smooth animation, runs towards money
    public int shakingAnimationMaxTurns = 15;                             // number of turns to shake the item
    public int shakingAnimationCurrentTurn = shakingAnimationMaxTurns;    // counts towards zero

    public ViewShop() {
        this.screen = Screen.SHOP;
    }

    @Override
    public void update() {
        super.update();
        Screen.board.update();

        game.checkTimeOut();

        shakeAnimationCountDown();
        IMAGES_CACHE.get(VisualElement.WIN_SHOP).drawAt(-1, -1);
        IMAGES_CACHE.get(VisualElement.WIN_SHOP_HEADER_FOOTER).drawAt(-1, 10);
        IMAGES_CACHE.get(VisualElement.WIN_SHOP_HEADER_FOOTER).drawAt(-1, 78);
        IMAGES_CACHE.get(VisualElement.SPR_BOARD_MINE).drawAt(10, 10);
        IMAGES_CACHE.get(VisualElement.SPR_BOARD_FLAG).drawAt(39, 11);
        IMAGES_CACHE.get(VisualElement.SHOP_COIN).drawAt(69 + getMoneyShakeValue(), 13);
        makeDisplayMoneyApproachRealMoney();
        Printer.print("" + game.countAllCells(Cell.Filter.DANGEROUS), 22, 12);
        Printer.print("" + game.inventory.getCount(ShopItem.ID.FLAG), 49, 12);
        Printer.print("" + moneyOnDisplay, 75 + getMoneyShakeValue(), 12);
        Printer.print("*** магазин ***", Theme.SHOP_TITLE.getColor(), -1, 22);
        Printer.print("очки:" + game.player.score.getCurrentScore(), Theme.SHOP_SCORE.getColor(), 13, 80);
        Printer.print("шаги:" + game.player.getMoves(), Theme.SHOP_MOVES.getColor(), 84, 80, true);
        displayShopItems();
        game.timer.draw();
        game.shop.goldenShovel.statusBar.draw();
        game.shop.luckyDice.statusBar.draw();
        game.displayDice();
    }

    private void displayShopItems() {
        int right = 30;
        int upper = 30;
        int bottom = 41;
        Image frame;
        Color frameColor;
        int currentFrame = -1; // to detect which frame is being drawn right now
        int shift;             // to shift pushed animation by this value when the button is pressed
        boolean littleTimePassed = (new Date().getTime() - game.shop.lastClickTime < 100);

        for (int y = 0; y < 2; y++) {
            int dy = y * 25 + 1;
            for (int x = 0; x < 3; x++) {
                int dx = x * 25;
                ShopItem item = game.shop.allItems.get(x + y * 3);
                currentFrame++;
                boolean justClickedIt = (currentFrame == game.shop.lastClickedItemNumber && littleTimePassed);
                frame = justClickedIt ? IMAGES_CACHE.get(VisualElement.SHOP_ITEM_FRAME_PRESSED) : IMAGES_CACHE.get(VisualElement.SHOP_ITEM_FRAME);
                shift = justClickedIt ? 1 : 0;
                frameColor = (item.isUnobtainable()) ? Color.RED : Theme.SHOP_ITEM_FRAME_AVAILABLE.getColor();
                frameColor = (item.isActivated()) ? Color.BLUE : frameColor;
                frame.replaceColor(frameColor, 3);
                frame.drawAt(15 + dx + shift, 30 + dy + shift);
                item.icon.drawAt(16 + dx + shift, 31 + dy + shift);

                if (item.inStock > 0 && !item.isActivated()) {
                    Printer.print("" + item.cost, Color.YELLOW, right + dx, bottom + dy, true);
                } else if (item.isActivated()) {
                    if (item.canExpire) {
                        Printer.print(Integer.toString(item.remainingMoves()), Color.MAGENTA, right + dx, upper + dy, true);
                    }
                    Printer.print("АКТ", Color.YELLOW, right + dx - getActShakeValue(currentFrame), bottom + dy, true);
                } else {
                    Printer.print("НЕТ", Theme.SHOP_SIGN_NO.getColor(), right + dx, bottom + dy, true);
                }
            }
        }
    }

    private void makeDisplayMoneyApproachRealMoney() {
        if (moneyOnDisplay < game.inventory.money) {
            moneyOnDisplay++;
        } else if (moneyOnDisplay > game.inventory.money) {
            moneyOnDisplay--;
        }
    }

    private int getMoneyShakeValue() { // to shake money when you can't afford an item
        if (game.shop.isUnaffordableAnimationTrigger) {
            return (evenFrame) ? 1 : 0;
        }
        return 0;
    }

    private int getActShakeValue(int currentFrame) { // to shake ACT sign if the item is activated
        if (currentFrame != game.shop.lastClickedItemNumber) { // shake only in current frame
            return 0;
        }
        if (game.shop.isAlreadyActivatedAnimationTrigger) {
            return (evenFrame) ? 1 : 0;
        }
        return 0;
    }

    public void shakeAnimationCountDown() { // helps to shake elements only for a certain amount of time
        if (shakingAnimationCurrentTurn > 0 && (game.shop.isAlreadyActivatedAnimationTrigger
                || game.shop.isUnaffordableAnimationTrigger)) {
            shakingAnimationCurrentTurn--;
        } else {
            game.shop.isUnaffordableAnimationTrigger = false;
            game.shop.isAlreadyActivatedAnimationTrigger = false;
            shakingAnimationCurrentTurn = shakingAnimationMaxTurns;
        }
    }
}
