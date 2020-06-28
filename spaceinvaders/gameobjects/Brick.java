package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.ShapeMatrix;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.shapes.ObjectShape;

public class Brick extends GameObject {
    private static final int JUMP_HEIGHT = 2;
    private int jumpPower;
    private boolean jumped;
    private boolean touched = false;

    public Brick(double x, double y) {
        super(x, y);
        setStaticView(ObjectShape.BRICK);
        jumpPower = JUMP_HEIGHT;
    }

    public void verifyTouch(Mario mario, SpaceInvadersGame game) {
        if (mario.getFaceDirection() == Direction.RIGHT) {
            if (this.isCollision(mario, false)) {
                this.touched = true;
                jump(game);
            }
        } else if (mario.getFaceDirection() == Direction.LEFT) {
            if (this.isCollision(mario, true)) {
                this.touched = true;
                jump(game);
            }
        }
    }

    public void jump(SpaceInvadersGame game) {
        if (touched) {
            if (!jumped && jumpPower >= 0) {
                this.y--;
                jumpPower--;
                if (jumpPower <= 0) {
                    jumped = true;
                    game.addBullet(fire());
                }
            } else if (jumpPower < JUMP_HEIGHT) {
                this.y++;
                jumpPower++;
                if (jumpPower == JUMP_HEIGHT) {
                    jumped = false;
                    touched = false;
                }
            }
        }
    }

    public Bullet fire() {
        return new Coin(x + 2, y, Direction.UP);
    }

}
