package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.gameobjects.Shooter;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;

import java.util.Optional;

public abstract class Battler extends GameObject implements Shooter {
    protected int hitPoints;

    public Battler(double x, double y) {
        super(x, y);
    }

    public abstract Optional<Bullet> getAmmo();

    /**
     * Ammo is spawned at the shooter, but only a living shooter has somewhere to spawn it.
     * Mario, for instance, can lose his bonus (and with it the ammo type) when hit while shooting.
     */
    protected boolean canShoot() {
        return isAlive;
    }

    public void kill() {
        if (hitPoints > 1) {
            hitPoints--;
        } else {
            isAlive = false;
        }
    }
}
