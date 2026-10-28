package com.javarush.games.spaceinvaders.model;

/** Номер текущей стадии. */
public final class StageProgress {
    private static final int FIRST_STAGE = 1;

    private static int number = FIRST_STAGE;

    /** Запрещает создание экземпляров. */
    private StageProgress() {
    }

    /** Возвращает номер стадии. */
    public static int getNumber() {
        return number;
    }

    /** Переводит на следующую стадию. */
    public static void advance() {
        number++;
    }

    /** Возвращает первую стадию. */
    public static void restart() {
        number = FIRST_STAGE;
    }
}
