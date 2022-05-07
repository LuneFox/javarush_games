package com.javarush.games.minesweeper.model.player;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.shop.items.ShopItem;

public class Player {
    private final MinesweeperGame game;
    private final Score score;
    private final Inventory inventory;
    private String title;
    private int moves;
    private int brokenShields;

    public Player(MinesweeperGame game) {
        this.game = game;
        this.score = new Score(this);
        this.inventory = new Inventory();
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

    public void increaseBrokenShieldsCounter() {
        this.brokenShields++;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void gainItem(ShopItem item) {
        inventory.putItem(item);
    }

    public void loseItem(ShopItem item) {
        inventory.removeItem(item);
    }

    public void gainMoney(int amount) {
        inventory.putMoney(amount);
    }

    public void loseMoney(int amount) {
        inventory.removeMoney(amount);
    }

    public int getMoneyBalance() {
        return inventory.getMoney();
    }

    public int countFlags() {
        return inventory.count(game.getShop().getFlag());
    }

    @DeveloperOption
    public void cheatMoney() {
        inventory.cheatMoney();
    }

    // Getters

    public String getTitle() {
        return title;
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
