package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

public class EnemyBossTankBullet extends Bullet {
    public EnemyBossTankBullet(double x, double y, Direction direction) {
        super(x, y, direction);
        setStaticView(ObjectShape.BOSS_TANK_AMMO);
    }
}
