package com.javarush.games.snake.model;

public class WormHole {
    public Coordinate location;
    public Coordinate destination;

    WormHole(int xLoc, int yLoc, int xDest, int yDest) {
        this.location = new Coordinate(xLoc, yLoc);
        this.destination = new Coordinate(xDest, yDest);
    }
}
