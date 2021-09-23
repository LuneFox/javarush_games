package com.javarush.games.minesweeper;

import java.util.HashMap;

/**
 * Keeps track of what player currently has in his bag. Can add, remove, reset items or check their count.
 */

public class Inventory {
    public int money;
    public static final int INIT_FLAGS = 3;
    public HashMap<ShopItem.ID, Integer> items;

    public Inventory() {
        reset();
    }

    public void reset() {
        money = 0;
        items = new HashMap<>();
        items.put(ShopItem.ID.SHIELD, 0);
        items.put(ShopItem.ID.SCANNER, 0);
        items.put(ShopItem.ID.FLAG, INIT_FLAGS);
        items.put(ShopItem.ID.SHOVEL, 0);
        items.put(ShopItem.ID.DICE, 0);
        items.put(ShopItem.ID.BOMB, 0);
    }

    public void add(ShopItem.ID itemID) {
        items.put(itemID, items.get(itemID) + 1);
    }

    public void remove(ShopItem.ID itemID) {
        if (items.get(itemID) <= 0) return;
        items.put(itemID, items.get(itemID) - 1);
    }

    public int getCount(ShopItem.ID itemID) {
        return items.get(itemID);
    }

    public boolean hasNoFlags() {
        return items.get(ShopItem.ID.FLAG) == 0;
    }
}
