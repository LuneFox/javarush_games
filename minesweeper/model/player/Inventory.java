package com.javarush.games.minesweeper.model.player;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.shop.item.ShopItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps track of what player currently has in his bag. Can add, remove, reset items or check their count.
 */

public class Inventory {
    private final MinesweeperGame game;
    private int money;
    private int displayMoney;
    private final Map<ShopItem, Integer> items = new HashMap<>();

    public Inventory(MinesweeperGame game) {
        this.game = game;
    }

    public void add(ShopItem item) {
        items.put(item, items.get(item) + 1);
    }

    public void remove(ShopItem item) {
        if (items.get(item) <= 0) return;
        items.put(item, items.get(item) - 1);
    }

    public void addMoney(Cell cell) {
        int moneyEarned = cell.getCountMinedNeighbors();
        if (game.getShop().getShovel().isActivated()) {
            moneyEarned *= 2;
            cell.makeNumberYellow();
        }
        money += moneyEarned;
    }

    public void reset() {
        money = 0;
        items.clear();

        game.getShop().getAllItems().forEach(shopItem -> items.put(shopItem, 0));

        for (int i = 0; i < 3; i++) {
            game.getShop().giveFlag();
        }
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
        ShopItem flag = game.getShop().getFlag();
        return items.get(flag) == 0;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getDisplayMoney() {
        return displayMoney;
    }

    public void setDisplayMoney(int displayMoney) {
        this.displayMoney = displayMoney;
    }
}
