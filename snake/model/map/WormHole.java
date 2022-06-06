package com.javarush.games.snake.model.map;

import com.javarush.games.snake.model.GameObject;
import com.javarush.games.snake.model.Snake;

public class WormHole {
    private final Coordinate beginning;
    private final Coordinate destination;

    public WormHole(int xLoc, int yLoc, int xDest, int yDest) {
        this.beginning = new Coordinate(xLoc, yLoc);
        this.destination = new Coordinate(xDest, yDest);
    }

    public void warpToDestination(Snake snake) {
        final GameObject snakeNewHead = snake.getNewHead();
        snakeNewHead.x = destination.x;
        snakeNewHead.y = destination.y;
    }

    public void warpToBeginning(Snake snake) {
        final GameObject snakeNewHead = snake.getNewHead();
        snakeNewHead.x = beginning.x;
        snakeNewHead.y = beginning.y;
    }

    public boolean locationIsAt(int x, int y) {
        return beginning.x == x && beginning.y == y;
    }

    public boolean destinationIsAt(int x, int y) {
        return destination.x == x && destination.y == y;
    }
}
