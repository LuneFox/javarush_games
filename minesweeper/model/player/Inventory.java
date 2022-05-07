package com.javarush.games.minesweeper.model.player;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.shop.items.ShopItem;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private int money;
    private final Map<ShopItem, Integer> items = new HashMap<>();

    public Inventory() {
    }

    void reset() {
        money = 0;
        items.clear();
    }

    void putItem(ShopItem item) {
        items.putIfAbsent(item, 0);
        items.put(item, count(item) + 1);
    }

    void removeItem(ShopItem item) {
        int count = count(item);
        if (count <= 0) return;
        items.put(item, count - 1);
    }

    int count(ShopItem item) {
        return items.getOrDefault(item, 0);
    }

    void putMoney(int amount) {
        money += amount;
    }

    void removeMoney(int amount) {
        money -= amount;
    }

    int getMoney() {
        return money;
    }

    @DeveloperOption
    void cheatMoney() {
        if (!Options.developerModeEnabled) return;
        money = 99;
        PopUpMessage.show("DEV: 99 GOLD");
    }
}
