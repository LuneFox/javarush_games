package com.javarush.games.spaceinvaders.model;

/**
 * Geometry of the playfield. Model classes depend on the field size, not on the game engine class.
 */
public final class GameField {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    public static final int FLOOR_HEIGHT = 4;
    public static final int CENTER_X = WIDTH / 2;

    private GameField() {
    }

    /**
     * Vertical position of an object of the given height standing on the ground.
     */
    public static int groundLevelFor(int objectHeight) {
        return HEIGHT - FLOOR_HEIGHT - objectHeight;
    }
}
