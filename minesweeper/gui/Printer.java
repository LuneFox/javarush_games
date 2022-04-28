package com.javarush.games.minesweeper.gui;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;

import java.util.Arrays;

/**
 * Utility class for drawing text information using symbol images.
 */

public class Printer {
    public static final int CENTER = Integer.MIN_VALUE;
    private static final int CHAR_SPACING = 1;
    private static final int LINE_HEIGHT = 9;
    public static final Cache<Character, Image> cache;

    public enum Align {
        LEFT, RIGHT
    }

    static {
        cache = new Cache<Character, Image>(128) {
            @Override
            protected Image put(Character character) {
                Arrays.stream(ImageType.values())
                        .filter(element -> element.name().startsWith("SYM_"))
                        .forEach(element -> {
                            for (char c : element.getCharacters()) {
                                cache.put(c, new Image(element));
                            }
                        });
                return cache.get(character);
            }
        };
    }

    public static void print(String text, int x, int y) {
        print(text, Color.WHITE, x, y, Align.LEFT);
    }

    public static void print(String text, Color color, int x, int y) {
        print(text, color, x, y, Align.LEFT);
    }

    public static void print(String input, Color color, int drawX, int drawY, Align align) {
        drawX = adjustDrawX(input, drawX);
        drawY = adjustDrawY(drawY);
        final int caretNewLineX = drawX;

        class Caret {
            private int x, y;

            private Caret(int x, int y) {
                this.x = x;
                this.y = y;
            }

            private void gotoNewLine() {
                x = caretNewLineX;
                y += LINE_HEIGHT;
            }

            private void shiftToNextSymbol(char c) {
                int shift = calculateWidth(Character.toString(c));
                x = (align == Align.RIGHT) ? (x - shift) : (x + shift);
            }
        }

        Caret caret = new Caret(drawX, drawY);
        char[] chars = getCharsInCorrectOrder(input, align);
        boolean isStrokeEnabled = false;
        if (align == Align.RIGHT) caret.shiftToNextSymbol(chars[0]);

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '\n') {
                caret.gotoNewLine();
                if (align == Align.RIGHT) {
                    caret.shiftToNextSymbol(selectRelativeCharForCaretShift(Align.RIGHT, chars, i));
                }
                continue;
            }

            if (charIsStrokeMarkup(chars[i])) {
                isStrokeEnabled = !isStrokeEnabled;
                continue;
            }

            if (isStrokeEnabled) {
                drawSymbolStroked(chars[i], color, caret.x, caret.y);
            } else {
                drawSymbol(chars[i], color, caret.x, caret.y);
            }

            char relativeChar = selectRelativeCharForCaretShift(align, chars, i);
            caret.shiftToNextSymbol(relativeChar);
        }
    }

    private static char selectRelativeCharForCaretShift(Align align, char[] chars, int i) {
        final int TAKE_CURRENT_CHAR = 0;
        final int TAKE_NEXT_CHAR = 1;
        final int CRITERIA = (isLastCharacter(chars, i) || align == Align.LEFT) ? TAKE_CURRENT_CHAR : TAKE_NEXT_CHAR;
        return (chars[i + CRITERIA]);
    }

    private static boolean isLastCharacter(char[] chars, int i) {
        return i == (chars.length - 1);
    }

    private static char[] getCharsInCorrectOrder(String input, Align align) {
        return align == Align.RIGHT ?
                new StringBuilder(input).reverse().toString().toLowerCase().toCharArray() :
                input.toLowerCase().toCharArray();
    }

    private static void drawSymbol(char c, Color color, int x, int y) {
        Image symbol = cache.get(c);
        symbol.replaceColor(color, 1);
        symbol.draw(x, y);
    }

    private static void drawSymbolStroked(char c, Color color, int x, int y) {
        Image symbol = cache.get(c);
        symbol.replaceColor(Theme.TEXT_SHADOW.getColor(), 1);
        symbol.draw(x - 1, y);
        symbol.draw(x + 1, y);
        symbol.draw(x, y - 1);
        symbol.draw(x, y + 1);
        symbol.replaceColor(color, 1);
        symbol.draw(x, y);
    }

    private static int adjustDrawX(String input, int drawX) {
        if (drawX == CENTER) {
            int width = calculateWidth(input);
            drawX = (Display.SIZE / 2) - (width / 2);
        }
        return drawX;
    }

    private static int adjustDrawY(int drawY) {
        if (drawY == CENTER) {
            drawY = 0;
        }
        return drawY;
    }

    public static int calculateWidth(String s) {
        int width = 0;
        Image symbol;
        char[] chars = s.toLowerCase().toCharArray();
        for (char c : chars) {
            if (charIsStrokeMarkup(c)) continue;
            symbol = cache.get(c);
            width += (symbol.matrix[0].length + CHAR_SPACING);
        }
        return width;
    }

    private static boolean charIsStrokeMarkup(char c) {
        return c == '<' || c == '>';
    }
}
