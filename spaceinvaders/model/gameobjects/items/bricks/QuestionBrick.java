package com.javarush.games.spaceinvaders.model.gameobjects.items.bricks;

import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses.ArkanoidBall;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses.Bonus;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses.Mushroom;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses.Star;
import com.javarush.games.spaceinvaders.view.shapes.BrickShape;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class QuestionBrick extends Brick {
    private final List<Bonus> bonuses;
    private boolean bonusIsRevealed;

    public QuestionBrick(double x, double y) {
        super(x, y);
        this.bonuses = new LinkedList<>();
    }

    public void move() {
        super.move();
        spawnRandomBonus();
        if (bonusIsRevealed) getBonus().ifPresent(Bonus::move);
    }

    private void spawnRandomBonus() {
        if (!bonuses.isEmpty()) return;
        if (Math.random() < 0.005) {
            bonuses.add(getRandomBonus());
            bonusIsRevealed = false;
        }
    }

    @Override
    public void shoot() {
        bonusIsRevealed = true;
    }

    private Bonus getRandomBonus() {
        List<Bonus> bonusList = new ArrayList<>();
        bonusList.add(new Mushroom(x, y));
        bonusList.add(new Star(x, y));
        bonusList.add(new ArkanoidBall(x + 2, y));

        Bonus bonus = bonusList.get(game.getRandomNumber(bonusList.size()));
        bonus.setParentQuestionBrick(this);
        return bonus;
    }

    @Override
    public void draw() {
        if (bonuses.isEmpty() || bonusIsRevealed) {
            setStaticView(BrickShape.QUESTION_BRICK_EMPTY);
        } else {
            setStaticView(BrickShape.QUESTION_BRICK_FULL);
        }
        if (bonusIsRevealed) getBonus().ifPresent(GameObject::draw);
        super.draw();
    }

    private Optional<Bonus> getBonus() {
        if (!bonuses.isEmpty()) return Optional.of(bonuses.get(0));
        else return Optional.empty();
    }

    public void clearBonuses() {
        bonuses.clear();
    }
}
