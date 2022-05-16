package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

public class FireballBullet extends Bullet {
    public FireballBullet(double x, double y) {
        super(x, y, Direction.UP);
        multiplySpeed(2);
        setAnimatedView(Sprite.Loop.ENABLED, 2,
                ObjectShape.FIREBALL_1,
                ObjectShape.FIREBALL_2,
                ObjectShape.FIREBALL_3,
                ObjectShape.FIREBALL_4);
        canKillEnemies = true;
    }
}
