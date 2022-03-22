package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.view.View;

public abstract class InteractiveObject implements Drawable, Clickable {
    protected static MinesweeperGame game = MinesweeperGame.getInstance();

    public int x;
    public int y;
    public int width;
    public int height;
    public int latestClickX; // remember where it was touched last time, useful if this drawable contains other drawables
    public int latestClickY;

    public InteractiveObject() {
        this(0, 0);
    }

    public InteractiveObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public InteractiveObject(int x, int y, View view) { // Constructor with attaching to view
        this(x, y);
        linkView(view);
    }

    public abstract void draw();

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // First check if the click hit the object and fire an action if it did
    public boolean tryClick(int x, int y, Controller.Click click) {
        boolean covers = x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + height;
        if (covers) {
            latestClickX = x;
            latestClickY = y;
            if (click == Controller.Click.LEFT) {
                onLeftClick();
            } else if (click == Controller.Click.RIGHT) {
                onRightClick();
            }
        }
        return covers;
    }

    public void onLeftClick() {
        // Do nothing by default, override to assign an action
    }

    public void onRightClick() {
        // Do nothing by default, override to assign an action
    }

    public <T extends InteractiveObject> T linkView(View view) {
        view.linkObject(this);
        return (T) this;
    }
}
