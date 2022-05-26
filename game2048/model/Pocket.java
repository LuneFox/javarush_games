package com.javarush.games.game2048.model;

public class Pocket {
    public final static String ICON = "⬤";
    public int x;
    public int y;
    private int score;
    private boolean isOpen;

    public Pocket(int x, int y) {
        this.x = x;
        this.y = y;
        isOpen = true;
    }

    public void addScore(int amount) {
        score += amount;

        if (score > 0) {
            isOpen = false;
        }
    }

    public void removeScore(int amount) {
        score -= amount;
        if (score == 0) {
            isOpen = true;
        }
    }

    public int getScore() {
        return score;
    }

    public boolean isOpen() {
        return isOpen;
    }

}
