package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.games.spaceinvaders.shapes.ObjectShape;

public class Brick extends GameObject {
    public Brick(double x, double y) {
        super(x, y);
        setStaticView(ObjectShape.BRICK);
    }
}
