package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

public class EnemyShip extends Ship {
    public int score = 15;

    public EnemyShip(double x, double y) {
        super(x, y);
        setAnimatedView(Sprite.Loop.ENABLED, 1, ObjectShape.TANK_1, ObjectShape.TANK_2);
    }

    public void move(Direction direction, double speed) {
        switch (direction) {
            case RIGHT:
                x += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case DOWN:
                y += 2;
                break;
            default:
                break;
        }
    }

    @Override
    public Bullet fire() {
        return new Bullet(x + 2, y + getHeight(), Direction.DOWN);
    }

    @Override
    public void kill() {
        if (isAlive) {
            isAlive = false;
            setAnimatedView(Sprite.Loop.DISABLED, 1,
                    ObjectShape.TANK_KILL_1,
                    ObjectShape.TANK_KILL_2,
                    ObjectShape.TANK_KILL_3,
                    ObjectShape.TANK_KILL_3);
        }
    }
}
