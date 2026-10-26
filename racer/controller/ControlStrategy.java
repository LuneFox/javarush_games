package com.javarush.games.racer.controller;


/**
 * Strategy interface for turning key events into game actions. Every method is a {@code default} no-op, so an
 * implementation overrides only the controls it cares about; {@link RaceStrategy} overrides all of them.
 */
public interface ControlStrategy {
    /**
     * Called when the UP key is pressed; does nothing by default.
     */
    default void pressUp() {
    }

    /**
     * Called when the UP key is released; does nothing by default.
     */
    default void releaseUp() {
    }

    /**
     * Called when the DOWN key is pressed; does nothing by default.
     */
    default void pressDown() {
    }

    /**
     * Called when the DOWN key is released; does nothing by default.
     */
    default void releaseDown() {
    }

    /**
     * Called when the RIGHT key is pressed; does nothing by default.
     */
    default void pressRight() {
    }

    /**
     * Called when the RIGHT key is released; does nothing by default.
     */
    default void releaseRight() {
    }

    /**
     * Called when the LEFT key is pressed; does nothing by default.
     */
    default void pressLeft() {
    }

    /**
     * Called when the LEFT key is released; does nothing by default.
     */
    default void releaseLeft() {
    }

    /**
     * Called when SPACE is pressed; does nothing by default. The race strategy uses it to restart.
     */
    default void pressSpace() {
    }

    /**
     * Called when SPACE is released; does nothing by default, because the game reacts only to the press.
     */
    default void releaseSpace() {
    }
}