package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.OverlapForm;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;
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
        realignBounds();
    }

    private void bounceFromWalls() {
        if (y <= 0) {
            dy = -dy;
        }

        if (x <= 0 || x >= 95) {
            dx = -dx;
        }
    }

    private void shift() {
        y += dy;
        x += dx;
    }

    private void realignBounds() {
        if (y < 0) y = 0;
        if (x < 0) x = 0;
        if (x > 95) x = 95;
    }

    @Override
    public void kill() {
        // Can't destroy
    }

    private void bounceFromObjects(List<? extends GameObject> objectList) {
        for (GameObject gameObject : objectList) {
            OverlapForm form = getOverlapForm(gameObject);
            switch (form) {
                case WIDE:
                    dy = -dy;
                    break;
                case HIGH:
                    dx = -dx;
                    break;
                case EVEN:
                    dy = -dy;
                    dx = -dx;
                    break;
                default:
                    break;
            }

            if (!(form == OverlapForm.NONE)) {
                break;
            }
        }
    }

    private void bounceFromMario() {
        if (new Date().getTime() - lastCollisionDate.getTime() < 500) {
            return;
        }

        Mario mario = SpaceInvadersGame.getInstance().getMario();
        OverlapForm form = getOverlapForm(mario);

        switch (form) {
            case WIDE:
                dy = -dy;
                doAdditionalKick(mario);
                lastCollisionDate = new Date();
                break;
            case HIGH:
                dx = -dx;
                doAdditionalKick(mario);
                lastCollisionDate = new Date();
                break;
            case EVEN:
                dy = -dy;
                dx = -dx;
                doAdditionalKick(mario);
                lastCollisionDate = new Date();
                break;
            default:
                break;
        }
    }

    private void doAdditionalKick(Mario mario) {
        switch (mario.getMoveDirection()) {
            case RIGHT:
                x += 2;
                break;
            case LEFT:
                x -= 2;
                break;
        }
    }
}
