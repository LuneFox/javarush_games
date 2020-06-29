package com.javarush.games.spaceinvaders.gameobjects.ammo;

import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.shapes.ObjectShape;

public class Bullet extends GameObject {

    private int dy;
    public boolean isAlive = true;
    public boolean deadlyForEnemies = false;

    public Bullet(double x, double y, Direction direction) {
        super(x, y);
        dy = (direction == Direction.UP) ? -1 : 1;
        setStaticView(ObjectShape.BULLET);
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
