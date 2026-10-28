package com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses;

import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.view.shapes.BonusShape;

import java.util.List;

/**
 * The star reflects enemy bullets back while it is carried, and turns all of them around at once
 * when it is used.
 */
public class Star extends Bonus {
    public Star(double x, double y) {
        super(x, y, BonusShape.STAR, BonusShape.STAR_OVERHEAD_ICON);
    }

    @Override
    protected void reactToBullets(List<Bullet> bullets) {
        for (Bullet bullet : bullets) {
            if (!collidesWith(bullet, Mirror.HORIZONTAL)) continue;
            if (!bullet.isCollisionCooledDown()) continue;

            bullet.reverseDirection();
            bullet.makeLethalToEnemies();
            bullet.markCollision();
        }
    }

    @Override
    public void activate() {
        destroy();
        game.getFlash().request();

        List<Bullet> enemyBullets = game.getEnemyBullets();
        Score.add(enemyBullets.size());
        for (Bullet bullet : enemyBullets) {
            bullet.reverseDirection();
            bullet.makeLethalToEnemies();
        }
    }
}
