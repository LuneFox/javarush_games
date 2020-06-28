package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.games.spaceinvaders.shapes.ObjectShape;

public class QuestionBrick extends Brick {
    public QuestionBrick(double x, double y) {
        super(x, y);
        this.matrix = ObjectShape.QUESTION_BRICK_EMPTY;
    }
}
