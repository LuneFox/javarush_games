package com.javarush.games.spaceinvaders.model.gameobjects.bullets;

/** Создаёт снаряды по типу. */
public final class BulletFactory {
    /** Запрещает создание экземпляров. */
    private BulletFactory() {
    }

    /** Создаёт снаряд нужного типа. */
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
