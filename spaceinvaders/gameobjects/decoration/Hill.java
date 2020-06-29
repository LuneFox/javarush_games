package com.javarush.games.spaceinvaders.gameobjects.decoration;

import com.javarush.games.spaceinvaders.shapes.DecoShape;

public class Hill extends Decoration {
    public Hill(double x, double y) {
        super(x, y);
        setStaticView(DecoShape.HILL);
    }
}
