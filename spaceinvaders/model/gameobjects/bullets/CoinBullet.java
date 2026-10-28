package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.view.shapes.CoinShape;

/**
 * A coin knocked out of a brick. Spins while flying and kills a tank it meets.
 */
public class CoinBullet extends Bullet {
    private static final int FRAME_DELAY = 1;

    CoinBullet(double x, double y) {
        super(x, y, Direction.UP);
        setAnimatedView(Sprite.Loop.REPEAT, FRAME_DELAY,
                CoinShape.COIN_WIDTH_7,
                CoinShape.COIN_WIDTH_5,
                CoinShape.COIN_WIDTH_3,
                CoinShape.COIN_WIDTH_1,
                CoinShape.COIN_WIDTH_3,
                CoinShape.COIN_WIDTH_5);
        makeLethalToEnemies();
    }
}
