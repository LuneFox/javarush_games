package com.javarush.games.minesweeper.gui.image;

/**
 * Utility class for generating images of different kind.
 */

public class ImageCreator {
    public static final int CHAR_AND_INT_DIFFERENCE = 48;

    // IMAGE GENERATION

    /**
     * Fill an array with one-digit numbers from strings.
     */
    public static int[][] makeArray(String... strings) {
        // Detect height
        int height = strings.length;

        // Detect width
        int width = 0;
        for (String s : strings) {
            if (s.length() > width) width = s.length();
        }

        // Create container
        int[][] result = new int[height][width];

        // Copy data
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

    /**
     * Place a sprite over an empty 10x10 grid, top and left are paddings.
     * Useful to place and align small images over 10x10 pixel cells without filling empty spaces with zeros.
     **/
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


    // FRAME GENERATION

    public static int[][] createFrame(int sizeX, int sizeY, boolean addShadow, boolean addStroke) {
        // Image with shadow is 1 px taller and wider
        if (addShadow) {
            sizeX++;
            sizeY++;
        }

        int[][] frame = new int[sizeY][sizeX];
        fillFrameBody(frame, sizeX, sizeY);
        if (addShadow) addFrameShadow(frame, sizeX, sizeY);
        if (addStroke) addInnerStroke(frame, sizeX, sizeY, addShadow);
        return frame;
    }

    private static void addFrameShadow(int[][] frame, int sizeX, int sizeY) {
        for (int x = 0; x < sizeX; x++) {
            frame[sizeY - 1][x] = (x == 0) ? 0 : 2;
        }
        for (int y = 0; y < sizeY; y++) {
            frame[y][sizeX - 1] = (y == 0) ? 0 : 2;
        }
    }

    private static void addInnerStroke(int[][] frame, int sizeX, int sizeY, boolean shadow) {
        if (shadow) { // if shadow is drawn, shrink the drawing zone back to normal window
            sizeX--;
            sizeY--;
        }
        for (int x = 0; x < sizeX; x++) {
            frame[0][x] = 3;
            frame[sizeY - 1][x] = 3;
        }
        for (int y = 0; y < sizeY; y++) {
            frame[y][0] = 3;
            frame[y][sizeX - 1] = 3;
        }
    }

    private static void fillFrameBody(int[][] frame, int sizeX, int sizeY) {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                frame[y][x] = 1;
            }
        }
    }
}
