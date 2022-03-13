package com.javarush.games.minesweeper.controller;

import com.javarush.games.minesweeper.model.Phase;

/**
 * Strategy pattern for different controls on different screens.
 */

public interface ControlStrategy {
    default void leftClick(int x, int y) {
        Phase.getCurrentView().click(x, y, Controller.Click.LEFT);
    }

    default void rightClick(int x, int y) {
        Phase.getCurrentView().click(x, y, Controller.Click.RIGHT);
    }

    default void pressUp() {
    }

    default void pressDown() {
    }

    default void pressRight() {
    }

    default void pressLeft() {
    }

    default void pressEnter() {
    }

    default void pressPause() {
    }

    default void pressSpace() {
    }

    default void pressEsc() {
    }

    default void pressOther() {
    }
}