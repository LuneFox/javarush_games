package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.view.shapes.FireballShape;

public class FireballBullet extends Bullet {
    public FireballBullet(double x, double y) {
        super(x, y, Direction.UP);
        doubleSpeed();
        setAnimatedView(Sprite.Loop.ENABLED, 2,
                FireballShape.FIREBALL_1,
                FireballShape.FIREBALL_2,
                FireballShape.FIREBALL_3,
                FireballShape.FIREBALL_4);
        canKillEnemies = true;
    }
}
