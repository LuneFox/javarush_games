package com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses;

import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

import java.util.List;

public class Mushroom extends Bonus {
    public Mushroom(double x, double y) {
        super(x, y);
        setStaticView(getMushroomSpriteCopy());
        overheadIcon = new GameObject() {
            {
                setStaticView(ObjectShape.MUSHROOM_OVERHEAD_ICON);
            }
        };
    }

    private int[][] getMushroomSpriteCopy() {
        int[][] sprite = new int[ObjectShape.MUSHROOM.length][ObjectShape.MUSHROOM[0].length];
        for (int matrixY = 0; matrixY < ObjectShape.MUSHROOM.length; matrixY++) {
            System.arraycopy(ObjectShape.MUSHROOM[matrixY], 0, sprite[matrixY], 0, ObjectShape.MUSHROOM[0].length);
        }
        return sprite;
    }

    @Override
    public void verifyHit(List<Bullet> bullets) {
        bullets.forEach(bullet -> {
            if (collidesWith(bullet, Mirror.NONE)) {
                bullet.kill();
                eraseCollisionPixel();
            }
        });
    }

    @Override
    public void consume() {
        game.mario.getAmmo().ifPresent(bullet -> game.addPlayerBullet(bullet));
    }
}
