package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;

/** Боец, который выдерживает несколько попаданий. */
public abstract class Battler extends GameObject {
    /** Оставшиеся очки прочности. */
    protected int hitPoints;

    /** Создаёт бойца в заданной точке. */
    public Battler(double x, double y) {
        super(x, y);
    }

    /** Применяет попадание: снимает прочность или уничтожает. */
    public void takeHit() {
        if (hitPoints > 1) {
            hitPoints--;
        } else {
            destroy();
        }
    }
}
