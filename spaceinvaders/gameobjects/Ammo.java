package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.shapes.ObjectShape;

public class Ammo extends Bullet {
    public Ammo(double x, double y, Direction direction) {
        super(x, y, direction);
        setStaticView(ObjectShape.BOSS_TANK_AMMO);
    }
}
