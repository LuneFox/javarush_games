package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.EnemyBossTankBullet;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

import java.util.Optional;

public class BossTank extends Tank {
    public BossTank(double x, double y) {
        super(x, y);
        score = 100;
        setAnimatedView(Sprite.Loop.ENABLED, 10,
                ObjectShape.BOSS_TANK_1,
                ObjectShape.BOSS_TANK_2
        );
    }

    @Override
    public Optional<Bullet> fire() {
        return Optional.of(new EnemyBossTankBullet(x + 3, y + getHeight(), Direction.DOWN));
    }

    @Override
    public void kill() {
        if (!isAlive) return;
        super.kill();
        setAnimatedView(Sprite.Loop.DISABLED, 1,
                ObjectShape.BOSS_TANK_KILL_1,
                ObjectShape.BOSS_TANK_KILL_2,
                ObjectShape.BOSS_TANK_KILL_3,
                ObjectShape.BOSS_TANK_KILL_4
        );
    }
}
