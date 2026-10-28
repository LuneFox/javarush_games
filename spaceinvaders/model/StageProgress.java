package com.javarush.games.spaceinvaders.model;

/**
 * Number of the stage being played. The value is shared by game objects created in different
 * classes, so it is kept static, the same way {@link Score} is.
 */
public final class StageProgress {
    private static final int FIRST_STAGE = 1;

    private static int number = FIRST_STAGE;

    private StageProgress() {
    }

    public static int getNumber() {
        return number;
    }

    /**
     * Called when the previous stage was completed.
     */
    public static void advance() {
        number++;
    }

    /**
     * Called when the player lost and the game starts over.
     */
    public static void restart() {
        number = FIRST_STAGE;
    }
}
