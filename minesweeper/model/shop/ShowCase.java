package com.javarush.games.minesweeper.model.shop;

import com.javarush.games.minesweeper.model.DrawableObject;
import com.javarush.games.minesweeper.view.graphics.*;

import java.util.ArrayList;
import java.util.List;

public class ShowCase extends DrawableObject {
    private final Image panel;
    private final List<Slot> slots = new ArrayList<>();

    public ShowCase() {
        this.panel = Cache.get(VisualElement.WIN_SHOP_SHOWCASE);
        this.x = 10;
        this.y = 10;
        this.height = 80;
        this.width = 80;
        int i = 0;

        // Put items on 3x2 grid
        for (int column = 0; column < 2; column++) {
            for (int row = 0; row < 3; row++) {
                int dx = 5 + 25 * row;
                int dy = 21 + 25 * column;
                slots.add(new Slot(x + dx, y + dy, game.shop.allItems.get(i)));
                i++;
            }
        }
    }

    @Override
    public void draw() {
        panel.draw(x, y);
        Printer.print("*** магазин ***", Theme.SHOP_TITLE.getColor(), Printer.CENTER, y + 12);
        slots.forEach(DrawableObject::draw);
    }

    @Override
    protected void onLeftTouch() {
        for (Slot slot : slots) {
            slot.checkLeftTouch(lastClickX, lastClickY);
        }
    }

    @Override
    public void onRightTouch() {
        for (Slot slot : slots) {
            slot.checkRightTouch(lastClickX, lastClickY);
        }
    }
}
