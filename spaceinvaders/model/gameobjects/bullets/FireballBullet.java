package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.view.shapes.FireballShape;

/**
 * Mario's fireball: flies twice as fast as a regular bullet and cannot be shot down.
 */
public class FireballBullet extends Bullet {
    private static final int FRAME_DELAY = 2;

    FireballBullet(double x, double y) {
        super(x, y, Direction.UP);
        doubleSpeed();
        setAnimatedView(Sprite.Loop.REPEAT, FRAME_DELAY,
                FireballShape.FIREBALL_1,
                FireballShape.FIREBALL_2,
                FireballShape.FIREBALL_3,
                FireballShape.FIREBALL_4);
        makeLethalToEnemies();
    }

    @Override
    public void destroy() {
        // A fireball burns through everything, it cannot be destroyed
    }
}
