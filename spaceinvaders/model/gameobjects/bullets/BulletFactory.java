package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

import java.util.Optional;

public class BulletFactory {
    public static Optional<Bullet> getBullet(BulletType bulletType, double x, double y) {
        switch (bulletType) {
            case COIN:
                return Optional.of(new CoinBullet(x, y));
            case FIREBALL:
                return Optional.of(new FireballBullet(x, y));
            case TETRIS:
                return Optional.of(new TetrisBullet(x, y));
            case RACING_CAR:
                return Optional.of(new RacingCarBullet(x, y));
            default:
                return Optional.empty();
        }
    }
}
