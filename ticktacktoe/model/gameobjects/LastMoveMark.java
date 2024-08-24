package com.javarush.games.ticktacktoe.model.gameobjects;

import com.javarush.games.ticktacktoe.view.shapes.Shape;

/**
 * Метка последнего хода
 *
 * @author LuneFox
 */
public class LastMoveMark extends GameObject {

    public LastMoveMark() {
        super.setStaticView(Shape.LAST_MOVE_MARK);
    }

    @Override
    public void draw() {
        if ((x != 0) && (y != 0)) {
            super.draw();
        }
    }
}
