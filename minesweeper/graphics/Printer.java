package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.Color;

import java.util.HashMap;

/**
 * Utility class for drawing letters on screen.
 */

public class Printer {
    private static final HashMap<Character, Image> ALPHABET = new HashMap<>(); // pre-loaded alphabet goes here
    private static Image symbol;

    static {
        loadAlphabet();
    }

    public static void print(String input, Color color, int drawX, int drawY, boolean alignRight) {

        class Caret { // a place where the symbol is drawn
            int x;
            int y;

            Caret(int x, int y) {
                this.x = x;
                this.y = y;
            }

            boolean newLine(char c) {
                if (c == '\n') {
                    x = drawX; // reset to start
                    y += 9;    // go to next line
                    return true;
                }
                return false;
            }

            void shift(boolean reverse, Image relativeSymbol) {
                int shift = relativeSymbol.matrix[0].length + 1;
                x = (reverse) ? x - shift : x + shift;
            }
        }

        Caret caret = new Caret(drawX, drawY);
        char[] chars = (alignRight) ?
                new StringBuilder(input).reverse().toString().toLowerCase().toCharArray() :
                input.toLowerCase().toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (caret.newLine(chars[i])) continue;
            drawChar(chars[i], color, caret.x, caret.y);
            int j = (i >= chars.length - 1 || !alignRight) ? 0 : 1;
            Image relativeSymbol = ALPHABET.get(chars[i + j]);
            caret.shift(alignRight, relativeSymbol);
        }
    }

    private static void drawChar(char c, Color color, int x, int y) {
        symbol = ALPHABET.get(c);
        symbol.replaceColor(color, 1);
        symbol.drawAt(x, y);
    }

    public static void loadAlphabet() {
        VisualElement.getBitmapsByPrefixes("SYM_").forEach(symbol -> {
            for (char c : symbol.characters) loadSymbol(c, symbol);
        });
    }

    private static void loadSymbol(Character c, VisualElement visualElement) {
        ALPHABET.put(c, new Image(visualElement));
    }

    public static int calculateLengthInPixels(String s) {
        int length = 0;
        char[] chars = s.toLowerCase().toCharArray();
        for (char c : chars) {
            symbol = ALPHABET.get(c);
            length += (symbol.matrix[0].length + 1);
        }
        return length;
    }
}
