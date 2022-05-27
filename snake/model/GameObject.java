package com.javarush.games.snake.model;

import com.javarush.games.snake.SnakeGame;

import java.util.Objects;

public class GameObject {
    public int x;
    public int y;
    protected SnakeGame game;

    // CONSTRUCTOR

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public GameObject(int x, int y, SnakeGame game) {
        this.x = x;
        this.y = y;
        this.game = game;
    }


    // COMPARE

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        GameObject that = (GameObject) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
