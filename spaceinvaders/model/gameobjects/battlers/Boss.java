package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

public class Boss extends EnemyShip {
    public Boss(double x, double y) {
        super(x, y);
        setAnimatedView(Sprite.Loop.ENABLED, 10,
                ObjectShape.BOSS_TANK_1,
                ObjectShape.BOSS_TANK_2);
        score = 100;
    }

    @Override
    public Bullet fire() {
        if (!isAlive) {
            return null;
        }
        return new Bullet(x + 3, y + getHeight(), Direction.DOWN) {
            {
                setStaticView(ObjectShape.BOSS_TANK_AMMO);
            }
        };
    }

    @Override
    public void kill() {
        if (isAlive) {
            isAlive = false;
            setAnimatedView(Sprite.Loop.DISABLED, 2,
                    ObjectShape.BOSS_TANK_KILL_1,
                    ObjectShape.BOSS_TANK_KILL_2,
                    ObjectShape.BOSS_TANK_KILL_3,
                    ObjectShape.BOSS_TANK_KILL_4,
                    ObjectShape.BOSS_TANK_KILL_4);
        }
    }
}
