package com.javarush.games.snake.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;

public abstract class View {
    protected static SnakeGame game = SnakeGame.getInstance();

    public abstract void update();

    protected void drawBlackBackground() {
        for (int x = 0; x < SnakeGame.SIZE; x++) {
            for (int y = 0; y < SnakeGame.SIZE; y++) {
                game.setCellValueEx(x, y, Color.BLACK, "");
            }
        }
    }
}
