package com.javarush.games.spaceinvaders.gameobjects.battlers;

import com.javarush.games.spaceinvaders.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.gameobjects.ammo.Bullet;

public class Ship extends GameObject {


    public Ship(double x, double y) {
        super(x, y);
    }


    // -------- BASIC ACTIONS

    public Bullet fire() {
        return null;
    }

    public void kill() {
        isAlive = false;
    }
}
