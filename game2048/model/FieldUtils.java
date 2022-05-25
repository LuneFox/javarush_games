package com.javarush.games.game2048.model;

public class FieldUtils {
    public static int[][] copyField(int[][] field) {
        int height = field.length;
        int width = field[0].length;
        int[][] result = new int[height][width];
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                result[y][x] = field[y][x];
            }
        }
        return result;
    }

    public static boolean fieldsAreEqual(int[][] field, int[][] anotherField) {
        int height = field.length;
        int width = field[0].length;

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
        int height = field.length;
        int width = field[0].length;

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                if (field[y][x] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean canUserMove(int[][] field) {
        int height = field.length;
        int width = field[0].length;

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                // пробегаемся по всем шарам построчно
                if (field[y][x] == 0 || field[y][x] == 16) {
                    // если ячейка пуста или на ней стоит белый шар (который можно убрать)
                    return true;
                } else {
                    if (field[y][x] == 15) {
                        // если шар 15-й, пропустить итерацию (его нельзя сливать ни с чем)
                        continue;
                    }
                    if (field[y + 1][x] == field[y][x]) {
                        // если шар ниже такой же, как и этот
                        if (y == height - 2) {
                            // если шаров ниже нет, пропустить итерацию
                            continue;
                        }
                        return true;
                    }
                    if (field[y][x + 1] == field[y][x]) {
                        // если шар правее такой же, как и этот
                        if (x == width - 2) {
                            // если шаров правее нет, пропустить итерацию
                            continue;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
