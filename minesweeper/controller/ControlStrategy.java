package com.javarush.games.minesweeper.controller;

import com.javarush.games.minesweeper.model.Phase;

public interface ControlStrategy {
    default void leftClick(int x, int y) {
        Phase.leftClickOnCurrentView(x, y);
    }

    default void rightClick(int x, int y) {
        Phase.rightClickOnCurrentView(x, y);
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