package com.javarush.games.minesweeper;

/**
 * Util class for various operations.
 */

public class Util {

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static boolean inside(int number, int from, int to) {
        return (number >= from && number <= to);
    }

    public static boolean isWithinScreen(int x, int y) {
        return x >= 0 && y >= 0 && x < 100 && y < 100;
    }
}
