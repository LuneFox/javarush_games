package com.javarush.games.racer;

public class NumberUtil {
    public static double roundDouble(double value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
