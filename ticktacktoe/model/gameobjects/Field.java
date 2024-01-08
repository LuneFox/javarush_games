package com.javarush.games.ticktacktoe.model.gameobjects;

import com.javarush.games.ticktacktoe.view.shapes.Shape;

import java.util.Arrays;

public class Field extends GameObject {
    private final static GameObject CELL = new GameObject();
    private final static GameObject TABLE = new GameObject();
    private final Disk[][] disks;

    static {
        CELL.setStaticView(Shape.FIELD_CELL_SHAPE);
        fillBackground();
    }

    public Field() {
        this.disks = new Disk[8][8];
    }

    private static void fillBackground() {
        int[][] bg = new int[100][100];
        int[] row = new int[100];
        int color = 45;
        Arrays.fill(row, color);
        Arrays.fill(bg, row);
        TABLE.setStaticView(bg);
    }

    @Override
    public void draw() {
        drawBoard();
        drawDisks();
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

    private void drawBoard() {
        TABLE.draw();

        for (int y = 1; y < 9; y++) {
            for (int x = 1; x < 9; x++) {
                CELL.setPosition(x * 10, y * 10);
                CELL.draw();
            }
        }
    }

    public void putDisk(int x, int y, Side side) {
        disks[y][x] = new Disk(x * 10 + 11, y * 10 + 11, side);
    }
}
