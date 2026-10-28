package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.OverlapForm;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses.Star;
import com.javarush.games.spaceinvaders.view.shapes.BonusShape;

import java.util.Date;
import java.util.List;

public class ArkanoidBallBullet extends Bullet {
    private int dx;

    public ArkanoidBallBullet(double x, double y) {
        super(x, y, Direction.UP);
        Mario mario = SpaceInvadersGame.getInstance().getMario();
        dx = (mario.getFaceDirection() == Direction.LEFT) ? -2 : 2;
        setStaticView(BonusShape.ARKANOID_BALL);
        canKillEnemies = true;
        lastCollisionDate = new Date();
    }

    @Override
    public void move() {
        if (game.isStopped()) {
            return;
        }

        shift();
        checkBounce();
    }

    private void checkBounce() {
        bounceFromObjects(SpaceInvadersGame.getInstance().getEnemyArmy().getEnemyTanks());
        bounceFromMario();
        bounceFromWalls();
    }

    private void bounceFromWalls() {
        // The ball may overshoot the border, so it is put back to the border it hit, otherwise it would teleport
        if (y < 0) {
            y = 0;
            dy = Math.abs(dy);
        }

        if (x < 0) {
            x = 0;
            dx = Math.abs(dx);
        } else if (x + getWidth() > SpaceInvadersGame.WIDTH) {
            x = SpaceInvadersGame.WIDTH - getWidth();
            dx = -Math.abs(dx);
        }
    }

    private void shift() {
        y += dy;
        x += dx;
    }

    @Override
    public void kill() {
        // Can't destroy
    }

    private void bounceFromObjects(List<? extends GameObject> objectList) {
        for (GameObject gameObject : objectList) {
            switch (getOverlapForm(gameObject)) {
                case WIDE:
                    dy = -dy;
                    return;
                case HIGH:
                    dx = -dx;
                    return;
                case EVEN:
                    dy = -dy;
                    dx = -dx;
                    return;
                default:
                    break;
            }
        }
    }

    private void bounceFromMario() {
        if (!bulletCollisionCooledDown()) {
            return;
        }

        Mario mario = SpaceInvadersGame.getInstance().getMario();
        OverlapForm form = getOverlapForm(mario);

        // Учитываем положение мяча относительно Марио для более точного отскока
        double hitPosition = (x - mario.x) / mario.getWidth();

        switch (form) {
            case WIDE:
                dy = -dy;
                // Меняем dx в зависимости от того, в какую часть Марио попал мяч
                dx += (int) (hitPosition * 2);
                break;
            case HIGH:
                dx = -dx;
                break;
            case EVEN:
                dy = -dy;
                dx = -dx;
                break;
            default:
                return;
        }

        // Добавляем "удар" в зависимости от направления движения Марио
        if (mario.getMoveDirection() == Direction.RIGHT) {
            dx += 1;
        } else if (mario.getMoveDirection() == Direction.LEFT) {
            dx -= 1;
        }

        // Ограничиваем максимальную скорость
        dx = Math.max(-5, Math.min(5, dx));
        dy = Math.max(-5, Math.min(5, dy));

        lastCollisionDate = new Date();
    }

    private boolean bulletCollisionCooledDown() {
        return new Date().getTime() - lastCollisionDate.getTime() >= Star.BULLET_COLLISION_COOLDOWN_MS;
    }
}
