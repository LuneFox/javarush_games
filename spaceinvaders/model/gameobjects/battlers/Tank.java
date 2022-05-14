package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;

import java.util.Optional;

public abstract class Tank extends GameObject {
    protected int score;

    public Tank(double x, double y) {
        super(x, y);
    }

    public void move(Direction direction, double speed) {
        if (direction == Direction.RIGHT) x += speed;
        else if (direction == Direction.LEFT) x -= speed;
        else if (direction == Direction.DOWN) y += 2;
    }

    public abstract Optional<Bullet> fire();

    public void kill() {
        Score.add(score);
        isAlive = false;
    }
}
