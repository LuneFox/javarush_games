package com.javarush.games.snake.model;

import com.javarush.games.snake.SnakeGame;

import java.util.Objects;

public class GameObject {
    protected SnakeGame game = SnakeGame.getInstance();
    public int x;
    public int y;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameObject that = (GameObject) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
