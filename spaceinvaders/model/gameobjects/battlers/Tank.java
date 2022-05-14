package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;

import java.util.Optional;

public class Tank extends GameObject {

    public Tank(double x, double y) {
        super(x, y);
    }

    public Optional<Bullet> fire() {
        return Optional.empty();
    }

    public void kill() {
        isAlive = false;
    }
}
