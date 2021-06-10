package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.MinesweeperGame;

import java.util.HashMap;

/**
 * Special class for displaying text in game
 */

public class Text extends Image {
    private static HashMap<Character, Text> alphabet = new HashMap<>(); // pre-loaded alphabet goes here
    private Text symbol;

    public Text(Bitmap bitmap, MinesweeperGame game) {
        super(bitmap, game);
    }    

    // command an object to draw
    public void write(String input, Color color, int drawX, int drawY, boolean alignRight) {
        char[] chars = input.toLowerCase().toCharArray();
        int caretX = drawX;
        int caretY = drawY;
        if (alignRight) { // reverse typing
            for (int i = chars.length - 1; i >= 0; i--) {
                if (chars[i] == '\n') { // new line
                    caretX = drawX;
                    caretY += 9;
                    continue;
                }
                symbol = alphabet.get(chars[i]);
                symbol.setPosition(caretX, caretY);
                symbol.replaceColor(color, 1);
                symbol.draw();
                if (i > 0) { // move caret by the length of the NEXT letter
                    caretX = caretX - (alphabet.get(chars[i - 1]).bitmapData[0].length + 1);
                }
            }
        } else {
            for (char c : chars) { // straight typing
                if (c == '\n') { // new line
                    caretX = drawX;
                    caretY = caretY + 9;
                    continue;
                }
                symbol = alphabet.get(c);
                symbol.setPosition(caretX, caretY);
                symbol.replaceColor(color, 1);
                symbol.draw();
                caretX += (symbol.bitmapData[0].length + 1);
            }
        }
    }

