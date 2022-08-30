package com.javarush.games.minesweeper.gui.image;

import java.util.Arrays;

/**
 * Utility class for generating images of different kinds.
 */

public class ImageCreator {
    public static final int CHAR_AND_INT_DIFFERENCE = 48;

    // IMAGE GENERATION

    /**
     * Fill an array with one-digit numbers from strings.
     */
    public static int[][] makeArray(String... strings) {
        int height = detectHeight(strings);
        int width = detectWidth(strings);
        return transformStringsToArray(strings, height, width);
    }

    private static int detectHeight(String[] strings) {
        return strings.length;
    }

    private static int detectWidth(String[] strings) {
        return Arrays.stream(strings)
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

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

    /**
     * Split string by width and fill the array with numbers.
     * e.g. String with length=12 and width=3 will make a 3x4 array.
     */
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

    /**
     * Place a sprite over an empty 10x10 grid, top and left are paddings.
     * Useful to place and align small images over 10x10 pixel cells without filling empty spaces with zeros.
     **/
    public static int[][] makeSprite(String string, int width, int top, int left) {
        int height = string.length() / width;
        if ((width + left > 10) || (height + top > 10)) {
            throw new IllegalArgumentException("Sprite is too big!");
        }

        int[][] overlay = makeArray(string, width);
        int[][] result = new int[10][10];

        for (int y = top; y < top + height; y++) {
            System.arraycopy(overlay[y - top], 0, result[y], left, width);
        }

        return result;
    }


    // FRAME GENERATION

    private static final int FRAME_COLOR_TRANSPARENT = 0;
    private static final int FRAME_COLOR_BACKGROUND = 1;
    private static final int FRAME_COLOR_SHADOW = 2;
    private static final int FRAME_COLOR_STROKE = 3;

    public static int[][] createFrame(int sizeX, int sizeY, boolean addShadow, boolean addStroke) {
        // Image with shadow is 1 px taller and wider
        if (addShadow) {
            sizeX++;
            sizeY++;
        }

        int[][] frame = new int[sizeY][sizeX];
        fillBackground(frame);
        if (addShadow) addFrameShadow(frame);
        if (addStroke) addInnerStroke(frame, addShadow);
        return frame;
    }

    private static void addFrameShadow(int[][] frame) {
        // Draw bottom borderline without left pixel;
        for (int x = 0; x < frame[0].length; x++) {
            frame[frame.length - 1][x] = (x == 0) ? FRAME_COLOR_TRANSPARENT : FRAME_COLOR_SHADOW;
        }

        // Draw right borderline without top pixel;
        for (int y = 0; y < frame.length; y++) {
            frame[y][frame[0].length - 1] = (y == 0) ? FRAME_COLOR_TRANSPARENT : FRAME_COLOR_SHADOW;
        }
    }

    private static void addInnerStroke(int[][] frame, boolean shadow) {
        int sizeY = frame.length;
        int sizeX = frame[0].length;

        // If shadow is drawn, shrink the drawing zone back to normal window
        if (shadow) {
            sizeX--;
            sizeY--;
        }

        // Draw top and bottom lines
        for (int x = 0; x < sizeX; x++) {
            frame[0][x] = FRAME_COLOR_STROKE;
            frame[sizeY - 1][x] = FRAME_COLOR_STROKE;
        }

        // Draw remaining left and right lines
        for (int y = 1; y < sizeY - 1; y++) {
            frame[y][0] = FRAME_COLOR_STROKE;
            frame[y][sizeX - 1] = FRAME_COLOR_STROKE;
        }
    }

    private static void fillBackground(int[][] frame) {
        for (int y = 0; y < frame.length; y++) {
            for (int x = 0; x < frame[0].length; x++) {
                frame[y][x] = FRAME_COLOR_BACKGROUND;
            }
        }
    }

    // CELL GENERATION

    private static final int CELL_LIGHT_COLOR = 2;
    private static final int CELL_SHADOW_COLOR = 3;

    public static int[][] createCell(int sizeX, int sizeY, boolean bevelUp) {
        int[][] cell = new int[sizeY][sizeX];
        fillBackground(cell);
        bevelCell(cell, sizeX, sizeY, bevelUp);
        return cell;
    }

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
