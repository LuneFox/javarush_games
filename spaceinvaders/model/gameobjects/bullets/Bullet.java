package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.Movable;

/** Снаряд, летящий по вертикали. */
public abstract class Bullet extends GameObject implements Movable {
    private static final int INITIAL_SPEED = 2;
    private static final long COLLISION_COOLDOWN_MS = 500;

    protected double speedY;

    private boolean lethalToEnemies;
    private long lastCollisionTime;

    /** Создаёт снаряд в заданной точке с направлением. */
    public Bullet(double x, double y, Direction direction) {
        super(x, y);
        this.speedY = (direction == Direction.UP) ? -INITIAL_SPEED : INITIAL_SPEED;
    }

    /** Двигает снаряд на шаг. */
    @Override
    public void move() {
        if (game.isRoundOver()) return;

        setPosition(getX(), getY() + speedY);
    }

    /** Сообщает, улетел ли снаряд с поля. */
    public boolean hasLeftField() {
        return getY() >= GameField.HEIGHT - 1 || getY() + getHeight() < 0;
    }

    /** Сообщает, опасен ли снаряд для врагов. */
    public boolean isLethalToEnemies() {
        return lethalToEnemies;
    }

    /** Делает снаряд опасным для врагов. */
    public void makeLethalToEnemies() {
        lethalToEnemies = true;
    }

    /** Сообщает, прошёл ли перерыв после столкновения. */
    public boolean isCollisionCooledDown() {
        return System.currentTimeMillis() - lastCollisionTime >= COLLISION_COOLDOWN_MS;
    }

    /** Запоминает время столкновения. */
    public void markCollision() {
        lastCollisionTime = System.currentTimeMillis();
    }

    /** Разворачивает снаряд. */
    public void reverseDirection() {
        speedY = -speedY;
    }

    /** Удваивает скорость снаряда. */
    protected void doubleSpeed() {
        speedY *= 2;
    }
}