    public void loadAlphabet() {
        alphabet.put('а', new Text(Bitmap.RUSSIAN_LETTER_A, game));
        alphabet.put('б', new Text(Bitmap.RUSSIAN_LETTER_B, game));
        alphabet.put('в', new Text(Bitmap.RUSSIAN_LETTER_V, game));
        alphabet.put('г', new Text(Bitmap.RUSSIAN_LETTER_G, game));
        alphabet.put('д', new Text(Bitmap.RUSSIAN_LETTER_D, game));
        alphabet.put('е', new Text(Bitmap.RUSSIAN_LETTER_YE, game));
        alphabet.put('ё', new Text(Bitmap.RUSSIAN_LETTER_YO, game));
        alphabet.put('ж', new Text(Bitmap.RUSSIAN_LETTER_J, game));
        alphabet.put('з', new Text(Bitmap.RUSSIAN_LETTER_Z, game));
        alphabet.put('и', new Text(Bitmap.RUSSIAN_LETTER_I, game));
        alphabet.put('й', new Text(Bitmap.RUSSIAN_LETTER_IKR, game));
        alphabet.put('к', new Text(Bitmap.RUSSIAN_LETTER_K, game));
        alphabet.put('л', new Text(Bitmap.RUSSIAN_LETTER_L, game));
        alphabet.put('м', new Text(Bitmap.RUSSIAN_LETTER_M, game));
        alphabet.put('н', new Text(Bitmap.RUSSIAN_LETTER_N, game));
        alphabet.put('о', new Text(Bitmap.RUSSIAN_LETTER_O, game));
        alphabet.put('п', new Text(Bitmap.RUSSIAN_LETTER_P, game));
        alphabet.put('р', new Text(Bitmap.RUSSIAN_LETTER_R, game));
        alphabet.put('с', new Text(Bitmap.RUSSIAN_LETTER_S, game));
        alphabet.put('т', new Text(Bitmap.RUSSIAN_LETTER_T, game));
        alphabet.put('у', new Text(Bitmap.RUSSIAN_LETTER_U, game));
        alphabet.put('ф', new Text(Bitmap.RUSSIAN_LETTER_F, game));
        alphabet.put('х', new Text(Bitmap.RUSSIAN_LETTER_H, game));
        alphabet.put('ц', new Text(Bitmap.RUSSIAN_LETTER_C, game));
        alphabet.put('ч', new Text(Bitmap.RUSSIAN_LETTER_CH, game));
        alphabet.put('ш', new Text(Bitmap.RUSSIAN_LETTER_SHA, game));
        alphabet.put('щ', new Text(Bitmap.RUSSIAN_LETTER_SCHA, game));
        alphabet.put('ь', new Text(Bitmap.RUSSIAN_LETTER_SOFT, game));
        alphabet.put('ы', new Text(Bitmap.RUSSIAN_LETTER_Y, game));
        alphabet.put('ъ', new Text(Bitmap.RUSSIAN_LETTER_HARD, game));
        alphabet.put('э', new Text(Bitmap.RUSSIAN_LETTER_E, game));
        alphabet.put('ю', new Text(Bitmap.RUSSIAN_LETTER_YU, game));
        alphabet.put('я', new Text(Bitmap.RUSSIAN_LETTER_YA, game));
        alphabet.put('a', new Text(Bitmap.RUSSIAN_LETTER_A, game));
        alphabet.put('b', new Text(Bitmap.RUSSIAN_LETTER_V, game));
        alphabet.put('c', new Text(Bitmap.RUSSIAN_LETTER_S, game));
        alphabet.put('d', new Text(Bitmap.ENGLISH_LETTER_D, game));
        alphabet.put('e', new Text(Bitmap.RUSSIAN_LETTER_YE, game));
        alphabet.put('f', new Text(Bitmap.ENGLISH_LETTER_F, game));
        alphabet.put('g', new Text(Bitmap.ENGLISH_LETTER_G, game));
        alphabet.put('h', new Text(Bitmap.RUSSIAN_LETTER_N, game));
        alphabet.put('i', new Text(Bitmap.ENGLISH_LETTER_I, game));
        alphabet.put('j', new Text(Bitmap.ENGLISH_LETTER_J, game));
        alphabet.put('k', new Text(Bitmap.RUSSIAN_LETTER_K, game));
        alphabet.put('l', new Text(Bitmap.ENGLISH_LETTER_L, game));
        alphabet.put('m', new Text(Bitmap.RUSSIAN_LETTER_M, game));
        alphabet.put('n', new Text(Bitmap.ENGLISH_LETTER_N, game));
        alphabet.put('o', new Text(Bitmap.RUSSIAN_LETTER_O, game));
        alphabet.put('p', new Text(Bitmap.RUSSIAN_LETTER_R, game));
        alphabet.put('q', new Text(Bitmap.ENGLISH_LETTER_Q, game));
        alphabet.put('r', new Text(Bitmap.ENGLISH_LETTER_R, game));
        alphabet.put('s', new Text(Bitmap.ENGLISH_LETTER_S, game));
        alphabet.put('t', new Text(Bitmap.RUSSIAN_LETTER_T, game));
        alphabet.put('u', new Text(Bitmap.ENGLISH_LETTER_U, game));
        alphabet.put('v', new Text(Bitmap.ENGLISH_LETTER_V, game));
        alphabet.put('w', new Text(Bitmap.ENGLISH_LETTER_W, game));
        alphabet.put('x', new Text(Bitmap.RUSSIAN_LETTER_H, game));
        alphabet.put('y', new Text(Bitmap.ENGLISH_LETTER_Y, game));
        alphabet.put('z', new Text(Bitmap.ENGLISH_LETTER_Z, game));
        alphabet.put('0', new Text(Bitmap.DIGIT_0, game));
        alphabet.put('1', new Text(Bitmap.DIGIT_1, game));
        alphabet.put('2', new Text(Bitmap.DIGIT_2, game));
        alphabet.put('3', new Text(Bitmap.DIGIT_3, game));
        alphabet.put('4', new Text(Bitmap.DIGIT_4, game));
        alphabet.put('5', new Text(Bitmap.DIGIT_5, game));
        alphabet.put('6', new Text(Bitmap.DIGIT_6, game));
        alphabet.put('7', new Text(Bitmap.DIGIT_7, game));
        alphabet.put('8', new Text(Bitmap.DIGIT_8, game));
        alphabet.put('9', new Text(Bitmap.DIGIT_9, game));
        alphabet.put(' ', new Text(Bitmap.SYMBOL_SPACE, game));
        alphabet.put('.', new Text(Bitmap.SYMBOL_DOT, game));
        alphabet.put(',', new Text(Bitmap.SYMBOL_COMMA, game));
        alphabet.put(':', new Text(Bitmap.SYMBOL_COLON, game));
        alphabet.put('-', new Text(Bitmap.SYMBOL_DASH, game));
        alphabet.put('=', new Text(Bitmap.SYMBOL_EQUALS, game));
        alphabet.put('!', new Text(Bitmap.SYMBOL_EXCLAMATION, game));
        alphabet.put('?', new Text(Bitmap.SYMBOL_QUESTION, game));
        alphabet.put('*', new Text(Bitmap.SYMBOL_ASTERISK, game));
        alphabet.put('\n', new Text(Bitmap.SYMBOL_NEWLINE, game));
    }

