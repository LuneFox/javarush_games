package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.StageProgress;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.view.shapes.BossTankShape;

/**
 * The flagship of the army: tougher, slower to fire and shooting racing cars.
 */
public class BossTank extends EnemyTank {
    private static final int BULLET_OFFSET_X = 3;
    private static final int SCORE_PER_HIT_POINT = 50;
    private static final int FIRE_RATE_DIVISOR = 4;

    public BossTank(double x, double y) {
        super(x, y);
    }

    @Override
    protected int hitPointsForStage() {
        return StageProgress.getNumber();
    }

    @Override
    protected int scoreForStage() {
        return SCORE_PER_HIT_POINT * hitPoints;
    }

    @Override
    protected int fireRateDivisor() {
        return FIRE_RATE_DIVISOR * StageProgress.getNumber();
    }

    @Override
    protected BulletType ammoType() {
        return BulletType.RACING_CAR;
    }

    @Override
    protected int muzzleOffsetX() {
        return BULLET_OFFSET_X;
    }

    @Override
    protected int[][] marchingFrameA() {
        return BossTankShape.BOSS_TANK_1;
    }

    @Override
    protected int[][] marchingFrameB() {
        return BossTankShape.BOSS_TANK_2;
    }

    @Override
    protected int[][] destructionFrameA() {
        return BossTankShape.BOSS_TANK_KILL_1;
    }

    @Override
    protected int[][] destructionFrameB() {
        return BossTankShape.BOSS_TANK_KILL_2;
    }

    @Override
    protected int[][] destructionFrameC() {
        return BossTankShape.BOSS_TANK_KILL_3;
    }
}
