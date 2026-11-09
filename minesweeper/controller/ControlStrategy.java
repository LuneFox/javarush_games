package com.javarush.games.minesweeper.controller;

import com.javarush.games.minesweeper.model.Phase;

/** Стратегия обработки ввода для одной фазы игры. */
public interface ControlStrategy {

    /** Обрабатывает левый клик. */
    default void leftClick(int x, int y) {
        Phase.leftClickOnCurrentView(x, y);
    }

    /** Обрабатывает правый клик. */
    default void rightClick(int x, int y) {
        Phase.rightClickOnCurrentView(x, y);
    }

    /** Обрабатывает нажатие вверх. */
    default void pressUp() {
    }

    /** Обрабатывает нажатие вниз. */
    default void pressDown() {
    }

    /** Обрабатывает нажатие вправо. */
    default void pressRight() {
    }

    /** Обрабатывает нажатие влево. */
    default void pressLeft() {
    }

    /** Обрабатывает нажатие Enter. */
    default void pressEnter() {
    }

    /** Обрабатывает нажатие паузы. */
    default void pressPause() {
    }

    /** Обрабатывает нажатие пробела. */
    default void pressSpace() {
    }

    /** Обрабатывает нажатие Escape. */
    default void pressEsc() {
    }

    /** Обрабатывает прочие клавиши. */
    default void pressOther() {
    }
}