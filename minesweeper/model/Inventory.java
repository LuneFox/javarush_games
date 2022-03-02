package com.javarush.games.minesweeper.model;

import java.util.HashMap;

/**
 * Keeps track of what player currently has in his bag. Can add, remove, reset items or check their count.
 */

public class Inventory {
    public int money;
    public static final int INIT_FLAG_NUMBER = 3;
    public HashMap<ShopItem.ID, Integer> items = new HashMap<>();

    public Inventory() {
        reset();
    }

    public void add(ShopItem.ID itemID) {
        items.put(itemID, items.get(itemID) + 1);
    }

    public void remove(ShopItem.ID itemID) {
        if (items.get(itemID) <= 0) return;
        items.put(itemID, items.get(itemID) - 1);
    }

    public void reset() {
        for (ShopItem.ID item : ShopItem.ID.values()) {
            if (item == ShopItem.ID.FLAG) items.put(item, INIT_FLAG_NUMBER);
            else items.put(item, 0);
        }
        money = 0;
    }

    public int getCount(ShopItem.ID itemID) {
        return items.get(itemID);
    }

    public boolean hasNoFlags() {
        return items.get(ShopItem.ID.FLAG) == 0;
    }
}
