package com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.LevelLayout;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.Movable;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bricks.Brick;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bricks.QuestionBrick;
import com.javarush.games.spaceinvaders.model.gameobjects.physics.JumpPhysics;
import com.javarush.games.spaceinvaders.model.gameobjects.physics.JumpPhysicsBuilder;

import java.util.List;

/**
 * An item hidden in a question brick: it pops out of the brick, jumps, slides along the brick row
 * and can be picked up by Mario. What the item does when it is used is defined by subclasses.
 */
public abstract class Bonus extends GameObject implements Movable {
    private static final int MAX_JUMP_ENERGY = 4;
    private static final int POP_OUT_STEP = 1;
    private static final int SLIDE_STEP = 1;
    private static final int SLIDE_DIRECTION_SWITCH_X = 40;
    private static final int SLIDE_END_X = 63;
    private static final int FALL_SPEED = 3;

    private final GameObject icon;
    private final JumpPhysics jumpPhysics;
    private final Direction slideDirection;
    private final int brickTopY;

    private QuestionBrick parentBrick;
    private boolean poppedOut;

    protected Bonus(double x, double y, int[][] image, int[][] iconImage) {
        super(x, y);
        this.slideDirection = (x < GameField.CENTER_X) ? Direction.RIGHT : Direction.LEFT;
        this.brickTopY = LevelLayout.BRICK_ROW_Y - image.length;
        setStaticView(image);
        icon = new GameObject();
        icon.setStaticView(iconImage);
        jumpPhysics = new JumpPhysicsBuilder(this)
                .setMaxJumpEnergy(MAX_JUMP_ENERGY)
                .setFloorLevel(brickTopY)
                .build();
    }

    /**
     * One step of the bonus life: getting out of the brick, reacting to hits, jumping, sliding.
     */
    @Override
    public void move() {
        if (game.isRoundOver() || !isAlive()) return;

        if (!poppedOut) {
            riseAboveBrick();
            return;
        }

        reactToBullets(game.getEnemyBullets());
        jumpOnBricks(game.getBricks());
        jumpPhysics.update();

        if (jumpPhysics.getCompletedJumps() < 1) return;

        slide();
        collectIfTouchedBy(game.getMario());
    }

    private void riseAboveBrick() {
        if (getY() <= brickTopY) {
            setPosition(getX(), brickTopY);
            poppedOut = true;
            return;
        }
        setPosition(getX(), getY() - POP_OUT_STEP);
    }

    private void jumpOnBricks(List<Brick> bricks) {
        for (Brick brick : bricks) {
            if (collidesWith(brick, Mirror.NONE)) {
                jumpPhysics.startJump();
                return;
            }
        }
    }

    private void slide() {
        if (slideDirection == Direction.RIGHT) {
            setPosition(getX() + SLIDE_STEP, getY());
            if (getX() < SLIDE_DIRECTION_SWITCH_X) return;
        } else {
            setPosition(getX() - SLIDE_STEP, getY());
            if (getX() + getWidth() >= SLIDE_END_X) return;
        }

        fallToGround();
        removeIfOutOfField();
    }

    private void fallToGround() {
        jumpPhysics.fallTo(GameField.groundLevelFor(getHeight()), FALL_SPEED);
    }

    private void removeIfOutOfField() {
        if (getX() + getWidth() < 0 || getX() > GameField.WIDTH) {
            destroy();
            detachFromParentBrick();
        }
    }

    private void collectIfTouchedBy(Mario mario) {
        if (!collidesWith(mario, Mirror.of(mario.getFacing()))) return;

        destroy();
        mario.pickUp(this);
        detachFromParentBrick();
        Score.addBonusPickup();
    }

    private void detachFromParentBrick() {
        if (parentBrick == null) return;

        parentBrick.clearBonus();
    }

    /*
     * Drawing
     */

    public int getIconWidth() {
        return icon.getWidth();
    }

    public void drawIconAt(double x, double y) {
        icon.setPosition(x, y);
        icon.draw();
    }

    /*
     * Behaviour of the concrete bonus
     */

    /**
     * How the bonus reacts to an enemy bullet that hits it.
     */
    protected abstract void reactToBullets(List<Bullet> bullets);

    /**
     * Uses the bonus up: called when the player presses SPACE.
     */
    public abstract void activate();

    public void setParentBrick(QuestionBrick parentBrick) {
        this.parentBrick = parentBrick;
    }
}
