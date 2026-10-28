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

/** Бонус из кирпича: вылезает, прыгает, скользит и подбирается. */
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

    /** Создаёт бонус с картинкой и иконкой. */
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

    /** Делает шаг жизненного цикла бонуса. */
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

    /** Выдвигает бонус из кирпича. */
    private void riseAboveBrick() {
        if (getY() <= brickTopY) {
            setPosition(getX(), brickTopY);
            poppedOut = true;
            return;
        }
        setPosition(getX(), getY() - POP_OUT_STEP);
    }

    /** Заставляет бонус прыгнуть от кирпича. */
    private void jumpOnBricks(List<Brick> bricks) {
        for (Brick brick : bricks) {
            if (collidesWith(brick, Mirror.NONE)) {
                jumpPhysics.startJump();
                return;
            }
        }
    }

    /** Двигает бонус по кирпичам и вниз. */
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

    /** Опускает бонус на землю. */
    private void fallToGround() {
        jumpPhysics.fallTo(GameField.groundLevelFor(getHeight()), FALL_SPEED);
    }

    /** Убирает бонус, ушедший за поле. */
    private void removeIfOutOfField() {
        if (getX() + getWidth() < 0 || getX() > GameField.WIDTH) {
            destroy();
            detachFromParentBrick();
        }
    }

    /** Отдаёт бонус Марио при касании. */
    private void collectIfTouchedBy(Mario mario) {
        if (!collidesWith(mario, Mirror.of(mario.getFacing()))) return;

        destroy();
        mario.pickUp(this);
        detachFromParentBrick();
        Score.addBonusPickup();
    }

    /** Освобождает кирпич от бонуса. */
    private void detachFromParentBrick() {
        if (parentBrick == null) return;

        parentBrick.clearBonus();
    }

    /*
     * Отрисовка
     */

    /** Возвращает ширину иконки. */
    public int getIconWidth() {
        return icon.getWidth();
    }

    /** Рисует иконку в заданной точке. */
    public void drawIconAt(double x, double y) {
        icon.setPosition(x, y);
        icon.draw();
    }

    /*
     * Поведение конкретного бонуса
     */

    /** Реагирует на вражеские пули. */
    protected abstract void reactToBullets(List<Bullet> bullets);

    /** Использует бонус. */
    public abstract void activate();

    /** Запоминает кирпич-владельца. */
    public void setParentBrick(QuestionBrick parentBrick) {
        this.parentBrick = parentBrick;
    }
}
