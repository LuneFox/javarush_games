package com.javarush.games.minesweeper.model.shop;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Message;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.options.Options;
import com.javarush.games.minesweeper.model.player.Inventory;
import com.javarush.games.minesweeper.model.shop.overlay.Footer;
import com.javarush.games.minesweeper.model.shop.overlay.Header;
import com.javarush.games.minesweeper.model.shop.overlay.ShowCase;
import com.javarush.games.minesweeper.view.graphics.Cache;
import com.javarush.games.minesweeper.view.graphics.VisualElement;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Sells various items.
 */

public class Shop {
    final static private MinesweeperGame game = MinesweeperGame.getInstance();

    // Usable items on sale
    public final List<ShopItem> allItems = new LinkedList<>();
    public ShopItem shield;
    public ShopItem scanner;
    public ShopItem flag;
    public ShopItem goldenShovel;
    public ShopItem luckyDice;
    public ShopItem miniBomb;

    public ShowCase showCase;
    public ShopItem helpDisplayItem;

    public void sell(ShopItem item) {
        if (item == null) return;
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
                break;
            default:
                break;
        }
    }

    public void offerFlag() {
        if (!Options.autoBuyFlagsSelector.isEnabled() && flag.inStock > 0) {
            Message.show("Купите флажок!");
            Screen.setActive(Screen.SHOP);
            return;
        }
        if (flag.isUnobtainable()) {
            Message.show("Невозможно купить!");
            return;
        }
        Message.show("Куплен флажок");
        sell(flag);
    }

    public void give(ShopItem item) {
        if (item.inStock > 0) {
            game.player.inventory.add(item.id);
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
        shield = new ShopItem(0, 13 + Options.difficulty / 5, 1, Cache.get(VisualElement.SHOP_ITEM_SHIELD));
        scanner = new ShopItem(1, 8 + Options.difficulty / 5, 1, Cache.get(VisualElement.SHOP_ITEM_SCANNER));
        flag = new ShopItem(2, 1, getFlagsAmount(), Cache.get(VisualElement.SHOP_ITEM_FLAG));
        goldenShovel = new ShopItem(3, 9, 1, Cache.get(VisualElement.SHOP_ITEM_SHOVEL));
        luckyDice = new ShopItem(4, 6, 1, Cache.get(VisualElement.SHOP_ITEM_DICE));
        miniBomb = new ShopItem(5, 6 + Options.difficulty / 10, 1, Cache.get(VisualElement.SHOP_ITEM_BOMB));

        allItems.clear();
        allItems.addAll(Arrays.asList(shield, scanner, flag, goldenShovel, luckyDice, miniBomb));

        showCase = new ShowCase();
    }

    private int getFlagsAmount() {
        return game.field.countAllCells(Cell.Filter.MINED) - Inventory.INIT_FLAG_NUMBER;
    }

    public void deactivateExpiredItems() {
        goldenShovel.expireCheck();
        luckyDice.expireCheck();
    }
}
