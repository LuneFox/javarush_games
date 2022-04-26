package com.javarush.games.minesweeper.model.player;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.shop.Shop;
import com.javarush.games.minesweeper.model.shop.item.ShopItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps track of what player currently has in his bag. Can put, remove, reset items or check their count.
 */

public class Inventory {
    private final MinesweeperGame game;
    private int money;
    private int displayMoney;
    private final Map<ShopItem, Integer> items = new HashMap<>();

    public Inventory(MinesweeperGame game) {
        this.game = game;
    }

    public void put(ShopItem item) {
        items.putIfAbsent(item, 0);
        items.put(item, count(item) + 1);
    }

    public int countFlags() {
        return count(game.getShop().getFlag());
    }

    public void remove(ShopItem item) {
        int count = count(item);
        if (count <= 0) return;
        items.put(item, count - 1);
    }

    public void reset() {
        money = 0;
        items.clear();
        final Shop shop = game.getShop();
        for (int i = 0; i < 3; i++) {
            shop.give(shop.getFlag());
        }
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount) {
        money += amount;
    }

    void removeMoney(int amount) {
        money -= amount;
    }

    public int shiftDisplayMoney() {
        if (displayMoney < money) displayMoney++;
        else if (displayMoney > money) displayMoney--;
        return displayMoney;
    }

    public void skipMoneyAnimation() {
        displayMoney = money;
    }

    @DeveloperOption
    public void cheatMoney() {
        if (!Options.developerMode) return;

        money = 99;
        PopUpMessage.show("DEV: 99 GOLD");
    }

    private int count(ShopItem item) {
        return items.getOrDefault(item, 0);
    }
}
