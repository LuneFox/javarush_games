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

    protected void lease() {
        if (game.isStopped() || isActivated) return;
        isActivated = true;
        expirationMove = game.getPlayer().getMoves() + effectDuration;
    }

    public void deactivate() {
        isActivated = false;
    }

    public void deactivateIfExpired() {
        if (isExpired()) {
            deactivate();
            restock();
            PopUpMessage.show(name + ": всё");
        }
    }

    public void drawIcon(int x, int y) {
        icon.draw(x, y);
    }

    private boolean isExpired() {
        return (game.getPlayer().getMoves() >= expirationMove) && isActivated;
    }

    public int countRemainingMoves() {
        return expirationMove - game.getPlayer().getMoves();
    }

    public String getRemainingMovesText() {
        return expirationMove == 0 ? " " : "" + countRemainingMoves();
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
