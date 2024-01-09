package com.javarush.games.ticktacktoe.model.gameobjects;

import com.javarush.games.ticktacktoe.controller.Click;
import com.javarush.games.ticktacktoe.view.shapes.Shape;

public class Field extends GameObject {
    private final static GameObject CELL = new GameObject();
    private final static GameObject TABLE = new GameObject();
    private final Disk[][] disks;

    static {
        CELL.setStaticView(Shape.FIELD_CELL_SHAPE);
        TABLE.setStaticView(Shape.TABLE);
    }

    public Field() {
        this.disks = new Disk[8][8];
        putDisk(3, 3, Side.WHITE);
        putDisk(3, 4, Side.BLACK);
        putDisk(4, 3, Side.BLACK);
        putDisk(4, 4, Side.WHITE);
    }

    @Override
    public void draw() {
        drawTableAndBoard();
        drawDisks();
    }

    private void drawTableAndBoard() {
        TABLE.draw();

        for (int y = 1; y < 9; y++) {
            for (int x = 1; x < 9; x++) {
                CELL.setPosition(x * 10, y * 10);
                CELL.draw();
            }
        }
    }

    private void drawDisks() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (disks[y][x] != null) {
                    disks[y][x].draw();
                }
            }
        }
    }


    public void clickOnBoard(int mouseClickX, int mouseClickY, Click click) {
        int boardClickX = (mouseClickX - 10) / 10;
        int boardClickY = (mouseClickY - 10) / 10;

        if (click == Click.LEFT) {
            putDisk(boardClickX, boardClickY, Side.BLACK);
        } else {
            putDisk(boardClickX, boardClickY, Side.WHITE);
        }
    }

    private void putDisk(int x, int y, Side side) {
        if (disks[y][x] != null) {
            disks[y][x].flip();
            return;
        }

        disks[y][x] = new Disk(x * 10 + 11, y * 10 + 11, side);
    }
}
