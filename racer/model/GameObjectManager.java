package com.javarush.games.racer.model;

/**
 * Per-frame lifecycle for a group of game objects that can move and be drawn. Implementations
 * wrap collections of objects, such as the road and its markings, and are driven by the main game
 * loop in that order: move everything first, then draw everything.
 */
public interface GameObjectManager {

    /**
     * Advances every managed object by one frame.
     *
     * @param boost extra leftward speed in cells per frame, added to each object's own speed
     */
    void moveObjects(double boost);

    /**
     * Draws every managed object into the off-screen display buffer. The buffer is flushed to the
     * screen separately, once per frame.
     */
    void drawObjects();
}
