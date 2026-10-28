package com.javarush.games.spaceinvaders.model;

/** Геометрия игрового поля. */
public final class GameField {
    /** Ширина поля в клетках. */
    public static final int WIDTH = 100;
    /** Высота поля в клетках. */
    public static final int HEIGHT = 100;
    /** Высота земли внизу поля. */
    public static final int FLOOR_HEIGHT = 4;
    /** Координата середины поля. */
    public static final int CENTER_X = WIDTH / 2;

    /** Запрещает создание экземпляров. */
    private GameField() {
    }

    /** Возвращает уровень земли для объекта заданной высоты. */
    public static int groundLevelFor(int objectHeight) {
        return HEIGHT - FLOOR_HEIGHT - objectHeight;
    }
}
