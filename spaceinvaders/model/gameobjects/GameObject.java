package com.javarush.games.spaceinvaders.model.gameobjects;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Mirror;

public class GameObject {
    protected static SpaceInvadersGame game;
    private Sprite sprite;
    public double x;
    public double y;
    public boolean isAlive;
    protected int jumpEnergy;
    private int lastCollisionX;
    private int lastCollisionY;

    public static void setGame(SpaceInvadersGame game) {
        GameObject.game = game;
    }

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
        this.isAlive = true;
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

    public boolean nothingHasRemained() {
        return (!isAlive && sprite.isAnimationFinished());
    }

    public void draw() {
        sprite.draw(x, y, Mirror.NONE);
    }

    public void draw(Mirror mirror) {
        sprite.draw(x, y, mirror);
    }

    public void eraseCollisionPixel() {
        sprite.erasePixel(lastCollisionX, lastCollisionY);
    }

    protected void raise() {
        y--;
    }

    protected void descend() {
        y++;
    }

    protected void loseJumpEnergy() {
        jumpEnergy--;
    }

    public int getWidth() {
        return sprite.width;
    }

    public int getHeight() {
        return sprite.height;
    }

    /*
     * Collisions
     */

    public boolean collidesWith(GameObject anotherObject, Mirror mirror) {
        // Check if any other object's pixels collide with this object's pixels, return true at first collision
        for (int x = 0; x < anotherObject.getWidth(); x++) {
            for (int y = 0; y < anotherObject.getHeight(); y++) {
                if (anotherObject.sprite.pixelIsNotTransparent(x, y)) {
                    double mirrorX = (mirror == Mirror.HORIZONTAL) ? (anotherObject.sprite.width - x) : x;
                    double checkedX = anotherObject.x + mirrorX;
                    double checkedY = anotherObject.y + y;
                    if (collidesWithPoint(checkedX, checkedY)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean collidesWithPoint(double pointX, double pointY) {
        // Check if a given point overlaps any non-transparent object's pixels
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                if (sprite.pixelIsNotTransparent(x, y)
                        && (int) (x + this.x) == (int) pointX
                        && (int) (y + this.y) == (int) pointY) {
                    memorizeCollisionPoint(x, y);
                    return true;
                }
            }
        }
        return false;
    }

    private void memorizeCollisionPoint(int x, int y) {
        lastCollisionX = x;
        lastCollisionY = y;
    }
}