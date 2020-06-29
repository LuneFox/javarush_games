package com.javarush.games.spaceinvaders.gameobjects.brick;

import com.javarush.games.spaceinvaders.gameobjects.ammo.Bullet;
import com.javarush.games.spaceinvaders.shapes.ObjectShape;

public class QuestionBrick extends Brick {
    private boolean isEmpty = false;

    public QuestionBrick(double x, double y) {
        super(x, y);
        this.setStaticView(ObjectShape.QUESTION_BRICK);
    }

    @Override
    public Bullet fire() {
        open();
        return null;
    }

    private void open() {
        this.isEmpty = true;
        setStaticView(ObjectShape.QUESTION_BRICK_EMPTY);
    }

    private void ejectItem() {

    }

    private void pushItem() {

    }

    private void generateItem() {

    }
}
