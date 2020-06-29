package com.javarush.games.spaceinvaders.gameobjects.item;

import com.javarush.games.spaceinvaders.gameobjects.ammo.Bullet;
import com.javarush.games.spaceinvaders.gameobjects.brick.QuestionBrick;
import com.javarush.games.spaceinvaders.shapes.ObjectShape;

import java.util.List;

public class Mushroom extends Bonus {
    public Mushroom(double x, double y, QuestionBrick brick) {
        super(x, y, brick);
        setStaticView(ObjectShape.MUSHROOM);
    }

    public void verifyHit(List<Bullet> bullets) {
        bullets.forEach(bullet -> {
            if (isCollision(bullet, true)) {
                bullet.kill();
            }
        });
    }
}
