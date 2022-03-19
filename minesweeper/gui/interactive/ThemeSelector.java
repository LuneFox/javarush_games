package com.javarush.games.minesweeper.gui.interactive;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.gui.image.Image;

import java.util.Arrays;

public class ThemeSelector extends InteractiveObject {
    private final Image[] themePalettes;
    private final Image themeCursor;

    public ThemeSelector(int x, int y) {
        super(x, y);
        this.themeCursor = new Image(ImageType.GUI_THEME_PALETTE);
        this.themeCursor.restrictColorUpdate(true);
        themePalettes = new Image[3];
        for (int i = 0; i < themePalettes.length; i++) {
            themePalettes[i] = new Image(ImageType.GUI_THEME_PALETTE);
            themePalettes[i].restrictColorUpdate(true);
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

        Image currentPalette = themePalettes[Theme.getCurrentNumber()];
        themeCursor.setPosition(currentPalette.x, currentPalette.y);
    }

    @Override
    public void draw() {
        Arrays.stream(themePalettes).forEach(Image::draw);
        Image currentPalette = themePalettes[Theme.getCurrentNumber()];
        if (themeCursor.x < currentPalette.x) {
            themeCursor.x++;
        } else if (themeCursor.x > currentPalette.x) {
            themeCursor.x--;
        }
        themeCursor.draw();
    }

    @Override
    public void onLeftClick() {
        for (int i = 0; i < themePalettes.length; i++) {
            if (themePalettes[i].tryClick(latestClickX, latestClickY)) {
                Theme.set(i);
            }
        }
    }
}
