package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Picture;
import com.javarush.games.minesweeper.view.View;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Sells various items.
 */

public class Shop {
    final private MinesweeperGame game;
    public ShopItem shield;
    public ShopItem scanner;
    public ShopItem flag;
    public ShopItem goldenShovel;
    public ShopItem luckyDice;
    public ShopItem miniBomb;
    public final LinkedList<ShopItem> allItems = new LinkedList<>();
    public Dice dice;
    public boolean autoBuyFlagsOptionOn;
    public boolean isUnaffordableAnimationTrigger;
    public boolean isAlreadyActivatedAnimationTrigger;

    public Shop(MinesweeperGame game) {
        this.game = game;
    }

    public void sell(ShopItem item) {
        if (item.isActivated()) {
            isAlreadyActivatedAnimationTrigger = true;
            View.shop.shakeAnimationCountDown();
            return;
        } else if (item.inStock == 0) {
            return;
        } else if (item.isUnaffordable()) {
            isUnaffordableAnimationTrigger = true;
            View.shop.shakeAnimationCountDown();
            return;
        } else {
            item.inStock--;
            game.inventory.money -= item.cost;
            game.inventory.add(item.id);
        }
        switch (item.id) {
            case SHIELD:
                shield.activate();
                break;
            case SCANNER:
                if (miniBomb.isActivated()) return;
                scanner.activate();
                purge(miniBomb);
                drawColoredFrame(Color.BLUE);
                break;
            case SHOVEL:
                goldenShovel.activate();
                goldenShovel.expireMove = game.player.countMoves + 5;
                break;
            case DICE:
                luckyDice.activate();
                luckyDice.expireMove = game.player.countMoves + 3;
                break;
            case BOMB:
                if (scanner.isActivated()) return;
                miniBomb.activate();
                purge(scanner);
                drawColoredFrame(Color.RED);
                break;
            default:
                break;
        }
    }

    public void give(ShopItem item) {
        if (item.inStock > 0) {
            game.inventory.add(item.id);
            item.inStock--;
        }
    }

    public void restock(ShopItem item, int amount) {
        item.inStock += amount;
    }

    public void purge(ShopItem item) {
        item.inStock = 0;
    }

    public void reset() {
        shield = new ShopItem(0, 13 + game.difficulty / 5, 1,
                (Picture) View.IMAGES.get(Bitmap.ITEM_SHIELD), game);
        scanner = new ShopItem(1, 8 + game.difficulty / 5, 1,
                (Picture) View.IMAGES.get(Bitmap.ITEM_SCANNER), game);
        flag = new ShopItem(2, 1, getFlagsAmount(),
                (Picture) View.IMAGES.get(Bitmap.ITEM_FLAG), game);
        goldenShovel = new ShopItem(3, 9, 1,
                (Picture) View.IMAGES.get(Bitmap.ITEM_SHOVEL), game);
        luckyDice = new ShopItem(4, 6, 1,
                (Picture) View.IMAGES.get(Bitmap.ITEM_DICE), game);
        miniBomb = new ShopItem(5, 6 + game.difficulty / 10, 1,
                (Picture) View.IMAGES.get(Bitmap.ITEM_BOMB), game);
        allItems.clear();
        allItems.addAll(Arrays.asList(shield, scanner, flag, goldenShovel, luckyDice, miniBomb));
        dice = new Dice(1);
    }

    private int getFlagsAmount() {
        return game.countAllCells(MinesweeperGame.Filter.MINED) - game.inventory.INIT_FLAGS;
    }

    private void drawColoredFrame(Color color) {
        View.IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).replaceColor(color, 3);
        View.IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).draw();
    }

    public ShopItem getClickedItem(int x, int y) {
        ShopItem[] result = new ShopItem[1];
        allItems.forEach(shopItem -> {
            boolean isAtX = (x >= shopItem.shopFramePosition[0] && x <= shopItem.shopFramePosition[1]);
            boolean isAtY = (y >= shopItem.shopFramePosition[2] && y <= shopItem.shopFramePosition[3]);
            if (isAtX && isAtY) result[0] = shopItem;
        });
        return result[0];
    }
}
