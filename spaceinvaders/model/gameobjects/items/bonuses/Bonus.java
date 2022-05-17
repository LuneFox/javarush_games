package com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bricks.Brick;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bricks.QuestionBrick;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

import java.util.List;

public abstract class Bonus extends GameObject {
    private final static int MAX_JUMP_ENERGY = 4;
    private QuestionBrick parentQuestionBrick;
    private final Direction direction;
    private boolean finishedEjectPhase;
    private boolean finishedJumpPhase;

    public Bonus(double x, double y) {
        super(x, y);
        direction = (x < 50) ? Direction.RIGHT : Direction.LEFT;
    }

    public void move() {
        if (!finishedEjectPhase) {
            eject();
            return;
        }

        verifyHit(game.enemyBullets);
        game.bricks.forEach(this::verifyHit);
        processJumping();

        if (!finishedJumpPhase) return;

        slide();
        verifyTouch(game.mario);
    }

    private void eject() {
        if (!isOnBrick()) raise();
        else finishedEjectPhase = true;
    }

    private boolean isOnBrick() {
        int height = SpaceInvadersGame.HEIGHT - 30 - ObjectShape.BRICK.length - getHeight();
        return y == height;
    }

    protected abstract void verifyHit(List<Bullet> bullets);

    public abstract void consume();

    private void verifyHit(Brick brick) {
        if (this.collidesWith(brick, Mirror.NONE)) {
            jump();
        }
    }

    private void jump() {
        if (isAboveBrick()) return;
        jumpEnergy = MAX_JUMP_ENERGY;
    }

    private void processJumping() {
        if (jumpEnergy > 0) {
            loseJumpEnergy();
            raise();
        } else if (isAboveBrick()) {
            descend();
            if (isOnBrick())
                finishedJumpPhase = true;
        }
    }

    private boolean isAboveBrick() {
        int height = SpaceInvadersGame.HEIGHT - 30 - ObjectShape.BRICK.length - getHeight();
        return y < height;
    }

    private void slide() {
        if (direction == Direction.RIGHT) {
            x += 1;
            if (x < 40) return;
            fallOnFloor();
        } else if (direction == Direction.LEFT) {
            x -= 1;
            if (x + getWidth() >= 60) return;
            fallOnFloor();
        }
        checkMovingOutsideBorders();
    }

    private void fallOnFloor() {
        y += 3;
        if (y > getFloorHeight()) y = getFloorHeight();
    }

    private int getFloorHeight() {
        return SpaceInvadersGame.HEIGHT - getHeight() - SpaceInvadersGame.FLOOR_HEIGHT;
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