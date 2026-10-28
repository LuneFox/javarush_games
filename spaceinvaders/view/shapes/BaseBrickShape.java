package com.javarush.games.spaceinvaders.view.shapes;

/** Кирпич базы с орлом. */
public class BaseBrickShape {
    /** Маленький рыжий кирпич базы. */
    public static final int[][] BRICK = new int[][]{
            {128, 131, 128},
            {23, 92, 23},
            {131, 128, 131}
    };

    /** Запрещает создание экземпляров. */
    private BaseBrickShape() {
    }
}
