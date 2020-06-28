package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.ShapeMatrix;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.shapes.MarioShape;

import java.util.List;

public class PlayerShip extends Ship {
    private Direction direction = Direction.UP;
    private Direction faceDirection = Direction.RIGHT;

    public PlayerShip() {
        super(SpaceInvadersGame.WIDTH / 2.0, SpaceInvadersGame.HEIGHT - MarioShape.STAND.length);
        setStaticView(MarioShape.STAND);
    }

    // -------- BASIC ACTIONS

    public void move() {
        if (isAlive) {
            switch (direction) {
                case RIGHT:
                    this.x++;
                    break;
                case LEFT:
                    this.x--;
                    break;
                default:
                    break;
            }

            if (x < 0) {
                x = 0;
            } else if (x + width > SpaceInvadersGame.WIDTH - 1) {
                x = SpaceInvadersGame.WIDTH - width;
            }
        }
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
        setAnimatedView(false,
                ShapeMatrix.KILL_PLAYER_ANIMATION_FIRST,
                ShapeMatrix.KILL_PLAYER_ANIMATION_SECOND,
                ShapeMatrix.KILL_PLAYER_ANIMATION_THIRD,
                ShapeMatrix.DEAD_PLAYER
        );
    }

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

    // -------- UTILITIES

    public void win() {
        setStaticView(ShapeMatrix.WIN_PLAYER);
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
        if (newDirection != Direction.DOWN) {
            direction = newDirection;
        }
    }
}
