package com.javarush.games.spaceinvaders;

import com.javarush.games.spaceinvaders.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.shapes.ObjectShape;

import java.util.Date;

public class Bullet extends GameObject {

    private int dy;
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

    public static class Coin extends Bullet {
        public Coin(double x, double y, Direction direction) {
            super(x, y, direction);
            setAnimatedView(true,
                    ObjectShape.COIN_WIDTH_7,
                    ObjectShape.COIN_WIDTH_5,
                    ObjectShape.COIN_WIDTH_3,
                    ObjectShape.COIN_WIDTH_1,
                    ObjectShape.COIN_WIDTH_3,
                    ObjectShape.COIN_WIDTH_5);
            deadlyForEnemies = true;
        }
    }

    public static class BossAmmo extends Bullet {
        public BossAmmo(double x, double y, Direction direction) {
            super(x, y, direction);
            setStaticView(ObjectShape.BOSS_TANK_AMMO);
        }
    }

    public static class FireBall extends Bullet {
        public FireBall(double x, double y, Direction direction) {
            super(x, y, direction);
            setAnimatedView(true,
                    ObjectShape.FIREBALL_1,
                    ObjectShape.FIREBALL_2,
                    ObjectShape.FIREBALL_3,
                    ObjectShape.FIREBALL_4);
            deadlyForEnemies = true;
        }
    }
}
