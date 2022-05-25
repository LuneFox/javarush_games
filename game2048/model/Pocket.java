package com.javarush.games.game2048.model;

public class Pocket {
    public final static String ICON = "⬤";
    public int x;
    public int y;
    public int score;
    private boolean isOpen;

    public Pocket(int x, int y) {
        this.x = x;
        this.y = y;
        isOpen = true;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        this.isOpen = true;
    }

    public void close() {
        this.isOpen = false;
    }
}
