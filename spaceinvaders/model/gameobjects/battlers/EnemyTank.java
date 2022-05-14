package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.EnemyTankBullet;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

import java.util.Optional;

public class EnemyTank extends Battler {
    protected int score;

    public EnemyTank(double x, double y) {
        super(x, y);
        score = 15;
        setAnimatedView(Sprite.Loop.ENABLED, 5,
                ObjectShape.TANK_1,
                ObjectShape.TANK_2
        );
    }

    public void move(Direction direction, double speed) {
        if (direction == Direction.RIGHT) x += speed;
        else if (direction == Direction.LEFT) x -= speed;
        else if (direction == Direction.DOWN) y += 2;
    }

    @Override
    public Optional<Bullet> fire() {
        return Optional.of(new EnemyTankBullet(x + 2, y + getHeight(), Direction.DOWN));
    }

    @Override
    public void kill() {
        if (!isAlive) return;
        super.kill();
        Score.add(score);
        setAnimatedView(Sprite.Loop.DISABLED, 1,
                ObjectShape.TANK_KILL_1,
                ObjectShape.TANK_KILL_2,
                ObjectShape.TANK_KILL_3
        );
    }
}
