package com.javarush.games.minesweeper.gui;

/** Создаёт короткую анимацию дрожания. */
public class ShakeHelper {
    /** Длительность дрожания. */
    private static final int SHAKE_DURATION = 10;
    /** Оставшееся время дрожания. */
    private int shakeCountDown;
    /** Текущий сдвиг дрожания. */
    private int shakeShift;

    /** Возвращает текущий сдвиг дрожания. */
    public int getShift() {
        if (shakeCountDown == 0) return 0;
        shakeShift = (shakeShift == 0) ? 1 : 0;
        shakeCountDown--;
        return shakeShift;
    }

    /** Запускает дрожание. */
    public void startShaking() {
        shakeCountDown = SHAKE_DURATION;
    }
}
