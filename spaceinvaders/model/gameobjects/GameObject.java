package com.javarush.games.spaceinvaders.model.gameobjects;

import com.javarush.games.spaceinvaders.Drawable;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Mirror;

/**
 * Base class of every sprite on the field: knows its position, its picture and its collisions.
 */
public class GameObject implements Drawable {
    protected static SpaceInvadersGame game;

    private Sprite sprite;
    private double x;
    private double y;
    private boolean alive = true;
    private int lastCollisionX;
    private int lastCollisionY;

    public static void setGame(SpaceInvadersGame game) {
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
     * Position and lifetime
     */

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean isAlive() {
        return alive;
    }

    public void destroy() {
        alive = false;
    }

    /**
     * An object may be forgotten once it is destroyed and its explosion animation is over.
     */
    public boolean canBeRemoved() {
        return !alive && sprite.isAnimationFinished();
    }

    /*
     * Graphics
     */

    public void setStaticView(int[][] frame) {
        sprite = new Sprite(game);
        sprite.setStaticView(frame);
    }

    public void setAnimatedView(Sprite.Loop loop, int frameDelay, int[][]... frames) {
        sprite = new Sprite(game);
        sprite.setAnimatedView(loop, frameDelay, frames);
    }

    @Override
    public void draw() {
        sprite.draw(x, y, Mirror.NONE);
    }

    public void draw(Mirror mirror) {
        sprite.draw(x, y, mirror);
    }

    public void eraseCollisionPixel() {
        sprite.erasePixel(lastCollisionX, lastCollisionY);
    }

    public int getWidth() {
        return sprite.getWidth();
    }

    public int getHeight() {
        return sprite.getHeight();
    }

    /*
     * Collisions
     */

    public Overlap overlapWith(GameObject other) {
        if (!collidesWith(other, Mirror.NONE)) {
            return Overlap.NONE;
        }

        double horizontalOverlap = Math.max(0, Math.min(
                x + getWidth(), other.x + other.getWidth()) - Math.max(x, other.x));
        double verticalOverlap = Math.max(0, Math.min(
                y + getHeight(), other.y + other.getHeight()) - Math.max(y, other.y));

        if (horizontalOverlap * verticalOverlap == 0) {
            return Overlap.NONE;
        }

        // Near-equal sides mean a corner hit
        final double threshold = 0.5;
        if (Math.abs(verticalOverlap - horizontalOverlap) < threshold) {
            return Overlap.SQUARE;
        }
        return (verticalOverlap > horizontalOverlap) ? Overlap.VERTICAL : Overlap.HORIZONTAL;
    }

    /**
     * Checks whether any non-transparent pixel of the other object touches any non-transparent
     * pixel of this one. The other object may be mirrored.
     */
    public boolean collidesWith(GameObject other, Mirror mirror) {
        for (int otherX = 0; otherX < other.getWidth(); otherX++) {
            for (int otherY = 0; otherY < other.getHeight(); otherY++) {
                if (!other.sprite.isPixelVisible(otherX, otherY)) {
                    continue;
                }

                double mirroredX = (mirror == Mirror.HORIZONTAL) ? (other.sprite.getWidth() - otherX) : otherX;
                if (collidesWithPoint(other.x + mirroredX, other.y + otherY)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean collidesWithPoint(double pointX, double pointY) {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                boolean sameCell = (int) (x + this.x) == (int) pointX && (int) (y + this.y) == (int) pointY;
                if (sameCell && sprite.isPixelVisible(x, y)) {
                    rememberCollisionPoint(x, y);
                    return true;
                }
            }
        }
        return false;
    }

    private void rememberCollisionPoint(int x, int y) {
        lastCollisionX = x;
        lastCollisionY = y;
    }
}
