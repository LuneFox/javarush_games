package com.javarush.games.minesweeper.model.player;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.shop.Shop;
import com.javarush.games.minesweeper.model.shop.item.ShopItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps track of what player currently has in his bag. Can add, remove, reset items or check their count.
 */

public class Inventory {
    private final Shop shop = MinesweeperGame.getInstance().shop;

    public int money;
    public int displayMoney;
    public final Map<ShopItem, Integer> items = new HashMap<>();

    public void add(ShopItem item) {
        items.put(item, items.get(item) + 1);
    }

    public void remove(ShopItem item) {
        if (items.get(item) <= 0) return;
        items.put(item, items.get(item) - 1);
    }

    public void addMoney(Cell cell) {
        int moneyEarned = cell.getCountMinedNeighbors();
        if (shop.shovel.isActivated()) {
            moneyEarned *= 2;
            cell.makeNumberYellow();
        }
        money += moneyEarned;
    }

    public void reset() {
        money = 0;
        items.clear();
        shop.allItems.forEach(shopItem -> {
            items.put(shopItem, 0);
        });
        shop.give(shop.flag);
        shop.give(shop.flag);
        shop.give(shop.flag);
    }

    // Animation. With each view update money on display slowly approaches the real amount that player has
    public void moneyApproach() {
        if (displayMoney < money) displayMoney++;
        else if (displayMoney > money) displayMoney--;
    }

    @DeveloperOption
    public void cheatMoreMoney() {
        if (!Options.developerMode) return;

        money = 99;
        PopUpMessage.show("DEV: 99 GOLD");
    }

    public int getCount(ShopItem item) {
        return items.get(item);
    }

    public boolean hasNoFlags() {
        return items.get(MinesweeperGame.getInstance().shop.flag) == 0;
    }
}
