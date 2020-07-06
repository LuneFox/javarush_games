package com.javarush.games.spaceinvaders;

import com.javarush.games.spaceinvaders.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.shapes.ObjectShape;

import java.util.Date;

public class Bullet extends GameObject {

    protected int dy;
    public boolean isAlive = true;
    public boolean deadlyForEnemies = false;
    public Date collisionDate;

    public Bullet(double x, double y, Direction direction) {
        super(x, y);
        dy = (direction == Direction.UP) ? -1 : 1;
        setStaticView(ObjectShape.BULLET);
        collisionDate = new Date();
    }

    public void move() {
        y += dy;
    }

    public void kill() {
        isAlive = false;
    }

    public void changeDirection() {
        this.dy = -dy;
    }
}
