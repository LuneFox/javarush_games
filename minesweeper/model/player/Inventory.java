package com.javarush.games.minesweeper.model.player;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.shop.items.ShopItem;

import java.util.HashMap;
import java.util.Map;

/** Золото и предметы игрока. */
public class Inventory {
    /** Золото. */
    private int money;
    /** Предметы. */
    private final Map<ShopItem, Integer> items = new HashMap<>();

    /** Создаёт экземпляр Inventory. */
    public Inventory() {
    }

    /** Сбрасывает состояние в начальное. */
    void reset() {
        money = 0;
        items.clear();
    }

    /** Добавляет предмет в инвентарь. */
    void putItem(ShopItem item) {
        items.putIfAbsent(item, 0);
        items.put(item, count(item) + 1);
    }

    /** Убирает предмет из инвентаря. */
    void removeItem(ShopItem item) {
        int count = count(item);
        if (count <= 0) return;
        items.put(item, count - 1);
    }

    /** Считает клетки по условию. */
    int count(ShopItem item) {
        return items.getOrDefault(item, 0);
    }

    /** Добавляет золото. */
    void putMoney(int amount) {
        money += amount;
    }

    /** Списывает золото. */
    void removeMoney(int amount) {
        money -= amount;
    }

    /** Возвращает баланс золота. */
    int getMoney() {
        return money;
    }

    /** Устанавливает 99 золота. */
    @DeveloperOption
    void cheatMoney() {
        if (!Options.isDeveloperModeEnabled()) return;
        money = 99;
    }
}
