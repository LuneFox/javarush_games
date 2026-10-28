package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.view.shapes.RedTankShape;

/** Красный танк: двойная прочность, учащённая стрельба и красные пули. */
public class RedTank extends EnemyTank {
    /** Создаёт красный танк в заданной точке. */
    public RedTank(double x, double y) {
        super(x, y);
    }

    /** Возвращает удвоенную прочность обычного танка. */
    @Override
    protected int hitPointsForStage() {
        return super.hitPointsForStage() * 2;
    }

    /** Возвращает удвоенные очки за обычный танк. */
    @Override
    protected int baseKillScore() {
        return 2 * baseKillScoreFor(super.hitPointsForStage());
    }

    /** Стреляет в два раза чаще обычного танка. */
    @Override
    protected int fireChanceMultiplier() {
        return 2;
    }

    /** Возвращает тип красной пули. */
    @Override
    protected BulletType ammoType() {
        return BulletType.RED;
    }

    /** Возвращает первый кадр движения красного танка. */
    @Override
    protected int[][] marchingFrameA() {
        return RedTankShape.TANK_1;
    }

    /** Возвращает второй кадр движения красного танка. */
    @Override
    protected int[][] marchingFrameB() {
        return RedTankShape.TANK_2;
    }

    /** Возвращает первый кадр взрыва красного танка. */
    @Override
    protected int[][] destructionFrameA() {
        return RedTankShape.TANK_KILL_1;
    }

    /** Возвращает второй кадр взрыва красного танка. */
    @Override
    protected int[][] destructionFrameB() {
        return RedTankShape.TANK_KILL_2;
    }

    /** Возвращает третий кадр взрыва красного танка. */
    @Override
    protected int[][] destructionFrameC() {
        return RedTankShape.TANK_KILL_3;
    }
}
