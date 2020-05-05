package com.javarush.games.snake;

import java.util.Objects;

public class GameObject {
    public int x, y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        GameObject that = (GameObject) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
