package com.javarush.games.snake.model.map;

import com.javarush.games.snake.model.GameObject;
import com.javarush.games.snake.model.Snake;

public class WormHole {
    private final Coordinate entrance;
    private final Coordinate exit;

    public WormHole(int entranceX, int entranceY, int exitX, int exitY) {
        this.entrance = new Coordinate(entranceX, entranceY);
        this.exit = new Coordinate(exitX, exitY);
    }

    public void warpToExit(Snake snake) {
        moveHeadTo(snake, exit);
    }

    public void warpToEntrance(Snake snake) {
        moveHeadTo(snake, entrance);
    }

    private void moveHeadTo(Snake snake, Coordinate destination) {
        final GameObject head = snake.getNewHead();
        head.x = destination.x;
        head.y = destination.y;
    }

    public boolean isEntranceAt(int x, int y) {
        return entrance.x == x && entrance.y == y;
    }

    public boolean isExitAt(int x, int y) {
        return exit.x == x && exit.y == y;
    }
}
