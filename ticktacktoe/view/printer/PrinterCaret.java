package com.javarush.games.ticktacktoe.view.printer;

/**
 * Каретка для печати и перевода на новую строку.
 * 
 * @author LuneFox
 */
class PrinterCaret {
    /** Высота строки */
    private static final int LINE_HEIGHT = 9;
    /** Крайнее базовое положение каретки */
    private final int caretBase;
    /** Выравнивание текста */
    private final TextAlign align;
    /** Текущее положение по горизонтали */
    int x;
    /** Текущее положение по вертикали */
    int y;

    PrinterCaret(int x, int y, TextAlign align) {
        this.caretBase = x;
        this.x = x;
        this.y = y;
        this.align = align;
    }

    /**
     * Сместиться на новую строку
     */
    void gotoNewLine() {
        x = caretBase;
        y += LINE_HEIGHT;
    }

    /**
     * Передвинуть своё положение на ширину переданного символа.
     * 
     * @param c печатаемый символ
     */
    void gotoNextSymbol(char c) {
        x += Printer.calculateWidth(Character.toString(c));
    }

    /**
     * Если текст печатается с выравниванием по правой стороне,
     * сместить каретку влево до положения, откуда начнёт печататься текст,
     * таким образом он закончит печататься ровно у правого края.
     * 
     * @param line строка, которую требуется напечатать
     */
    void shiftForRightAlignedText(String line) {
        if (align == TextAlign.RIGHT) x = caretBase - Printer.calculateWidth(line);
    }
}
