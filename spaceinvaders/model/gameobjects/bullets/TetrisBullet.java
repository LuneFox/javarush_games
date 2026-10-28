package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.view.shapes.TankShape;

/** Фигурка тетриса, которой стреляет танк. */
public class TetrisBullet extends Bullet {
    /** Создаёт фигурку. */
    TetrisBullet(double x, double y) {
        super(x, y, Direction.DOWN);
        setStaticView(TankShape.getTetrisBullet());
    }
}
