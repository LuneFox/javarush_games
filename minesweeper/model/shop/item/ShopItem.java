package com.javarush.games.minesweeper.model.shop.item;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.image.Image;

public abstract class ShopItem {
    protected final MinesweeperGame game;
    protected Image icon;
    protected String name;
    protected String description;
    protected int cost;
    protected int inStock;
    protected int expirationMove;
    protected int effectDuration;
    protected boolean isActivated;

    public ShopItem(MinesweeperGame game) {
        this.game = game;
    }

    public abstract void activate();

    public void deactivate() {
        isActivated = false;
    }

    public void checkExpiration() {
        if ((game.getPlayer().getMoves() >= expirationMove) && isActivated) {
            PopUpMessage.show(name + ": всё");
            deactivate();
            restock();
        }
    }

    public int countRemainingMoves() {
        return expirationMove - game.getPlayer().getMoves();
    }

    public String getRemainingMovesText() {
        if (expirationMove == 0) return "";
        return Integer.toString(countRemainingMoves());
    }

    public boolean isUnaffordable() {
        return (game.getPlayer().getInventory().getMoney() < cost);
    }

    public boolean isUnobtainable() {
        return (isUnaffordable() || inStock <= 0);
    }

    public int inStock() {
        return inStock;
    }

    public void empty() {
        inStock = 0;
    }

    public void restock() {
        inStock++;
    }

    public void take() {
        inStock--;
    }

    @DeveloperOption
    public void cheat99() {
        activate();
        expirationMove = game.getPlayer().getMoves() + 99;
    }

    // Getters, setters, etc.

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Image getIcon() {
        return icon;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public int getCost() {
        return cost;
    }
}
