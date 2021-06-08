package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Picture;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Sells various items.
 */

public class Shop {
    final private MinesweeperGame game;
    public ShopItem shield;
    public ShopItem scanner;
    public ShopItem flag;
    public ShopItem goldenShovel;
    public ShopItem luckyDice;
    public ShopItem miniBomb;
    public final LinkedList<ShopItem> allItems = new LinkedList<>();
    public Dice dice;

    public Shop(MinesweeperGame game) {
        this.game = game;
    }

    public void sell(ShopItem item) {
        if (item.isUnobtainableOrActivated()) {
            return;
        } else {
            item.inStock--;
            game.inventory.money -= item.cost;
            game.inventory.add(item.id);
        }
        switch (item.id) {
            case SHIELD:
                shield.activate();
                break;
            case SCANNER:
                if (miniBomb.isActivated()) return;
                scanner.activate();
                removeAll(miniBomb);
                drawColoredFrame(Color.BLUE);
                break;
            case SHOVEL:
                goldenShovel.activate();
                goldenShovel.expireMove = game.player.countMoves + 5;
                break;
            case DICE:
                luckyDice.activate();
                luckyDice.expireMove = game.player.countMoves + 3;
                break;
            case BOMB:
                if (scanner.isActivated()) return;
                miniBomb.activate();
                removeAll(scanner);
                drawColoredFrame(Color.RED);
                break;
            default:
                break;
        }
        game.menu.displayShop();
    }

    public void give(ShopItem item) {
        if (item.inStock > 0) {
            game.inventory.add(item.id);
            item.inStock--;
        }
    }

    public void restock(ShopItem item, int amount) {
        item.inStock += amount;
    }

    public void removeAll(ShopItem item) {
        item.inStock = 0;
    }

    public void reset() {
        this.shield = new ShopItem(0, 13 + game.difficulty / 5, 1,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_SHIELD), game);
        this.scanner = new ShopItem(1, 8 + game.difficulty / 5, 1,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_SCANNER), game);
        this.flag = new ShopItem(2, 1, game.countAllCells(MinesweeperGame.Filter.MINED) - game.inventory.INIT_FLAGS,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_FLAG), game);
        this.goldenShovel = new ShopItem(3, 9, 1,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_SHOVEL), game);
        this.luckyDice = new ShopItem(4, 6, 1,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_DICE), game);
        this.miniBomb = new ShopItem(5, 6 + game.difficulty / 10, 1,
                (Picture) Menu.IMAGES.get(Bitmap.ITEM_BOMB), game);
        this.allItems.clear();
        this.allItems.addAll(Arrays.asList(shield, scanner, flag,
                goldenShovel, luckyDice, miniBomb));
        this.dice = new Dice(1);
    }

    private void drawColoredFrame(Color color) {
        Menu.IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).replaceColor(color, 3);
        Menu.IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).draw();
    }
}
