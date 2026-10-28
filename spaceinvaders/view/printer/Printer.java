package com.javarush.games.spaceinvaders.view.printer;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.model.GameField;

/**
 * Prints pixel font text into the screen buffer. Supports several lines separated by {@code \n},
 * horizontal alignment and an outline toggled with {@code <} and {@code >}.
 */
public class Printer {
    private static final SymbolCache CACHE = new SymbolCache();
    private static final Color STROKE_COLOR = Color.SADDLEBROWN;
    private static final int CHAR_SPACING = 1;

    private Printer() {
    }

    public static void print(String text, int x, int y) {
        print(text, Color.WHITE, x, y, TextAlign.LEFT);
    }

    public static void print(String text, Color color, int x, int y) {
        print(text, color, x, y, TextAlign.LEFT);
    }

    public static void print(String text, Color color, int x, int y, TextAlign align) {
        int startX = (align == TextAlign.CENTER) ? (GameField.WIDTH / 2) - (measureWidth(text) / 2) : x;
        PrinterCaret caret = new PrinterCaret(startX, y, align);

        // The outline state must not leak from one print call to the next
        boolean stroked = false;
        int lineStart = 0;
        while (lineStart <= text.length()) {
            int lineEnd = text.indexOf('\n', lineStart);
            if (lineEnd < 0) lineEnd = text.length();

            stroked = printLine(text.substring(lineStart, lineEnd), color, caret, stroked);
            lineStart = lineEnd + 1;
        }
    }

    private static boolean printLine(String line, Color color, PrinterCaret caret, boolean stroked) {
        caret.alignTo(line);

        for (char symbol : line.toLowerCase().toCharArray()) {
            stroked = printGlyph(symbol, color, caret.getX(), caret.getY(), stroked);
            caret.advance(symbol);
        }

        caret.newLine();
        return stroked;
    }

    private static boolean printGlyph(char symbol, Color color, int x, int y, boolean stroked) {
        if (isStrokeMarkup(symbol)) {
            return !stroked;
        }

        SymbolImage glyph = CACHE.get(symbol);
        if (stroked) {
            drawStroke(glyph, x, y);
        }
        glyph.draw(x, y, color);
        return stroked;
    }

    private static void drawStroke(SymbolImage glyph, int x, int y) {
        glyph.draw(x - 1, y, STROKE_COLOR);
        glyph.draw(x + 1, y, STROKE_COLOR);
        glyph.draw(x, y - 1, STROKE_COLOR);
        glyph.draw(x, y + 1, STROKE_COLOR);
    }

    static int glyphWidth(char symbol) {
        if (isStrokeMarkup(symbol)) return 0;

        return CACHE.get(symbol).getWidth() + CHAR_SPACING;
    }

    static int measureWidth(String text) {
        int width = 0;
        for (char symbol : text.toLowerCase().toCharArray()) {
            width += glyphWidth(symbol);
        }
        return width;
    }

    private static boolean isStrokeMarkup(char symbol) {
        return symbol == '<' || symbol == '>';
    }
}
