package com.javarush.games.spaceinvaders.gameobjects.brick;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.Bullet;
import com.javarush.games.spaceinvaders.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.gameobjects.item.Bonus;
import com.javarush.games.spaceinvaders.gameobjects.item.Mushroom;
import com.javarush.games.spaceinvaders.gameobjects.item.Star;
import com.javarush.games.spaceinvaders.shapes.ObjectShape;

public class QuestionBrick extends Brick {
    private Bonus bonus;
    private boolean isOpened;
    private boolean hasItem;

    public QuestionBrick(double x, double y) {
        super(x, y);
        this.setStaticView(ObjectShape.QUESTION_BRICK_EMPTY);
        hasItem = false;
        isOpened = true;
    }

    @Override
    public Bullet fire() {
        open();
        return null;
    }

    @Override
    public void verifyTouch(Mario mario, SpaceInvadersGame game) {
        super.verifyTouch(mario, game);
        putItem(); // constantly invoked
        ejectItem(game);
    }

    private void open() {
        this.isOpened = true;
        setStaticView(ObjectShape.QUESTION_BRICK_EMPTY);
    }

    private void ejectItem(SpaceInvadersGame game) {
        if (isOpened && hasItem) {
            Bonus bonus = generateItem();
            this.bonus = bonus;
            game.addBonus(bonus);
            hasItem = false;
        }
    }

    private Bonus generateItem() {
        double random = Math.random();
        if (random < 0.5) {
            return new Mushroom(x, y, this);
        } else {
            return new Star(x, y + 1, this);
        }
    }

    private void putItem() {
        double random = Math.random();
        if (random < 0.005 && isOpened && !hasItem) {
            if (bonus == null || bonus.isCollected) {
                hasItem = true;
                isOpened = false;
                this.bonus = null;
                setStaticView(ObjectShape.QUESTION_BRICK);
            }
        }
    }
}
