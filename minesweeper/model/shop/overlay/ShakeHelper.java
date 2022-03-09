package com.javarush.games.minesweeper.model.shop.overlay;

/**
 * Use getShift to quickly shake the X coordinate of anything you want.
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
