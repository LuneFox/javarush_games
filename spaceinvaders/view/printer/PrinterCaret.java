package com.javarush.games.spaceinvaders.view.printer;

/**
 * Text cursor: remembers where the next glyph goes and how the lines of one call are aligned.
 */
class PrinterCaret {
    private static final int LINE_HEIGHT = 9;

    private final int baseX;
    private final TextAlign align;

    private int x;
    private int y;

    PrinterCaret(int x, int y, TextAlign align) {
        this.baseX = x;
        this.x = x;
        this.y = y;
        this.align = align;
    }

    void newLine() {
        x = baseX;
        y += LINE_HEIGHT;
    }

    void advance(char symbol) {
        x += Printer.glyphWidth(symbol);
    }

    void alignTo(String line) {
        if (align == TextAlign.RIGHT) {
            x = baseX - Printer.measureWidth(line);
        }
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }
}
