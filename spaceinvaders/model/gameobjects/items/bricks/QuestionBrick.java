package com.javarush.games.spaceinvaders.model.gameobjects.items.bricks;

import com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses.ArkanoidBall;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses.Bonus;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses.Mushroom;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses.Star;
import com.javarush.games.spaceinvaders.view.shapes.BrickShape;

/**
 * A question brick hides a random bonus that appears from time to time and pops out when Mario hits
 * the brick. Once the bonus is taken away, the brick stays empty.
 */
public class QuestionBrick extends Brick {
    private static final double BONUS_SPAWN_CHANCE = 0.005;
    private static final int BONUS_VARIANTS = 3;
    private static final int ARKANOID_BALL_OFFSET_X = 2;

    private Bonus bonus;
    private boolean bonusRevealed;

    public QuestionBrick(double x, double y) {
        // An empty brick looks the same as a brick whose bonus has already been taken
        super(x, y, BrickShape.QUESTION_BRICK_EMPTY);
    }

    @Override
    public void move() {
        super.move();

        spawnBonusIfAbsent();
        if (bonusRevealed && bonus != null) {
            bonus.move();
        }
    }

    private void spawnBonusIfAbsent() {
        if (bonus != null) return;
        if (Math.random() >= BONUS_SPAWN_CHANCE) return;

        bonus = createRandomBonus();
        bonusRevealed = false;
        showFilled();
    }

    private Bonus createRandomBonus() {
        Bonus created;
        switch (game.getRandomNumber(BONUS_VARIANTS)) {
            case 0:
                created = new Mushroom(getX(), getY());
                break;
            case 1:
                created = new Star(getX(), getY());
                break;
            default:
                created = new ArkanoidBall(getX() + ARKANOID_BALL_OFFSET_X, getY());
                break;
        }
        created.setParentBrick(this);
        return created;
    }

    @Override
    protected void releaseContent() {
        bonusRevealed = true;
        showEmpty();
    }

    @Override
    public void draw() {
        if (bonusRevealed && bonus != null) {
            bonus.draw();
        }
        super.draw();
    }

    /**
     * Called by the bonus itself once it is taken or lost.
     */
    public void clearBonus() {
        bonus = null;
        bonusRevealed = false;
        showEmpty();
    }

    private void showFilled() {
        setStaticView(BrickShape.QUESTION_BRICK_FULL);
    }

    private void showEmpty() {
        setStaticView(BrickShape.QUESTION_BRICK_EMPTY);
    }
}
