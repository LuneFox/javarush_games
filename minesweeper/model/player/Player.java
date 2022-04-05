package com.javarush.games.minesweeper.model.player;

import com.javarush.games.minesweeper.MinesweeperGame;

/**
 * Contains player data like score
 */

public class Player {
    private final Score score;
    private final Inventory inventory;
    private String title;
    private int moves;
    private int brokenShields;

    public Player(MinesweeperGame game) {
        this.score = new Score(this);
        this.inventory = new Inventory(game);
        this.title = "";
    }

    public void reset() {
        moves = 0;
        brokenShields = 0;
        score.reset();
        inventory.reset();
    }

    public void addMove() {
        this.moves++;
    }

    public void addBrokenShield() {
        this.brokenShields++;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int countMoves() {
        return moves;
    }

    public int countBrokenShields() {
        return brokenShields;
    }

    public Score getScore() {
        return score;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
