package com.javarush.games.spaceinvaders.model;

/**
 * Lifecycle of the game session. A single value replaces the three booleans
 * (paused / stopped / ending displayed) that used to allow impossible combinations.
 */
public enum GameStatus {
    /**
     * The battle is in progress.
     */
    RUNNING,

    /**
     * The battle is frozen by the player.
     */
    PAUSED,

    /**
     * The stage is finished, Mario plays his death or victory animation and the ending is coming.
     */
    OVER
}
