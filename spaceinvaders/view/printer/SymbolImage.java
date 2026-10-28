package com.javarush.games.spaceinvaders.view.printer;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.view.PixelScreen;

/**
 * A printable glyph. Matrix values are 1 for a visible pixel and 0 for a transparent one.
 */
public class SymbolImage {
    private static final int TRANSPARENT = 0;

    private static PixelScreen screen;

    private final int[][] matrix;

    public static void setScreen(PixelScreen screen) {
        SymbolImage.screen = screen;
    }

    SymbolImage(Symbol symbol) {
        this.matrix = symbol.getMatrix();
    }

    /**
     * Draws the glyph with the given color, so no glyph keeps a global current color.
     */
    void draw(int x, int y, Color color) {
        for (int row = 0; row < getHeight(); row++) {
            for (int column = 0; column < getWidth(); column++) {
                if (matrix[row][column] == TRANSPARENT) continue;

                screen.setPixel(x + column, y + row, color);
            }
        }
    }

    int getHeight() {
        return matrix.length;
    }

    int getWidth() {
        return matrix[0].length;
    }
}
