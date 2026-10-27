package com.javarush.games.snake.model.map;

/** Неизменяемая точка на карте. */
public class Coordinate {
    public final int x;
    public final int y;

    /** Создаёт точку с координатами. */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
