package com.javarush.games.spaceinvaders.gameobjects.battlers;

import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.ShapeMatrix;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.gameobjects.ammo.Bullet;
import com.javarush.games.spaceinvaders.gameobjects.ammo.Coin;
import com.javarush.games.spaceinvaders.gameobjects.ammo.FireBall;
import com.javarush.games.spaceinvaders.gameobjects.item.Bonus;
import com.javarush.games.spaceinvaders.shapes.MarioShape;
import com.javarush.games.spaceinvaders.shapes.ObjectShape;

import java.util.List;

public class Mario extends Ship {
    public static final int JUMP_HEIGHT_LIMIT = 30;
    private static final int FLOOR_LEVEL = 4;

    public Bonus bonus;
    public boolean isJumping = false;
    private boolean isWalking = false;
    private boolean isBraking = false;
    private Direction direction = Direction.UP;
    private Direction faceDirection = Direction.RIGHT;
    private int frameCounter = 0;
    private boolean reachedJumpTop;

    public Mario() {
        super(SpaceInvadersGame.WIDTH / 2.0, SpaceInvadersGame.HEIGHT - MarioShape.STAND.length - FLOOR_LEVEL);
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
        if (!isAlive || bonus == null || !bonus.getClass().getName().contains("Mushroom")) {
            return null;
        }
        bonus = null;
        return new FireBall(
                x + width / 2.0 - ObjectShape.FIREBALL_1.length / 2.0,
                y - ObjectShape.FIREBALL_1.length, Direction.UP);
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
            if (!isJumping) {
                setStaticView(MarioShape.BRAKE);
                isBraking = true;
            }
        } else if (x + width > SpaceInvadersGame.WIDTH + 4) {
            x = SpaceInvadersGame.WIDTH - width + 4;
            if (!isJumping) {
                setStaticView(MarioShape.BRAKE);
                isBraking = true;
            }
        }
    }

    public void controlJump() {
        Direction lastDirection = direction;
        if (isJumping) {
            setStaticView(MarioShape.JUMP);
            if (y > SpaceInvadersGame.HEIGHT - JUMP_HEIGHT_LIMIT - FLOOR_LEVEL && !reachedJumpTop) {
                this.y -= 2;
            } else {
                reachedJumpTop = true;
                this.y += 2;
                if (y >= SpaceInvadersGame.HEIGHT - height - FLOOR_LEVEL) {
                    y = SpaceInvadersGame.HEIGHT - height - FLOOR_LEVEL;
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

        if (faceDirection == Direction.RIGHT) {
            bullets.forEach(bullet -> {
                if (isAlive && bullet.isAlive && isCollision(bullet, false)) {
                    kill();
                    bullet.kill();
                }
            });
        } else if (faceDirection == Direction.LEFT) {
            bullets.forEach(bullet -> {
                if (isAlive && bullet.isAlive && isCollision(bullet, true)) {
                    kill();
                    bullet.kill();
                }
            });
        }

    }

    public void collect(Bonus bonus) {
        this.bonus = bonus;
    }


    // -------- GETTERS AND SETTERS

    public Direction getDirection() {
        return direction;
    }

    public Direction getFaceDirection() {
        return faceDirection;
    }

    public void setDirection(Direction newDirection) {
        if ((newDirection == Direction.RIGHT) || (newDirection == Direction.LEFT)) {
            if (!isJumping && !isWalking || isBraking) {
                setAnimatedView(true,
                        MarioShape.WALK_3,
                        MarioShape.WALK_2,
                        MarioShape.WALK_1,
                        MarioShape.WALK_2);
                isWalking = true;
                isBraking = false;
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
