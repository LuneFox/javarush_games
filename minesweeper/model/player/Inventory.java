package com.javarush.games.minesweeper.model.player;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.shop.ShopItem;

import java.util.Arrays;
import java.util.EnumMap;

import java.util.Map;

/**
 * Keeps track of what player currently has in his bag. Can add, remove, reset items or check their count.
 */

public class Inventory {
    public int money;
    public int displayMoney;
    public static final int INIT_FLAG_NUMBER = 3;
    public Map<ShopItem.ID, Integer> items = new EnumMap<>(ShopItem.ID.class);

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

    public void addMoney(Cell cell) {
        ShopItem shovel = MinesweeperGame.getInstance().shop.goldenShovel;
        int moneyEarned = cell.countMinedNeighbors;
        if (shovel.isActivated()) {
            moneyEarned *= 2;
            cell.makeNumberYellow();
        }
        money += moneyEarned;
    }

    public void reset() {
        Arrays.stream(ShopItem.ID.values())
                .forEach(id -> items.put(id, (id == ShopItem.ID.FLAG) ? INIT_FLAG_NUMBER : 0));
        money = 0;
    }

    // Animation. With each view update money on display slowly approaches the real amount that player has
    public void moneyApproach() {
        if (displayMoney < money) displayMoney++;
        else if (displayMoney > money) displayMoney--;
    }

    public int getCount(ShopItem.ID itemID) {
        return items.get(itemID);
    }

    public boolean hasNoFlags() {
        return items.get(ShopItem.ID.FLAG) == 0;
    }
}
