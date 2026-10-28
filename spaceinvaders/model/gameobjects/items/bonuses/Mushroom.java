package com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses;

import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.view.shapes.BonusShape;
import com.javarush.games.spaceinvaders.view.shapes.FireballShape;

import java.util.List;

/** Гриб: восстанавливает здоровье при подборе, гасит пули и становится огненным шаром. */
public class Mushroom extends Bonus {
    /** Создаёт гриб. */
    public Mushroom(double x, double y) {
        super(x, y, copyOf(BonusShape.MUSHROOM), BonusShape.MUSHROOM_OVERHEAD_ICON);
    }

    /** Копирует картинку, чтобы гриб мог терять пиксели. */
    private static int[][] copyOf(int[][] shape) {
        int[][] copy = new int[shape.length][shape[0].length];
        for (int row = 0; row < shape.length; row++) {
            System.arraycopy(shape[row], 0, copy[row], 0, shape[row].length);
        }
        return copy;
    }

    /** Гасит попавшие пули и теряет пиксель. */
    @Override
    protected void reactToBullets(List<Bullet> bullets) {
        for (Bullet bullet : bullets) {
            if (!collidesWith(bullet, Mirror.NONE)) continue;

            bullet.destroy();
            eraseCollisionPixel();
        }
    }

    /** Запускает огненный шар. */
    @Override
    public void activate() {
        destroy();
        game.getMario().launchProjectile(BulletType.FIREBALL, FireballShape.FIREBALL_1);
    }
}
