package com.javarush.games.ticktacktoe.view.shapes;

public class Shape {
    public static final int[][] FIELD_CELL_SHAPE = new int[][]{
            {131, 126, 131, 126, 131, 126, 131, 126, 131, 126, 131},
            {126, 140, 128, 140, 128, 140, 128, 140, 128, 140, 126},
            {131, 128, 140, 128, 140, 128, 140, 128, 140, 128, 131},
            {126, 140, 128, 140, 128, 140, 128, 140, 128, 140, 126},
            {131, 128, 140, 128, 140, 128, 140, 128, 140, 128, 131},
            {126, 140, 128, 140, 128, 140, 128, 140, 128, 140, 126},
            {131, 128, 140, 128, 140, 128, 140, 128, 140, 128, 131},
            {126, 140, 128, 140, 128, 140, 128, 140, 128, 140, 126},
            {131, 128, 140, 128, 140, 128, 140, 128, 140, 128, 131},
            {126, 140, 128, 140, 128, 140, 128, 140, 128, 140, 126},
            {131, 126, 131, 126, 131, 126, 131, 126, 131, 126, 131}
    };

    public static final int[][] BLACK_DISK = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 1, 1, 1, 1, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 2, 1, 1, 1, 1, 1, 2, 0},
            {0, 0, 2, 1, 1, 1, 2, 0, 0},
            {0, 0, 0, 2, 2, 2, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}};

    public static final int[][] WHITE_DISK = swapBlackAndWhite(BLACK_DISK);

    private static int[][] swapBlackAndWhite(int[][] original) {
        int[][] result = new int[original.length][original[0].length];
        for (int y = 0; y < original.length; y++) {
            for (int x = 0; x < original.length; x++) {
                if (original[y][x] == 1) {
                    result[y][x] = 2;
                } else if (original[y][x] == 2) {
                    result[y][x] = 1;
                } else
                    result[y][x] = original[y][x];
            }
        }
        return result;
    }
}
