package com.javarush.games.spaceinvaders.view.printer;

/** Курсор печати: позиция и выравнивание строк. */
class PrinterCaret {
    private static final int LINE_HEIGHT = 9;

    private final int baseX;
    private final TextAlign align;

    private int x;
    private int y;

    /** Создаёт курсор в заданной точке. */
    PrinterCaret(int x, int y, TextAlign align) {
        this.baseX = x;
        this.x = x;
        this.y = y;
        this.align = align;
    }

    /** Переводит курсор на новую строку. */
    void newLine() {
        x = baseX;
        y += LINE_HEIGHT;
    }

    /** Сдвигает курсор на ширину символа. */
    void advance(char symbol) {
        x += Printer.glyphWidth(symbol);
    }

    /** Выравнивает строку по правому краю. */
    void alignTo(String line) {
        if (align == TextAlign.RIGHT) {
            x = baseX - Printer.measureWidth(line);
        }
    }

    /** Возвращает X курсора. */
    int getX() {
        return x;
    }

    /** Возвращает Y курсора. */
    int getY() {
        return y;
    }
}
