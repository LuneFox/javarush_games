package com.javarush.games.spaceinvaders.model;

import com.javarush.games.spaceinvaders.model.gameobjects.items.bricks.Brick;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bricks.QuestionBrick;
import com.javarush.games.spaceinvaders.view.shapes.BrickShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Placement of the bricks for a stage. Brick positions and the line the enemy army must not cross
 * are defined here only.
 */
public final class LevelLayout {
    /**
     * Top row of the bricks. It is also the line the enemy army must not reach.
     */
    public static final int BRICK_ROW_Y = GameField.HEIGHT - 30 - BrickShape.BRICK.length;

    private static final int[] PLAIN_BRICK_COLUMNS = {0, 20, 30, 60, 70, 90};
    private static final int[] QUESTION_BRICK_COLUMNS = {10, 80};

    private LevelLayout() {
    }

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
