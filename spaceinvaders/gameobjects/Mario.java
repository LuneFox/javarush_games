package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.ShapeMatrix;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.shapes.MarioShape;

import java.util.List;

public class Mario extends Ship {
    private static final int JUMP_HEIGHT_LIMIT = 30;
    public boolean isJumping = false;
    public boolean isWalking = false;
    private Direction direction = Direction.UP;
    private Direction faceDirection = Direction.RIGHT;
    private int frameCounter = 0;
    private boolean reachedJumpTop;

    public Mario() {
        super(SpaceInvadersGame.WIDTH / 2.0, SpaceInvadersGame.HEIGHT - MarioShape.STAND.length);
        setStaticView(MarioShape.STAND);
    }

    // -------- BASIC ACTIONS

    public void move() {
        if (isAlive) {
            switch (direction) {
                case RIGHT:
                    this.x += 2;
                    break;
                case LEFT:
                    this.x -= 2;
                    break;
                default:
                    break;
            }
            controlJump();
            keepInBounds();
        }
    }

    public void jump() {
        isJumping = true;
        isWalking = false;
        reachedJumpTop = false;
    }

    @Override
    public Bullet fire() {
        if (!isAlive) {
            return null;
        }
        return new Bullet(x + 2, y - ShapeMatrix.BULLET.length, Direction.UP);
    }

    @Override
    public void kill() {
        if (!isAlive) {
            return;
        }
        isAlive = false;
        setStaticView(MarioShape.DEAD);
    }


    // -------- GRAPHICS

    public void draw(SpaceInvadersGame game) {
        switch (direction) {
            case RIGHT:
                faceDirection = Direction.RIGHT;
                break;
            case LEFT:
                faceDirection = Direction.LEFT;
                break;
            default:
                break;
        }
        boolean reverse = false;
        if (faceDirection == Direction.LEFT) {
            reverse = true;
        }
        super.draw(game, reverse);
    }

    @Override
    public void nextFrame() {
        frameCounter++;
        if (frameCounter == 2) {
            super.nextFrame();
            frameCounter = 0;
        }
    }


    // -------- UTILITIES

    public void keepInBounds() {
        if (x < -4) {
            x = -4;
        } else if (x + width > SpaceInvadersGame.WIDTH + 3) {
            x = SpaceInvadersGame.WIDTH - width + 4;
        }
    }

    public void controlJump() {
        Direction lastDirection = direction;
        if (isJumping) {
            setStaticView(MarioShape.JUMP);
            if (y > SpaceInvadersGame.HEIGHT - JUMP_HEIGHT_LIMIT && !reachedJumpTop) {
                this.y -= 2;
            } else {
                reachedJumpTop = true;
                this.y += 2;
                if (y >= SpaceInvadersGame.HEIGHT - height) {
                    y = SpaceInvadersGame.HEIGHT - height;
                    isJumping = false;
                    setDirection(lastDirection);
                }
            }
        }
    }

    public void win() {
        setStaticView(MarioShape.JUMP);
    }

    public void verifyHit(List<Bullet> bullets) {
        if (bullets.isEmpty()) {
            return;
        }

        bullets.forEach(bullet -> {
            if (isAlive && bullet.isAlive && isCollision(bullet)) {
                kill();
                bullet.kill();
            }
        });
    }


    // -------- GETTERS AND SETTERS

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction newDirection) {
        if ((newDirection == Direction.RIGHT) ||
                (newDirection == Direction.LEFT)) {
            if (!isJumping && !isWalking) {
                setAnimatedView(true,
                        MarioShape.WALK_3,
                        MarioShape.WALK_2,
                        MarioShape.WALK_1,
                        MarioShape.WALK_2);
                isWalking = true;
            }
        } else if (newDirection == Direction.UP) {
            setStaticView(MarioShape.STAND);
            isWalking = false;
        }
        if (newDirection != Direction.DOWN) {
            direction = newDirection;
        }
    }
}
