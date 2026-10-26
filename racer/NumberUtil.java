package com.javarush.games.racer;

/**
 * Small numeric helper used when printing values such as energy or fuel to the HUD.
 */
public class NumberUtil {
    /**
     * Rounds a value to two decimal places by scaling it up by 100, rounding to the nearest {@code long} and
     * scaling back down. {@link Math#round(double)} rounds exact halves toward positive infinity, so negative
     * halves are rounded toward zero.
     *
     * @param value the value to round
     * @return the value rounded to two decimal places
     */
    public static double roundDouble(double value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
