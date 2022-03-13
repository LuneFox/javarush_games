package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.controller.Controller;

public interface Clickable {
    void onLeftClick();

    void onRightClick();

    boolean tryClick(int x, int y, Controller.Click click);

    default boolean tryClick(int x, int y){
        return tryClick(x, y, Controller.Click.LEFT);
    }
}
