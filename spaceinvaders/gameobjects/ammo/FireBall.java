package com.javarush.games.spaceinvaders.gameobjects.ammo;

import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.shapes.ObjectShape;

public class FireBall extends Bullet {
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
