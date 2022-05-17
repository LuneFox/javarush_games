package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.engine.cell.Key;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.controller.Control;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.FireballBullet;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses.Bonus;
import com.javarush.games.spaceinvaders.view.shapes.MarioShape;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

import java.util.List;
import java.util.Optional;

public class Mario extends Battler {
    private static final int MAX_JUMP_ENERGY = 5;
    private static final double START_SPEED = 0.6;
    private static final double MAX_SPEED = 2.4;
    private static final double ACCELERATION = 0.2;
    private static final double JUMP_RAISE_SPEED = 4;
    private static final double JUMP_DESCEND_SPEED = 2;
    private static final double JUMP_BOUND = 66;

    private final int leftWalkingBound;
    private final int rightWalkingBound;
    private Animation animation;
    private Direction moveDirection = Direction.NONE;
    private Direction faceDirection = Direction.RIGHT;
    public Bonus bonus;
    public int finalAnimationCounter;
    private double speed;

    public enum Animation {
        STANDING, WALKING, JUMPING, BRAKING, DEAD
    }

    public Mario() {
        super(marioSpawnX(), marioSpawnY());
        setStandingAnimation();
        finalAnimationCounter = 0;
        jumpEnergy = 0;
        leftWalkingBound = -4;
        rightWalkingBound = SpaceInvadersGame.WIDTH - getWidth() + 4;
        speed = 0;
    }

    private static double marioSpawnX() {
        return SpaceInvadersGame.WIDTH / 2.0 - MarioShape.STAND[0].length / 2.0;
    }

    private static int marioSpawnY() {
        return SpaceInvadersGame.HEIGHT - SpaceInvadersGame.FLOOR_HEIGHT - MarioShape.STAND.length;
    }

    public void move() {
        if (isAlive) {
            processWalking();
            processWalkingBounds();
            processJumping();
        } else {
            playDeathAnimation();
        }
    }

    private void processWalking() {
        changeSpeed();
        selectAnimationAccordingToSpeed();
        brakeOnReverseDirectionInput();
        x += speed;
    }

    private void changeSpeed() {
        if (moveDirection == Direction.RIGHT) {
            if (speed == 0) speed = START_SPEED;
            if (speed < MAX_SPEED) speed += ACCELERATION;
        } else if (moveDirection == Direction.LEFT) {
            if (speed == 0) speed = -START_SPEED;
            if (speed > -MAX_SPEED) speed -= ACCELERATION;
        } else if (moveDirection == Direction.NONE) {
            speed += ((speed > 0) ? -ACCELERATION : ACCELERATION);
            if (Math.abs(speed) < 0.5) speed = 0;
        }
    }

    private void selectAnimationAccordingToSpeed() {
        if (Math.abs(speed) > ACCELERATION) {
            setWalkingAnimation();
        } else {
            setStandingAnimation();
        }
    }

    private void brakeOnReverseDirectionInput() {
        if (moveDirection == Direction.RIGHT && speed < ACCELERATION) {
            speed += ACCELERATION / 2;
            setBrakingAnimation();
        }
        if (moveDirection == Direction.LEFT && speed > -ACCELERATION) {
            speed -= ACCELERATION / 2;
            setBrakingAnimation();
        }
    }

    public void processWalkingBounds() {
        if (x < leftWalkingBound) {
            x = leftWalkingBound;
        } else if (x > rightWalkingBound) {
            x = rightWalkingBound;
        }
    }

    public void processJumping() {
        if (jumpEnergy > 0) {
            setJumpingAnimation();
            loseJumpEnergy();
            raise();
        } else if (isAboveFloor()) {
            setJumpingAnimation();
            descend();
        }
    }

    @Control(Key.UP)
    public void jump() {
        if (isAboveFloor()) return;
        jumpEnergy = MAX_JUMP_ENERGY;
    }

    private boolean isAboveFloor() {
        return y < (SpaceInvadersGame.HEIGHT - getHeight() - SpaceInvadersGame.FLOOR_HEIGHT);
    }

    @Override
    protected void raise() {
        y -= JUMP_RAISE_SPEED;
        if (y < JUMP_BOUND) y = JUMP_BOUND;
    }

    @Override
    protected void descend() {
        y += JUMP_DESCEND_SPEED;
    }

    private void playDeathAnimation() {
        setDeadAnimation();
        if (finalAnimationCounter < 30) finalAnimationCounter++;
        if (finalAnimationCounter < 10) return;
        if (finalAnimationCounter < 15) y -= 2;
        else if (finalAnimationCounter < 30) y += 3;
    }

    public void playVictoryAnimation() {
        if (finalAnimationCounter % 20 < 10) {
            setJumpingAnimation();
        } else {
            setStandingAnimation();
        }
        finalAnimationCounter++;
    }

    private void setStandingAnimation() {
        if (animation == Animation.STANDING) return;
        animation = Animation.STANDING;
        setStaticView(MarioShape.STAND);
    }

    private void setWalkingAnimation() {
        if (animation == Animation.WALKING) return;
        animation = Animation.WALKING;
        setAnimatedView(Sprite.Loop.ENABLED, 2,
                MarioShape.WALK_3,
                MarioShape.WALK_2,
                MarioShape.WALK_1,
                MarioShape.WALK_2);
    }

    private void setJumpingAnimation() {
        if (animation == Animation.JUMPING) return;
        animation = Animation.JUMPING;
        setStaticView(MarioShape.JUMP);
    }

    private void setBrakingAnimation() {
        if (animation == Animation.BRAKING) return;
        animation = Animation.BRAKING;
        setStaticView(MarioShape.BRAKE);
    }

    private void setDeadAnimation() {
        if (animation == Animation.DEAD) return;
        animation = Animation.DEAD;
        setStaticView(MarioShape.DEAD);
    }

    @Override
    public void draw() {
        if (moveDirection == Direction.RIGHT) faceDirection = Direction.RIGHT;
        else if (moveDirection == Direction.LEFT) faceDirection = Direction.LEFT;
        super.draw(faceDirection == Direction.LEFT ? Mirror.HORIZONTAL : Mirror.NONE);
    }

    public void verifyHit(List<Bullet> bullets) {
        bullets.forEach(this::destroyMarioAndBulletOnCollision);
    }

    private void destroyMarioAndBulletOnCollision(Bullet bullet) {
        Mirror mirror = (faceDirection == Direction.RIGHT) ? Mirror.NONE : Mirror.HORIZONTAL;
        if (!isAlive) return;
        if (!bullet.isAlive) return;
        if (!collidesWith(bullet, mirror)) return;

        kill();
        bullet.kill();
    }

    @Override
    public void kill() {
        if (!isAlive) return;
        super.kill();
    }

    @Control(Key.SPACE)
    public void shoot() {
        if (!isAlive) return;
        if (bonus == null) return;

        bonus.consume();
        bonus = null;
    }

    @Override
    public Optional<Bullet> getAmmo() {
        return Optional.of(new FireballBullet(getFireballSpawnX(), getFireballSpawnY()));
    }

    private double getFireballSpawnX() {
        return (x + getWidth() / 2.0) - (ObjectShape.FIREBALL_1.length / 2.0);
    }

    private double getFireballSpawnY() {
        return (y - ObjectShape.FIREBALL_1.length) + 4;
    }

    /*
     * Plain setters and getters
     */

    public Direction getMoveDirection() {
        return moveDirection;
    }

    @Control({Key.RIGHT, Key.LEFT})
    public void setMoveDirection(Direction moveDirection) {
        this.moveDirection = moveDirection;
    }

    public Direction getFaceDirection() {
        return faceDirection;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }
}
