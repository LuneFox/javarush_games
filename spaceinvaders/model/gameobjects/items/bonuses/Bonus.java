package com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.JumpHelper;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bricks.Brick;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bricks.QuestionBrick;
import com.javarush.games.spaceinvaders.view.shapes.BrickShape;

import java.util.List;

public abstract class Bonus extends GameObject {
    public GameObject overheadIcon;
    private QuestionBrick parentQuestionBrick;
    private final JumpHelper jumpHelper;
    private final Direction direction;
    private boolean finishedEjectPhase;

    public Bonus(double x, double y) {
        super(x, y);
        jumpHelper = new JumpHelper(this);
        direction = (x < 50) ? Direction.RIGHT : Direction.LEFT;
    }

    protected void configureJumpHelper() {
        jumpHelper.setMaxJumpEnergy(4);
        jumpHelper.setFloorLevel(SpaceInvadersGame.HEIGHT - 30 - BrickShape.BRICK.length - getHeight());
    }

    public void move() {
        if (game.isStopped) return;
        if (!finishedEjectPhase) {
            eject();
            return;
        }

        verifyHit(game.enemyBullets);
        game.bricks.forEach(this::verifyHit);

        jumpHelper.progressJump();

        if (jumpHelper.getJumpCount() < 1) {
            return;
        }

        slide();
        verifyTouch(game.mario);
    }

    private void eject() {
        if (isOnBrick()) {
            finishedEjectPhase = true;
        } else {
            y--;
        }
    }

    private boolean isOnBrick() {
        int height = SpaceInvadersGame.HEIGHT - 30 - BrickShape.BRICK.length - getHeight();
        return y == height;
    }

    protected abstract void verifyHit(List<Bullet> bullets);

    public abstract void consume();

    private void verifyHit(Brick brick) {
        if (this.collidesWith(brick, Mirror.NONE)) {
            jumpHelper.initJump();
        }
    }

    private void slide() {
        if (direction == Direction.RIGHT) {
            x += 1;
            if (x < 40) return;
            fallOnFloor();
        } else if (direction == Direction.LEFT) {
            x -= 1;
            if (x + getWidth() >= 63) return;
            fallOnFloor();
        }
        checkMovingOutsideBorders();
    }

    private void fallOnFloor() {
        jumpHelper.setFloorLevel(SpaceInvadersGame.HEIGHT - getHeight() - SpaceInvadersGame.FLOOR_HEIGHT);
        jumpHelper.setDescendSpeed(3);
    }

    private void checkMovingOutsideBorders() {
        if (x + getWidth() < 0 || x > SpaceInvadersGame.WIDTH) {
            parentQuestionBrick.clearBonuses();
        }
    }

    private void verifyTouch(Mario mario) {
        Mirror mirror = (mario.getFaceDirection() == Direction.RIGHT) ? Mirror.NONE : Mirror.HORIZONTAL;
        if (this.collidesWith(mario, mirror)) {
            mario.setBonus(this);
            parentQuestionBrick.clearBonuses();
            Score.add(10);
        }
    }

    public void setParentQuestionBrick(QuestionBrick parentQuestionBrick) {
        this.parentQuestionBrick = parentQuestionBrick;
    }
}