package com.javarush.games.minesweeper.gui.interactive;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.DrawableObject;
import com.javarush.games.minesweeper.gui.image.Image;

import java.util.Arrays;

public class ThemeSelector extends DrawableObject {
    private final Image[] themePalettes;
    private final Image themeCursor;

    public ThemeSelector(int x, int y) {
        super(x, y);
        this.themeCursor = new Image(ImageType.MENU_THEME_PALETTE);
        themePalettes = new Image[3];
        for (int i = 0; i < themePalettes.length; i++) {
            themePalettes[i] = new Image(ImageType.MENU_THEME_PALETTE);
            themePalettes[i].setPosition(this.x + i * 12, this.y);
            this.width += themePalettes[i].width + 2;
        }
        this.height = themePalettes[0].height;

        themePalettes[0].replaceColor(Color.RED, 1);
        themePalettes[1].replaceColor(Color.GREEN, 1);
        themePalettes[2].replaceColor(Color.BLUE, 1);

        this.themeCursor.replaceColor(Color.NONE, 1);
        this.themeCursor.replaceColor(Color.NONE, 2);
        this.themeCursor.replaceColor(Color.YELLOW, 3);
        themeCursor.setPosition(themePalettes[Theme.getCurrentNumber()].x, themePalettes[Theme.getCurrentNumber()].y);
    }

    @Override
    public void draw() {
        Arrays.stream(themePalettes).forEach(Image::draw);
        if (themeCursor.x < themePalettes[Theme.getCurrentNumber()].x)
            themeCursor.x++;
        else if (themeCursor.x > themePalettes[Theme.getCurrentNumber()].x)
            themeCursor.x--;
        themeCursor.draw();
    }

    @Override
    protected void onLeftTouch() {
        for (int i = 0; i < themePalettes.length; i++) {
            if (themePalettes[i].checkLeftTouch(lastClickX, lastClickY)) {
                Theme.set(i);
            }
        }
    }
}
