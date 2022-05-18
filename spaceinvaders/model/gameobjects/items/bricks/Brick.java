package com.javarush.games.spaceinvaders.model.gameobjects.items.bricks;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.Shooter;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletFactory;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.view.shapes.BrickShape;

import java.util.Optional;

public class Brick extends GameObject implements Shooter {
    private static final int MAX_JUMP_ENERGY = 3;

    public Brick(double x, double y) {
        super(x, y);
        setStaticView(BrickShape.BRICK);
        jumpEnergy = 0;
    }

    public void verifyTouch(Mario mario) {
        if (!mario.isAlive) return;

        final Mirror mirror = (mario.getFaceDirection() == Direction.RIGHT) ? Mirror.NONE : Mirror.HORIZONTAL;
        if (this.collidesWith(mario, mirror)) {
            jump();
            shoot();
        }
    }

    public void move() {
        processJumping();
    }

    private void processJumping() {
        if (jumpEnergy > 0) {
            loseJumpEnergy();
            raise();
        } else if (isAboveBrickBase()) {
            descend();
        }
    }

    public void shoot() {
        Optional<Bullet> bulletOptional = getAmmo();
        bulletOptional.ifPresent(coin -> game.addPlayerBullet(coin));
    }

    public Optional<Bullet> getAmmo() {
        return BulletFactory.getBullet(BulletType.COIN, x + 2, y);
    }

    private void jump() {
        if (isAboveBrickBase()) return;
        jumpEnergy = MAX_JUMP_ENERGY;
    }

    private boolean isAboveBrickBase() {
        int height = SpaceInvadersGame.HEIGHT - 30 - BrickShape.BRICK.length;
        return y < height;
    }
}
