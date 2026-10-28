package com.javarush.games.spaceinvaders.model;

import com.javarush.games.spaceinvaders.model.gameobjects.items.bricks.Brick;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bricks.QuestionBrick;
import com.javarush.games.spaceinvaders.view.shapes.BrickShape;

import java.util.ArrayList;
import java.util.List;

/** Раскладка кирпичей уровня и линия обороны. */
public final class LevelLayout {
    /** Верхний ряд кирпичей и линия, до которой не должна дойти армия. */
    public static final int BRICK_ROW_Y = GameField.HEIGHT - 30 - BrickShape.BRICK.length;

    /** Колонки обычных кирпичей. */
    private static final int[] PLAIN_BRICK_COLUMNS = {0, 20, 30, 60, 70, 90};
    /** Колонки кирпичей с бонусами. */
    private static final int[] QUESTION_BRICK_COLUMNS = {10, 80};

    /** Запрещает создание экземпляров. */
    private LevelLayout() {
    }

    /** Создаёт кирпичи уровня. */
    public static List<Brick> createBricks() {
        List<Brick> bricks = new ArrayList<>();

        for (int column : PLAIN_BRICK_COLUMNS) {
            bricks.add(new Brick(column, BRICK_ROW_Y));
        }
        for (int column : QUESTION_BRICK_COLUMNS) {
            bricks.add(new QuestionBrick(column, BRICK_ROW_Y));
        }

        return bricks;
    }
}
