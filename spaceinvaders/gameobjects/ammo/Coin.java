package com.javarush.games.spaceinvaders.gameobjects.ammo;

import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.shapes.ObjectShape;

public class Coin extends Bullet {
    public Coin(double x, double y, Direction direction) {
        super(x, y, direction);
        setAnimatedView(true,
                ObjectShape.COIN_WIDTH_7,
                ObjectShape.COIN_WIDTH_5,
                ObjectShape.COIN_WIDTH_3,
                ObjectShape.COIN_WIDTH_1,
                ObjectShape.COIN_WIDTH_3,
                ObjectShape.COIN_WIDTH_5);
    }
}
