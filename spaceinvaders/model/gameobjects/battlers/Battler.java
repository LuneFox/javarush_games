package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;

import java.util.Optional;

public abstract class Battler extends GameObject {
    public Battler(double x, double y) {
        super(x, y);
    }

    public abstract Optional<Bullet> fire();

    public void kill() {
        isAlive = false;
    }
}
