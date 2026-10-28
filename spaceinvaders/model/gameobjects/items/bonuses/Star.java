package com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses;

import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.view.shapes.BonusShape;

import java.util.List;

/** Звёздочка: отражает пули и разворачивает их все. */
public class Star extends Bonus {
    /** Создаёт звёздочку. */
    public Star(double x, double y) {
        super(x, y, BonusShape.STAR, BonusShape.STAR_OVERHEAD_ICON);
    }

    /** Отражает попавшую пулю. */
    @Override
    protected void reactToBullets(List<Bullet> bullets) {
        for (Bullet bullet : bullets) {
            // Пуля рисуется без отражения, поэтому и хитбокс у неё обычный
            if (!collidesWith(bullet, Mirror.NONE)) continue;
            if (!bullet.isCollisionCooledDown()) continue;

            bullet.reverseDirection();
            bullet.makeLethalToEnemies();
            bullet.markCollision();
        }
    }

    /** Разворачивает все вражеские пули и включает вспышку. */
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
