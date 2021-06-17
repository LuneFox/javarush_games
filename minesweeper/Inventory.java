package com.javarush.games.minesweeper;

import java.util.HashMap;

/**
 * Keeps track of what player currently has in his bag. Can add, remove, reset items or check their count.
 */

public class Inventory {
    public int money;
    public final int INIT_FLAGS = 3;
    public HashMap<ShopItem.ID, Integer> items;

    public Inventory() {
        reset();
    }

    public void reset() {
        this.money = 0;
        this.items = new HashMap<>();
        this.items.put(ShopItem.ID.SHIELD, 0);
        this.items.put(ShopItem.ID.SCANNER, 0);
        this.items.put(ShopItem.ID.FLAG, INIT_FLAGS);
        this.items.put(ShopItem.ID.SHOVEL, 0);
        this.items.put(ShopItem.ID.DICE, 0);
        this.items.put(ShopItem.ID.BOMB, 0);
    }

    public void add(ShopItem.ID itemID) {
        this.items.put(itemID, this.items.get(itemID) + 1);
    }

    public void remove(ShopItem.ID itemID) {
        if (this.items.get(itemID) <= 0) return;
        this.items.put(itemID, this.items.get(itemID) - 1);
    }

    public int getCount(ShopItem.ID itemID) {
        return this.items.get(itemID);
    }
}
