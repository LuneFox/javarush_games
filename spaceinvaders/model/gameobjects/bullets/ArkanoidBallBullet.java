package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.Overlap;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.EnemyTank;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.view.shapes.BonusShape;

import java.util.List;

/**
 * The arkanoid ball: instead of flying straight it bounces off walls, tanks and Mario.
 */
public class ArkanoidBallBullet extends Bullet {
    private static final int START_SPEED_X = 2;
    private static final int MAX_SPEED = 5;
    private static final double HIT_POSITION_FACTOR = 2;

    private int speedX;

    public ArkanoidBallBullet(double x, double y) {
        super(x, y, Direction.UP);
        this.speedX = (game.getMario().getFacing() == Direction.LEFT) ? -START_SPEED_X : START_SPEED_X;
        setStaticView(BonusShape.ARKANOID_BALL);
        makeLethalToEnemies();
        // The ball is launched from Mario, so the first frames must not bounce off him
        markCollision();
    }

    @Override
    public void move() {
        if (game.isRoundOver()) return;

        setPosition(getX() + speedX, getY() + speedY);
        bounceOffTanks(game.getEnemyArmy().getTanks());
        bounceOffMario();
        bounceOffWalls();
    }

    private void bounceOffTanks(List<EnemyTank> tanks) {
        for (EnemyTank tank : tanks) {
            if (reboundFrom(tank)) return;
        }
    }

    private void bounceOffMario() {
        if (!isCollisionCooledDown()) return;

        Mario mario = game.getMario();
        Overlap overlap = overlapWith(mario);
        if (overlap == Overlap.NONE) return;

        applyBounce(overlap);

        double hitPosition = (getX() - mario.getX()) / mario.getWidth();
        if (overlap == Overlap.HORIZONTAL) {
            // The part of Mario the ball hit changes the horizontal direction
            speedX += (int) (hitPosition * HIT_POSITION_FACTOR);
        }
        kickWithMario(mario);
        limitSpeed();
        markCollision();
    }

    private void kickWithMario(Mario mario) {
        if (mario.getWalkingDirection() == Direction.RIGHT) {
            speedX++;
        } else if (mario.getWalkingDirection() == Direction.LEFT) {
            speedX--;
        }
    }

    private void bounceOffWalls() {
        // The ball may overshoot the wall, so it is put back to the wall, otherwise it would teleport
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

    private boolean reboundFrom(GameObject obstacle) {
        Overlap overlap = overlapWith(obstacle);
        if (overlap == Overlap.NONE) return false;

        applyBounce(overlap);
        return true;
    }

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

    private void limitSpeed() {
        speedX = Math.max(-MAX_SPEED, Math.min(MAX_SPEED, speedX));
        speedY = Math.max(-MAX_SPEED, Math.min(MAX_SPEED, speedY));
    }

    @Override
    public void destroy() {
        // The ball is indestructible
    }
}
