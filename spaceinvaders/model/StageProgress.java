package com.javarush.games.spaceinvaders.model;

/** Номер текущей стадии. */
public final class StageProgress {
    /** Номер первой стадии. */
    private static final int FIRST_STAGE = 1;

    /** Номер текущей стадии. */
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
