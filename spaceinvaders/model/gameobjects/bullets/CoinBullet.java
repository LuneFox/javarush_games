package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.view.shapes.CoinShape;

public class CoinBullet extends Bullet {
    public CoinBullet(double x, double y, Direction direction) {
        super(x, y, direction);
        setAnimatedView(Sprite.Loop.ENABLED, 1,
                CoinShape.COIN_WIDTH_7,
                CoinShape.COIN_WIDTH_5,
                CoinShape.COIN_WIDTH_3,
                CoinShape.COIN_WIDTH_1,
                CoinShape.COIN_WIDTH_3,
                CoinShape.COIN_WIDTH_5);
        canKillEnemies = true;
    }
}
