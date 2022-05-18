package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.EnemyTankBullet;
import com.javarush.games.spaceinvaders.view.shapes.TankShape;

import java.util.Optional;

public class EnemyTank extends Battler {
    protected int score;

    public EnemyTank(double x, double y) {
        super(x, y);
        score = 15;
        setAnimatedView(Sprite.Loop.ENABLED, 5,
                TankShape.TANK_1,
                TankShape.TANK_2
        );
    }

    public void move(Direction direction, double speed) {
        if (direction == Direction.RIGHT) x += speed;
        else if (direction == Direction.LEFT) x -= speed;
        else if (direction == Direction.DOWN) y += 2;
    }

    @Override
    public void shoot() {
        Optional<Bullet> bulletOptional = getAmmo();
        if (bulletOptional.isPresent()) {
            Bullet bullet = bulletOptional.get();
            game.enemyBullets.add(bullet);
        }
    }

    @Override
    public Optional<Bullet> getAmmo() {
        int chanceToFire = game.getRandomNumber(100 / SpaceInvadersGame.DIFFICULTY);
        if (chanceToFire != 0) return Optional.empty();

        return Optional.of(new EnemyTankBullet(x + 2, y + getHeight(), Direction.DOWN));
    }

    @Override
    public void kill() {
        if (!isAlive) return;
        super.kill();
        Score.add(score);
        setAnimatedView(Sprite.Loop.DISABLED, 1,
                TankShape.TANK_KILL_1,
                TankShape.TANK_KILL_2,
                TankShape.TANK_KILL_3
        );
    }
}
