package com.javarush.games.game2048.controller;


/**
 * Strategy interface for handling user input (mouse clicks and key
 * press/release events). All methods have empty default implementations so a
 * strategy only has to override the events it cares about.
 */
public interface ControlStrategy {
    /**
     * Called on a left mouse click.
     *
     * @param x the column of the clicked cell
     * @param y the row of the clicked cell
     */
    default void leftClick(int x, int y) {
    }

    /**
     * Called on a right mouse click.
     *
     * @param x the column of the clicked cell
     * @param y the row of the clicked cell
     */
    default void rightClick(int x, int y) {
    }

    /**
     * Called when the Up arrow key is pressed.
     */
    default void pressUp() {
    }

    /**
     * Called when the Up arrow key is released.
     */
    default void releaseUp() {
    }

    /**
     * Called when the Down arrow key is pressed.
     */
    default void pressDown() {
    }

    /**
     * Called when the Down arrow key is released.
     */
    default void releaseDown() {
    }

    /**
     * Called when the Right arrow key is pressed.
     */
    default void pressRight() {
    }

    /**
     * Called when the Right arrow key is released.
     */
    default void releaseRight() {
    }

    /**
     * Called when the Left arrow key is pressed.
     */
    default void pressLeft() {
    }

    /**
     * Called when the Left arrow key is released.
     */
    default void releaseLeft() {
    }

    /**
     * Called when the Space key is pressed.
     */
    default void pressSpace() {
    }

    /**
     * Called when the Space key is released.
     */
    default void releaseSpace() {
    }

    /**
     * Called when the Enter key is pressed.
     */
    default void pressEnter() {
    }

    /**
     * Called when the Enter key is released.
     */
    default void releaseEnter() {
    }

    /**
     * Called when any other (unhandled) key is pressed.
     */
    default void pressAnyOtherKey() {

    }
}