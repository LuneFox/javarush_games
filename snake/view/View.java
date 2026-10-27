package com.javarush.games.snake.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;

/** Базовый экран с обновлением и заливкой фона. */
public abstract class View {
    protected static SnakeGame game = SnakeGame.getInstance();

    /** Обновляет содержимое экрана. */
    public abstract void update();

    /** Заливает всё поле чёрным. */
    protected void drawBlackBackground() {
        for (int x = 0; x < SnakeGame.SIZE; x++) {
            for (int y = 0; y < SnakeGame.SIZE; y++) {
                game.setCellValueEx(x, y, Color.BLACK, "");
            }
        }
    }
}