    int calculateLengthInPixels(String s) {
        int length = 0;
        char[] chars = s.toLowerCase().toCharArray();
        for (char c : chars) {
            symbol = alphabet.get(c);
            length += (symbol.bitmapData[0].length + 1);
        }
        return length;
    }

    @Override
    protected int[][] assignBitmap(Bitmap bitmap) {
        switch (bitmap) { // y = 6 is base line, can be any px wide, max 8 px tall
            case RUSSIAN_LETTER_A:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1}
                };
            case RUSSIAN_LETTER_B:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case RUSSIAN_LETTER_V:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case RUSSIAN_LETTER_G:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {1, 0, 0},
                        {1, 0, 0},
                        {1, 0, 0},
                        {1, 0, 0}
                };
            case RUSSIAN_LETTER_D:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 1, 1, 1, 0},
                        {0, 1, 0, 1, 0},
                        {0, 1, 0, 1, 0},
                        {0, 1, 0, 1, 0},
                        {1, 1, 1, 1, 1},
                        {1, 0, 0, 0, 1}
                };
            case RUSSIAN_LETTER_YE:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 0},
                        {1, 1, 1, 1}
                };
            case RUSSIAN_LETTER_YO:
                return new int[][]{
                        {1, 0, 1, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 0},
                        {1, 1, 1, 1}
                };
            case RUSSIAN_LETTER_J:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {0, 1, 1, 1, 0},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1}
                };
            case RUSSIAN_LETTER_Z:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {0, 0, 0, 1},
                        {0, 1, 1, 0},
                        {0, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case RUSSIAN_LETTER_I:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 1, 1},
                        {1, 1, 0, 1},
                        {1, 0, 0, 1}
                };
            case RUSSIAN_LETTER_IKR:
                return new int[][]{
                        {0, 1, 1, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 1, 1},
                        {1, 1, 0, 1},
                        {1, 0, 0, 1}
                };
            case RUSSIAN_LETTER_K:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 1, 0},
                        {1, 1, 0, 0},
                        {1, 0, 1, 0},
                        {1, 0, 0, 1}
                };
            case RUSSIAN_LETTER_L:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 0, 1, 1},
                        {0, 1, 0, 1},
                        {0, 1, 0, 1},
                        {0, 1, 0, 1},
                        {1, 0, 0, 1}
                };
            case RUSSIAN_LETTER_M:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 1},
                        {1, 1, 0, 1, 1},
                        {1, 0, 1, 0, 1},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1}
                };
            case RUSSIAN_LETTER_N:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 1, 1, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1}
                };
            case RUSSIAN_LETTER_O:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}
                };
            case RUSSIAN_LETTER_P:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1}
                };
            case RUSSIAN_LETTER_R:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0}
                };
            case RUSSIAN_LETTER_S:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0},
                        {0, 1, 1, 1}
                };
            case RUSSIAN_LETTER_T:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {0, 1, 0},
                        {0, 1, 0},
                        {0, 1, 0},
                        {0, 1, 0}
                };
            case RUSSIAN_LETTER_U:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 1},
                        {0, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case RUSSIAN_LETTER_F:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 1, 1, 1, 0},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {0, 1, 1, 1, 0},
                        {0, 0, 1, 0, 0}
                };
            case RUSSIAN_LETTER_H:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 1},
                        {0, 1, 0, 1, 0},
                        {0, 0, 1, 0, 0},
                        {0, 1, 0, 1, 0},
                        {1, 0, 0, 0, 1}
                };
            case RUSSIAN_LETTER_C:
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
            case RUSSIAN_LETTER_CH:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 1},
                        {0, 0, 0, 1},
                        {0, 0, 0, 1}
                };
            case RUSSIAN_LETTER_SHA:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 1, 1, 1, 1}
                };
            case RUSSIAN_LETTER_SCHA:
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
            case RUSSIAN_LETTER_SOFT:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case RUSSIAN_LETTER_Y:
                return new int[][]{
                        {0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 1},
                        {1, 1, 1, 0, 0, 1},
                        {1, 0, 0, 1, 0, 1},
                        {1, 1, 1, 0, 0, 1}
                };
            case RUSSIAN_LETTER_HARD:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 1, 0, 0, 0},
                        {0, 1, 0, 0, 0},
                        {0, 1, 1, 1, 0},
                        {0, 1, 0, 0, 1},
                        {0, 1, 1, 1, 0}
                };
            case RUSSIAN_LETTER_E:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {0, 0, 0, 1},
                        {0, 1, 1, 1},
                        {0, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case RUSSIAN_LETTER_YU:
                return new int[][]{
                        {0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0},
                        {1, 0, 0, 1, 1, 0},
                        {1, 0, 1, 0, 0, 1},
                        {1, 1, 1, 0, 0, 1},
                        {1, 0, 1, 0, 0, 1},
                        {1, 0, 0, 1, 1, 0}
                };
            case RUSSIAN_LETTER_YA:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 1},
                        {0, 1, 0, 1},
                        {1, 0, 0, 1}
                };
            case ENGLISH_LETTER_D:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case ENGLISH_LETTER_F:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 0},
                        {1, 0, 0, 0}
                };
            case ENGLISH_LETTER_G:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 0, 1, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}
                };
            case ENGLISH_LETTER_I:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 1, 1},
                        {0, 1, 0},
                        {0, 1, 0},
                        {0, 1, 0},
                        {1, 1, 1}
                };
            case ENGLISH_LETTER_J:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 1},
                        {0, 0, 1, 0},
                        {0, 0, 1, 0},
                        {1, 0, 1, 0},
                        {0, 1, 0, 0}
                };
            case ENGLISH_LETTER_L:
                return new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 0, 0},
                        {1, 0, 0},
                        {1, 0, 0},
                        {1, 0, 0},
                        {1, 1, 1}
                };
            case ENGLISH_LETTER_N:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 1, 0, 1},
                        {1, 0, 1, 1},
                        {1, 0, 0, 1}
                };
            case ENGLISH_LETTER_Q:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 1, 0},
                        {0, 1, 0, 1}
                };
            case ENGLISH_LETTER_R:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 1, 1, 0},
                        {1, 0, 1, 0},
                        {1, 0, 0, 1}
                };
            case ENGLISH_LETTER_S:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 1, 1, 1},
                        {1, 0, 0, 0},
                        {0, 1, 1, 0},
                        {0, 0, 0, 1},
                        {1, 1, 1, 0}
                };
            case ENGLISH_LETTER_U:
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}
                };
            case ENGLISH_LETTER_V:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1},
                        {0, 1, 0, 1, 0},
                        {0, 0, 1, 0, 0}
                };
            case ENGLISH_LETTER_W:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 1, 0, 1, 1},
                        {1, 0, 0, 0, 1}
                };
            case ENGLISH_LETTER_Y:
                return new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 1},
                        {0, 1, 0, 1, 0},
                        {0, 0, 1, 0, 0},
                        {0, 0, 1, 0, 0},
                        {0, 0, 1, 0, 0}
                };
            case ENGLISH_LETTER_Z:
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
