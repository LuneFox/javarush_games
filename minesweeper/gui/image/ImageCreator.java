package com.javarush.games.minesweeper.gui.image;

import com.javarush.games.minesweeper.Text;

import com.javarush.games.minesweeper.gui.Effect;

import java.util.Arrays;

/** Создаёт матрицы изображений, спрайтов и рамок. */
public class ImageCreator {
    /** Разница между символом цифры и числом. */
    public static final int CHAR_AND_INT_DIFFERENCE = 48;

    // СОЗДАНИЕ ИЗОБРАЖЕНИЙ

    /** Создаёт матрицу из строк. */
    public static int[][] makeArray(String... strings) {
        int height = detectHeight(strings);
        int width = detectWidth(strings);
        return transformStringsToArray(strings, height, width);
    }

    /** Определяет высоту матрицы. */
    private static int detectHeight(String[] strings) {
        return strings.length;
    }

    /** Определяет ширину матрицы. */
    private static int detectWidth(String[] strings) {
        return Arrays.stream(strings)
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    /** Преобразует строки в матрицу. */
    private static int[][] transformStringsToArray(String[] strings, int height, int width) {
        int[][] result = new int[height][width];

        for (int y = 0; y < height; y++) {
            char[] row = strings[y].toCharArray();
            for (int x = 0; x < row.length; x++) {
                if (row[x] == ' ') row[x] = '0';
                result[y][x] = row[x] - CHAR_AND_INT_DIFFERENCE;
            }
        }

        return result;
    }

    /** Создаёт матрицу из строк. */
    public static int[][] makeArray(String singleString, int width) {
        int height = singleString.length() / width;
        int[][] result = new int[height][width];
        char[] symbols = singleString.toCharArray();

        int index = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result[y][x] = symbols[index++] - CHAR_AND_INT_DIFFERENCE;
            }
        }

        return result;
    }

    /** Создаёт спрайт с отступами. */
    public static int[][] makeSprite(String string, int width, int top, int left) {
        int height = string.length() / width;
        if ((width + left > 10) || (height + top > 10)) {
            throw new IllegalArgumentException(Text.ERROR_SPRITE_TOO_BIG.getText());
        }

        int[][] overlay = makeArray(string, width);
        int[][] result = new int[10][10];

        for (int y = top; y < top + height; y++) {
            System.arraycopy(overlay[y - top], 0, result[y], left, width);
        }

        return result;
    }

    // СОЗДАНИЕ РАМОК

    /** Прозрачный цвет рамки. */
    private static final int FRAME_COLOR_TRANSPARENT = 0;
    /** Цвет фона рамки. */
    private static final int FRAME_COLOR_BACKGROUND = 1;
    /** Цвет тени рамки. */
    private static final int FRAME_COLOR_SHADOW = 2;
    /** Цвет обводки рамки. */
    private static final int FRAME_COLOR_STROKE = 3;

    /** Создаёт матрицу рамки. */
    public static int[][] createFrame(int sizeX, int sizeY, Effect... effects) {
        final boolean addShadow = Arrays.asList(effects).contains(Effect.SHADOW);
        final boolean addStroke = Arrays.asList(effects).contains(Effect.STROKE);

        int[][] frame = new int[addShadow ? ++sizeY : sizeY][addShadow ? ++sizeX : sizeX];
        fillBackground(frame);

        if (addShadow) addFrameShadow(frame);
        if (addStroke) addInnerStroke(frame, addShadow);

        return frame;
    }

    /** Заполняет фон матрицы. */
    private static void fillBackground(int[][] frame) {
        for (int y = 0; y < frame.length; y++) {
            for (int x = 0; x < frame[0].length; x++) {
                frame[y][x] = FRAME_COLOR_BACKGROUND;
            }
        }
    }

    /** Добавляет тень рамки. */
    private static void addFrameShadow(int[][] frame) {
        // Нижняя граница без левого пикселя.
        for (int x = 0; x < frame[0].length; x++) {
            frame[frame.length - 1][x] = (x == 0) ? FRAME_COLOR_TRANSPARENT : FRAME_COLOR_SHADOW;
        }

        // Правая граница без верхнего пикселя.
        for (int y = 0; y < frame.length; y++) {
            frame[y][frame[0].length - 1] = (y == 0) ? FRAME_COLOR_TRANSPARENT : FRAME_COLOR_SHADOW;
        }
    }

    /** Добавляет внутреннюю обводку. */
    private static void addInnerStroke(int[][] frame, boolean addShadow) {
        int sizeY = frame.length - (addShadow ? 1 : 0);
        int sizeX = frame[0].length - (addShadow ? 1 : 0);

        // Верхняя и нижняя линии.
        for (int x = 0; x < sizeX; x++) {
            frame[0][x] = FRAME_COLOR_STROKE;
            frame[sizeY - 1][x] = FRAME_COLOR_STROKE;
        }

        // Остальные левые и правые линии.
        for (int y = 1; y < sizeY - 1; y++) {
            frame[y][0] = FRAME_COLOR_STROKE;
            frame[y][sizeX - 1] = FRAME_COLOR_STROKE;
        }
    }

    // СОЗДАНИЕ КЛЕТОК

    /** Светлый цвет клетки. */
    private static final int CELL_LIGHT_COLOR = 2;
    /** Тёмный цвет клетки. */
    private static final int CELL_SHADOW_COLOR = 3;

    /** Создаёт матрицу клетки. */
    public static int[][] createCell(int sizeX, int sizeY, boolean bevelUp) {
        int[][] cell = new int[sizeY][sizeX];
        fillBackground(cell);
        bevelCell(cell, sizeX, sizeY, bevelUp);
        return cell;
    }

    /** Рисует скос клетки. */
    private static void bevelCell(int[][] cell, int sizeX, int sizeY, boolean up) {
        for (int x = 0; x < sizeX; x++) {
            cell[0][x] = CELL_LIGHT_COLOR;
        }

        for (int y = 0; y < sizeY; y++) {
            cell[y][0] = CELL_LIGHT_COLOR;
        }

        if (up) {
            for (int x = 1; x < sizeX; x++) {
                cell[sizeY - 1][x] = CELL_SHADOW_COLOR;
            }
            for (int y = 1; y < sizeY; y++) {
                cell[y][sizeX - 1] = CELL_SHADOW_COLOR;
            }
        } else {
            for (int x = 1; x < sizeX; x++) {
                cell[1][x] = CELL_SHADOW_COLOR;
            }
            for (int y = 1; y < sizeY; y++) {
                cell[y][1] = CELL_SHADOW_COLOR;
            }
        }
    }
}
