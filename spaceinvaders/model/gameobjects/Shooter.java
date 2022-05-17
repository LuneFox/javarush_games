package com.javarush.games.spaceinvaders.model.gameobjects;

import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;

import java.util.Optional;

public interface Shooter {
    Optional<Bullet> fire();
}
