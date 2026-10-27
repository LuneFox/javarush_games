package com.javarush.games.snake.model;

import com.javarush.games.snake.SnakeGame;

public class GameObject {
    protected SnakeGame game = SnakeGame.getInstance();
    public int x;
    public int y;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
