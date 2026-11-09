package com.javarush.games.minesweeper;

/** Вспомогательные математические и координатные функции. */
public class Util {

    /** Округляет число до заданного количества знаков. */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    /** Проверяет, входит ли число в диапазон. */
    public static boolean inside(int number, int from, int to) {
        return (number >= from && number <= to);
    }

    /** Сообщает, находится ли точка в пределах экрана. */
    public static boolean isWithinScreen(int x, int y) {
        return x >= 0 && y >= 0 && x < 100 && y < 100;
    }
}
