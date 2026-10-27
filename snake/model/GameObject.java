package com.javarush.games.snake.model;

import com.javarush.games.snake.SnakeGame;

/** Объект поля с координатами клетки. */
public class GameObject {
    protected SnakeGame game = SnakeGame.getInstance();
    public int x;
    public int y;

    /** Создаёт объект в заданной клетке. */
    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
