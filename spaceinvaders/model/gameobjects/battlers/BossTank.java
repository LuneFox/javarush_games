package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletFactory;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.view.shapes.BossTankShape;

import java.util.Optional;

public class BossTank extends EnemyTank {
    public BossTank(double x, double y) {
        super(x, y);
        score = 100;
        setAnimatedView(Sprite.Loop.ENABLED, 10,
                BossTankShape.BOSS_TANK_1,
                BossTankShape.BOSS_TANK_2
        );
    }

    @Override
    public Optional<Bullet> getAmmo() {
        int chanceToFire = game.getRandomNumber(100 / (4 * SpaceInvadersGame.DIFFICULTY));
        if (chanceToFire != 0) return Optional.empty();

        return BulletFactory.getBullet(BulletType.RACING_CAR, x + 3, y + getHeight());
    }

    @Override
    public void kill() {
        if (!isAlive) return;
        super.kill();
        Score.add(score);
        setAnimatedView(Sprite.Loop.DISABLED, 1,
                BossTankShape.BOSS_TANK_KILL_1,
                BossTankShape.BOSS_TANK_KILL_2,
                BossTankShape.BOSS_TANK_KILL_3,
                BossTankShape.BOSS_TANK_KILL_4
        );
    }
}
