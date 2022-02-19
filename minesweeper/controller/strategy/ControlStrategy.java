package com.javarush.games.minesweeper.controller.strategy;

public interface ControlStrategy {
    void leftClick(int x, int y);

    void rightClick(int x, int y);

    void pressUp();

    void pressDown();

    void pressRight();

    void pressLeft();

    void pressSpace();

    void pressEsc();

    void pressOther();
}
