package com.javarush.games.minesweeper.model.player;

import com.javarush.games.minesweeper.MinesweeperGame;

/**
 * Contains player data: inventory, score and some counters
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

    public void pay(int amount) {
        inventory.removeMoney(amount);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void addMove() {
        this.moves++;
    }

    public int getMoves() {
        return moves;
    }

    public void addBrokenShield() {
        this.brokenShields++;
    }

    public int getBrokenShields() {
        return brokenShields;
    }

    public Score getScore() {
        return score;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
