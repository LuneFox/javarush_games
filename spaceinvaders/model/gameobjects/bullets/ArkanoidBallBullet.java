package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.view.shapes.BonusShape;

public class ArkanoidBallBullet extends Bullet {
    private int bounceLimit = 30;
    private int dx;

    public ArkanoidBallBullet(double x, double y) {
        super(x, y, Direction.UP);
        Mario mario = SpaceInvadersGame.getInstance().getMario();
        dx = (mario.getFaceDirection() == Direction.LEFT) ? -2 : 2;
        setStaticView(BonusShape.ARKANOID_BALL);
        canKillEnemies = true;
    }

    @Override
    public void move() {
        if (game.isStopped()) return;

        if (y <= 0 || y >= 91) {
            dy = -dy;
        }

        if (x <= 0 || x >= 95) {
            dx = -dx;
        }

        y += dy;
        x += dx;

        if (y < 0) y = 0;
        if (x < 0) x = 0;
        if (y > 91) y = 91;
        if (x > 95) x = 95;
    }

    @Override
    public void kill() {
        super.kill();
    }
}
