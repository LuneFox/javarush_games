package com.javarush.games.racer.view.printer;

/**
 * Mutable pen used by {@link Printer} while a string is being rendered.
 * It remembers a reference column ({@code caretBase}) so each new line can start from it, advances
 * by each glyph's measured width, and uses a fixed nine-cell line height.
 */
class PrinterCaret {
    private static final int LINE_HEIGHT = 9;
    private final int caretBase;
    private final TextAlign align;
    int x;
    int y;

    /**
     * Creates a caret at the given cell and remembers that x as the reference column for later
     * lines.
     *
     * @param x starting column, also the reference column for each line
     * @param y starting row
     * @param align alignment mode used when a line starts
     */
    PrinterCaret(int x, int y, TextAlign align) {
        this.caretBase = x;
        this.x = x;
        this.y = y;
        this.align = align;
    }

    /**
     * Moves to the next line by restoring x to the reference column and adding nine rows to y.
     */
    void gotoNewLine() {
        x = caretBase;
        y += LINE_HEIGHT;
    }

    /**
     * Advances x by the measured width of one character, which already includes the one-cell
     * spacing; markup characters measure as zero and therefore do not move the caret.
     *
     * @param c character that was just drawn
     */
    void gotoNextSymbol(char c) {
        x += Printer.calculateWidth(Character.toString(c));
    }

    /**
     * Prepares the caret for a line. RIGHT alignment rewinds x from the caret base by the line's
     * measured width, while LEFT and CENTER keep the base x; CENTER positioning is done by
     * {@link Printer} before the caret is created.
     *
     * @param line line about to be drawn, measured as a whole
     */
    void gotoStartPosition(String line) {
        if (align == TextAlign.RIGHT) x = caretBase - Printer.calculateWidth(line);
    }
}
