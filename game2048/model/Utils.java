package com.javarush.games.game2048.model;

import com.javarush.engine.cell.Color;

public class Utils {
    public static final char[] BALL_SYMBOLS = " ❶❷❸❹❺❻❼❽➈➉⑪⑫⑬⑭⑮⬤".toCharArray();

    public static Color getColorForBallNumber(int ballNumber) {
        switch (ballNumber) {
            case 1:
            case 9:
                return Color.YELLOW;
            case 2:
            case 10:
                return Color.MEDIUMBLUE;
            case 3:
            case 11:
                return Color.RED;
            case 4:
            case 12:
                return Color.PURPLE;
            case 5:
            case 13:
                return Color.ORANGE;
            case 6:
            case 14:
                return Color.LAWNGREEN;
            case 7:
            case 15:
                return Color.SADDLEBROWN;
            case 8:
                return Color.BLACK;
            default:
                return Color.WHITE;
        }
    }

    public static int[][] copyField(int[][] field) {
        int height = field.length;
        int width = field[0].length;
        int[][] result = new int[height][width];
        for (int y = 0; y < height; y++) {
            System.arraycopy(field[y], 0, result[y], 0, width);
        }
        return result;
    }

    public static boolean fieldsAreEqual(int[][] field, int[][] anotherField) {
        int height = field.length;
        int width = field[0].length;

        if (field.length != anotherField.length) return false;
        if (field[0].length != anotherField[0].length) return false;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (field[y][x] != anotherField[y][x]) return false;
            }
        }

        return true;
    }
}
