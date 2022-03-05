package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.view.graphics.Drawable;

public abstract class DrawableObject implements Drawable {
    protected static MinesweeperGame game = MinesweeperGame.getInstance();
    public int x;
    public int y;
    public int width;
    public int height;
    public int lastClickX; // remember where it was touched last time, useful if this drawable contains other drawables
    public int lastClickY;

    public DrawableObject() {
        this.x = 0;
        this.y = 0;
    }

    public DrawableObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private boolean checkCover(int hitX, int hitY) {
        return (hitX >= x && hitX <= x + width && hitY >= y && hitY <= y + height);
    }

    // Check if click coordinates are on top of this object and, if so, fire and action
    public boolean checkLeftTouch(int hitX, int hitY) {
        boolean covers = checkCover(hitX, hitY);
        if (covers) {
            lastClickX = hitX;
            lastClickY = hitY;
            onLeftTouch();
        }
        return covers;
    }

    public boolean checkRightTouch(int hitX, int hitY) {
        boolean covers = checkCover(hitX, hitY);
        if (covers) {
            lastClickX = hitX;
            lastClickY = hitY;
            onRightTouch();
        }
        return covers;
    }

    protected void onLeftTouch() {
        // Do nothing by default, override to assign some action
    }

    protected void onRightTouch() {
        // Do nothing by default, override to assign some action
    }
}
