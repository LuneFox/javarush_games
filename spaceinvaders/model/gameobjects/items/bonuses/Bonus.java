package com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.jumphelper.JumpHelper;
import com.javarush.games.spaceinvaders.model.gameobjects.Movable;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bricks.Brick;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bricks.QuestionBrick;
import com.javarush.games.spaceinvaders.model.gameobjects.jumphelper.JumpHelperBuilder;
import com.javarush.games.spaceinvaders.view.shapes.BrickShape;

import java.util.List;

public abstract class Bonus extends GameObject implements Movable {
    public GameObject overheadIcon;
    private QuestionBrick parentQuestionBrick;
    private JumpHelper jumpHelper;
    private final Direction direction;
    private boolean isEjected;

    public Bonus(double x, double y) {
        super(x, y);
        direction = (x < 50) ? Direction.RIGHT : Direction.LEFT;
    }

    protected void buildJumpHelper() {
        jumpHelper = new JumpHelperBuilder(this)
                .setMaxJumpEnergy(4)
                .setFloorLevel(SpaceInvadersGame.HEIGHT - 30 - BrickShape.BRICK.length - getHeight())
                .build();
    }

    public void move() {
        if (game.isStopped()) return;

        if (!isEjected) {
            eject();
            return;
        }

        doThingsAfterEjection();

        if (jumpHelper.getJumpCount() < 1) {
            return;
        }

        doThingsAfterFirstJump();
    }

    private void eject() {
        if (isOnBrick()) {
            isEjected = true;
        } else {
            y--;
        }
    }

    private void doThingsAfterEjection() {
        verifyHit(game.getEnemyBullets());
        game.getBricks().forEach(this::verifyHit);
        jumpHelper.progressJump();
    }

    private void doThingsAfterFirstJump() {
        slide();
        verifyTouch(game.getMario());
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
            Score.add(9 + SpaceInvadersGame.getStage());
        }
    }

    public void setParentQuestionBrick(QuestionBrick parentQuestionBrick) {
        this.parentQuestionBrick = parentQuestionBrick;
    }
}