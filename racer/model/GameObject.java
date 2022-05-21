package com.javarush.games.racer.model;

import com.javarush.games.racer.RacerGame;

public class GameObject {
    protected static RacerGame game;
    private Sprite sprite;
    public double x;
    public double y;
    protected HitBox hitBox;

    public static void setGame(RacerGame game) {
        GameObject.game = game;
    }

    public GameObject() {
        this(0, 0);
    }

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

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

    public void maskIn(int step) {
        sprite.maskIn(step);
    }

    public void maskOut(int step) {
        sprite.maskOut(step);
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

    public void setAnimationDelay(int delay) {
        sprite.setAnimationDelay(delay);
    }
}