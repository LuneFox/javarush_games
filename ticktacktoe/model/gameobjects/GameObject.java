package com.javarush.games.ticktacktoe.model.gameobjects;

import com.javarush.games.ticktacktoe.Drawable;
import com.javarush.games.ticktacktoe.TicTacToeGame;

public class GameObject implements Drawable {
    protected static TicTacToeGame game;
    private Sprite sprite;
    public double x;
    public double y;

    public static void setGame(TicTacToeGame game) {
        GameObject.game = game;
    }

    public GameObject() {
        this(0, 0);
    }

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /*
     * Graphics
     */

    public void setStaticView(int[][] frame) {
        sprite = new Sprite(game);
        sprite.setStaticView(frame);
    }

    public void setAnimatedView(Sprite.Loop loop, int nextFrameDelay, int[][]... frames) {
        sprite = new Sprite(game);
        sprite.setAnimatedView(loop, nextFrameDelay, frames);
    }

    public void draw() {
        sprite.draw(x, y, Mirror.NONE);
    }

    public void draw(Mirror mirror) {
        sprite.draw(x, y, mirror);
    }

    public int getWidth() {
        return sprite.width;
    }

    public int getHeight() {
        return sprite.height;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
}