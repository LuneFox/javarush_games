package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.controller.Click;

/** Элемент, реагирующий на клики. */
public interface Clickable {
    /** Обрабатывает левый клик. */
    void onLeftClick();

    /** Обрабатывает правый клик. */
    void onRightClick();

    /** Обрабатывает клик. */
    void click(int x, int y, Click click);

    /** Обрабатывает клик. */
    default void click(int x, int y) {
        click(x, y, Click.LEFT);
    }
}
