package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.StageProgress;
import com.javarush.games.spaceinvaders.model.gameobjects.Shooter;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletFactory;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.view.shapes.TankShape;

/** Обычный танк врага; босс отличается прочностью, спрайтами и снарядами. */
public class EnemyTank extends Battler implements Shooter {
    /** Задержка между кадрами марша. */
    private static final int MARCH_FRAME_DELAY = 5;
    /** Шаг вниз при достижении края. */
    private static final int STEP_DOWN = 2;
    /** Диапазон случайного шанса выстрела. */
    private static final int FIRE_CHANCE_RANGE = 100;
    /** Сдвиг дула от левого края. */
    private static final int BULLET_OFFSET_X = 2;
    /** Задержка между кадрами взрыва. */
    private static final int EXPLOSION_FRAME_DELAY = 1;

    /** Очки за уничтожение танка. */
    private final int scoreForKill;
    /** Признак восстановления после попадания. */
    private boolean recoveringFromHit;

    /** Создаёт танк в заданной точке. */
    public EnemyTank(double x, double y) {
        super(x, y);
        this.hitPoints = hitPointsForStage();
        this.scoreForKill = baseKillScore();
        showMarching();
    }

    /*
     * То, чем босс отличается от обычного танка
     */

    /** Возвращает прочность танка для текущей стадии. */
    protected int hitPointsForStage() {
        return StageProgress.getNumber() / 5 + 1;
    }

    /**
     * Возвращает базовые очки за уничтожение танка. Множитель стадии добавляет {@link Score},
     * поэтому здесь стадия не учитывается — иначе очки умножались бы на неё дважды.
     */
    protected int baseKillScore() {
        return 9 + hitPoints;
    }

    /** Возвращает делитель шанса выстрела. */
    protected int fireRateDivisor() {
        return StageProgress.getNumber();
    }

    /** Возвращает тип снаряда. */
    protected BulletType ammoType() {
        return BulletType.TETRIS;
    }

    /** Возвращает сдвиг дула от левого края. */
    protected int muzzleOffsetX() {
        return BULLET_OFFSET_X;
    }

    /** Возвращает первый кадр движения. */
    protected int[][] marchingFrameA() {
        return TankShape.TANK_1;
    }

    /** Возвращает второй кадр движения. */
    protected int[][] marchingFrameB() {
        return TankShape.TANK_2;
    }

    /** Возвращает первый кадр взрыва. */
    protected int[][] destructionFrameA() {
        return TankShape.TANK_KILL_1;
    }

    /** Возвращает второй кадр взрыва. */
    protected int[][] destructionFrameB() {
        return TankShape.TANK_KILL_2;
    }

    /** Возвращает третий кадр взрыва. */
    protected int[][] destructionFrameC() {
        return TankShape.TANK_KILL_3;
    }

    /*
     * Поведение
     */

    /** Делает шаг строя в заданном направлении. */
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

    /** Стреляет, если выпал шанс. */
    @Override
    public void shoot() {
        if (!isAlive()) return;
        if (!isFireTime()) return;

        game.getEnemyBullets().add(BulletFactory.create(
                ammoType(), getX() + muzzleOffsetX(), getY() + getHeight()));
    }

    /** Проверяет, выпал ли шанс выстрела. */
    private boolean isFireTime() {
        int chanceRange = FIRE_CHANCE_RANGE / fireRateDivisor() + 1;
        return game.getRandomNumber(chanceRange) == 0;
    }

    /** Применяет попадание и обновляет анимацию. */
    @Override
    public void takeHit() {
        if (!isAlive()) return;

        super.takeHit();

        if (!isAlive()) {
            Score.add(scoreForKill);
        }

        showHitReaction();
    }

    /** Включает анимацию движения. */
    private void showMarching() {
        setAnimatedView(Sprite.Loop.REPEAT, MARCH_FRAME_DELAY, marchingFrameA(), marchingFrameB());
    }

    /** Показывает попадание или взрыв. */
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
