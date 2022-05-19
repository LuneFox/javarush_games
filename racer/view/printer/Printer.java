package com.javarush.games.racer.view.printer;

import com.javarush.engine.cell.Color;
import com.javarush.games.racer.view.Display;

import java.util.Arrays;

/**
 * Utility class for drawing text information using symbol images.
 */

public class Printer {
    public static final int CENTER = Integer.MIN_VALUE;
    private static final int CHAR_SPACING = 1;
    private static final int LINE_HEIGHT = 9;
    private static final Color STROKE_COLOR = Color.SADDLEBROWN;
    public static final Cache<Character, Image> cache;
    private static boolean isStrokeEnabled;

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
        final int caretBase = drawX;

        class Caret {
            private int x, y;

            private Caret(int x, int y) {
                this.x = x;
                this.y = y;
            }

            private void gotoNewLine() {
                x = caretBase;
                y += LINE_HEIGHT;
            }

            private void gotoNextSymbol(char c) {
                x += calculateWidth(Character.toString(c));
            }

            private void gotoStartPosition(String line) {
                if (align == Align.RIGHT) x = caretBase - calculateWidth(line);
            }
        }

        Caret caret = new Caret(drawX, drawY);
        String[] lines = input.split("\n");
        for (String line : lines) {
            caret.gotoStartPosition(line);
            char[] sequence = line.toLowerCase().toCharArray();
            for (char c : sequence) {
                drawSymbol(c, color, caret.x, caret.y);
                caret.gotoNextSymbol(c);
            }
            caret.gotoNewLine();
        }
    }

    private static void drawSymbol(char c, Color color, int x, int y) {
        if (charIsStrokeMarkup(c)) {
            isStrokeEnabled = !isStrokeEnabled;
            return;
        }

        Image symbol = cache.get(c);
        if (isStrokeEnabled) stroke(x, y, symbol);
        symbol.changeColor(color, 1);
        symbol.draw(x, y);
    }

    private static boolean charIsStrokeMarkup(char c) {
        return (c == '<') || (c == '>');
    }

    private static void stroke(int x, int y, Image symbol) {
        symbol.changeColor(STROKE_COLOR, 1);
        symbol.draw(x - 1, y);
        symbol.draw(x + 1, y);
        symbol.draw(x, y - 1);
        symbol.draw(x, y + 1);
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
}
