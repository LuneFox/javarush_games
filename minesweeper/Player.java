package com.javarush.games.minesweeper;

/**
 * Contains player data like score
 */

public class Player {
    public Score score;
    private String title;
    private int moves;
    private int brokenShields;

    public Player() {
        this.score = new Score(this);
        this.title = "";
        reset();
    }

    public void reset() {
        moves = 0;
        brokenShields = 0;
        score.reset();
    }

    public void incMoves() {
        this.moves++;
    }

    public void incBrokenShields() {
        this.brokenShields++;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMoves() {
        return moves;
    }

    public int getBrokenShields() {
        return brokenShields;
    }
}
