package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.view.graphics.Drawable;

public abstract class DrawableObject implements Drawable {
    public int x;
    public int y;
    public int width;
    public int height;

    private boolean isHidden = false;

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

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
}
