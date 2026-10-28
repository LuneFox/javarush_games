package com.javarush.games.spaceinvaders.controller;

/**
 * Reaction to the game controls. Every method has a do-nothing default, so a strategy implements
 * only the keys it cares about.
 */
public interface ControlStrategy {
    default void upPressed() {
    }

    default void upReleased() {
    }

    default void downPressed() {
    }

    default void downReleased() {
    }

    default void leftPressed() {
    }

    default void leftReleased() {
    }

    default void rightPressed() {
    }

    default void rightReleased() {
    }

    default void spacePressed() {
    }

    default void spaceReleased() {
    }

    default void pausePressed() {
    }

    default void pauseReleased() {
    }
}
