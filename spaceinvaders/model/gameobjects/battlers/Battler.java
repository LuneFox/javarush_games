package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.Shooter;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;

import java.util.Optional;

public abstract class Battler extends GameObject implements Shooter {
    public Battler(double x, double y) {
        super(x, y);
    }

    public abstract Optional<Bullet> getAmmo();

    public void kill() {
        isAlive = false;
    }
}
