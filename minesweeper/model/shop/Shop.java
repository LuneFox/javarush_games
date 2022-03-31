package com.javarush.games.minesweeper.model.shop;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.shop.item.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sells various items.
 */

public class Shop {
    private final MinesweeperGame game;
    public final List<ShopSlot> showCaseSlots = new ArrayList<>();
    public final List<ShopItem> allItems = new ArrayList<>();
    public Shield shield;
    public Scanner scanner;
    public Flag flag;
    public Shovel shovel;
    public Dice dice;
    public Bomb bomb;
    public ShopItem helpDisplayItem;

    public Shop(MinesweeperGame game) {
        this.game = game;
        createSlots();
    }

    public void reset() {
        createNewItems();
        fillSlots();
    }

    public void restock(ShopItem item, int amount) {
        item.setInStock(item.getInStock() + amount);
    }

    public void give(ShopItem item) {
        if (item.getInStock() > 0) {
            game.player.inventory.add(item);
            item.setInStock(item.getInStock() - 1);
        }
    }

    public void sell(ShopItem item) {
        if (item == null) return;
        makeTransaction(item);
        activate(item);
    }

    private void makeTransaction(ShopItem item) {
        item.setInStock(item.getInStock() - 1);
        game.player.inventory.money -= item.getCost();
        game.player.inventory.add(item);
    }

    private void activate(ShopItem item) {
        item.activate();
    }

    public void offerFlag() {
        if (!Options.autoBuyFlagsSelector.isEnabled() && flag.getInStock() > 0) {
            PopUpMessage.show("Купите флажок!");
            Phase.setActive(Phase.SHOP);
            return;
        }
        if (flag.isUnobtainable()) {
            PopUpMessage.show("Невозможно купить!");
            return;
        }
        PopUpMessage.show("Куплен флажок");
        sell(flag);
    }

    @DeveloperOption
    public void cheatMoreTools() {
        if (!Options.developerMode) return;

        shovel.activate();
        dice.activate();
        shovel.setExpireMove(game.player.getMoves() + 99);
        dice.setExpireMove(game.player.getMoves() + 99);
        PopUpMessage.show("DEV: 99 TOOLS");
    }

    private void createNewItems() {
        allItems.clear();
        shield = new Shield();
        scanner = new Scanner();
        flag = new Flag();
        shovel = new Shovel();
        dice = new Dice();
        bomb = new Bomb();
        allItems.addAll(Arrays.asList(shield, scanner, flag, shovel, dice, bomb));
    }

    private void createSlots() {
        for (int column = 0; column < 2; column++) {
            for (int row = 0; row < 3; row++) {
                int dx = 5 + 25 * row;
                int dy = 21 + 25 * column;
                ShopSlot slot = new ShopSlot(10 + dx, 10 + dy);
                showCaseSlots.add(slot);
            }
        }
    }

    private void fillSlots() {
        for (int i = 0; i < showCaseSlots.size(); i++) {
            showCaseSlots.get(i).setItem(allItems.get(i));
        }
    }

    public void checkExpiredItems() {
        shovel.checkExpiration();
        dice.checkExpiration();
    }
}
