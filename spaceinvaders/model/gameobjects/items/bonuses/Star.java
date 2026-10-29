package com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses;

import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.view.shapes.BonusShape;

import java.util.List;

/** Звёздочка: отражает пули, отталкивает танки и замораживает их; красные пули не отражаются. */
public class Star extends Bonus {
    /** Создаёт звёздочку. */
    public Star(double x, double y) {
        super(x, y, BonusShape.STAR, BonusShape.STAR_OVERHEAD_ICON);
    }

    /** Отражает попавшую отражаемую пулю. */
    @Override
    protected void reactToBullets(List<Bullet> bullets) {
        for (Bullet bullet : bullets) {
            if (!bullet.isReflectable()) continue;
            // Пуля рисуется без отражения, поэтому и хитбокс у неё обычный
            if (!collidesWith(bullet, Mirror.NONE)) continue;
            if (!bullet.isCollisionCooledDown()) continue;

            bullet.reverseDirection();
            bullet.makeLethalToEnemies();
            bullet.markCollision();
        }
    }

    /** Разворачивает пули, отталкивает танки и включает вспышку. */
    @Override
    public void activate() {
        destroy();
        game.getFlash().request();
        game.getEnemyArmy().pushBackAndFreeze();

        List<Bullet> enemyBullets = game.getEnemyBullets();
        int reflectedCount = 0;
        for (Bullet bullet : enemyBullets) {
            if (!bullet.isReflectable()) continue;

            bullet.reverseDirection();
            bullet.makeLethalToEnemies();
            reflectedCount++;
        }
        Score.add(reflectedCount);
    }
}
