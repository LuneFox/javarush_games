package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.Movable;

/**
 * A projectile flying vertically. An enemy bullet reflected by a star becomes lethal to its
 * own side, that is why the flag exists.
 */
public abstract class Bullet extends GameObject implements Movable {
    private static final int INITIAL_SPEED = 2;
    private static final long COLLISION_COOLDOWN_MS = 500;

    protected int speedY;

    private boolean lethalToEnemies;
    private long lastCollisionTime;

    public Bullet(double x, double y, Direction direction) {
        super(x, y);
        this.speedY = (direction == Direction.UP) ? -INITIAL_SPEED : INITIAL_SPEED;
    }

    @Override
    public void move() {
        if (game.isRoundOver()) return;

        setPosition(getX(), getY() + speedY);
    }

    public boolean hasLeftField() {
        return getY() >= GameField.HEIGHT - 1 || getY() + getHeight() < 0;
    }

    public boolean isLethalToEnemies() {
        return lethalToEnemies;
    }

    public void makeLethalToEnemies() {
        lethalToEnemies = true;
    }

    /**
     * A reflected bullet may hit its own shooter, so the same bullet is not reflected twice in a row.
     */
    public boolean isCollisionCooledDown() {
        return System.currentTimeMillis() - lastCollisionTime >= COLLISION_COOLDOWN_MS;
    }

    public void markCollision() {
        lastCollisionTime = System.currentTimeMillis();
    }

    public void reverseDirection() {
        speedY = -speedY;
    }

    protected void doubleSpeed() {
        speedY *= 2;
    }
}
