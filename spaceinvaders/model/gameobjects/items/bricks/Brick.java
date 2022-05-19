package com.javarush.games.spaceinvaders.model.gameobjects.items.bricks;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.Movable;
import com.javarush.games.spaceinvaders.model.gameobjects.Shooter;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.JumpHelper;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletFactory;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.view.shapes.BrickShape;

import java.util.Optional;

public class Brick extends GameObject implements Shooter, Movable {
    private JumpHelper jumpHelper;

    public Brick(double x, double y) {
        super(x, y);
        setStaticView(BrickShape.BRICK);
        configureJumpHelper();
    }

    private void configureJumpHelper() {
        jumpHelper = new JumpHelper(this);
        jumpHelper.setFloorLevel(SpaceInvadersGame.HEIGHT - 30 - BrickShape.BRICK.length);
    }

    public void verifyTouch(Mario mario) {
        if (!mario.isAlive) return;

        final Mirror mirror = (mario.getFaceDirection() == Direction.RIGHT) ? Mirror.NONE : Mirror.HORIZONTAL;
        if (this.collidesWith(mario, mirror)) {
            jumpHelper.initJump();
            shoot();
        }
    }

    public void move() {
        if (game.isStopped()) return;
        jumpHelper.progressJump();
    }

    public void shoot() {
        Optional<Bullet> bulletOptional = getAmmo();
        bulletOptional.ifPresent(coin -> game.addPlayerBullet(coin));
    }

    public Optional<Bullet> getAmmo() {
        return BulletFactory.getBullet(BulletType.COIN, x + 2, y);
    }
}
