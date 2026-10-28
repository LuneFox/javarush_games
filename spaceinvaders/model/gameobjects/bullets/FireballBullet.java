package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.view.shapes.FireballShape;

/** Огненный шар Марио. */
public class FireballBullet extends Bullet {
    /** Задержка между кадрами огненного шара. */
    private static final int FRAME_DELAY = 2;

    /** Создаёт огненный шар. */
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

    /** Огненный шар уничтожить нельзя. */
    @Override
    public void destroy() {
        // Огненный шар пролетает сквозь всё, уничтожить его нельзя
    }
}
