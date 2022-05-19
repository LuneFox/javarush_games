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
    public static SimpleCache<Character, SymbolImage> SYMBOL_CACHE;
    private static boolean isStrokeEnabled;

    public enum Align {
        LEFT, RIGHT
    }

    static {
        fillSymbolsCache();
    }

    private static void fillSymbolsCache() {
        SYMBOL_CACHE = new SimpleCache<Character, SymbolImage>(128) {
            @Override
            protected SymbolImage put(Character character) {

                Arrays.stream(Symbol.values()).forEach(element -> {
                    for (char c : element.getCharacters()) {
                        cache.put(c, new SymbolImage(element));
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
        drawX = drawX != CENTER ? drawX : (Display.SIZE / 2) - (calculateWidth(input) / 2);
        drawY = drawY != CENTER ? drawY : 0;

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

        SymbolImage image = SYMBOL_CACHE.get(c);
        if (isStrokeEnabled) stroke(x, y, image);
        image.changeColor(color);
        image.draw(x, y);
    }

    private static boolean charIsStrokeMarkup(char c) {
        return (c == '<') || (c == '>');
    }

    private static void stroke(int x, int y, SymbolImage image) {
        image.changeColor(STROKE_COLOR);
        image.draw(x - 1, y);
        image.draw(x + 1, y);
        image.draw(x, y - 1);
        image.draw(x, y + 1);
    }

    private static int calculateWidth(String s) {
        int width = 0;
        SymbolImage image;
        char[] chars = s.toLowerCase().toCharArray();

        for (char c : chars) {
            if (charIsStrokeMarkup(c)) continue;
            image = SYMBOL_CACHE.get(c);
            width += (image.matrix[0].length + CHAR_SPACING);
        }

        return width;
    }
}
