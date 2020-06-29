package com.javarush.games.spaceinvaders.gameobjects.decoration;

import com.javarush.games.spaceinvaders.shapes.DecoShape;

public class Bush extends Decoration{
    public Bush(double x, double y) {
        super(x, y);
        setStaticView(DecoShape.BUSH);
    }
}
