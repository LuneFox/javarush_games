package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.view.shapes.BossTankShape;

/** Машинка, которой стреляет босс. */
public class RacingCarBullet extends Bullet {
    /** Создаёт машинку. */
    RacingCarBullet(double x, double y) {
        super(x, y, Direction.DOWN);
        setStaticView(BossTankShape.BOSS_TANK_AMMO);
    }
}
