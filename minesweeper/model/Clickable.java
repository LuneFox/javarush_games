package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.controller.Click;

public interface Clickable {
    void onLeftClick();

    void onRightClick();

    void click(int x, int y, Click click);

    default void click(int x, int y) {
        click(x, y, Click.LEFT);
    }
}
