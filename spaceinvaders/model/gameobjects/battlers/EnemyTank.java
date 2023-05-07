package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletFactory;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.view.shapes.TankShape;

import java.util.Optional;

public class EnemyTank extends Battler {
    protected int scoreForKill;
    protected int[][] MOVE_1;
    protected int[][] MOVE_2;
    protected int[][] KILL_1;
    protected int[][] KILL_2;
    protected int[][] KILL_3;
    private boolean wasHit;     // for correct animation

    public EnemyTank(double x, double y) {
        super(x, y);
        setAnimationSprites();
        setDefaultAnimation();
        setHitPointsAndScore();
    }

    protected void setAnimationSprites() {
        MOVE_1 = TankShape.TANK_1;
        MOVE_2 = TankShape.TANK_2;
        KILL_1 = TankShape.TANK_KILL_1;
        KILL_2 = TankShape.TANK_KILL_2;
        KILL_3 = TankShape.TANK_KILL_3;
    }

    protected void setHitPointsAndScore() {
        hitPoints = (SpaceInvadersGame.getStage() / 5) + 1;
        scoreForKill = 9 + hitPoints * SpaceInvadersGame.getStage();
    }

    public void move(Direction direction, double speed) {
        if (isAlive && wasHit) {
            setDefaultAnimation();
            wasHit = false;
        }
        if (direction == Direction.RIGHT) x += speed;
        else if (direction == Direction.LEFT) x -= speed;
        else if (direction == Direction.DOWN) y += 2;
    }

    protected void setDefaultAnimation() {
        setAnimatedView(Sprite.Loop.ENABLED, 5, MOVE_1, MOVE_2);
    }

    @Override
    public void shoot() {
        Optional<Bullet> bulletOptional = getAmmo();
        if (bulletOptional.isPresent()) {
            Bullet bullet = bulletOptional.get();
            game.getEnemyBullets().add(bullet);
        }
    }

    @Override
    public Optional<Bullet> getAmmo() {
        int chanceToFire = game.getRandomNumber((100 / SpaceInvadersGame.getStage()) + 1);
        if (chanceToFire != 0) return Optional.empty();

        return BulletFactory.getBullet(BulletType.TETRIS, x + 2, y + getHeight());
    }

    @Override
    public void kill() {
        if (!isAlive) return;

        super.kill();

        if (!isAlive) {
            Score.add(scoreForKill);
        }

        setHitAnimation();
    }

    protected void setHitAnimation() {
        if (isAlive) {
            setStaticView(KILL_1);
            wasHit = true;
        } else {
            setAnimatedView(Sprite.Loop.DISABLED, 1, KILL_1, KILL_2, KILL_3);
        }
    }
}
