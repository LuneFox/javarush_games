package com.javarush.games.minesweeper.model.shop;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.board.Dice;
import com.javarush.games.minesweeper.model.options.Options;
import com.javarush.games.minesweeper.model.player.Inventory;
import com.javarush.games.minesweeper.view.graphics.Cache;
import com.javarush.games.minesweeper.view.graphics.VisualElement;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Sells various items.
 */

public class Shop {
    final private MinesweeperGame game = MinesweeperGame.getInstance();
    public double lastClickTime;
    public int lastClickedItemNumber;
    public final List<ShopItem> allItems = new LinkedList<>();
    public ShopItem shield;
    public ShopItem scanner;
    public ShopItem flag;
    public ShopItem goldenShovel;
    public ShopItem luckyDice;
    public ShopItem miniBomb;
    public Dice dice;


    public void sell(ShopItem item) {
        if (item == null) return;
        if (!isPurchasable(item)) return;
        // Make transaction
        item.inStock--;
        game.player.inventory.money -= item.cost;
        game.player.inventory.add(item.id);
        // Apply item effect
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
                goldenShovel.expireMove = game.player.getMoves() + goldenShovel.effectDuration;
                break;
            case DICE:
                luckyDice.activate();
                luckyDice.expireMove = game.player.getMoves() + luckyDice.effectDuration;
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

    public void offerFlag() {
        if (!Options.autoBuyFlagsEnabled && flag.inStock > 0) {
            Screen.set(Screen.SHOP);
            return;
        }
        if (flag.isUnobtainable()) {
            return;
        }
        sell(flag);
    }

    private boolean isPurchasable(ShopItem item) {
        // Item is not activated, affordable and exists in stock
        if (item.isActivated()) {
            Screen.shop.isAlreadyActivatedAnimationTrigger = true;
            Screen.shop.shakeAnimationCountDown();
            return false;
        } else if (item.inStock == 0) {
            return false;
        } else if (item.isUnaffordable()) {
            Screen.shop.isUnaffordableAnimationTrigger = true;
            Screen.shop.shakeAnimationCountDown();
            return false;
        } else {
            return true;
        }
    }

    public void give(ShopItem item) {
        if (item.inStock > 0) {
            game.player.inventory.add(item.id);
            item.inStock--;
        }
    }

    public void rememberLastClickOnItem(ShopItem item) {
        if (item == null) return;
        lastClickTime = new Date().getTime();
        lastClickedItemNumber = item.number;
    }

    public void restock(ShopItem item, int amount) {
        item.inStock += amount;
    }

    public void purge(ShopItem item) {
        item.inStock = 0;
    }

    public void reset() {
        shield = new ShopItem(0, 13 + Options.difficulty / 5, 1, Cache.get(VisualElement.SHOP_ITEM_SHIELD));
        scanner = new ShopItem(1, 8 + Options.difficulty / 5, 1, Cache.get(VisualElement.SHOP_ITEM_SCANNER));
        flag = new ShopItem(2, 1, getFlagsAmount(), Cache.get(VisualElement.SHOP_ITEM_FLAG));
        goldenShovel = new ShopItem(3, 9, 1, Cache.get(VisualElement.SHOP_ITEM_SHOVEL));
        luckyDice = new ShopItem(4, 6, 1, Cache.get(VisualElement.SHOP_ITEM_DICE));
        miniBomb = new ShopItem(5, 6 + Options.difficulty / 10, 1, Cache.get(VisualElement.SHOP_ITEM_BOMB));
        allItems.clear();
        allItems.addAll(Arrays.asList(shield, scanner, flag, goldenShovel, luckyDice, miniBomb));
        dice = new Dice(1);
    }

    private int getFlagsAmount() {
        return game.field.countAllCells(Cell.Filter.MINED) - Inventory.INIT_FLAG_NUMBER;
    }

    private void drawColoredFrame(Color color) {
        Cache.get(VisualElement.WIN_BOARD_TRANSPARENT_FRAME).replaceColor(color, 3);
        Cache.get(VisualElement.WIN_BOARD_TRANSPARENT_FRAME).draw();
    }

    public ShopItem getClickedItem(int x, int y) { // checks click coordinates and if item has them, returns it
        ShopItem[] result = new ShopItem[1];
        allItems.forEach(shopItem -> {
            boolean isAtX = (x >= shopItem.shopFramePosition[0] && x <= shopItem.shopFramePosition[1]);
            boolean isAtY = (y >= shopItem.shopFramePosition[2] && y <= shopItem.shopFramePosition[3]);
            if (isAtX && isAtY) result[0] = shopItem;
        });
        return result[0];
    }

    public void deactivateExpiredItems() {
        goldenShovel.expireCheck();
        luckyDice.expireCheck();
    }
}
