package com.javarush.games.minesweeper.gui;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.shop.items.ShopItem;

/** Полоса оставшегося времени предмета. */
public class ShopItemStatusBar extends InteractiveObject {
    /** Цвет. */
    private final Color color;
    /** Предмет магазина. */
    private final ShopItem item;

    /** Создаёт экземпляр ShopItemStatusBar. */
    public ShopItemStatusBar(int y, Color color, ShopItem item) {
        this.x = 0;
        this.y = y;
        this.color = color;
        this.item = item;
    }

    /** Рисует объект на экране. */
    public void draw() {
        for (int i = 0; i < item.countRemainingMoves() * 2; i += 2) {
            game.drawPixel(x, y - i, color);
        }
    }
}
