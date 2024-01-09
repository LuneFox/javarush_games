package com.javarush.games.ticktacktoe.model.gameobjects;

import com.javarush.games.ticktacktoe.controller.Click;
import com.javarush.games.ticktacktoe.view.shapes.Shape;

import java.util.ArrayList;
import java.util.LinkedList;

public class Field extends GameObject {
    private final static GameObject CELL = new GameObject();
    private final static GameObject TABLE = new GameObject();
    private final Disk[][] disks;
    private ArrayList<LegalMoveMark> legalMoveMarks;

    static {
        CELL.setStaticView(Shape.FIELD_CELL_SHAPE);
        TABLE.setStaticView(Shape.TABLE);
    }

    public Field() {
        this.disks = new Disk[8][8];
        legalMoveMarks = new ArrayList<>();
        putStartingDisks();
    }

    private void putStartingDisks() {
        putStartingDisk(3, 3, Side.WHITE);
        putStartingDisk(4, 4, Side.WHITE);
        putStartingDisk(3, 4, Side.BLACK);
        putStartingDisk(4, 3, Side.BLACK);
    }

    private void putStartingDisk(int x, int y, Side side) {
        disks[y][x] = new Disk(x * 10 + 11, y * 10 + 11, side);
    }

    @Override
    public void draw() {
        TABLE.draw();
        drawBoard();
        drawDisks();
        legalMoveMarks.forEach(GameObject::draw);
    }

    private static void drawBoard() {
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
        putDisk(mouseToBoard(mouseClickX), mouseToBoard(mouseClickY));

    }

    private int mouseToBoard(int value) {
        // converts mouse click coordinate into board click coordinate from 0 to 7
        return ((value - 10) / 10);
    }

    private void putDisk(int x, int y) {
        if (disks[y][x] != null) {
            return;
        }

        disks[y][x] = new Disk(x * 10 + 11, y * 10 + 11, game.getCurrentPlayer());
        flipEnemyDisks(x, y);
        game.changePlayer();
    }

    private void flipEnemyDisks(int diskX, int diskY) {
        flipInDirection(diskX, diskY, -1, 0);
        flipInDirection(diskX, diskY, 0, -1);
        flipInDirection(diskX, diskY, 1, 0);
        flipInDirection(diskX, diskY, 0, 1);
        flipInDirection(diskX, diskY, 1, 1);
        flipInDirection(diskX, diskY, -1, -1);
        flipInDirection(diskX, diskY, 1, -1);
        flipInDirection(diskX, diskY, -1, 1);
    }

    private void flipInDirection(int diskX, int diskY, int dirX, int dirY) {
        LinkedList<Disk> line = getLineToFlip(diskX, diskY, dirX, dirY);

        if (!line.isEmpty()) {
            line.forEach(Disk::flip);
        }
    }

    private boolean isOutOfBoard(int x, int y) {
        return (x < 0 || x > 7 || y < 0 || y > 7);
    }

    private LinkedList<Disk> getLineToFlip(int diskX, int diskY, int dirX, int dirY) {
        LinkedList<Disk> line = new LinkedList<>();

        diskX += dirX;
        diskY += dirY;

        while (!isOutOfBoard(diskX, diskY)) {
            Disk disk = disks[diskY][diskX];

            if (disk == null) {
                line.clear();
                return line;
            }

            if (disk.getSide() == game.getCurrentPlayer()) {
                return line;
            }

            line.add(disk);
            diskX += dirX;
            diskY += dirY;
        }

        line.clear();
        return line;
    }

    private void markLegalMoves() {
        System.out.println("Method in");
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {

            }
        }
    }

    /*
     * Getters
     */

    public Disk[][] getDisks() {
        return disks;
    }

}
