package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.EnemyTankBullet;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

import java.util.Optional;

public class SmallTank extends Tank {
    public SmallTank(double x, double y) {
        super(x, y);
        score = 15;
        setAnimatedView(Sprite.Loop.ENABLED, 5,
                ObjectShape.TANK_1,
                ObjectShape.TANK_2
        );
    }

    @Override
    public Optional<Bullet> fire() {
        return Optional.of(new EnemyTankBullet(x + 2, y + getHeight(), Direction.DOWN));
    }

    @Override
    public void kill() {
        if (!isAlive) return;
        super.kill();
        setAnimatedView(Sprite.Loop.DISABLED, 1,
                ObjectShape.TANK_KILL_1,
                ObjectShape.TANK_KILL_2,
                ObjectShape.TANK_KILL_3
        );
    }
}
