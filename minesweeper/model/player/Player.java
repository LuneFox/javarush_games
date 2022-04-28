package com.javarush.games.minesweeper.model.player;

import com.javarush.games.minesweeper.MinesweeperGame;

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

    public void addMove() {
        this.moves++;
    }

    public void addBrokenShield() {
        this.brokenShields++;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    // Getters

    public String getTitle() {
        return title;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Score getScore() {
        return score;
    }

    public int getMoves() {
        return moves;
    }

    public int getBrokenShields() {
        return brokenShields;
    }
}
