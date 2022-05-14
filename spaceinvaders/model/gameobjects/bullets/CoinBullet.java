package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

public class CoinBullet extends Bullet {
    public CoinBullet(double x, double y, Direction direction) {
        super(x, y, direction);
        setAnimatedView(Sprite.Loop.ENABLED, 1,
                ObjectShape.COIN_WIDTH_7,
                ObjectShape.COIN_WIDTH_5,
                ObjectShape.COIN_WIDTH_3,
                ObjectShape.COIN_WIDTH_1,
                ObjectShape.COIN_WIDTH_3,
                ObjectShape.COIN_WIDTH_5);
        canKillEnemies = true;
    }
}
