package com.javarush.games.minesweeper.gui;

/**
 * A simple tool that returns 1 or 0 every time getShift() is called until duration becomes 0.
 * Helps to animate shaking elements by adding the result of getShift() to their coordinates with each view update.
 */

public class ShakeHelper {
    private static final int SHAKE_DURATION = 10;
    private int shakeCountDown;
    private int shakeShift;

    public int getShift() {
        if (shakeCountDown == 0) return 0;
        shakeShift = (shakeShift == 0) ? 1 : 0;
        shakeCountDown--;
        return shakeShift;
    }

    public void startShaking() {
        shakeCountDown = SHAKE_DURATION;
    }
}
