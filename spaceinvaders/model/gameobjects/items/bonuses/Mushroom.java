package com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses;

import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.view.shapes.BonusShape;
import com.javarush.games.spaceinvaders.view.shapes.FireballShape;

import java.util.List;

/**
 * The mushroom absorbs enemy bullets, losing a pixel with every hit, and turns into a fireball.
 */
public class Mushroom extends Bonus {
    public Mushroom(double x, double y) {
        super(x, y, copyOf(BonusShape.MUSHROOM), BonusShape.MUSHROOM_OVERHEAD_ICON);
    }

    /**
     * The mushroom loses pixels when it absorbs hits, so it must not share its image with the shape.
     */
    private static int[][] copyOf(int[][] shape) {
        int[][] copy = new int[shape.length][shape[0].length];
        for (int row = 0; row < shape.length; row++) {
            System.arraycopy(shape[row], 0, copy[row], 0, shape[row].length);
        }
        return copy;
    }

    @Override
    protected void reactToBullets(List<Bullet> bullets) {
        for (Bullet bullet : bullets) {
            if (!collidesWith(bullet, Mirror.NONE)) continue;

            bullet.destroy();
            eraseCollisionPixel();
        }
    }

    @Override
    public void activate() {
        destroy();
        game.getMario().launchProjectile(BulletType.FIREBALL, FireballShape.FIREBALL_1);
    }
}
