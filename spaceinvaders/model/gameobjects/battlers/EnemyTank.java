package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.StageProgress;
import com.javarush.games.spaceinvaders.model.gameobjects.Shooter;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletFactory;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.view.shapes.TankShape;

/**
 * A regular enemy tank. The boss tank differs only in strength, picture and ammo, which is why
 * those details are overridable methods instead of a second copy of this class.
 */
public class EnemyTank extends Battler implements Shooter {
    private static final int MARCH_FRAME_DELAY = 5;
    private static final int STEP_DOWN = 2;
    private static final int FIRE_CHANCE_RANGE = 100;
    private static final int BULLET_OFFSET_X = 2;
    private static final int EXPLOSION_FRAME_DELAY = 1;

    private final int scoreForKill;
    private boolean recoveringFromHit;

    public EnemyTank(double x, double y) {
        super(x, y);
        this.hitPoints = hitPointsForStage();
        this.scoreForKill = scoreForStage();
        showMarching();
    }

    /*
     * Everything that makes the boss tank different
     */

    protected int hitPointsForStage() {
        return StageProgress.getNumber() / 5 + 1;
    }

    protected int scoreForStage() {
        return 9 + hitPoints * StageProgress.getNumber();
    }

    protected int fireRateDivisor() {
        return StageProgress.getNumber();
    }

    protected BulletType ammoType() {
        return BulletType.TETRIS;
    }

    protected int muzzleOffsetX() {
        return BULLET_OFFSET_X;
    }

    protected int[][] marchingFrameA() {
        return TankShape.TANK_1;
    }

    protected int[][] marchingFrameB() {
        return TankShape.TANK_2;
    }

    protected int[][] destructionFrameA() {
        return TankShape.TANK_KILL_1;
    }

    protected int[][] destructionFrameB() {
        return TankShape.TANK_KILL_2;
    }

    protected int[][] destructionFrameC() {
        return TankShape.TANK_KILL_3;
    }

    /*
     * Behaviour
     */

    /**
     * Takes one step of the formation march in the given direction.
     */
    public void advance(Direction direction, double speed) {
        if (isAlive() && recoveringFromHit) {
            showMarching();
            recoveringFromHit = false;
        }

        if (direction == Direction.RIGHT) {
            setPosition(getX() + speed, getY());
        } else if (direction == Direction.LEFT) {
            setPosition(getX() - speed, getY());
        } else if (direction == Direction.DOWN) {
            setPosition(getX(), getY() + STEP_DOWN);
        }
    }

    @Override
    public void shoot() {
        if (!isAlive()) return;
        if (!isFireTime()) return;

        game.getEnemyBullets().add(BulletFactory.create(
                ammoType(), getX() + muzzleOffsetX(), getY() + getHeight()));
    }

    private boolean isFireTime() {
        int chanceRange = FIRE_CHANCE_RANGE / fireRateDivisor() + 1;
        return game.getRandomNumber(chanceRange) == 0;
    }

    @Override
    public void takeHit() {
        if (!isAlive()) return;

        super.takeHit();

        if (!isAlive()) {
            Score.add(scoreForKill);
        }

        showHitReaction();
    }

    private void showMarching() {
        setAnimatedView(Sprite.Loop.REPEAT, MARCH_FRAME_DELAY, marchingFrameA(), marchingFrameB());
    }

    private void showHitReaction() {
        if (isAlive()) {
            setStaticView(destructionFrameA());
            recoveringFromHit = true;
        } else {
            setAnimatedView(Sprite.Loop.ONCE, EXPLOSION_FRAME_DELAY,
                    destructionFrameA(), destructionFrameB(), destructionFrameC());
        }
    }
}
