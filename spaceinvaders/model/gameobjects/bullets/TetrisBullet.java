package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.view.shapes.TankShape;

/**
 * A tetromino dropped by a regular tank.
 */
public class TetrisBullet extends Bullet {
    TetrisBullet(double x, double y) {
        super(x, y, Direction.DOWN);
        setStaticView(TankShape.getTetrisBullet());
    }
}
