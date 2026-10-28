package com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses;

import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.view.shapes.BonusShape;

import java.util.List;

/** Шар арканоида как бонус. */
public class ArkanoidBall extends Bonus {
    /** Создаёт бонус-шар. */
    public ArkanoidBall(double x, double y) {
        super(x, y, BonusShape.ARKANOID_BALL, BonusShape.ARKANOID_BALL);
    }

    /** Шар не реагирует на пули. */
    @Override
    protected void reactToBullets(List<Bullet> bullets) {
        // Шар не реагирует на вражеские пули
    }

    /** Запускает шар. */
    @Override
    public void activate() {
        destroy();
        game.getMario().launchProjectile(BulletType.ARKANOID_BALL, BonusShape.ARKANOID_BALL);
    }
}
