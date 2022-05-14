package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

public class EnemyTankBullet extends Bullet {
    public EnemyTankBullet(double x, double y, Direction direction) {
        super(x, y, direction);
        setStaticView(ObjectShape.getRandomTetrisBullet());
    }
}
