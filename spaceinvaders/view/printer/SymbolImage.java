package com.javarush.games.spaceinvaders.view.printer;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.view.PixelScreen;

/** Печатный глиф: 1 в матрице — видимый пиксель. */
public class SymbolImage {
    /** Код прозрачного пикселя. */
    private static final int TRANSPARENT = 0;

    /** Экран для печати. */
    private static PixelScreen screen;

    /** Пиксельная матрица глифа. */
    private final int[][] matrix;

    /** Запоминает экран для печати. */
    public static void setScreen(PixelScreen screen) {
        SymbolImage.screen = screen;
    }

    /** Создаёт глиф по символу шрифта. */
    SymbolImage(Symbol symbol) {
        this.matrix = symbol.getMatrix();
    }

    /** Рисует глиф заданным цветом. */
    void draw(int x, int y, Color color) {
        for (int row = 0; row < getHeight(); row++) {
            for (int column = 0; column < getWidth(); column++) {
                if (matrix[row][column] == TRANSPARENT) continue;

                screen.setPixel(x + column, y + row, color);
            }
        }
    }

    /** Возвращает высоту глифа. */
    int getHeight() {
        return matrix.length;
    }

    /** Возвращает ширину глифа. */
    int getWidth() {
        return matrix[0].length;
    }
}
