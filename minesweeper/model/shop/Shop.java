package com.javarush.games.minesweeper.model.shop;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.Cache;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.player.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sells various items.
 */

public class Shop {
    private final MinesweeperGame game;
    public final List<ShopSlot> slots = new ArrayList<>();
    public final List<ShopItem> allItems = new ArrayList<>();
    public ShopItem shield;
    public ShopItem scanner;
    public ShopItem flag;
    public ShopItem goldenShovel;
    public ShopItem luckyDice;
    public ShopItem miniBomb;
    public ShopItem helpDisplayItem;

    public Shop(MinesweeperGame game) {
        this.game = game;
        for (int column = 0; column < 2; column++) {
            for (int row = 0; row < 3; row++) {
                int dx = 5 + 25 * row;
                int dy = 21 + 25 * column;
                ShopSlot slot = new ShopSlot(10 + dx, 10 + dy);
                slots.add(slot);
            }
        }
    }

    public void reset() {
        createNewItems();
        fillSlots();
    }

    public void restock(ShopItem item, int amount) {
        item.inStock += amount;
    }

    public void give(ShopItem item) {
        if (item.inStock > 0) {
            game.player.inventory.add(item.id);
            item.inStock--;
        }
    }

    public void sell(ShopItem item) {
        if (item == null) return;
        makeTransaction(item);
        activate(item);
    }

    private void makeTransaction(ShopItem item) {
        item.inStock--;
        game.player.inventory.money -= item.cost;
        game.player.inventory.add(item.id);
    }

    private void activate(ShopItem item) {
        switch (item.id) {
            case SHIELD:
                shield.activate();
                break;
            case SCANNER:
                if (miniBomb.isActivated()) return;
                scanner.activate();
                miniBomb.inStock = 0;
                break;
            case SHOVEL:
                goldenShovel.activate();
                break;
            case DICE:
                luckyDice.activate();
                break;
            case BOMB:
                if (scanner.isActivated()) return;
                miniBomb.activate();
                scanner.inStock = 0;
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

    @DeveloperOption
    public void cheatMoreTools() {
        if (!Options.developerMode) return;

        goldenShovel.activate();
        luckyDice.activate();
        goldenShovel.expireMove += 10;
        luckyDice.expireMove += 10;
        PopUpMessage.show("DEV: 10 TOOLS");
    }

    private void createNewItems() {
        allItems.clear();
        final Cache<ImageType, Image> cache = Image.cache;
        shield = new ShopItem(0, 13 + Options.difficulty / 5, 1, cache.get(ImageType.SHOP_SHOWCASE_SHIELD));
        scanner = new ShopItem(1, 8 + Options.difficulty / 5, 1, cache.get(ImageType.SHOP_SHOWCASE_SCANNER));
        flag = new ShopItem(2, 1, getMaximumFlagsAmount(), cache.get(ImageType.SHOP_SHOWCASE_FLAG));
        goldenShovel = new ShopItem(3, 9, 1, cache.get(ImageType.SHOP_SHOWCASE_SHOVEL));
        luckyDice = new ShopItem(4, 6, 1, cache.get(ImageType.SHOP_SHOWCASE_DICE));
        miniBomb = new ShopItem(5, 6 + Options.difficulty / 10, 1, cache.get(ImageType.SHOP_SHOWCASE_BOMB));
        allItems.addAll(Arrays.asList(shield, scanner, flag, goldenShovel, luckyDice, miniBomb));
    }

    private void fillSlots() {
        for (int i = 0; i < slots.size(); i++) {
            slots.get(i).setItem(allItems.get(i));
        }
    }

    private int getMaximumFlagsAmount() {
        return game.boardManager.getField().countAllCells(Cell.Filter.MINED) - Inventory.INIT_FLAG_NUMBER;
    }

    public void deactivateExpiredItems() {
        goldenShovel.expireCheck();
        luckyDice.expireCheck();
    }
}
