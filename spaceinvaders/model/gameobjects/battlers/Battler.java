package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.Shooter;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;

import java.util.Optional;

public abstract class Battler extends GameObject implements Shooter {
    protected static SpaceInvadersGame game;

    public Battler(double x, double y) {
        super(x, y);
    }

    public abstract Optional<Bullet> getAmmo();

    public void kill() {
        isAlive = false;
    }

    public static void setGame(SpaceInvadersGame game) {
        Battler.game = game;
    }
}
