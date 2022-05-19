package com.javarush.games.racer.view.printer;

public class ImageCreator {
    public static final int CHAR_AND_INT_DIFFERENCE = 48;

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
}
