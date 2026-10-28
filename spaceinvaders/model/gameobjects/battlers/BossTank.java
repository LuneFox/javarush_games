package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.StageProgress;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.view.shapes.BossTankShape;

/** Босс армии: прочнее, стреляет реже и машинками. */
public class BossTank extends EnemyTank {
    /** Сдвиг дула босса от левого края. */
    private static final int BULLET_OFFSET_X = 3;
    /** Очки за каждую единицу прочности босса. */
    private static final int SCORE_PER_HIT_POINT = 50;
    /** Базовый делитель шанса выстрела босса. */
    private static final int FIRE_RATE_DIVISOR = 4;

    /** Создаёт босса в заданной точке. */
    public BossTank(double x, double y) {
        super(x, y);
    }

    /** Возвращает прочность босса, равную номеру стадии. */
    @Override
    protected int hitPointsForStage() {
        return StageProgress.getNumber();
    }

    /** Возвращает базовые очки за уничтожение босса по его прочности. */
    @Override
    protected int baseKillScore() {
        return SCORE_PER_HIT_POINT * hitPoints;
    }

    /** Возвращает делитель шанса выстрела босса. */
    @Override
    protected int fireRateDivisor() {
        return FIRE_RATE_DIVISOR * StageProgress.getNumber();
    }

    /** Возвращает тип снаряда босса. */
    @Override
    protected BulletType ammoType() {
        return BulletType.RACING_CAR;
    }

    /** Возвращает сдвиг дула босса. */
    @Override
    protected int muzzleOffsetX() {
        return BULLET_OFFSET_X;
    }

    /** Возвращает первый кадр движения босса. */
    @Override
    protected int[][] marchingFrameA() {
        return BossTankShape.BOSS_TANK_1;
    }

    /** Возвращает второй кадр движения босса. */
    @Override
    protected int[][] marchingFrameB() {
        return BossTankShape.BOSS_TANK_2;
    }

    /** Возвращает первый кадр взрыва босса. */
    @Override
    protected int[][] destructionFrameA() {
        return BossTankShape.BOSS_TANK_KILL_1;
    }

    /** Возвращает второй кадр взрыва босса. */
    @Override
    protected int[][] destructionFrameB() {
        return BossTankShape.BOSS_TANK_KILL_2;
    }

    /** Возвращает третий кадр взрыва босса. */
    @Override
    protected int[][] destructionFrameC() {
        return BossTankShape.BOSS_TANK_KILL_3;
    }
}
