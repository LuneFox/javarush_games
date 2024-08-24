package com.javarush.games.ticktacktoe.model.gameobjects.field;

import com.javarush.games.ticktacktoe.model.gameobjects.GameObject;
import com.javarush.games.ticktacktoe.view.shapes.Shape;

/**
 * Клетки, их которых состоит игровая доска
 *
 * @author LuneFox
 */
public class Cell extends GameObject {

    Cell() {
        setStaticView(Shape.FIELD_CELL_SHAPE);
    }

    /**
     * Отрисовка клетки 64 раза (получается доска 8х8, которая лежит по центру экрана)
     */
    void drawAsBoard() {
        for (int y = 1; y < 9; y++) {
            for (int x = 1; x < 9; x++) {
                setPosition(x * 10, y * 10);
                draw();
            }
        }
    }
}
