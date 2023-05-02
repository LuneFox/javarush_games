package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletFactory;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.view.shapes.BossTankShape;

import java.util.Optional;

public class BossTank extends EnemyTank {
    public BossTank(double x, double y) {
        super(x, y);
    }

    @Override
    protected void setHitPointsAndScore() {
        hitPoints = SpaceInvadersGame.getStage();
        scoreForKill = 50 * hitPoints;
    }

    @Override
    protected void setAnimationSprites() {
        MOVE_1 = BossTankShape.BOSS_TANK_1;
        MOVE_2 = BossTankShape.BOSS_TANK_2;
        KILL_1 = BossTankShape.BOSS_TANK_KILL_1;
        KILL_2 = BossTankShape.BOSS_TANK_KILL_2;
        KILL_3 = BossTankShape.BOSS_TANK_KILL_3;
    }

    @Override
    public Optional<Bullet> getAmmo() {
        int chanceToFire = game.getRandomNumber((100 / (4 * SpaceInvadersGame.getStage())) + 1);
        if (chanceToFire != 0) return Optional.empty();

        return BulletFactory.getBullet(BulletType.RACING_CAR, x + 3, y + getHeight());
    }
}
