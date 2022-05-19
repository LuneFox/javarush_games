package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.engine.cell.Key;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.controller.Control;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.JumpHelper;
import com.javarush.games.spaceinvaders.model.gameobjects.Movable;
import com.javarush.games.spaceinvaders.model.gameobjects.Sprite;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletFactory;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses.Bonus;
import com.javarush.games.spaceinvaders.view.shapes.FireballShape;
import com.javarush.games.spaceinvaders.view.shapes.MarioShape;

import java.util.List;
import java.util.Optional;

public class Mario extends Battler implements Movable {
    private static final double START_SPEED = 0.6;
    private static final double MAX_SPEED = 2.4;
    private static final double ACCELERATION = 0.2;

    private final int leftWalkingBound;
    private final int rightWalkingBound;
    private Animation animation;
    private Direction moveDirection = Direction.NONE;
    private Direction faceDirection = Direction.RIGHT;
    private JumpHelper jumpHelper;
    public Bonus bonus;
    public int finalAnimationCounter;
    private double walkingSpeed;

    public enum Animation {
        STANDING, WALKING, JUMPING, BRAKING, DEAD
    }

    public Mario() {
        super(marioSpawnX(), marioSpawnY());
        setStandingAnimation();
        configureJumpHelper();
        finalAnimationCounter = 0;
        leftWalkingBound = -4;
        rightWalkingBound = SpaceInvadersGame.WIDTH - getWidth() + 4;
        walkingSpeed = 0;
    }

    private void configureJumpHelper() {
        jumpHelper = new JumpHelper(this);
        jumpHelper.setMaxJumpEnergy(5);
        jumpHelper.setFloorLevel(SpaceInvadersGame.HEIGHT - getHeight() - SpaceInvadersGame.FLOOR_HEIGHT);
        jumpHelper.setCeilingLevel(66);
        jumpHelper.setRaiseSpeed(4);
        jumpHelper.setDescendSpeed(2);
    }

    private static double marioSpawnX() {
        return SpaceInvadersGame.WIDTH / 2.0 - MarioShape.STAND[0].length / 2.0;
    }

    private static int marioSpawnY() {
        return SpaceInvadersGame.HEIGHT - SpaceInvadersGame.FLOOR_HEIGHT - MarioShape.STAND.length;
    }

    public void move() {
        if (!isAlive) return;
        walk();
        keepInBounds();
        jumpHelper.progressJump();
        if (jumpHelper.isAboveFloor()) {
            setJumpingAnimation();
        }
    }

    private void walk() {
        changeSpeed();
        selectAnimationAccordingToSpeed();
        brakeOnReverseDirectionInput();
        x += walkingSpeed;
    }

    private void changeSpeed() {
        if (moveDirection == Direction.RIGHT) {
            if (walkingSpeed == 0) walkingSpeed = START_SPEED;
            if (walkingSpeed < MAX_SPEED) walkingSpeed += ACCELERATION;
        } else if (moveDirection == Direction.LEFT) {
            if (walkingSpeed == 0) walkingSpeed = -START_SPEED;
            if (walkingSpeed > -MAX_SPEED) walkingSpeed -= ACCELERATION;
        } else if (moveDirection == Direction.NONE) {
            walkingSpeed += ((walkingSpeed > 0) ? -ACCELERATION : ACCELERATION);
            if (Math.abs(walkingSpeed) < 0.5) walkingSpeed = 0;
        }
    }

    private void selectAnimationAccordingToSpeed() {
        if (Math.abs(walkingSpeed) > ACCELERATION) {
            setWalkingAnimation();
        } else {
            setStandingAnimation();
        }
    }

    private void brakeOnReverseDirectionInput() {
        if (moveDirection == Direction.RIGHT && walkingSpeed < ACCELERATION) {
            walkingSpeed += ACCELERATION / 2;
            setBrakingAnimation();
        }
        if (moveDirection == Direction.LEFT && walkingSpeed > -ACCELERATION) {
            walkingSpeed -= ACCELERATION / 2;
            setBrakingAnimation();
        }
    }

    public void keepInBounds() {
        if (x < leftWalkingBound) {
            x = leftWalkingBound;
        } else if (x > rightWalkingBound) {
            x = rightWalkingBound;
        }
    }

    @Control(Key.UP)
    public void jump() {
        jumpHelper.initJump();
    }

    public void playDeathAnimation() {
        if (finalAnimationCounter > 30) return;
        finalAnimationCounter++;
        setDeadAnimation();
        this.bonus = null;

        if (finalAnimationCounter >= 10) {
            y += (finalAnimationCounter < 15) ? -2 : 3;
        }
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
        drawBonusOverheadIcon();
    }

    private void drawBonusOverheadIcon() {
        if (bonus == null) return;

        final double bx = x + (getWidth() / 2.0) - (bonus.overheadIcon.getWidth() / 2.0);
        final double by = y - 3;
        bonus.overheadIcon.setPosition(bx, by);
        bonus.overheadIcon.draw();
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
        return BulletFactory.getBullet(BulletType.FIREBALL, getFireballSpawnX(), getFireballSpawnY());
    }

    private double getFireballSpawnX() {
        return (x + getWidth() / 2.0) - (FireballShape.FIREBALL_1.length / 2.0);
    }

    private double getFireballSpawnY() {
        return (y - FireballShape.FIREBALL_1.length) + 4;
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
