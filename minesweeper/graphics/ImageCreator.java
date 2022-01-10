package com.javarush.games.minesweeper.graphics;

/**
 * Utility class for generating images of different kind.
 */

public class ImageCreator {
    public static final int CHAR_AND_INT_DIFFERENCE = 48;

    // IMAGE GENERATION

    // Fill the array with numbers from strings
    public static int[][] makeArray(String... strings) {
        int width = strings[0].length();
        int height = strings.length;
        int[][] result = new int[height][width];
        for (int y = 0; y < height; y++) {
            char[] row = strings[y].toCharArray();
            for (int x = 0; x < width; x++) {
                result[y][x] = row[x] - CHAR_AND_INT_DIFFERENCE;
            }
        }
        return result;
    }

    // Split string by width and fill the array with numbers
    public static int[][] makeArray(int width, String string) {
        int height = string.length() / width;
        int[][] result = new int[height][width];
        char[] symbols = string.toCharArray();
        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result[y][x] = symbols[index++] - CHAR_AND_INT_DIFFERENCE;
            }
        }
        return result;
    }

    // Place a sprite over an empty 10x10 grid, top and left are paddings
    public static int[][] makeSprite(String string, int width, int top, int left) {
        int height = string.length() / width;
        if ((width + left > 10) || (height + top > 10)) throw new IllegalArgumentException("Sprite is too big!");
        int[][] overlay = makeArray(width, string);
        int[][] result = new int[10][10];
        for (int y = top; y < top + height; y++) {
            System.arraycopy(overlay[y - top], 0, result[y], left, width);
        }
        return result;
    }


    // WINDOW GENERATION

    public static int[][] createWindowBitmap(int sizeX, int sizeY, boolean addShadow, boolean addFrame) {
        // Image with shadow is 1 px taller and wider
        if (addShadow) {
            sizeX++;
            sizeY++;
        }

        int[][] window = new int[sizeY][sizeX];
        fillWindowBackground(window, sizeX, sizeY);
        if (addShadow) addWindowShadow(window, sizeX, sizeY);
        if (addFrame) addWindowFrame(window, sizeX, sizeY, addShadow);
        return window;
    }

    private static void addWindowShadow(int[][] window, int sizeX, int sizeY) {
        for (int x = 0; x < sizeX; x++) {
            window[sizeY - 1][x] = (x == 0) ? 0 : 2;
        }
        for (int y = 0; y < sizeY; y++) {
            window[y][sizeX - 1] = (y == 0) ? 0 : 2;
        }
    }

    private static void addWindowFrame(int[][] window, int sizeX, int sizeY, boolean shadow) {
        if (shadow) { // if shadow is drawn, shrink the drawing zone back to normal window
            sizeX--;
            sizeY--;
        }
        for (int x = 0; x < sizeX; x++) {
            window[0][x] = 3;
            window[sizeY - 1][x] = 3;
        }
        for (int y = 0; y < sizeY; y++) {
            window[y][0] = 3;
            window[y][sizeX - 1] = 3;
        }
    }

    private static void fillWindowBackground(int[][] window, int sizeX, int sizeY) {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                window[y][x] = 1;
            }
        }
    }
}
