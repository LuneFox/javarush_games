package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;

/**
 * A game object that withstands several hits before it is destroyed.
 */
public abstract class Battler extends GameObject {
    protected int hitPoints;

    public Battler(double x, double y) {
        super(x, y);
    }

    /**
     * Applies one hit: either takes a hit point away or destroys the battler.
     */
    public void takeHit() {
        if (hitPoints > 1) {
            hitPoints--;
        } else {
            destroy();
        }
    }
}
