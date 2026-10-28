package com.javarush.games.spaceinvaders.view.printer;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.view.Display;

public class Printer {
    private static final SymbolCache CACHE = new SymbolCache();
    private static final Color STROKE_COLOR = Color.SADDLEBROWN;
    private static final int CHAR_SPACING = 1;

    public static void print(String text, int x, int y) {
        print(text, Color.WHITE, x, y, TextAlign.LEFT);
    }

    public static void print(String text, Color color, int x, int y) {
        print(text, color, x, y, TextAlign.LEFT);
    }

    public static void print(String text, Color color, int drawX, int drawY, TextAlign align) {
        drawX = align != TextAlign.CENTER ? drawX : (Display.SIZE / 2) - (calculateWidth(text) / 2);

        PrinterCaret caret = new PrinterCaret(drawX, drawY, align);
        // Stroke state must not leak from one print call to the next (for example, from a stray '<')
        boolean strokeEnabled = false;

        int lineStart = 0;
        while (lineStart <= text.length()) {
            int lineEnd = text.indexOf('\n', lineStart);
            if (lineEnd < 0) lineEnd = text.length();
            String line = text.substring(lineStart, lineEnd);

            caret.gotoStartPosition(line);
            char[] sequence = line.toLowerCase().toCharArray();

            for (char c : sequence) {
                strokeEnabled = drawSymbol(c, color, caret.x, caret.y, strokeEnabled);
                caret.gotoNextSymbol(c);
            }

            caret.gotoNewLine();
            lineStart = lineEnd + 1;
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

    private static boolean drawSymbol(char c, Color color, int x, int y, boolean strokeEnabled) {
        if (charIsStrokeMarkup(c)) {
            return !strokeEnabled;
        }

        SymbolImage image = CACHE.get(c);
        if (strokeEnabled) stroke(x, y, image);
        image.changeColor(color);
        image.draw(x, y);
        return strokeEnabled;
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
