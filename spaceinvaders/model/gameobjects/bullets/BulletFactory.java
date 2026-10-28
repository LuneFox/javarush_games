package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

/**
 * The only place that knows which class implements a bullet type.
 */
public final class BulletFactory {
    private BulletFactory() {
    }

    public static Bullet create(BulletType type, double x, double y) {
        switch (type) {
            case COIN:
                return new CoinBullet(x, y);
            case FIREBALL:
                return new FireballBullet(x, y);
            case ARKANOID_BALL:
                return new ArkanoidBallBullet(x, y);
            case TETRIS:
                return new TetrisBullet(x, y);
            case RACING_CAR:
                return new RacingCarBullet(x, y);
            default:
                throw new IllegalArgumentException("Unknown bullet type: " + type);
        }
    }
}
