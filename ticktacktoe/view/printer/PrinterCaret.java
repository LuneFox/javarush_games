package com.javarush.games.ticktacktoe.view.printer;

/**
 * Каретка для печати и перевода на новую строку.
 * Singleton.
 *
 * @author LuneFox
 */
class PrinterCaret {

    /** Экземпляр */
    private static PrinterCaret instance;
    /** Высота строки */
    private static final int LINE_HEIGHT = 9;
    /** Крайнее базовое положение каретки */
    private int caretBase;
    /** Выравнивание текста */
    private TextAlign align;
    /** Текущее положение по горизонтали */
    int x;
    /** Текущее положение по вертикали */
    int y;

    static PrinterCaret getInstance() {
        if (instance == null) {
            instance = new PrinterCaret();
        }
        return instance;
    }

    /**
     * Установить положение и выравнивание текста
     *
     * @param x по горизонтали
     * @param y по вертикали
     * @param align выравнивание
     */
    void set(int x, int y, TextAlign align) {
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
     * Когда текст печатается с выравниванием по правой стороне,
     * сместить каретку влево до положения, откуда начнёт печататься текст,
     * таким образом он закончит печататься ровно у правого края.
     *
     * @param line строка, которую требуется напечатать
     */
    void shiftLeftByTextWidth(String line) {
        x = caretBase - Printer.calculateWidth(line);
    }

    /**
     * Каретка выровнена по правому краю?
     * @return выровнена по правому краю
     */
    boolean isAlignRight() {
        return align == TextAlign.RIGHT;
    }

    /**
     * Синглтон, запрещаем конструктор
     */
    private PrinterCaret() {
        this.caretBase = 0;
        this.x = 0;
        this.y = 0;
        this.align = TextAlign.LEFT;
    }
}
