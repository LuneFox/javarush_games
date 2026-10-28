package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.gameobjects.Movable;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletFactory;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses.Bonus;
import com.javarush.games.spaceinvaders.model.gameobjects.physics.JumpPhysics;
import com.javarush.games.spaceinvaders.model.gameobjects.physics.JumpPhysicsBuilder;
import com.javarush.games.spaceinvaders.view.shapes.MarioShape;

import java.util.List;

/**
 * The player character: walks with inertia, jumps, carries a bonus and fires it when asked.
 */
public class Mario extends Battler implements Movable {
    private static final int HIT_POINTS = 1;
    private static final int WALKING_MARGIN = 4;
    private static final int MAX_JUMP_ENERGY = 5;
    private static final int JUMP_CEILING_Y = 66;
    private static final int JUMP_RAISE_SPEED = 4;
    private static final int JUMP_DESCEND_SPEED = 2;
    private static final int DEATH_ANIMATION_FRAMES = 30;
    private static final int DEATH_RISE_FROM_FRAME = 10;
    private static final int DEATH_RISE_UNTIL_FRAME = 15;
    private static final int DEATH_RISE_STEP = -2;
    private static final int DEATH_FALL_STEP = 3;
    private static final int BULLET_TOP_GAP = 4;

    private final MarioWalk walk = new MarioWalk();
    private final MarioAnimator animator = new MarioAnimator(this);
    private final JumpPhysics jumpPhysics;
    private final int leftWalkingBound;
    private final int rightWalkingBound;

    private Direction walkingDirection = Direction.NONE;
    private Direction facing = Direction.LEFT;
    private Bonus bonus;
    private int animationFrame;

    public Mario() {
        super(spawnX(), spawnY());
        this.hitPoints = HIT_POINTS;
        animator.stand();
        this.leftWalkingBound = -WALKING_MARGIN;
        this.rightWalkingBound = GameField.WIDTH - getWidth() + WALKING_MARGIN;
        this.jumpPhysics = new JumpPhysicsBuilder(this)
                .setMaxJumpEnergy(MAX_JUMP_ENERGY)
                .setFloorLevel(GameField.groundLevelFor(getHeight()))
                .setCeilingLevel(JUMP_CEILING_Y)
                .setRaiseSpeed(JUMP_RAISE_SPEED)
                .setDescendSpeed(JUMP_DESCEND_SPEED)
                .build();
    }

    private static double spawnX() {
        return GameField.WIDTH - MarioShape.STAND[0].length - 3;
    }

    private static int spawnY() {
        return GameField.groundLevelFor(MarioShape.STAND.length);
    }

    /*
     * Movement
     */

    @Override
    public void move() {
        if (!isAlive()) return;

        boolean braking = walk.update(walkingDirection);
        showWalkPose(braking);
        setPosition(getX() + walk.getSpeed(), getY());
        keepInBounds();

        jumpPhysics.update();
        if (jumpPhysics.isInAir()) {
            animator.jump();
        }
    }

    private void showWalkPose(boolean braking) {
        if (braking) {
            animator.brake();
        } else if (walk.isMoving()) {
            animator.walk();
        } else {
            animator.stand();
        }
    }

    private void keepInBounds() {
        if (getX() < leftWalkingBound) {
            setPosition(leftWalkingBound, getY());
        } else if (getX() > rightWalkingBound) {
            setPosition(rightWalkingBound, getY());
        }
    }

    public void jump() {
        jumpPhysics.startJump();
    }

    public void walkTowards(Direction direction) {
        this.walkingDirection = direction;
    }

    public Direction getWalkingDirection() {
        return walkingDirection;
    }

    public Direction getFacing() {
        return facing;
    }

    /*
     * Bonus
     */

    public void pickUp(Bonus bonus) {
        this.bonus = bonus;
    }

    public boolean hasBonus() {
        return bonus != null;
    }

    /**
     * SPACE action: the carried bonus is used up and does whatever it is meant to do.
     */
    public void useBonus() {
        if (!isAlive() || bonus == null) return;

        Bonus usedBonus = bonus;
        bonus = null;
        usedBonus.activate();
    }

    /**
     * Spawns a projectile of the given type right above Mario, centered on his sprite.
     */
    public void launchProjectile(BulletType type, int[][] projectileImage) {
        if (!isAlive()) return;

        double x = getX() + (getWidth() - projectileImage[0].length) / 2.0;
        double y = getY() - projectileImage.length + BULLET_TOP_GAP;
        game.addPlayerBullet(BulletFactory.create(type, x, y));
    }

    /*
     * Damage and death
     */

    public void receiveBullets(List<Bullet> bullets) {
        for (Bullet bullet : bullets) {
            if (isHitBy(bullet)) {
                takeHit();
                bullet.destroy();
            }
        }
    }

    private boolean isHitBy(Bullet bullet) {
        if (!isAlive() || !bullet.isAlive()) return false;

        return collidesWith(bullet, Mirror.of(facing));
    }

    @Override
    public void takeHit() {
        if (!isAlive()) return;

        // A bonus absorbs the hit instead of Mario, taking its points away
        if (bonus != null) {
            bonus = null;
            Score.removeBonusPickup();
            return;
        }

        super.takeHit();
    }

    public void playDeathAnimation() {
        if (animationFrame > DEATH_ANIMATION_FRAMES) return;

        animationFrame++;
        animator.die();
        bonus = null;

        if (animationFrame >= DEATH_RISE_FROM_FRAME) {
            double step = (animationFrame < DEATH_RISE_UNTIL_FRAME) ? DEATH_RISE_STEP : DEATH_FALL_STEP;
            setPosition(getX(), getY() + step);
        }
    }

    public void playVictoryAnimation() {
        walkTowards(Direction.NONE);
        animator.victoryFrame(animationFrame);
        animationFrame++;
    }

    /*
     * Drawing
     */

    @Override
    public void draw() {
        updateFacing();
        super.draw(Mirror.of(facing));
        drawBonusIcon();
    }

    private void updateFacing() {
        if (walkingDirection == Direction.RIGHT) {
            facing = Direction.RIGHT;
        } else if (walkingDirection == Direction.LEFT) {
            facing = Direction.LEFT;
        }
    }

    private void drawBonusIcon() {
        if (bonus == null) return;

        double iconX = getX() + (getWidth() - bonus.getIconWidth()) / 2.0;
        bonus.drawIconAt(iconX, getY() - 3);
    }
}
