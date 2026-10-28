package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.view.shapes.TankShape;

/** Ярко-красная пуля красного танка; звёздочкой не отражается. */
public class RedBullet extends Bullet {
    /** Создаёт красную пулю. */
    RedBullet(double x, double y) {
        super(x, y, Direction.DOWN);
        setStaticView(TankShape.RED_BULLET);
        makeNonReflectable();
    }
}
