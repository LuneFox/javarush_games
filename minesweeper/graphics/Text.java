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

    public Text(Bitmap bitmap, MinesweeperGame game) {
        super(bitmap, game);
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
        ALPHABET.put(c, new Text(bitmap, game));
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

    @Override
    protected int[][] assignBitmap(Bitmap bitmap) {
        switch (bitmap) { // 6 to 8 px tall, any px wide
            case SYM_RU_LETTER_A:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1}
                };
            case SYM_RU_LETTER_B:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case SYM_RU_LETTER_V:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case SYM_RU_LETTER_G:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {1, 0, 0},
                        {1, 0, 0},
                        {1, 0, 0},
                        {1, 0, 0}
                };
            case SYM_RU_LETTER_D:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 1, 1, 0},
                        {0, 1, 0, 1, 0},
                        {0, 1, 0, 1, 0},
                        {0, 1, 0, 1, 0},
                        {1, 1, 1, 1, 1},
                        {1, 0, 0, 0, 1}
                };
            case SYM_RU_LETTER_YE:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 0},
                        {1, 1, 1, 1}
                };
            case SYM_RU_LETTER_YO:
                return new int[][]{
                        {1, 0, 1, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 0},
                        {1, 1, 1, 1}
                };
            case SYM_RU_LETTER_J:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {0, 1, 1, 1, 0},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1}
                };
            case SYM_RU_LETTER_Z:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {0, 0, 0, 1},
                        {0, 1, 1, 0},
                        {0, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case SYM_RU_LETTER_I:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 1, 1},
                        {1, 1, 0, 1},
                        {1, 0, 0, 1}
                };
            case SYM_RU_LETTER_IKR:
                return new int[][]{
                        {0, 1, 1, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 1, 1},
                        {1, 1, 0, 1},
                        {1, 0, 0, 1}
                };
            case SYM_RU_LETTER_K:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 1, 0},
                        {1, 1, 0, 0},
                        {1, 0, 1, 0},
                        {1, 0, 0, 1}
                };
            case SYM_RU_LETTER_L:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 0, 1, 1},
                        {0, 1, 0, 1},
                        {0, 1, 0, 1},
                        {0, 1, 0, 1},
                        {1, 0, 0, 1}
                };
            case SYM_RU_LETTER_M:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 1},
                        {1, 1, 0, 1, 1},
                        {1, 0, 1, 0, 1},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1}
                };
            case SYM_RU_LETTER_N:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 1, 1, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1}
                };
            case SYM_RU_LETTER_O:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}
                };
            case SYM_RU_LETTER_P:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1}
                };
            case SYM_RU_LETTER_R:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0}
                };
            case SYM_RU_LETTER_S:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0},
                        {0, 1, 1, 1}
                };
            case SYM_RU_LETTER_T:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {0, 1, 0},
                        {0, 1, 0},
                        {0, 1, 0},
                        {0, 1, 0}
                };
            case SYM_RU_LETTER_U:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 1},
                        {0, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case SYM_RU_LETTER_F:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 1, 1, 1, 0},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {0, 1, 1, 1, 0},
                        {0, 0, 1, 0, 0}
                };
            case SYM_RU_LETTER_H:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1}
                };
            case SYM_RU_LETTER_C:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 0, 1, 0},
                        {1, 0, 0, 1, 0},
                        {1, 0, 0, 1, 0},
                        {1, 0, 0, 1, 0},
                        {1, 1, 1, 1, 1},
                        {0, 0, 0, 0, 1}
                };
            case SYM_RU_LETTER_CH:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 1},
                        {0, 0, 0, 1},
                        {0, 0, 0, 1}
                };
            case SYM_RU_LETTER_SHA:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 1, 1, 1, 1}
                };
            case SYM_RU_LETTER_SCHA:
                return new int[][]{
                        {0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0},
                        {1, 0, 1, 0, 1, 0},
                        {1, 0, 1, 0, 1, 0},
                        {1, 0, 1, 0, 1, 0},
                        {1, 0, 1, 0, 1, 0},
                        {1, 1, 1, 1, 1, 1},
                        {0, 0, 0, 0, 0, 1}
                };
            case SYM_RU_LETTER_SOFT:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case SYM_RU_LETTER_Y:
                return new int[][]{
                        {0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 1},
                        {1, 1, 1, 0, 0, 1},
                        {1, 0, 0, 1, 0, 1},
                        {1, 1, 1, 0, 0, 1}
                };
            case SYM_RU_LETTER_HARD:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 1, 0, 0, 0},
                        {0, 1, 0, 0, 0},
                        {0, 1, 1, 1, 0},
                        {0, 1, 0, 0, 1},
                        {0, 1, 1, 1, 0}
                };
            case SYM_RU_LETTER_E:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {0, 0, 0, 1},
                        {0, 1, 1, 1},
                        {0, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case SYM_RU_LETTER_YU:
                return new int[][]{
                        {0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0},
                        {1, 0, 0, 1, 1, 0},
                        {1, 0, 1, 0, 0, 1},
                        {1, 1, 1, 0, 0, 1},
                        {1, 0, 1, 0, 0, 1},
                        {1, 0, 0, 1, 1, 0}
                };
            case SYM_RU_LETTER_YA:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 1},
                        {0, 1, 0, 1},
                        {1, 0, 0, 1}
                };
            case SYM_EN_LETTER_D:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case SYM_EN_LETTER_F:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0}
                };
            case SYM_EN_LETTER_G:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 0, 1, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}
                };
            case SYM_EN_LETTER_I:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {0, 1, 0},
                        {0, 1, 0},
                        {0, 1, 0},
                        {1, 1, 1}
                };
            case SYM_EN_LETTER_J:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 1},
                        {0, 0, 1, 0},
                        {0, 0, 1, 0},
                        {1, 0, 1, 0},
                        {0, 1, 0, 0}
                };
            case SYM_EN_LETTER_L:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 0, 0},
                        {1, 0, 0},
                        {1, 0, 0},
                        {1, 0, 0},
                        {1, 1, 1}
                };
            case SYM_EN_LETTER_N:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 1, 0, 1},
                        {1, 0, 1, 1},
                        {1, 0, 0, 1}
                };
            case SYM_EN_LETTER_Q:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 1, 0},
                        {0, 1, 0, 1}
                };
            case SYM_EN_LETTER_R:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0},
                        {1, 0, 1, 0},
                        {1, 0, 0, 1}
                };
            case SYM_EN_LETTER_S:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 1},
                        {1, 0, 0, 0},
                        {0, 1, 1, 0},
                        {0, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case SYM_EN_LETTER_U:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}
                };
            case SYM_EN_LETTER_V:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1},
                        {0, 1, 0, 1, 0},
                        {0, 0, 1, 0, 0}
                };
            case SYM_EN_LETTER_W:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 1, 0, 1, 1},
                        {1, 0, 0, 0, 1}
                };
            case SYM_EN_LETTER_X:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 1},
                        {0, 1, 0, 1, 0},
                        {0, 0, 1, 0, 0},
                        {0, 1, 0, 1, 0},
                        {1, 0, 0, 0, 1}
                };
            case SYM_EN_LETTER_Y:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 1},
                        {0, 1, 0, 1, 0},
                        {0, 0, 1, 0, 0},
                        {0, 0, 1, 0, 0},
                        {0, 0, 1, 0, 0}
                };
            case SYM_EN_LETTER_Z:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {0, 0, 0, 1},
                        {0, 1, 1, 0},
                        {1, 0, 0, 0},
                        {1, 1, 1, 1}
                };
            case SYM_SYMBOL_DOT:
                return new int[][]{
                        {0},
                        {0},
                        {0},
                        {0},
                        {0},
                        {0},
                        {1}
                };
            case SYM_SYMBOL_COMMA:
                return new int[][]{
                        {0, 0},
                        {0, 0},
                        {0, 0},
                        {0, 0},
                        {0, 0},
                        {0, 1},
                        {0, 1},
                        {1, 0}
                };
            case SYM_SYMBOL_COLON:
                return new int[][]{
                        {0},
                        {0},
                        {0},
                        {1},
                        {0},
                        {1},
                        {0}
                };
            case SYM_SYMBOL_EXCLAMATION:
                return new int[][]{
                        {0},
                        {0},
                        {1},
                        {1},
                        {1},
                        {0},
                        {1}
                };
            case SYM_SYMBOL_QUESTION:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 0},
                        {0, 0, 0, 1},
                        {0, 1, 1, 0},
                        {0, 0, 0, 0},
                        {0, 1, 0, 0}
                };
            case SYM_SYMBOL_SLASH:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 1},
                        {0, 0, 1},
                        {0, 1, 0},
                        {0, 1, 0},
                        {1, 0, 0},
                        {1, 0, 0}
                };
            case SYM_SYMBOL_DASH:
                return new int[][]{
                        {0, 0},
                        {0, 0},
                        {0, 0},
                        {0, 0},
                        {1, 1},
                        {0, 0},
                        {0, 0}
                };
            case SYM_SYMBOL_EQUALS:
                return new int[][]{
                        {0, 0},
                        {0, 0},
                        {0, 0},
                        {1, 1},
                        {0, 0},
                        {1, 1},
                        {0, 0}
                };
            case SYM_SYMBOL_ASTERISK:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 0, 1},
                        {0, 1, 0},
                        {1, 0, 1},
                        {0, 0, 0}
                };
            case SYM_SYMBOL_SPACE:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
            case SYM_DIGIT_0:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {1, 0, 1},
                        {1, 0, 1},
                        {1, 0, 1},
                        {1, 1, 1}
                };
            case SYM_DIGIT_1:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 0},
                        {0, 1, 0},
                        {0, 1, 0},
                        {0, 1, 0},
                        {1, 1, 1}
                };
            case SYM_DIGIT_2:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {0, 0, 1},
                        {1, 1, 1},
                        {1, 0, 0},
                        {1, 1, 1}
                };
            case SYM_DIGIT_3:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {0, 0, 1},
                        {1, 1, 1},
                        {0, 0, 1},
                        {1, 1, 1}
                };
            case SYM_DIGIT_4:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 0, 1},
                        {1, 0, 1},
                        {1, 1, 1},
                        {0, 0, 1},
                        {0, 0, 1}
                };
            case SYM_DIGIT_5:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {1, 0, 0},
                        {1, 1, 1},
                        {0, 0, 1},
                        {1, 1, 1}
                };
            case SYM_DIGIT_6:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {1, 0, 0},
                        {1, 1, 1},
                        {1, 0, 1},
                        {1, 1, 1}
                };
            case SYM_DIGIT_7:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {0, 0, 1},
                        {0, 0, 1},
                        {0, 0, 1},
                        {0, 0, 1}
                };
            case SYM_DIGIT_8:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {1, 0, 1},
                        {1, 1, 1},
                        {1, 0, 1},
                        {1, 1, 1}
                };
            case SYM_DIGIT_9:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {1, 0, 1},
                        {1, 1, 1},
                        {0, 0, 1},
                        {1, 1, 1}
                };
            case NONE:
                return new int[1][];
            default:
                return new int[][]{
                        {1, 1, 1, 1},
                        {1, 1, 1, 1},
                        {1, 1, 1, 1},
                        {1, 1, 1, 1},
                        {1, 1, 1, 1},
                        {1, 1, 1, 1},
                        {1, 1, 1, 1},
                        {1, 1, 1, 1},
                };
        }
    }
}
