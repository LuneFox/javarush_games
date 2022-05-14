package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.FireBallBullet;
import com.javarush.games.spaceinvaders.model.gameobjects.items.QuestionBrick.Bonus;
import com.javarush.games.spaceinvaders.view.shapes.MarioShape;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

import java.util.*;

public class Mario extends Battler {
    public static final int JUMP_HEIGHT_LIMIT = 30;
    private static final int FLOOR_LEVEL = 4;
    private static final int WALK_SPEED = 2;
    private static final int JUMP_SPEED = 2;

    public Bonus bonus;
    private final Set<State> states;
    private Direction direction = Direction.NONE;
    private Direction facingDirection = Direction.RIGHT;
    public int faintCounter = 0;

    public enum State {
        STANDING, WALKING, JUMPING, BRAKING, DEAD, TOUCHED_CEILING
    }

    public Mario() {
        super(SpaceInvadersGame.WIDTH / 2.0, SpaceInvadersGame.HEIGHT - MarioShape.STAND.length - FLOOR_LEVEL);
        this.states = new HashSet<>();
        setStandingState();
    }

    // -------- BASIC ACTIONS

    public void move() {
        if (isAlive) {
            if (direction == Direction.RIGHT) this.x += WALK_SPEED;
            else if (direction == Direction.LEFT) this.x -= WALK_SPEED;
            controlJump();
            keepInBounds();
        } else {
            playGameOverAnimation();
        }
    }

    public void controlJump() {
        Direction lastDirection = direction;
        if (hasState(State.JUMPING)) {
            setJumpingState();
            if (y > getCeiling() && !hasState(State.TOUCHED_CEILING)) {
                this.y -= JUMP_SPEED;
            } else {
                states.add(State.TOUCHED_CEILING);
                this.y += JUMP_SPEED;
                if (y >= getFloor()) {
                    y = getFloor();
                    states.remove(State.JUMPING);
                    setDirection(lastDirection);
                }
            }
        }
    }

    public void jump() {
        states.add(State.JUMPING);
        states.remove(State.WALKING);
        states.remove(State.TOUCHED_CEILING);
    }

    private int getCeiling() {
        return SpaceInvadersGame.HEIGHT - JUMP_HEIGHT_LIMIT - FLOOR_LEVEL;
    }

    private int getFloor() {
        return SpaceInvadersGame.HEIGHT - getHeight() - FLOOR_LEVEL;
    }

    public void keepInBounds() {
        if (x < -4) {
            x = -4;
            if (!hasState(State.JUMPING)) {
                setBrakingState();
                states.add(State.BRAKING);
            }
        } else if (x + getWidth() > SpaceInvadersGame.WIDTH + 4) {
            x = SpaceInvadersGame.WIDTH - getWidth() + 4;
            if (!hasState(State.JUMPING)) {
                setBrakingState();
                states.add(State.BRAKING);
            }
        }
    }

    private void playGameOverAnimation() {
        setStaticView(MarioShape.DEAD);
        if (faintCounter < 5) {
            y -= 2;
            faintCounter++;
        } else if (faintCounter < 20) {
            y += 3;
            faintCounter++;
        }
    }

    @Override
    public Optional<Bullet> fire() {
        if (!isAlive || bonus == null || !bonus.getClass().getName().contains("Mushroom")) {
            return Optional.empty();
        }
        bonus = null;
        return Optional.of(new FireBallBullet(
                (x + getWidth() / 2.0) - (ObjectShape.FIREBALL_1.length / 2.0),
                y - ObjectShape.FIREBALL_1.length + 4));
    }

    public boolean wipeEnemyBullets() {
        if (!isAlive || bonus == null || !bonus.getClass().getName().contains("Star")) {
            return false;
        }
        bonus = null;
        return true;
    }

    @Override
    public void kill() {
        if (!isAlive) return;
        isAlive = false;
        setDeadState();
    }

    // -------- GRAPHICS

    @Override
    public void draw() {
        if (direction == Direction.RIGHT) facingDirection = Direction.RIGHT;
        else if (direction == Direction.LEFT) facingDirection = Direction.LEFT;
        super.draw(facingDirection == Direction.LEFT ? Mirror.HORIZONTAL : Mirror.NONE);
    }

    // -------- UTILITIES

    public void verifyHit(List<Bullet> bullets) {
        bullets.forEach(this::destroyMarioAndBulletOnCollision);
    }

    private void destroyMarioAndBulletOnCollision(Bullet bullet) {
        Mirror mirror = (facingDirection == Direction.RIGHT) ? Mirror.NONE : Mirror.HORIZONTAL;
        if (!isAlive) return;
        if (!bullet.isAlive) return;
        if (!collidesWith(bullet, mirror)) return;

        kill();
        bullet.kill();
    }

    public void win() {
        setJumpingState();
    }

    public void collect(Bonus bonus) {
        this.bonus = bonus;
    }

    // -------- GETTERS AND SETTERS


    public void setDirection(Direction newDirection) {
        if ((newDirection == Direction.RIGHT) || (newDirection == Direction.LEFT)) {
            if (!hasState(State.JUMPING) && !hasState(State.WALKING) || hasState(State.BRAKING)) {
                setWalkingState();
            }
        } else if (newDirection == Direction.NONE) {
            setStandingState();
        }
        direction = newDirection;
    }

    /*
     * States
     */

    private void setStandingState() {
        states.add(State.STANDING);
        states.remove(State.WALKING);
        setStaticView(MarioShape.STAND);
    }

    private void setWalkingState() {
        states.add(State.WALKING);
        states.remove(State.BRAKING);
        setAnimatedView(Sprite.Loop.ENABLED, 2,
                MarioShape.WALK_3,
                MarioShape.WALK_2,
                MarioShape.WALK_1,
                MarioShape.WALK_2);
    }

    private void setJumpingState() {
        states.add(State.JUMPING);
        setStaticView(MarioShape.JUMP);
    }

    private void setBrakingState() {
        states.add(State.BRAKING);
        setStaticView(MarioShape.BRAKE);
    }

    private void setDeadState() {
        states.retainAll(Collections.singletonList(State.DEAD));
        setStaticView(MarioShape.DEAD);
    }

    /*
     * Getters
     */

    public Direction getDirection() {
        return direction;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public boolean hasState(State state) {
        return states.contains(state);
    }
}
