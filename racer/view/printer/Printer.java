package com.javarush.games.racer.view.printer;

import com.javarush.engine.cell.Color;
import com.javarush.games.racer.view.Display;

public class Printer {
    private static final SymbolCache CACHE = new SymbolCache(128);
    private static final Color STROKE_COLOR = Color.SADDLEBROWN;
    private static final int CHAR_SPACING = 1;
    private static boolean isStrokeEnabled;

    public static void print(String text, int x, int y) {
        print(text, Color.WHITE, x, y, TextAlign.LEFT);
    }

    public static void print(String text, Color color, int x, int y) {
        print(text, color, x, y, TextAlign.LEFT);
    }

    public static void print(String text, Color color, int drawX, int drawY, TextAlign align) {
        drawX = align != TextAlign.CENTER ? drawX : (Display.SIZE / 2) - (calculateWidth(text) / 2);

        PrinterCaret caret = new PrinterCaret(drawX, drawY, align);
        String[] lines = text.split("\n");

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

    static int calculateWidth(String s) {
        int result = 0;
        SymbolImage image;
        char[] chars = s.toLowerCase().toCharArray();

        for (char c : chars) {
            if (charIsStrokeMarkup(c)) continue;
            image = CACHE.get(c);
            result += image.getWidth() + CHAR_SPACING;
        }

        return result;
    }

    private static void drawSymbol(char c, Color color, int x, int y) {
        if (charIsStrokeMarkup(c)) {
            isStrokeEnabled = !isStrokeEnabled;
            return;
        }

        SymbolImage image = CACHE.get(c);
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
}
