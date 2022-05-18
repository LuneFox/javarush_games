package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.view.shapes.TankShape;

public class TetrisBullet extends Bullet {
    TetrisBullet(double x, double y) {
        super(x, y, Direction.DOWN);
        setStaticView(TankShape.getTetrisBullet());
    }
}
