package com.javarush.games.ticktacktoe.model.gameobjects;

class BestMove {
    private final int x;
    private final int y;

    public BestMove(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }
}
