package com.javarush.games.game2048.model;

public class Pocket {
    public final static String POCKET_ICON = "⬤";
    public int x;
    public int y;
    public int score;
    public boolean open;

    public Pocket(int x, int y) {
        this.x = x;
        this.y = y;
        open = true;
    }
}
