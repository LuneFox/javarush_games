package com.javarush.games.ticktacktoe.model.gameobjects;

import com.javarush.games.ticktacktoe.view.shapes.Shape;

public class Disk extends GameObject {
    private Side side;

    public Disk(double x, double y, Side side) {
        super(x, y);
        this.side = side;
        setView();
    }

    public void flip() {
        if (side == Side.BLACK) {
            side = Side.WHITE;
            setAnimatedView(Sprite.Loop.DISABLED, 1,
                    Shape.BLACK_DISK_FLIP_1,
                    Shape.BLACK_DISK_FLIP_2,
                    Shape.BLACK_DISK_FLIP_3,
                    Shape.BLACK_DISK_FLIP_4,
                    Shape.BLACK_DISK_FLIP_5);
        } else if (side == Side.WHITE) {
            side = Side.BLACK;
            setAnimatedView(Sprite.Loop.DISABLED, 1,
                    Shape.WHITE_DISK_FLIP_1,
                    Shape.WHITE_DISK_FLIP_2,
                    Shape.WHITE_DISK_FLIP_3,
                    Shape.WHITE_DISK_FLIP_4,
                    Shape.WHITE_DISK_FLIP_5);
        }
    }

    private void setView() {
        super.setStaticView(this.side == Side.BLACK ? Shape.BLACK_DISK : Shape.WHITE_DISK);
    }
}
