package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.Color;

import java.util.HashMap;

/**
 * Utility class for drawing text information using symbol images.
 */

public class Printer {
    private static final HashMap<Character, Image> SYMBOLS_CACHE = new HashMap<>();
    private static final int CHAR_SPACING = 1;
    private static final int LINE_SPACING = 9;

    static {
        cacheAllSymbols();
    }

    public static void print(String input, Color color, int drawX, int drawY, boolean alignRight) {

        // If drawX is negative, print in the middle
        if (drawX < 0 || drawX > 99) {
            int width = calculateWidth(input);
            drawX = 50 - width / 2;
        }
        // Must be effectively final for inner Caret class
        int caretStartPosition = drawX;

        // Don't allow printing outside screen
        if (drawY < 0 || drawY > 99) {
            drawY = 0;
        }

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
                    x = caretStartPosition;
                    y += LINE_SPACING;
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

        // For each char in the array
        for (int i = 0; i < chars.length; i++) {
            // If it equals '\n' move the caret to the new line and skip the rest
            if (caret.isAtNewLine(chars[i])) continue;
            // Draw symbol on the screen
            drawSymbol(chars[i], color, caret.x, caret.y);
            // j = 0 means we take the CURRENT symbol, j = 1 means we take the NEXT symbol to calculate the shift
            // We need to take the next symbol only when typing from right to left and if there is one
            int j = (i >= chars.length - 1 || !alignRight) ? 0 : 1;
            char relativeChar = (chars[i + j]);
            caret.shift(alignRight, relativeChar);
        }
    }

    private static void drawSymbol(char c, Color color, int x, int y) {
        Image symbol = SYMBOLS_CACHE.get(c);
        symbol.replaceColor(color, 1);
        symbol.drawAt(x, y);
    }

    public static void cacheAllSymbols() {
        // For all elements that are marked as symbols
        VisualElement.getElementsByPrefixes("SYM_").forEach(symbol -> {
            // For every char that is assigned to a symbol
            for (char c : symbol.characters) {
                // Cache a pair <char, image> for frequent reuse
                SYMBOLS_CACHE.put(c, new Image(symbol));
            }
        });
    }

    // Calculates the width in pixels of the text that comes as an argument
    public static int calculateWidth(String s) {
        int width = 0;
        Image symbol;
        char[] chars = s.toLowerCase().toCharArray();
        for (char c : chars) {
            symbol = SYMBOLS_CACHE.get(c);
            width += (symbol.matrix[0].length + CHAR_SPACING);
        }
        return width;
    }
}
