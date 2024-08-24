package com.javarush.games.ticktacktoe.view.printer;

import com.javarush.engine.cell.Color;
import com.javarush.games.ticktacktoe.view.Display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Принтер для отображения рисованного текста на экране игры.
 *
 * @author LuneFox
 */
public class Printer {

    private static final PrinterCaret CARET = PrinterCaret.getInstance();
    /** Кэш использованных символов */
    private static final SymbolCache CACHE = new SymbolCache();
    /** Цвет текста по умолчанию */
    private static final Color DEFAULT_COLOR = Color.WHITE;
    /** Цвет обводки текста */
    private static final Color STROKE_COLOR = Color.DARKSLATEBLUE;
    /** Расстояние между символами */
    private static final int CHAR_SPACING = 1;
    /** Использование обводки */
    private static boolean isStrokeEnabled;
    /** Список символов, по которому срабатывает обводка */
    private static final List<Character> strokeSymbols = new ArrayList<>(Arrays.asList('<','>'));

    /**
     * Напечатать текст на экране (авто-выравнивание по левому краю, цвет по умолчанию)
     *
     * @param text текст
     * @param x положение по горизонтали
     * @param y положение по вертикали
     */
    public static void print(String text, int x, int y) {
        print(text, DEFAULT_COLOR, x, y, TextAlign.LEFT);
    }

    /**
     * Напечатать текст на экране (авто-выравнивание по левому краю)
     *
     * @param text текст
     * @param color цвет текста
     * @param x положение по горизонтали
     * @param y положение по вертикали
     */
    public static void print(String text, Color color, int x, int y) {
        print(text, color, x, y, TextAlign.LEFT);
    }

    /**
     * Напечатать текст на экране
     *
     * @param text текст
     * @param color цвет текста
     * @param drawX положение по горизонтали
     * @param drawY положение по вертикали
     * @param align выравнивание
     */
    public static void print(String text, Color color, int drawX, int drawY, TextAlign align) {
        drawX = align != TextAlign.CENTER ? drawX : (Display.SIZE / 2) - (calculateWidth(text) / 2);
        CARET.set(drawX, drawY, align);
        String[] lines = text.split("\n");
        for (String line : lines) {
            printLine(color, line);
        }
    }

    /**
     * Печать одной строки
     *
     * @param color цвет
     * @param line  строка
     */
    private static void printLine(Color color, String line) {
        if (CARET.isAlignRight()) {
            CARET.shiftLeftByTextWidth(line);
        }
        char[] sequence = line.toLowerCase().toCharArray();
        for (char c : sequence) {
            drawSymbol(c, color, CARET.x, CARET.y);
            CARET.gotoNextSymbol(c);
        }
        CARET.gotoNewLine();
    }

    /**
     * Рассчитать ширину строки в пикселях
     *
     * @param s строка
     * @return вычисленная ширина строки
     */
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

    /**
     * Напечатать один символ
     *
     * @param c символ
     * @param color цвет
     * @param x положение по горизонтали
     * @param y положение по вертикали
     */
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

    /**
     * Проверяет, является символ служебным для переключения режима обводки
     *
     * @param c проверяемый символ
     * @return принадлежность символа к служебному для переключения режима обводки
     */
    private static boolean charIsStrokeMarkup(char c) {
        return strokeSymbols.contains(c);
    }

    /**
     * Рисует символ четыре раза на фоне со смещением, чтобы создать эффект обводки.
     *
     * @param x положение по горизонтали
     * @param y положение по вертикали
     * @param image изображение символа
     */
    private static void stroke(int x, int y, SymbolImage image) {
        image.changeColor(STROKE_COLOR);
        image.draw(x - 1, y);
        image.draw(x + 1, y);
        image.draw(x, y - 1);
        image.draw(x, y + 1);
    }
}
