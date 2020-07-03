package com.javarush.games.spaceinvaders.gameobjects.decoration;

import com.javarush.games.spaceinvaders.shapes.DecoShape;

public class FloorTile extends Decoration {
    public FloorTile(double x, double y) {
        super(x, y);
        setStaticView(DecoShape.FLOOR);
    }
}
