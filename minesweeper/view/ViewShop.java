package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.*;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Image;

import java.util.Date;

/**
 * Shows the shop over the game board.
 */

public final class ViewShop extends View {
    public int moneyOnDisplay;                                            // for smooth animation, runs towards money
    public int shakingAnimationMaxTurns = 15;                             // number of turns to shake the item
    public int shakingAnimationCurrentTurn = shakingAnimationMaxTurns;    // counts towards zero

    public ViewShop(MinesweeperGame game) {
        this.game = game;
        this.screenType = Screen.ScreenType.SHOP;
    }

    @Override
    public void display() {
        if (Screen.getType() != Screen.ScreenType.SHOP) { // if changed from screen other than shop, don't animate money
            View.shop.moneyOnDisplay = game.inventory.money;
        }
        super.display();
        game.redrawAllCells();
        shakeAnimationCountDown();
        IMAGES.get(Bitmap.WINDOW_SHOP).drawAt(-1, -1);
        IMAGES.get(Bitmap.WINDOW_SHOP_PANEL).drawAt(-1, 10);
        IMAGES.get(Bitmap.WINDOW_SHOP_PANEL).drawAt(-1, 78);
        IMAGES.get(Bitmap.BOARD_MINE).drawAt(10, 10);
        IMAGES.get(Bitmap.BOARD_FLAG).drawAt(39, 11);
        IMAGES.get(Bitmap.BOARD_COIN).drawAt(69 + getMoneyShakeValue(), 13);
        makeDisplayMoneyApproachRealMoney();
        game.print("" + game.countAllCells(Util.Filter.DANGEROUS), 22, 12);
        game.print("" + game.inventory.getCount(ShopItem.ID.FLAG), 49, 12);
        game.print("" + moneyOnDisplay, 75 + getMoneyShakeValue(), 12);
        game.print("* магазин *", Color.DARKSEAGREEN, 25, 22);
        game.print("очки:" + game.player.score, Color.LIGHTCYAN, 13, 80);
        game.print("шаги:" + game.player.countMoves, Color.LIGHTBLUE, 84, 80, true);
        displayShopItems();
        game.timer.draw();
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
            int dy = y * 25;
            for (int x = 0; x < 3; x++) {
                int dx = x * 25;
                ShopItem item = game.shop.allItems.get(x + y * 3);
                currentFrame++;
                boolean justClickedIt = (currentFrame == game.shop.lastClickedItemNumber && littleTimePassed);
                frame = justClickedIt ? IMAGES.get(Bitmap.ITEM_FRAME_PRESSED) : IMAGES.get(Bitmap.ITEM_FRAME);
                shift = justClickedIt ? 1 : 0;
                frameColor = (item.isUnobtainable()) ? Color.RED : Color.GREEN;
                frameColor = (item.isActivated()) ? Color.BLUE : frameColor;
                frame.replaceColor(frameColor, 3);
                frame.drawAt(15 + dx + shift, 30 + dy + shift);
                item.icon.drawAt(16 + dx + shift, 31 + dy + shift);

                if (item.inStock > 0 && !item.isActivated()) {
                    game.print("" + item.cost, Color.YELLOW, right + dx, bottom + dy, true);
                } else if (item.isActivated()) {
                    if (item.canExpire) {
                        game.print(item.remainingMoves(), Color.MAGENTA, right + dx, upper + dy, true);
                    }
                    game.print("АКТ", Color.YELLOW, right + dx - getActShakeValue(currentFrame), bottom + dy, true);
                } else {
                    game.print("НЕТ", Color.RED, right + dx, bottom + dy, true);
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
            return (game.evenTurn) ? 1 : 0;
        }
        return 0;
    }

    private int getActShakeValue(int currentFrame) { // to shake ACT sign if the item is activated
        if (currentFrame != game.shop.lastClickedItemNumber) { // shake only in current frame
            return 0;
        }
        if (game.shop.isAlreadyActivatedAnimationTrigger) {
            return (game.evenTurn) ? 1 : 0;
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
