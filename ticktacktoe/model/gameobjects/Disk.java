package com.javarush.games.ticktacktoe.model.gameobjects;

import com.javarush.games.ticktacktoe.view.shapes.Shape;

public class Disk extends GameObject {

    public Disk(double x, double y) {
        super(x, y);
        super.setStaticView(Shape.BLACK_DISK);
        System.out.println("Disk created");
    }
}
