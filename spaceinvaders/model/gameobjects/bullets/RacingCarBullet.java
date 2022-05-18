package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.view.shapes.BossTankShape;

public class RacingCarBullet extends Bullet {
    RacingCarBullet(double x, double y) {
        super(x, y, Direction.DOWN);
        setStaticView(BossTankShape.BOSS_TANK_AMMO);
    }
}
