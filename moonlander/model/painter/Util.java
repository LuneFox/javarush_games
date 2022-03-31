package com.javarush.games.moonlander.model.painter;

public class Util {
    private Util() {

    }

    public static String exportArrayToCode(int[][] array, int rightBound, int bottomBound, boolean includeLineBreaks) {
        StringBuilder result = new StringBuilder();
        result.append("new int[][]{");
        for (int y = 0; y < bottomBound; y++) {
            for (int x = 0; x < rightBound; x++) {
                if (x == 0) {
                    if (includeLineBreaks) result.append("\n");
                    result.append("{").append(array[y][x]).append(",");
                } else if (x < rightBound - 1) {
                    result.append(array[y][x]).append(",");
                } else if (x == rightBound - 1) {
                    result.append(array[y][x]).append("}");
                }
                if (x == rightBound - 1 && y != bottomBound - 1) {
                    result.append(",");
                }
            }
        }
        if (includeLineBreaks) result.append("\n");
        result.append("}");
        return result.toString();
    }
}
