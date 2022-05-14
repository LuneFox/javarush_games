package com.javarush.games.spaceinvaders.model.gameobjects;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

import java.util.Date;

public class Bullet extends GameObject {

    protected int dy;
    public boolean deadlyForEnemies = false;
    public Date collisionDate;

    public Bullet(double x, double y, Direction direction) {
        super(x, y);
        dy = (direction == Direction.UP) ? -1 : 1;
        setStaticView(ObjectShape.getRandomTetrisBullet());
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
