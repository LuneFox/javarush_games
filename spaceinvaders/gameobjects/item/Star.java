package com.javarush.games.spaceinvaders.gameobjects.item;

import com.javarush.games.spaceinvaders.Bullet;
import com.javarush.games.spaceinvaders.gameobjects.brick.QuestionBrick;
import com.javarush.games.spaceinvaders.shapes.ObjectShape;

import java.util.List;

public class Star extends Bonus {
    public Star(double x, double y, QuestionBrick brick) {
        super(x, y, brick);
        setStaticView(ObjectShape.STAR);
    }

    @Override
    public void verifyHit(List<Bullet> bullets) {
        bullets.forEach(bullet -> {
            if (isCollision(bullet, true)) {
                bullet.changeDirection();
                bullet.deadlyForEnemies = true;
            }
        });
    }
}
