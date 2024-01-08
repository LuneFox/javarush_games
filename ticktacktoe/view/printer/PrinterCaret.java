package com.javarush.games.ticktacktoe.view.printer;

class PrinterCaret {
    private static final int LINE_HEIGHT = 9;
    private final int caretBase;
    private final TextAlign align;
    int x;
    int y;

    PrinterCaret(int x, int y, TextAlign align) {
        this.caretBase = x;
        this.x = x;
        this.y = y;
        this.align = align;
    }

    void gotoNewLine() {
        x = caretBase;
        y += LINE_HEIGHT;
    }

    void gotoNextSymbol(char c) {
        x += Printer.calculateWidth(Character.toString(c));
    }

    void gotoStartPosition(String line) {
        if (align == TextAlign.RIGHT) x = caretBase - Printer.calculateWidth(line);
    }
}
