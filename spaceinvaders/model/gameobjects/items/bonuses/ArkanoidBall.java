package com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses;

import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.view.shapes.BonusShape;

import java.util.List;

public class ArkanoidBall extends Bonus {
    public ArkanoidBall(double x, double y) {
        super(x, y);
        setStaticView(BonusShape.ARKANOID_BALL);
        buildJumpHelper();
        overheadIcon = new GameObject() {
            {
                setStaticView(BonusShape.ARKANOID_BALL);
            }
        };

        bulletType = BulletType.ARKANOID_BALL;
    }

    @Override
    protected void verifyHit(List<Bullet> bullets) {

    }

    @Override
    public void consume() {
        game.getMario().getAmmo().ifPresent(bullet -> game.addPlayerBullet(bullet));
    }
}
