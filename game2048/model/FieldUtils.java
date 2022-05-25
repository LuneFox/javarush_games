package com.javarush.games.game2048.model;

public class FieldUtils {
    private static int height;
    private static int width;

    private static void getDimensions(int[][] field) {
        FieldUtils.height = field.length;
        FieldUtils.width = field[0].length;
    }

    public static int[][] copyField(int[][] field) {
        getDimensions(field);

        int[][] result = new int[height][width];
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                result[y][x] = field[y][x];
            }
        }
        return result;
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

    public static boolean fieldHasEmptySpace(int[][] field) {
        getDimensions(field);

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                if (field[y][x] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getWhiteBallRow(int[][] field) {
        getDimensions(field);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (field[y][x] == 16) {
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

                if (isEmptyOrHasWhiteBall(cell)) {
                    return true;
                } else {
                    if (hasMaximumPossibleBall(cell)) continue;

                    if (isLastLine(height, y)) continue;
                    if (isLastLine(width, x)) continue;

                    final int belowCell = field[y + 1][x];
                    final int rightCell = field[y][x + 1];

                    if (canMerge(cell, belowCell)) return true;
                    if (canMerge(cell, rightCell)) return true;
                }
            }
        }
        return false;
    }

    private static boolean isLastLine(int dimension, int line) {
        return line == (dimension - 2);
    }

    private static boolean isEmptyOrHasWhiteBall(int cellValue) {
        return cellValue == 0 || cellValue == 16;
    }

    private static boolean hasMaximumPossibleBall(int cellValue) {
        return cellValue == 15;
    }

    public static boolean canMerge(int cell1, int cell2) {
        return cell1 == cell2;
    }
}
