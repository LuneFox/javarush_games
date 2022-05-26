package com.javarush.games.game2048.model;

import static com.javarush.games.game2048.Game2048.*;

public class FieldUtils {
    private static int height;
    private static int width;

    private static void getDimensions(int[][] field) {
        FieldUtils.height = field.length;
        FieldUtils.width = field[0].length;
    }

    public static int[][] copyField(int[][] field) {
        getDimensions(field);

        int[][] copy = new int[height][width];
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                copy[y][x] = field[y][x];
            }
        }
        return copy;
    }

    public static boolean fieldsAreEqual(int[][] field, int[][] anotherField) {
        getDimensions(field);

        if (field.length != anotherField.length) return false;
        if (field[0].length != anotherField[0].length) return false;

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                if (field[y][x] != anotherField[y][x]) return false;
            }
        }

        return true;
    }

    public static boolean hasNoEmptySpace(int[][] field) {
        getDimensions(field);

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                if (field[y][x] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int getWhiteBallRowNumber(int[][] field) {
        getDimensions(field);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (field[y][x] == WHITE_BALL) {
                    return y;
                }
            }
        }
        return 0;
    }

    public static boolean isMovePossible(int[][] field) {
        getDimensions(field);

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {

                final int cell = field[y][x];
                final int belowCell = field[y + 1][x];
                final int rightCell = field[y][x + 1];

                if (cell == EMPTY || cell == WHITE_BALL) {
                    return true;
                } else {
                    if (cell == MAX_BALL) continue;

                    if (isNotLastLine(height, y)) {
                        if (isMergePossible(cell, belowCell)) return true;
                        if (isNotLastLine(width, x)) {
                            if (isMergePossible(cell, rightCell)) return true;
                        }
                    }

                    if (isNotLastLine(width, x)) {
                        if (isMergePossible(cell, rightCell)) return true;
                        if (isNotLastLine(height, y)) {
                            if (isMergePossible(cell, belowCell)) return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean isNotLastLine(int dimension, int line) {
        return line != (dimension - 2);
    }

    public static boolean isMergePossible(int cell1, int cell2) {
        return cell1 == cell2;
    }
}
