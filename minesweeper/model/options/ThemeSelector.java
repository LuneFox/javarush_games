package com.javarush.games.minesweeper.model.options;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.model.DrawableObject;
import com.javarush.games.minesweeper.view.graphics.Image;
import com.javarush.games.minesweeper.view.graphics.Theme;
import com.javarush.games.minesweeper.view.graphics.VisualElement;

public class ThemeSelector extends DrawableObject {
    private final Image[] themePalettes;

    public ThemeSelector(int x, int y) {
        super(x, y);
        themePalettes = new Image[3];
        for (int i = 0; i < themePalettes.length; i++) {
            themePalettes[i] = new Image(VisualElement.MENU_THEME_PALETTE);
            themePalettes[i].setPosition(this.x + i * 12, this.y);
            this.width += themePalettes[i].width + 2;
        }
        this.height = themePalettes[0].height;
        themePalettes[0].replaceColor(Color.RED, 1);
        themePalettes[1].replaceColor(Color.GREEN, 1);
        themePalettes[2].replaceColor(Color.BLUE, 1);
    }

    @Override
    public void draw() {
        for (int i = 0; i < themePalettes.length; i++) {
            themePalettes[i].replaceColor(Theme.getCurrentNumber() == i ? Color.YELLOW : Color.BLACK, 3);
            themePalettes[i].draw();
        }
    }

    @Override
    protected void onTouch() {
        for (int i = 0; i < themePalettes.length; i++) {
            if (themePalettes[i].checkTouch(lastClickX, lastClickY)) {
                Theme.set(i);
            }
        }
    }
}
