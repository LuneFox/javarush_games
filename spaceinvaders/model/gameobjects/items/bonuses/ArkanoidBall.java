package com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses;

import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.view.shapes.BonusShape;

import java.util.List;

/**
 * The arkanoid ball bounces around the field, killing tanks it meets, and ignores enemy bullets.
 */
public class ArkanoidBall extends Bonus {
    public ArkanoidBall(double x, double y) {
        super(x, y, BonusShape.ARKANOID_BALL, BonusShape.ARKANOID_BALL);
    }

    @Override
    protected void reactToBullets(List<Bullet> bullets) {
        // The ball is not affected by enemy bullets
    }

    @Override
    public void activate() {
        destroy();
        game.getMario().launchProjectile(BulletType.ARKANOID_BALL, BonusShape.ARKANOID_BALL);
    }
}
