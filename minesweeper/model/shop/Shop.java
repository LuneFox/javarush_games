package com.javarush.games.minesweeper.model.shop;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.player.Inventory;
import com.javarush.games.minesweeper.gui.image.ImageType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sells various items.
 */

public class Shop {
    final static private MinesweeperGame game = MinesweeperGame.getInstance();

    // Usable items on sale
    public final List<ShopItem> allItems = new ArrayList<>();
    public final List<ShopSlot> shopSlots = new ArrayList<>();
    public ShopItem shield;
    public ShopItem scanner;
    public ShopItem flag;
    public ShopItem goldenShovel;
    public ShopItem luckyDice;
    public ShopItem miniBomb;

    public ShopItem helpDisplayItem;

    public Shop() {
        for (int column = 0; column < 2; column++) {
            for (int row = 0; row < 3; row++) {
                int dx = 5 + 25 * row;
                int dy = 21 + 25 * column;
                ShopSlot slot = new ShopSlot(10 + dx, 10 + dy);
                shopSlots.add(slot);
            }
        }
    }

    public void sell(ShopItem item) {
        if (item == null) return;
        // Make transaction
        item.inStock--;
        game.player.inventory.money -= item.cost;
        game.player.inventory.add(item.id);
        // Apply item effect
        switch (item.id) {
            case SHIELD:
                shield.activate();
                break;
            case SCANNER:
                if (miniBomb.isActivated()) return;
                scanner.activate();
                purge(miniBomb);
                break;
            case SHOVEL:
                goldenShovel.activate();
                goldenShovel.expireMove = game.player.getMoves() + goldenShovel.effectDuration;
                break;
            case DICE:
                luckyDice.activate();
                luckyDice.expireMove = game.player.getMoves() + luckyDice.effectDuration;
                break;
            case BOMB:
                if (scanner.isActivated()) return;
                miniBomb.activate();
                purge(scanner);
                break;
            default:
                break;
        }
    }

    public void offerFlag() {
        if (!Options.autoBuyFlagsSelector.isEnabled() && flag.inStock > 0) {
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

    public void give(ShopItem item) {
        if (item.inStock > 0) {
            game.player.inventory.add(item.id);
            item.inStock--;
        }
    }

    public void restock(ShopItem item, int amount) {
        item.inStock += amount;
    }

    public void purge(ShopItem item) {
        item.inStock = 0;
    }

    public void reset() {
        // Create new items
        shield = new ShopItem(0, 13 + Options.difficulty / 5, 1, Image.cache.get(ImageType.SHOP_SHOWCASE_SHIELD));
        scanner = new ShopItem(1, 8 + Options.difficulty / 5, 1, Image.cache.get(ImageType.SHOP_SHOWCASE_SCANNER));
        flag = new ShopItem(2, 1, getFlagsAmount(), Image.cache.get(ImageType.SHOP_SHOWCASE_FLAG));
        goldenShovel = new ShopItem(3, 9, 1, Image.cache.get(ImageType.SHOP_SHOWCASE_SHOVEL));
        luckyDice = new ShopItem(4, 6, 1, Image.cache.get(ImageType.SHOP_SHOWCASE_DICE));
        miniBomb = new ShopItem(5, 6 + Options.difficulty / 10, 1, Image.cache.get(ImageType.SHOP_SHOWCASE_BOMB));

        // Replace old items
        allItems.clear();
        allItems.addAll(Arrays.asList(shield, scanner, flag, goldenShovel, luckyDice, miniBomb));

        // Put new items to slots
        for (int i = 0; i < shopSlots.size(); i++) {
            shopSlots.get(i).setItem(allItems.get(i));
        }
    }

    private int getFlagsAmount() {
        return game.field.countAllCells(Cell.Filter.MINED) - Inventory.INIT_FLAG_NUMBER;
    }

    public void deactivateExpiredItems() {
        goldenShovel.expireCheck();
        luckyDice.expireCheck();
    }
}
