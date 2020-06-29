package com.javarush.games.spaceinvaders.gameobjects.decoration;

import com.javarush.games.spaceinvaders.shapes.DecoShape;

public class Cloud extends Decoration {
    public Cloud(double x, double y) {
        super(x, y);
        setStaticView(DecoShape.CLOUD);
    }
}
