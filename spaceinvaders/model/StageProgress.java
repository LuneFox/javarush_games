package com.javarush.games.spaceinvaders.model;

/** Номер текущей стадии. */
public final class StageProgress {
    /** Стартовая стадия; для тестирования можно поставить нужный номер. */
    private static final int START_STAGE = 1;

    /** Номер текущей стадии. */
    private static int number = START_STAGE;
    /** Самый большой достигнутый номер стадии за сессию. */
    private static int maxNumber = START_STAGE;

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
        maxNumber = Math.max(maxNumber, number);
    }

    /** Возвращает самый большой достигнутый номер стадии. */
    public static int getMaxNumber() {
        return maxNumber;
    }

    /** Возвращает стартовую стадию. */
    public static void restart() {
        number = START_STAGE;
    }
}
