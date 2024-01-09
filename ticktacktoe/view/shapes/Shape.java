package com.javarush.games.ticktacktoe.view.shapes;

import java.util.Arrays;

public class Shape {
    public static final int[][] TABLE = drawTable();

    private static int[][] drawTable() {
        int[][] result = new int[100][100];
        int[] row = new int[100];
        Arrays.fill(row, 45);
        Arrays.fill(result, row);
        return result;
    }

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

    public static final int[][] BLACK_DISK_FLIP_1 = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 1, 1, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 2, 1, 1, 1, 1, 1, 2, 0},
            {0, 0, 2, 2, 2, 2, 2, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}};

    public static final int[][] BLACK_DISK_FLIP_2 = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 1, 1, 1, 0, 0},
            {0, 2, 1, 1, 1, 1, 1, 2, 0},
            {0, 0, 2, 2, 2, 2, 2, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}};

    public static final int[][] BLACK_DISK_FLIP_3 = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 2, 2, 2, 2, 2, 0, 0},
            {0, 1, 2, 2, 2, 2, 2, 1, 0},
            {0, 0, 1, 1, 1, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}};

    public static final int[][] BLACK_DISK_FLIP_4 = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 2, 2, 2, 2, 2, 0, 0},
            {0, 2, 2, 2, 2, 2, 2, 2, 0},
            {0, 1, 2, 2, 2, 2, 2, 1, 0},
            {0, 1, 2, 2, 2, 2, 2, 1, 0},
            {0, 0, 1, 1, 1, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}};

    public static final int[][] BLACK_DISK_FLIP_5 = swapBlackAndWhite(BLACK_DISK);

    public static final int[][] WHITE_DISK = swapBlackAndWhite(BLACK_DISK);
    public static final int[][] WHITE_DISK_FLIP_1 = swapBlackAndWhite(BLACK_DISK_FLIP_1);
    public static final int[][] WHITE_DISK_FLIP_2 = swapBlackAndWhite(BLACK_DISK_FLIP_2);
    public static final int[][] WHITE_DISK_FLIP_3 = swapBlackAndWhite(BLACK_DISK_FLIP_3);
    public static final int[][] WHITE_DISK_FLIP_4 = swapBlackAndWhite(BLACK_DISK_FLIP_4);
    public static final int[][] WHITE_DISK_FLIP_5 = BLACK_DISK;


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
