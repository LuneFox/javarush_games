package com.javarush.games.spaceinvaders.view;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.games.spaceinvaders.Drawable;
import com.javarush.games.spaceinvaders.model.GameField;

/**
 * Pixel buffer the whole picture is drawn into. At the end of a turn the buffer is flushed to the
 * game engine cell by cell, which makes drawing order explicit.
 */
public class PixelScreen implements Drawable {
    private final Game game;
    private final Color[][] pixels;

    public PixelScreen(Game game) {
        this.game = game;
        this.pixels = new Color[GameField.HEIGHT][GameField.WIDTH];
        clear();
    }

    private void clear() {
        for (int y = 0; y < GameField.HEIGHT; y++) {
            for (int x = 0; x < GameField.WIDTH; x++) {
                pixels[y][x] = Color.NONE;
            }
        }
    }

    /**
     * Paints a single pixel. Everything outside the field and all transparent colors are ignored.
     */
    public void setPixel(int x, int y, Color color) {
        if (!isOnScreen(x, y)) return;
        if (color == null || color == Color.NONE) return;

        pixels[y][x] = color;
    }

    private boolean isOnScreen(int x, int y) {
        return x >= 0 && y >= 0 && x < GameField.WIDTH && y < GameField.HEIGHT;
    }

    public Color getPixel(int x, int y) {
        if (!isOnScreen(x, y)) return Color.NONE;

        return pixels[y][x];
    }

    @Override
    public void draw() {
        for (int y = 0; y < GameField.HEIGHT; y++) {
            for (int x = 0; x < GameField.WIDTH; x++) {
                game.setCellColor(x, y, pixels[y][x]);
            }
        }
    }
}
