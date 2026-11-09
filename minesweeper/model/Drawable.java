package com.javarush.games.minesweeper.model;

/** Элемент, который можно нарисовать. */
public interface Drawable {
    /** Рисует объект на экране. */
    void draw();

    /** Задаёт позицию. */
    void setPosition(int x, int y);
}
