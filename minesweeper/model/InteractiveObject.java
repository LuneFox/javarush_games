package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.controller.Click;
import com.javarush.games.minesweeper.view.View;

public abstract class InteractiveObject implements Drawable, Clickable {
    protected static MinesweeperGame game;
    public int x;
    public int y;
    public int width;
    public int height;
    protected int latestClickX;
    protected int latestClickY;

    public InteractiveObject() {
        this(0, 0);
    }

    public InteractiveObject(int x, int y) {
        setPosition(x, y);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void click(int x, int y, Click click) {
        rememberLatestClick(x, y);
        fireAction(click);
    }

    private void rememberLatestClick(int x, int y) {
        latestClickX = x;
        latestClickY = y;
    }

    private void fireAction(Click click) {
        if (click == Click.LEFT) {
            onLeftClick();
        } else if (click == Click.RIGHT) {
            onRightClick();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void onLeftClick() {
    }

    public void onRightClick() {
    }

    public abstract void draw();

    public boolean covers(int x, int y) {
        return (x >= this.x) && (x <= this.x + this.width) && (y >= this.y) && (y <= this.y + height);
    }

    @SuppressWarnings("unchecked")
    public <T extends InteractiveObject> T linkView(View view) {
        view.linkObject(this);
        return (T) this;
    }

    public static void setGame(MinesweeperGame game) {
        InteractiveObject.game = game;
    }
}
