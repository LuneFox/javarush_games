package com.javarush.games.snake.model.map;

import com.javarush.games.snake.model.GameObject;
import com.javarush.games.snake.model.Snake;

/** Пара «вход-выход» червоточины. */
public class WormHole {
    private final Coordinate entrance;
    private final Coordinate exit;

    /** Создаёт червоточину по координатам входа и выхода. */
    public WormHole(int entranceX, int entranceY, int exitX, int exitY) {
        this.entrance = new Coordinate(entranceX, entranceY);
        this.exit = new Coordinate(exitX, exitY);
    }

    /** Переносит голову змейки на выход. */
    public void warpToExit(Snake snake) {
        moveHeadTo(snake, exit);
    }

    /** Переносит голову змейки на вход. */
    public void warpToEntrance(Snake snake) {
        moveHeadTo(snake, entrance);
    }

    /** Перемещает новую голову в точку. */
    private void moveHeadTo(Snake snake, Coordinate destination) {
        final GameObject head = snake.getNewHead();
        head.x = destination.x;
        head.y = destination.y;
    }

    /** Находится ли вход в клетке. */
    public boolean isEntranceAt(int x, int y) {
        return entrance.x == x && entrance.y == y;
    }

    /** Находится ли выход в клетке. */
    public boolean isExitAt(int x, int y) {
        return exit.x == x && exit.y == y;
    }
}
