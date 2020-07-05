package com.javarush.games.spaceinvaders;

import com.javarush.games.spaceinvaders.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.shapes.DecoShape;

public abstract class Decoration extends GameObject {
    public Decoration(double x, double y) {
        super(x, y);
    }

    public static class FloorTile extends Decoration {
        public FloorTile(double x, double y) {
            super(x, y);
            setStaticView(DecoShape.FLOOR);
        }
    }

    public static class Cloud extends Decoration {
        public Cloud(double x, double y) {
            super(x, y);
            setStaticView(DecoShape.CLOUD);
        }
    }

    public static class Hill extends Decoration {
        public Hill(double x, double y) {
            super(x, y);
            setStaticView(DecoShape.HILL);
        }
    }

    public static class Bush extends Decoration{
        public Bush(double x, double y) {
            super(x, y);
            setStaticView(DecoShape.BUSH);
        }
    }
}

