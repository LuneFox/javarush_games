package com.javarush.games.spaceinvaders.gameobjects.decorations;

import com.javarush.games.spaceinvaders.shapes.DecoShape;

public class FloorTile extends Decoration{
    public FloorTile(double x, double y) {
        super(x, y);
        setMatrix(DecoShape.FLOOR);
    }
}
