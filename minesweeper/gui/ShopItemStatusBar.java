package com.javarush.games.minesweeper.gui;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.shop.item.ShopItem;

public class ShopItemStatusBar extends InteractiveObject {
    private final Color color;
    private final ShopItem item;

    public ShopItemStatusBar(int y, Color color, ShopItem item) {
        this.x = 0;
        this.y = y;
        this.color = color;
        this.item = item;
    }

    public void draw() {
        for (int i = 0; i < item.countRemainingMoves() * 2; i += 2) {
            game.drawPixel(x, y - i, color);
        }
    }
}
