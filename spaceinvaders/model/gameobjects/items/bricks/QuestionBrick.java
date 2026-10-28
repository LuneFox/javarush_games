package com.javarush.games.spaceinvaders.model.gameobjects.items.bricks;

import com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses.ArkanoidBall;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses.Bonus;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses.Mushroom;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses.Star;
import com.javarush.games.spaceinvaders.view.shapes.BrickShape;

/** Кирпич с вопросом: прячет бонус и выдаёт его при ударе. */
public class QuestionBrick extends Brick {
    private static final double BONUS_SPAWN_CHANCE = 0.005;
    private static final int BONUS_VARIANTS = 3;
    private static final int ARKANOID_BALL_OFFSET_X = 2;

    private Bonus bonus;
    private boolean bonusRevealed;

    /** Создаёт кирпич с вопросом. */
    public QuestionBrick(double x, double y) {
        // Пустой кирпич выглядит так же, как кирпич с уже забранным бонусом
        super(x, y, BrickShape.QUESTION_BRICK_EMPTY);
    }

    /** Обновляет кирпич и его бонус. */
    @Override
    public void move() {
        super.move();

        spawnBonusIfAbsent();
        if (bonusRevealed && bonus != null) {
            bonus.move();
        }
    }

    /** Иногда создаёт бонус. */
    private void spawnBonusIfAbsent() {
        if (bonus != null) return;
        if (Math.random() >= BONUS_SPAWN_CHANCE) return;

        bonus = createRandomBonus();
        bonusRevealed = false;
        showFilled();
    }

    /** Создаёт случайный бонус. */
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

    /** Открывает бонус при ударе. */
    @Override
    protected void releaseContent() {
        bonusRevealed = true;
        showEmpty();
    }

    /** Рисует бонус и кирпич. */
    @Override
    public void draw() {
        if (bonusRevealed && bonus != null) {
            bonus.draw();
        }
        super.draw();
    }

    /** Очищает кирпич от бонуса. */
    public void clearBonus() {
        bonus = null;
        bonusRevealed = false;
        showEmpty();
    }

    /** Показывает кирпич с бонусом. */
    private void showFilled() {
        setStaticView(BrickShape.QUESTION_BRICK_FULL);
    }

    /** Показывает пустой кирпич. */
    private void showEmpty() {
        setStaticView(BrickShape.QUESTION_BRICK_EMPTY);
    }
}
