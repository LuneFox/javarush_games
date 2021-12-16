package com.javarush.games.minesweeper.graphics;

public class ImageDataCreator {
    public static final int CHAR_AND_INT_DIFFERENCE = 48;

    // IMAGE GENERATION

    public static int[][] stringsToArray(String... strings) {
        int sizeX = strings[0].length();
        int sizeY = strings.length;
        int[][] result = new int[sizeY][sizeX];
        for (int y = 0; y < sizeY; y++) {
            char[] row = strings[y].toCharArray();
            for (int x = 0; x < sizeX; x++) {
                result[y][x] = row[x] - CHAR_AND_INT_DIFFERENCE;
            }
        }
        return result;
    }


    // WINDOW GENERATION

    public static int[][] createWindowBitmap(int sizeX, int sizeY, boolean addShadow, boolean addFrame) {
        if (addShadow) { // if shadow is ON, image is 1 px taller and wider
            sizeX++;
            sizeY++;
        }

        int[][] window = new int[sizeY][sizeX];
        generateWindowBackground(window, sizeX, sizeY);
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

    private static void generateWindowBackground(int[][] window, int sizeX, int sizeY) {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                window[y][x] = 1;
            }
        }
    }
}
