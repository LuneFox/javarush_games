package com.javarush.games.minesweeper.gui;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;

import java.util.Arrays;

/** Печатает текст пиксельными символами. */
public class Printer {
    /** Маркер центрирования. */
    public static final int CENTER = Integer.MIN_VALUE;
    /** Расстояние между символами. */
    private static final int CHAR_SPACING = 1;
    /** Высота строки. */
    private static final int LINE_HEIGHT = 9;
    /** Кэш значений. */
    public static final Cache<Character, Image> cache;
    /**
     * Признак, что таблица символов шрифта уже собрана.
     */
    private static boolean cacheInitialized;
    /** Включена ли обводка текста. */
    private static boolean isStrokeEnabled;

    /** Выравнивание текста. */
    public enum Align {
        /** Выравнивание по левому краю. */
        LEFT,
        /** Выравнивание по правому краю. */
        RIGHT
    }

    static {
        cache = new Cache<Character, Image>(128) {
            /** Создаёт и сохраняет значение. */
            @Override
            protected Image put(Character character) {
                if (!cacheInitialized) {
                    cacheInitialized = true;
                    Arrays.stream(ImageType.values())
                            .filter(element -> element.name().startsWith("SYM_"))
                            .forEach(element -> {
                                for (char c : element.getCharacters()) {
                                    cache.put(c, new Image(element));
                                }
                            });
                }
                return getIfPresent(character);
            }
        };
    }

    /** Печатает текст. */
    public static void print(String text, int x, int y) {
        print(text, Color.WHITE, x, y, Align.LEFT);
    }

    /** Печатает текст. */
    public static void print(String text, Color color, int x, int y) {
        print(text, color, x, y, Align.LEFT);
    }

    /** Печатает текст. */
    public static void print(String input, Color color, int drawX, int drawY, Align align) {
        print(input, color, Theme.TEXT_SHADOW.getColor(), drawX, drawY, align);
    }

    /** Печатает текст с заданным цветом обводки. */
    public static void print(String input, Color color, Color strokeColor, int drawX, int drawY) {
        print(input, color, strokeColor, drawX, drawY, Align.LEFT);
    }

    /** Печатает текст с заданным цветом обводки и выравниванием. */
    public static void print(String input, Color color, Color strokeColor, int drawX, int drawY, Align align) {
        isStrokeEnabled = false;
        drawX = adjustDrawX(input, drawX);
        drawY = adjustDrawY(drawY);
        final int caretBase = drawX;

        /** Курсор посимвольной печати текста. */
        class Caret {
            /** Координата X. */
            private int x, y;

            /** Создаёт экземпляр Caret. */
            private Caret(int x, int y) {
                this.x = x;
                this.y = y;
            }

            /** Переходит на новую строку. */
            private void gotoNewLine() {
                x = caretBase;
                y += LINE_HEIGHT;
            }

            /** Переходит к следующему символу. */
            private void gotoNextSymbol(char c) {
                if (!charIsStrokeMarkup(c)) {
                    x += symbolWidth(c);
                }
            }

            /** Переходит к началу строки. */
            private void gotoStartPosition(String line) {
                if (align == Align.RIGHT) x = caretBase - calculateWidth(line);
            }
        }

        Caret caret = new Caret(drawX, drawY);
        String[] lines = input.split("\n");
        for (String line : lines) {
            caret.gotoStartPosition(line);
            char[] sequence = line.toLowerCase().toCharArray();
            for (char c : sequence) {
                drawSymbol(c, color, strokeColor, caret.x, caret.y);
                caret.gotoNextSymbol(c);
            }
            caret.gotoNewLine();
        }
    }

    /** Рисует символ. */
    private static void drawSymbol(char c, Color color, Color strokeColor, int x, int y) {
        if (charIsStrokeMarkup(c)) {
            isStrokeEnabled = !isStrokeEnabled;
            return;
        }

        Image symbol = getSymbol(c);
        if (symbol == null) return;
        if (isStrokeEnabled) stroke(x, y, symbol, strokeColor);
        symbol.changeColor(color, 1);
        symbol.draw(x, y);
    }

    /**
     * Возвращает символ шрифта или пробел для неизвестного символа.
     */
    private static Image getSymbol(char c) {
        Image symbol = cache.get(c);
        return symbol == null ? cache.get(' ') : symbol;
    }

    /** Возвращает ширину символа. */
    private static int symbolWidth(char c) {
        Image symbol = getSymbol(c);
        return symbol == null ? 0 : symbol.getMatrix()[0].length + CHAR_SPACING;
    }

    /** Сообщает, является ли символ разметкой обводки. */
    private static boolean charIsStrokeMarkup(char c) {
        return (c == '<') || (c == '>');
    }

    /** Рисует обводку символа. */
    private static void stroke(int x, int y, Image symbol, Color strokeColor) {
        symbol.changeColor(strokeColor, 1);
        symbol.draw(x - 1, y);
        symbol.draw(x + 1, y);
        symbol.draw(x, y - 1);
        symbol.draw(x, y + 1);
    }

    /** Корректирует X для выравнивания. */
    private static int adjustDrawX(String input, int drawX) {
        if (drawX == CENTER) {
            int width = calculateWidth(input);
            drawX = (Display.SIZE / 2) - (width / 2);
        }
        return drawX;
    }

    /** Корректирует Y для выравнивания. */
    private static int adjustDrawY(int drawY) {
        if (drawY == CENTER) {
            drawY = 0;
        }
        return drawY;
    }

    /** Вычисляет ширину текста. */
    public static int calculateWidth(String s) {
        int width = 0;
        for (char c : s.toLowerCase().toCharArray()) {
            if (!charIsStrokeMarkup(c)) width += symbolWidth(c);
        }
        return width;
    }
}
