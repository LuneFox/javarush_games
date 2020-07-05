package com.javarush.games.spaceinvaders;

import com.javarush.games.spaceinvaders.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.shapes.DecoShape;

abstract class Decoration extends GameObject {
    Decoration(double x, double y) {
        super(x, y);
    }

    static class FloorTile extends Decoration {
        FloorTile(double x, double y) {
            super(x, y);
            setStaticView(DecoShape.FLOOR);
        }
    }

    static class Cloud extends Decoration {
        Cloud(double x, double y) {
            super(x, y);
            setStaticView(DecoShape.CLOUD);
        }
    }

    static class Hill extends Decoration {
        Hill(double x, double y) {
            super(x, y);
            setStaticView(DecoShape.HILL);
        }
    }

    static class Bush extends Decoration{
        Bush(double x, double y) {
            super(x, y);
            setStaticView(DecoShape.BUSH);
        }
    }
}

