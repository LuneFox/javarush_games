package com.javarush.games.ticktacktoe.model.gameobjects;

import com.javarush.games.ticktacktoe.view.shapes.Shape;

public class Disk extends GameObject {

    public Disk(double x, double y, Side side) {
        super(x, y);
        super.setStaticView(side == Side.BLACK ? Shape.BLACK_DISK : Shape.WHITE_DISK);
        System.out.println("Disk created");
    }
}
