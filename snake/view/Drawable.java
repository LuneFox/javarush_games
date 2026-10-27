package com.javarush.games.snake.view;

import com.javarush.engine.cell.Game;

/** Объект, который умеет рисоваться в клетке поля. */
public interface Drawable {
    /** Рисует объект в клетке поля. */
    void draw(Game target);
}
