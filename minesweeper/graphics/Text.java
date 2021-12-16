package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;

import java.util.HashMap;

/**
 * An image piece that can take a form of a letter.
 * Private object "symbol" uses public "write" method to become every character of a string and draw itself in a cycle.
 */

public class Text extends Image {
    private static final HashMap<Character, Text> ALPHABET = new HashMap<>(); // pre-loaded alphabet goes here
    private Text symbol;

    public Text(Bitmap bitmap) {
        super(bitmap);
    }

    public void write(String input, Color color, int drawX, int drawY, boolean alignRight) {

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

            void shift(boolean reverse, Text relativeSymbol) {
                int shift = relativeSymbol.bitmapData[0].length + 1;
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
            Text relativeSymbol = ALPHABET.get(chars[i + j]);
            caret.shift(alignRight, relativeSymbol);
        }
    }

    private void drawChar(char c, Color color, int x, int y) {
        symbol = ALPHABET.get(c);
        symbol.replaceColor(color, 1);
        symbol.drawAt(x, y);
    }

    public void loadAlphabet() {
        Bitmap.getBitmapsByPrefixes("SYM_").forEach(symbol -> {
            for (char c : symbol.characters) loadSymbol(c, symbol);
        });
    }

    private void loadSymbol(Character c, Bitmap bitmap) {
        ALPHABET.put(c, new Text(bitmap));
    }

    public int calculateLengthInPixels(String s) {
        int length = 0;
        char[] chars = s.toLowerCase().toCharArray();
        for (char c : chars) {
            symbol = ALPHABET.get(c);
            length += (symbol.bitmapData[0].length + 1);
        }
        return length;
    }
}
