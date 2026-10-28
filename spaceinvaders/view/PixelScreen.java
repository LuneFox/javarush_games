package com.javarush.games.spaceinvaders.view;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.games.spaceinvaders.Drawable;
import com.javarush.games.spaceinvaders.model.GameField;

/** Пиксельный буфер, который в конце хода переносится в движок. */
public class PixelScreen implements Drawable {
    private final Game game;
    private final Color[][] pixels;

    /** Создаёт экран и очищает его. */
    public PixelScreen(Game game) {
        this.game = game;
        this.pixels = new Color[GameField.HEIGHT][GameField.WIDTH];
        clear();
    }

    /** Заливает экран прозрачным цветом. */
    private void clear() {
        for (int y = 0; y < GameField.HEIGHT; y++) {
            for (int x = 0; x < GameField.WIDTH; x++) {
                pixels[y][x] = Color.NONE;
            }
        }
    }

    /** Рисует пиксель, если он в пределах поля. */
    public void setPixel(int x, int y, Color color) {
        if (!isOnScreen(x, y)) return;
        if (color == null || color == Color.NONE) return;

        pixels[y][x] = color;
    }

    /** Проверяет, попадает ли точка на экран. */
    private boolean isOnScreen(int x, int y) {
        return x >= 0 && y >= 0 && x < GameField.WIDTH && y < GameField.HEIGHT;
    }

    /** Возвращает цвет пикселя. */
    public Color getPixel(int x, int y) {
        if (!isOnScreen(x, y)) return Color.NONE;

        return pixels[y][x];
    }

    /** Переносит буфер в клетки движка. */
    @Override
    public void draw() {
        for (int y = 0; y < GameField.HEIGHT; y++) {
            for (int x = 0; x < GameField.WIDTH; x++) {
                game.setCellColor(x, y, pixels[y][x]);
            }
        }
    }
}
