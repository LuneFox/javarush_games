package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.FireballBullet;
import com.javarush.games.spaceinvaders.model.gameobjects.items.QuestionBrick.Bonus;
import com.javarush.games.spaceinvaders.view.shapes.MarioShape;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

import java.util.List;
import java.util.Optional;

public class Mario extends Battler {
    private static final int MAX_JUMP_ENERGY = 10;
    private static final int WALK_SPEED = 2;
    private static final int JUMP_SPEED = 2;

    private final int leftWalkingBound;
    private final int rightWalkingBound;
    private Animation animation;
    private Direction moveDirection = Direction.NONE;
    private Direction faceDirection = Direction.RIGHT;
    public Bonus bonus;
    public int finishAnimationCounter;
    private int jumpEnergy;

    public enum Animation {
        STANDING, WALKING, JUMPING, BRAKING, DEAD
    }

    public Mario() {
        super(SpaceInvadersGame.WIDTH / 2.0, SpaceInvadersGame.HEIGHT - MarioShape.STAND.length - SpaceInvadersGame.FLOOR_HEIGHT);
        setStandingAnimation();
        finishAnimationCounter = 0;
        jumpEnergy = 0;
        leftWalkingBound = -4;
        rightWalkingBound = SpaceInvadersGame.WIDTH - getWidth() + 4;
    }

    public void move() {
        if (isAlive) {
            controlWalking();
            controlJumping();
        } else {
            playDeathAnimation();
        }
    }

    private void controlWalking() {
        if (moveDirection == Direction.NONE) {
            setStandingAnimation();
        } else if (moveDirection == Direction.RIGHT || moveDirection == Direction.LEFT) {
            setWalkingAnimation();
            walkInDirection();
        }
        controlWalkingBounds();
    }

    private void walkInDirection() {
        if (moveDirection == Direction.RIGHT) {
            x += WALK_SPEED;
        } else if (moveDirection == Direction.LEFT) {
            x -= WALK_SPEED;
        }
    }

    public void controlWalkingBounds() {
        if (x < leftWalkingBound) {
            x = leftWalkingBound;
            setBrakingAnimation();
        } else if (x > rightWalkingBound) {
            x = rightWalkingBound;
            setBrakingAnimation();
        }
    }

    public void controlJumping() {
        if (jumpEnergy > 0) {
            setJumpingAnimation();
            loseJumpEnergy();
            raise();
        } else if (isAboveFloor()) {
            setJumpingAnimation();
            descend();
        }
    }

    public void jump() {
        if (isAboveFloor()) return;
        jumpEnergy = MAX_JUMP_ENERGY;
    }

    private boolean isAboveFloor() {
        return y < (SpaceInvadersGame.HEIGHT - getHeight() - SpaceInvadersGame.FLOOR_HEIGHT);
    }

    private void raise() {
        y -= JUMP_SPEED;
    }

    private void loseJumpEnergy() {
        jumpEnergy -= JUMP_SPEED;
    }

    private void descend() {
        y += JUMP_SPEED;
    }

    private void playDeathAnimation() {
        setDeadAnimation();
        if (finishAnimationCounter < 30) finishAnimationCounter++;
        if (finishAnimationCounter < 10) return;
        if (finishAnimationCounter < 15) y -= 2;
        else if (finishAnimationCounter < 30) y += 3;
    }

    public void playVictoryAnimation() {
        if (finishAnimationCounter % 20 < 10) {
            setJumpingAnimation();
        } else {
            setStandingAnimation();
        }
        finishAnimationCounter++;
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

    @Override
    public Optional<Bullet> fire() {
        if (!isAlive) return Optional.empty();
        if (bonus == null) return Optional.empty();
        if (!bonus.getClass().getName().contains("Mushroom")) return Optional.empty();

        bonus = null;

        return Optional.of(new FireballBullet(getFireballSpawnX(), getFireballSpawnY()));
    }

    private double getFireballSpawnX() {
        return (x + getWidth() / 2.0) - (ObjectShape.FIREBALL_1.length / 2.0);
    }

    private double getFireballSpawnY() {
        return (y - ObjectShape.FIREBALL_1.length) + 4;
    }

    public boolean wipeEnemyBullets() {
        if (!isAlive) return false;
        if (bonus == null) return false;
        if (!bonus.getClass().getName().contains("Star")) return false;

        bonus = null;

        return true;
    }

    /*
     * Plain setters and getters
     */

    public Direction getMoveDirection() {
        return moveDirection;
    }

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
