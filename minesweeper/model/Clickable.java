package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.controller.Click;

public interface Clickable {
    void onLeftClick();

    void onRightClick();

    boolean tryClick(int x, int y, Click click);

    // Unspecified click = left click
    default boolean tryClick(int x, int y){
        return tryClick(x, y, Click.LEFT);
    }
}
