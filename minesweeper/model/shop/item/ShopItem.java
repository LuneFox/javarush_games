package com.javarush.games.minesweeper.model.shop.item;

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
    protected int expireMove;
    protected int effectDuration;
    protected boolean isActivated;

    public ShopItem(MinesweeperGame game) {
        this.game = game;
    }

    public abstract void activate();

    public void deactivate() {
        this.isActivated = false;
    }

    public void checkExpiration() {
        if (game.getPlayer().getMoves() >= this.expireMove && this.isActivated) {
            PopUpMessage.show(this.name + ": всё");
            this.deactivate();
            this.inStock = 1;
        }
    }

    public int getRemainingMoves() {
        return expireMove - game.getPlayer().getMoves();
    }

    public String getRemainingMovesText() {
        if (expireMove == 0) return "";
        return Integer.toString(getRemainingMoves());
    }

    public boolean isUnaffordable() {
        return (game.getPlayer().getInventory().getMoney() < this.cost);
    }

    public boolean isUnobtainable() {
        return (isUnaffordable() || this.inStock <= 0);
    }


    // Getters, setters, etc.

    public void setExpireMove(int expireMove) {
        this.expireMove = expireMove;
    }

    public Image getIcon() {
        return icon;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
