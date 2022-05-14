package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;

import java.util.Date;

public abstract class Bullet extends GameObject {
    protected int dy;
    public boolean canKillEnemies;
    public Date lastCollisionDate;

    public Bullet(double x, double y, Direction direction) {
        super(x, y);
        dy = (direction == Direction.UP) ? -1 : 1;
        canKillEnemies = false;
        lastCollisionDate = new Date();
    }

    public void move() {
        y += dy;
    }

    public void kill() {
        isAlive = false;
    }

    public void inverseDirection() {
        this.dy = -dy;
    }

    protected void multiplySpeed(int times) {
        dy *= times;
    }
}
