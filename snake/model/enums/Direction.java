package com.javarush.games.snake.model.enums;

/** Направление движения с приращениями координат. */
public enum Direction {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);

    private final int dx;
    private final int dy;

    /** Запоминает приращения по осям. */
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /** Приращение по X. */
    public int getDx() {
        return dx;
    }

    /** Приращение по Y. */
    public int getDy() {
        return dy;
    }
}
