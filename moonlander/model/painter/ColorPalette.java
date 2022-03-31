package com.javarush.games.moonlander.model.painter;

import com.javarush.engine.cell.Color;
import com.javarush.games.moonlander.MoonLanderGame;

public class ColorPalette extends DrawableObject {
    private final MoonLanderGame game = MoonLanderGame.getInstance();

    public ColorPalette(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw() {
        drawPalette();
        drawPickedColors();
    }

    public void pickPrimaryColor(int x, int y) {
        if (checkClickOutsidePalette(x, y)) return;
        game.painter.primaryColor = game.getCellColor(x, y).ordinal();
    }

    public void pickSecondaryColor(int x, int y) {
        if (checkClickOutsidePalette(x, y)) return;
        game.painter.secondaryColor = game.getCellColor(x, y).ordinal();
    }

    private void drawPalette() {
        int colorValue = 1;
        for (int y = posY; y < posY + height; y++) {
            for (int x = posX; x < posX + width; x++) {
                if (colorValue > 148) {
                    continue;
                }
                String shownNumber = game.painter.showNumbers ? String.valueOf(colorValue) : "";
                game.setCellValueEx(x, y, Color.values()[colorValue], shownNumber, Color.WHITE);
                colorValue++;
            }
        }
    }

    private void drawPickedColors() {
        String shownNumber;
        // Background
        for (int x = 2; x < 6; x++) {
            for (int y = 3; y < 7; y++) {
                game.setCellValueEx(x, y, game.painter.canvas.getBackgroundColor(), "");
            }
        }
        shownNumber = game.painter.showNumbers ? String.valueOf(game.painter.canvas.getBackgroundColor().ordinal()) : "";
        game.setCellValueEx(2, 3, Color.NONE, shownNumber, Color.WHITE);

        // Secondary Color
        for (int x = 4; x < 6; x++) {
            for (int y = 5; y < 7; y++) {
                game.setCellValueEx(x, y, Color.values()[game.painter.secondaryColor], "");
            }
        }
        shownNumber = game.painter.showNumbers ? String.valueOf(game.painter.secondaryColor) : "";
        game.setCellValueEx(5, 6, Color.NONE, shownNumber, Color.WHITE);

        // Primary color
        for (int x = 3; x < 5; x++) {
            for (int y = 4; y < 6; y++) {
                game.setCellValueEx(x, y, Color.values()[game.painter.primaryColor], "");
            }
        }
        shownNumber = game.painter.showNumbers ? String.valueOf(game.painter.primaryColor) : "";
        game.setCellValueEx(4, 5, Color.NONE, shownNumber, Color.WHITE);
    }

    private boolean checkClickOutsidePalette(int x, int y) {
        return x < posX || x >= posX + width || y < posY || y >= posY + height;
    }
}
