package com.javarush.games.ticktacktoe.model.gameobjects;

import com.javarush.games.ticktacktoe.view.shapes.Shape;

public class LegalMoveMark extends GameObject {
    public LegalMoveMark(double x, double y) {
        super(x, y);
        super.setStaticView(Shape.LEGAL_MOVE_MARK);
    }
}
