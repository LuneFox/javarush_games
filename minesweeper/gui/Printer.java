package com.javarush.games.minesweeper.gui;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.image.Image;

/**
 * Utility class for drawing text information using symbol images.
 */

public class Printer {
    public static final int CENTER = Integer.MIN_VALUE;
    private static final int CHAR_SPACING = 1;
    private static final int LINE_HEIGHT = 9;

    public static void print(String input, Color color, int drawX, int drawY, boolean alignRight) {
        if (drawX == CENTER) {
            int width = calculateWidth(input);
            drawX = 50 - width / 2;
        }

        if (drawY == CENTER) {
            drawY = 0;
        }

        final int finalDrawX = drawX;


        // A caret marks the place where the next symbol is going to be drawn
        class Caret {
            int x, y;

            Caret(int x, int y) {
                this.x = x;
                this.y = y;
            }

            // Return the caret to the beginning of the next line if the char is '\n'
            boolean isAtNewLine(char c) {
                if (c == '\n') {
                    x = finalDrawX;
                    y += LINE_HEIGHT;
                    return true;
                }
                return false;
            }

            // Move the caret by the width of the symbol + spacing, direction depends on the alignment
            void shift(boolean alignRight, char c) {
                int shift = calculateWidth(Character.toString(c));
                x = (alignRight) ? x - shift : x + shift;
            }
        }

        Caret caret = new Caret(drawX, drawY);

        // Choosing between normal order or reversed order of chars to draw from right to left
        char[] chars = (alignRight) ?
                new StringBuilder(input).reverse().toString().toLowerCase().toCharArray() :
                input.toLowerCase().toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (caret.isAtNewLine(chars[i])) continue; // Return caret to new line at "\n" symbol, don't draw it

            if (alignRight && i == 0) {
                int width1st = Cache.get(chars[0]).width;
                if (width1st > 4) {
                    caret.x -= width1st - 4; // First letter position fix
                }
            }

            drawSymbol(chars[i], color, caret.x, caret.y);

            // j = 0 means we take the CURRENT symbol, j = 1 means we take the NEXT symbol to calculate the shift
            // We need to take the next symbol only when typing from right to left and if there is one
            int j = (i >= chars.length - 1 || !alignRight) ? 0 : 1;
            char relativeChar = (chars[i + j]);

            caret.shift(alignRight, relativeChar);
        }
    }

    public static void print(String text, Color color, int x, int y) {
        print(text, color, x, y, false);
    }

    public static void print(String text, int x, int y) {
        print(text, Color.WHITE, x, y, false);
    }

    private static void drawSymbol(char c, Color color, int x, int y) {
        Image symbol = Cache.get(c);
        symbol.replaceColor(color, 1);
        symbol.draw(x, y);
    }

    /**
     * Calculates the width in pixels of the text that comes as an argument
     */
    public static int calculateWidth(String s) {
        int width = 0;
        Image symbol;
        char[] chars = s.toLowerCase().toCharArray();
        for (char c : chars) {
            symbol = Cache.get(c);
            width += (symbol.matrix[0].length + CHAR_SPACING);
        }
        return width;
    }
}
