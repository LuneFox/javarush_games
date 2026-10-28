package com.javarush.games.spaceinvaders.model;

/**
 * Score of the current game. The value is shared by game objects created in different classes,
 * so it is kept static.
 */
public final class Score {
    private static final int BONUS_PICKUP_POINTS = 9;

    private static int score;
    private static int topScore;

    static {
        reset();
    }

    private Score() {
    }

    public static void reset() {
        score = 0;
    }

    /**
     * Adds points multiplied by the number of the current stage, as the game has always scored.
     */
    public static void add(int baseAmount) {
        score += baseAmount * StageProgress.getNumber();
        topScore = Math.max(score, topScore);
    }

    public static void addBonusPickup() {
        add(BONUS_PICKUP_POINTS + StageProgress.getNumber());
    }

    /**
     * A bonus that absorbed a hit is lost together with the points it brought.
     */
    public static void removeBonusPickup() {
        add(-BONUS_PICKUP_POINTS - StageProgress.getNumber());
    }

    public static int getScore() {
        return score;
    }

    public static int getTopScore() {
        return topScore;
    }
}
