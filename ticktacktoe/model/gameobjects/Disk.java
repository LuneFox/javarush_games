package com.javarush.games.ticktacktoe.model.gameobjects;

import com.javarush.games.ticktacktoe.view.shapes.Shape;

public class Disk extends GameObject {
    private Side side;

    public Disk(double x, double y, Side side) {
        super(x, y);
        this.side = side;
        super.setStaticView(this.side == Side.BLACK ? Shape.BLACK_DISK : Shape.WHITE_DISK);
    }

    public void flip() {
        if (side == Side.BLACK) side = Side.WHITE;
        else if (side == Side.WHITE) side = Side.BLACK;

        //TODO:  flip logic and animations here
    }
}
