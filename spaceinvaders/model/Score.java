package com.javarush.games.spaceinvaders.model;

/** Счёт текущей игры. */
public final class Score {
    private static final int BONUS_PICKUP_POINTS = 9;

    private static int score;
    private static int topScore;

    static {
        reset();
    }

    /** Запрещает создание экземпляров. */
    private Score() {
    }

    /** Сбрасывает счёт. */
    public static void reset() {
        score = 0;
    }

    /** Начисляет очки с учётом номера стадии. */
    public static void add(int baseAmount) {
        score += baseAmount * StageProgress.getNumber();
        topScore = Math.max(score, topScore);
    }

    /** Начисляет очки за подобранный бонус. */
    public static void addBonusPickup() {
        add(BONUS_PICKUP_POINTS + StageProgress.getNumber());
    }

    /** Списывает очки за потерянный бонус. */
    public static void removeBonusPickup() {
        add(-BONUS_PICKUP_POINTS - StageProgress.getNumber());
    }

    /** Возвращает текущий счёт. */
    public static int getScore() {
        return score;
    }

    /** Возвращает лучший счёт. */
    public static int getTopScore() {
        return topScore;
    }
}
