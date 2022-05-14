package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.EnemyBossTankBullet;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

public class BossTank extends EnemyTank {
    public BossTank(double x, double y) {
        super(x, y);
        score = 100;
        setAnimatedView(Sprite.Loop.ENABLED, 10,
                ObjectShape.BOSS_TANK_1,
                ObjectShape.BOSS_TANK_2
        );
    }

    @Override
    public Bullet fire() {
        return new EnemyBossTankBullet(x + 3, y + getHeight(), Direction.DOWN);
    }

    @Override
    public void kill() {
        if (!isAlive) return;
        isAlive = false;
        setAnimatedView(Sprite.Loop.DISABLED, 2,
                ObjectShape.BOSS_TANK_KILL_1,
                ObjectShape.BOSS_TANK_KILL_2,
                ObjectShape.BOSS_TANK_KILL_3,
                ObjectShape.BOSS_TANK_KILL_4,
                ObjectShape.BOSS_TANK_KILL_4
        );
    }
}
