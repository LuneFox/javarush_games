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

            void shift(boolean reverse, Text letter) {
                int shift = letter.bitmapData[0].length + 1;
                x = (reverse) ? x - shift : x + shift;
            }
        }

        Caret caret = new Caret(drawX, drawY);
        char[] chars = input.toLowerCase().toCharArray();

        if (alignRight) {
            for (int i = chars.length - 1; i >= 0; i--) {
                if (caret.newLine(chars[i])) continue;
                drawSymbol(chars[i], color, caret.x, caret.y);
                Text nextSymbol = (i > 0) ? ALPHABET.get(chars[i - 1]) : ALPHABET.get(chars[i]);
                caret.shift(true, nextSymbol);
            }
        } else {
            for (char c : chars) {
                if (caret.newLine(c)) continue;
                drawSymbol(c, color, caret.x, caret.y);
                Text currentSymbol = ALPHABET.get(c);
                caret.shift(false, currentSymbol);
            }
        }
    }

    private void drawSymbol(char c, Color color, int x, int y) {
        symbol = ALPHABET.get(c);
        symbol.replaceColor(color, 1);
        symbol.drawAt(x, y);
    }

    public void loadAlphabet() {
        loadLetter('а', Bitmap.RU_LETTER_A);
        loadLetter('б', Bitmap.RU_LETTER_B);
        loadLetter('в', Bitmap.RU_LETTER_V);
        loadLetter('г', Bitmap.RU_LETTER_G);
        loadLetter('д', Bitmap.RU_LETTER_D);
        loadLetter('е', Bitmap.RU_LETTER_YE);
        loadLetter('ё', Bitmap.RU_LETTER_YO);
        loadLetter('ж', Bitmap.RU_LETTER_J);
        loadLetter('з', Bitmap.RU_LETTER_Z);
        loadLetter('и', Bitmap.RU_LETTER_I);
        loadLetter('й', Bitmap.RU_LETTER_IKR);
        loadLetter('к', Bitmap.RU_LETTER_K);
        loadLetter('л', Bitmap.RU_LETTER_L);
        loadLetter('м', Bitmap.RU_LETTER_M);
        loadLetter('н', Bitmap.RU_LETTER_N);
        loadLetter('о', Bitmap.RU_LETTER_O);
        loadLetter('п', Bitmap.RU_LETTER_P);
        loadLetter('р', Bitmap.RU_LETTER_R);
        loadLetter('с', Bitmap.RU_LETTER_S);
        loadLetter('т', Bitmap.RU_LETTER_T);
        loadLetter('у', Bitmap.RU_LETTER_U);
        loadLetter('ф', Bitmap.RU_LETTER_F);
        loadLetter('х', Bitmap.RU_LETTER_H);
        loadLetter('ц', Bitmap.RU_LETTER_C);
        loadLetter('ч', Bitmap.RU_LETTER_CH);
        loadLetter('ш', Bitmap.RU_LETTER_SHA);
        loadLetter('щ', Bitmap.RU_LETTER_SCHA);
        loadLetter('ь', Bitmap.RU_LETTER_SOFT);
        loadLetter('ы', Bitmap.RU_LETTER_Y);
        loadLetter('ъ', Bitmap.RU_LETTER_HARD);
        loadLetter('э', Bitmap.RU_LETTER_E);
        loadLetter('ю', Bitmap.RU_LETTER_YU);
        loadLetter('я', Bitmap.RU_LETTER_YA);
        loadLetter('a', Bitmap.RU_LETTER_A);
        loadLetter('b', Bitmap.RU_LETTER_V);
        loadLetter('c', Bitmap.RU_LETTER_S);
        loadLetter('d', Bitmap.EN_LETTER_D);
        loadLetter('e', Bitmap.RU_LETTER_YE);
        loadLetter('f', Bitmap.EN_LETTER_F);
        loadLetter('g', Bitmap.EN_LETTER_G);
        loadLetter('h', Bitmap.RU_LETTER_N);
        loadLetter('i', Bitmap.EN_LETTER_I);
        loadLetter('j', Bitmap.EN_LETTER_J);
        loadLetter('k', Bitmap.RU_LETTER_K);
        loadLetter('l', Bitmap.EN_LETTER_L);
        loadLetter('m', Bitmap.RU_LETTER_M);
        loadLetter('n', Bitmap.EN_LETTER_N);
        loadLetter('o', Bitmap.RU_LETTER_O);
        loadLetter('p', Bitmap.RU_LETTER_R);
        loadLetter('q', Bitmap.EN_LETTER_Q);
        loadLetter('r', Bitmap.EN_LETTER_R);
        loadLetter('s', Bitmap.EN_LETTER_S);
        loadLetter('t', Bitmap.RU_LETTER_T);
        loadLetter('u', Bitmap.EN_LETTER_U);
        loadLetter('v', Bitmap.EN_LETTER_V);
        loadLetter('w', Bitmap.EN_LETTER_W);
        loadLetter('x', Bitmap.EN_LETTER_X);
        loadLetter('y', Bitmap.EN_LETTER_Y);
        loadLetter('z', Bitmap.EN_LETTER_Z);
        loadLetter('0', Bitmap.DIGIT_0);
        loadLetter('1', Bitmap.DIGIT_1);
        loadLetter('2', Bitmap.DIGIT_2);
        loadLetter('3', Bitmap.DIGIT_3);
        loadLetter('4', Bitmap.DIGIT_4);
        loadLetter('5', Bitmap.DIGIT_5);
        loadLetter('6', Bitmap.DIGIT_6);
        loadLetter('7', Bitmap.DIGIT_7);
        loadLetter('8', Bitmap.DIGIT_8);
        loadLetter('9', Bitmap.DIGIT_9);
        loadLetter(' ', Bitmap.SYMBOL_SPACE);
        loadLetter('.', Bitmap.SYMBOL_DOT);
        loadLetter(',', Bitmap.SYMBOL_COMMA);
        loadLetter(':', Bitmap.SYMBOL_COLON);
        loadLetter('-', Bitmap.SYMBOL_DASH);
        loadLetter('=', Bitmap.SYMBOL_EQUALS);
        loadLetter('!', Bitmap.SYMBOL_EXCLAMATION);
        loadLetter('?', Bitmap.SYMBOL_QUESTION);
        loadLetter('*', Bitmap.SYMBOL_ASTERISK);
        loadLetter('/', Bitmap.SYMBOL_SLASH);
        loadLetter('\n', Bitmap.SYMBOL_NEWLINE);
    }

    private void loadLetter(Character c, Bitmap bitmap) {
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
            case RU_LETTER_A:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1}
                };
            case RU_LETTER_B:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case RU_LETTER_V:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case RU_LETTER_G:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {1, 0, 0},
                        {1, 0, 0},
                        {1, 0, 0},
                        {1, 0, 0}
                };
            case RU_LETTER_D:
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
            case RU_LETTER_YE:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 0},
                        {1, 1, 1, 1}
                };
            case RU_LETTER_YO:
                return new int[][]{
                        {1, 0, 1, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 0},
                        {1, 1, 1, 1}
                };
            case RU_LETTER_J:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {0, 1, 1, 1, 0},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1}
                };
            case RU_LETTER_Z:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {0, 0, 0, 1},
                        {0, 1, 1, 0},
                        {0, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case RU_LETTER_I:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 1, 1},
                        {1, 1, 0, 1},
                        {1, 0, 0, 1}
                };
            case RU_LETTER_IKR:
                return new int[][]{
                        {0, 1, 1, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 1, 1},
                        {1, 1, 0, 1},
                        {1, 0, 0, 1}
                };
            case RU_LETTER_K:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 1, 0},
                        {1, 1, 0, 0},
                        {1, 0, 1, 0},
                        {1, 0, 0, 1}
                };
            case RU_LETTER_L:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 0, 1, 1},
                        {0, 1, 0, 1},
                        {0, 1, 0, 1},
                        {0, 1, 0, 1},
                        {1, 0, 0, 1}
                };
            case RU_LETTER_M:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 1},
                        {1, 1, 0, 1, 1},
                        {1, 0, 1, 0, 1},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1}
                };
            case RU_LETTER_N:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 1, 1, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1}
                };
            case RU_LETTER_O:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}
                };
            case RU_LETTER_P:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1}
                };
            case RU_LETTER_R:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0}
                };
            case RU_LETTER_S:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0},
                        {0, 1, 1, 1}
                };
            case RU_LETTER_T:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {0, 1, 0},
                        {0, 1, 0},
                        {0, 1, 0},
                        {0, 1, 0}
                };
            case RU_LETTER_U:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 1},
                        {0, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case RU_LETTER_F:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 1, 1, 1, 0},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {0, 1, 1, 1, 0},
                        {0, 0, 1, 0, 0}
                };
            case RU_LETTER_H:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1}
                };
            case RU_LETTER_C:
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
            case RU_LETTER_CH:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 1},
                        {0, 0, 0, 1},
                        {0, 0, 0, 1}
                };
            case RU_LETTER_SHA:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 1, 1, 1, 1}
                };
            case RU_LETTER_SCHA:
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
            case RU_LETTER_SOFT:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case RU_LETTER_Y:
                return new int[][]{
                        {0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 1},
                        {1, 1, 1, 0, 0, 1},
                        {1, 0, 0, 1, 0, 1},
                        {1, 1, 1, 0, 0, 1}
                };
            case RU_LETTER_HARD:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 1, 0, 0, 0},
                        {0, 1, 0, 0, 0},
                        {0, 1, 1, 1, 0},
                        {0, 1, 0, 0, 1},
                        {0, 1, 1, 1, 0}
                };
            case RU_LETTER_E:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {0, 0, 0, 1},
                        {0, 1, 1, 1},
                        {0, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case RU_LETTER_YU:
                return new int[][]{
                        {0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0},
                        {1, 0, 0, 1, 1, 0},
                        {1, 0, 1, 0, 0, 1},
                        {1, 1, 1, 0, 0, 1},
                        {1, 0, 1, 0, 0, 1},
                        {1, 0, 0, 1, 1, 0}
                };
            case RU_LETTER_YA:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 1},
                        {0, 1, 0, 1},
                        {1, 0, 0, 1}
                };
            case EN_LETTER_D:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case EN_LETTER_F:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0}
                };
            case EN_LETTER_G:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 0, 1, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}
                };
            case EN_LETTER_I:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {0, 1, 0},
                        {0, 1, 0},
                        {0, 1, 0},
                        {1, 1, 1}
                };
            case EN_LETTER_J:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 1},
                        {0, 0, 1, 0},
                        {0, 0, 1, 0},
                        {1, 0, 1, 0},
                        {0, 1, 0, 0}
                };
            case EN_LETTER_L:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 0, 0},
                        {1, 0, 0},
                        {1, 0, 0},
                        {1, 0, 0},
                        {1, 1, 1}
                };
            case EN_LETTER_N:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 1, 0, 1},
                        {1, 0, 1, 1},
                        {1, 0, 0, 1}
                };
            case EN_LETTER_Q:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 1, 0},
                        {0, 1, 0, 1}
                };
            case EN_LETTER_R:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0},
                        {1, 0, 1, 0},
                        {1, 0, 0, 1}
                };
            case EN_LETTER_S:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 1},
                        {1, 0, 0, 0},
                        {0, 1, 1, 0},
                        {0, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case EN_LETTER_U:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}
                };
            case EN_LETTER_V:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1},
                        {0, 1, 0, 1, 0},
                        {0, 0, 1, 0, 0}
                };
            case EN_LETTER_W:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 1, 0, 1, 1},
                        {1, 0, 0, 0, 1}
                };
            case EN_LETTER_X:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 1},
                        {0, 1, 0, 1, 0},
                        {0, 0, 1, 0, 0},
                        {0, 1, 0, 1, 0},
                        {1, 0, 0, 0, 1}
                };
            case EN_LETTER_Y:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 1},
                        {0, 1, 0, 1, 0},
                        {0, 0, 1, 0, 0},
                        {0, 0, 1, 0, 0},
                        {0, 0, 1, 0, 0}
                };
            case EN_LETTER_Z:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {0, 0, 0, 1},
                        {0, 1, 1, 0},
                        {1, 0, 0, 0},
                        {1, 1, 1, 1}
                };
            case SYMBOL_DOT:
                return new int[][]{
                        {0},
                        {0},
                        {0},
                        {0},
                        {0},
                        {0},
                        {1}
                };
            case SYMBOL_COMMA:
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
            case SYMBOL_COLON:
                return new int[][]{
                        {0},
                        {0},
                        {0},
                        {1},
                        {0},
                        {1},
                        {0}
                };
            case SYMBOL_EXCLAMATION:
                return new int[][]{
                        {0},
                        {0},
                        {1},
                        {1},
                        {1},
                        {0},
                        {1}
                };
            case SYMBOL_QUESTION:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 0},
                        {0, 0, 0, 1},
                        {0, 1, 1, 0},
                        {0, 0, 0, 0},
                        {0, 1, 0, 0}
                };
            case SYMBOL_SLASH:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 1},
                        {0, 0, 1},
                        {0, 1, 0},
                        {0, 1, 0},
                        {1, 0, 0},
                        {1, 0, 0}
                };
            case SYMBOL_DASH:
                return new int[][]{
                        {0, 0},
                        {0, 0},
                        {0, 0},
                        {0, 0},
                        {1, 1},
                        {0, 0},
                        {0, 0}
                };
            case SYMBOL_EQUALS:
                return new int[][]{
                        {0, 0},
                        {0, 0},
                        {0, 0},
                        {1, 1},
                        {0, 0},
                        {1, 1},
                        {0, 0}
                };
            case SYMBOL_ASTERISK:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 0, 1},
                        {0, 1, 0},
                        {1, 0, 1},
                        {0, 0, 0}
                };
            case SYMBOL_SPACE:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
            case DIGIT_0:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {1, 0, 1},
                        {1, 0, 1},
                        {1, 0, 1},
                        {1, 1, 1}
                };
            case DIGIT_1:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 0},
                        {0, 1, 0},
                        {0, 1, 0},
                        {0, 1, 0},
                        {1, 1, 1}
                };
            case DIGIT_2:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {0, 0, 1},
                        {1, 1, 1},
                        {1, 0, 0},
                        {1, 1, 1}
                };
            case DIGIT_3:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {0, 0, 1},
                        {1, 1, 1},
                        {0, 0, 1},
                        {1, 1, 1}
                };
            case DIGIT_4:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 0, 1},
                        {1, 0, 1},
                        {1, 1, 1},
                        {0, 0, 1},
                        {0, 0, 1}
                };
            case DIGIT_5:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {1, 0, 0},
                        {1, 1, 1},
                        {0, 0, 1},
                        {1, 1, 1}
                };
            case DIGIT_6:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {1, 0, 0},
                        {1, 1, 1},
                        {1, 0, 1},
                        {1, 1, 1}
                };
            case DIGIT_7:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {0, 0, 1},
                        {0, 0, 1},
                        {0, 0, 1},
                        {0, 0, 1}
                };
            case DIGIT_8:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {1, 0, 1},
                        {1, 1, 1},
                        {1, 0, 1},
                        {1, 1, 1}
                };
            case DIGIT_9:
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
