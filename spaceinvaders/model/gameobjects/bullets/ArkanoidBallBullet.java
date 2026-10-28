package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.Overlap;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.EnemyTank;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.view.shapes.BonusShape;

import java.util.List;

/** Шар арканоида: скачет по полю и убивает танки. */
public class ArkanoidBallBullet extends Bullet {
    /** Начальная горизонтальная скорость. */
    private static final int START_SPEED_X = 2;
    /** Влияние места попадания в Марио на направление. */
    private static final double HIT_POSITION_FACTOR = 2;

    /** Постоянный модуль скорости шара. */
    private final double ballSpeed;
    /** Текущая горизонтальная скорость. */
    private double speedX;

    /** Создаёт шар у Марио. */
    public ArkanoidBallBullet(double x, double y) {
        super(x, y, Direction.UP);
        this.speedX = (game.getMario().getFacing() == Direction.LEFT) ? -START_SPEED_X : START_SPEED_X;
        this.ballSpeed = Math.sqrt(speedX * speedX + speedY * speedY);
        setStaticView(BonusShape.ARKANOID_BALL);
        makeLethalToEnemies();
        // Шар запускается из Марио, поэтому первые кадры не должны отражаться от него
        markCollision();
    }

    /** Двигает шар и отражает его от препятствий. */
    @Override
    public void move() {
        if (game.isRoundOver()) return;

        setPosition(getX() + speedX, getY() + speedY);
        bounceOffTanks(game.getEnemyArmy().getTanks());
        bounceOffMario();
        bounceOffWalls();
        normalizeSpeed();
    }

    /** Отражает шар от первого задетого танка. */
    private void bounceOffTanks(List<EnemyTank> tanks) {
        for (EnemyTank tank : tanks) {
            if (reboundFrom(tank)) return;
        }
    }

    /** Отражает шар от Марио. */
    private void bounceOffMario() {
        if (!isCollisionCooledDown()) return;

        Mario mario = game.getMario();
        Overlap overlap = overlapWith(mario);
        if (overlap == Overlap.NONE) return;

        applyBounce(overlap);

        double hitPosition = (getX() - mario.getX()) / mario.getWidth();
        if (overlap == Overlap.HORIZONTAL) {
            // Место попадания в Марио меняет горизонтальное направление
            speedX += (int) (hitPosition * HIT_POSITION_FACTOR);
        }
        kickWithMario(mario);
        markCollision();
    }

    /** Добавляет шару скорость от движения Марио. */
    private void kickWithMario(Mario mario) {
        if (mario.getWalkingDirection() == Direction.RIGHT) {
            speedX++;
        } else if (mario.getWalkingDirection() == Direction.LEFT) {
            speedX--;
        }
    }

    /** Отражает шар от стен и потолка. */
    private void bounceOffWalls() {
        // Шар может проскочить стену, поэтому возвращаем его к границе, иначе он телепортируется
        if (getY() < 0) {
            setPosition(getX(), 0);
            speedY = Math.abs(speedY);
        }

        if (getX() < 0) {
            setPosition(0, getY());
            speedX = Math.abs(speedX);
        } else if (getX() + getWidth() > GameField.WIDTH) {
            setPosition(GameField.WIDTH - getWidth(), getY());
            speedX = -Math.abs(speedX);
        }
    }

    /** Отражает шар от препятствия при контакте. */
    private boolean reboundFrom(GameObject obstacle) {
        Overlap overlap = overlapWith(obstacle);
        if (overlap == Overlap.NONE) return false;

        applyBounce(overlap);
        return true;
    }

    /** Меняет скорость шара по форме контакта. */
    private void applyBounce(Overlap overlap) {
        switch (overlap) {
            case HORIZONTAL:
                speedY = -speedY;
                break;
            case VERTICAL:
                speedX = -speedX;
                break;
            case SQUARE:
                speedY = -speedY;
                speedX = -speedX;
                break;
            default:
                break;
        }
    }

    /** Сохраняет постоянный модуль скорости при изменении направления. */
    private void normalizeSpeed() {
        double currentSpeed = Math.sqrt(speedX * speedX + speedY * speedY);
        if (currentSpeed == 0) {
            speedX = 0;
            speedY = -ballSpeed;
            return;
        }

        speedX = speedX / currentSpeed * ballSpeed;
        speedY = speedY / currentSpeed * ballSpeed;
    }

    /** Шар уничтожить нельзя. */
    @Override
    public void destroy() {
        // Шар неуничтожим
    }
}
