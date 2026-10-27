package com.javarush.games.snake.view;

import com.javarush.engine.cell.Game;

/**
 * Something that can render itself into a single cell of the game field.
 */
public interface Drawable {
    void draw(Game target);
}
