package com.javarush.games.ticktacktoe.model.gameobjects;

import com.javarush.games.ticktacktoe.controller.Click;
import com.javarush.games.ticktacktoe.view.shapes.Shape;

import java.util.ArrayList;
import java.util.LinkedList;

public class Field extends GameObject {
    private final static GameObject CELL = new GameObject();
    private final static GameObject TABLE = new GameObject();
    private final Disk[][] disks;
    private final ArrayList<LegalMoveMark> legalMoveMarks;

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
        markLegalMoves();
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
        if (click == Click.LEFT) {
            putDisk(mouseToBoard(mouseClickX), mouseToBoard(mouseClickY));
        }
    }

    private int mouseToBoard(int value) {
        // converts mouse click coordinate into board click coordinate from 0 to 7
        return ((value - 10) / 10);
    }

    private void putDisk(int x, int y) {
        if (disks[y][x] != null) return;
        if (moveIsNotLegal(x, y)) return;

        disks[y][x] = new Disk(x * 10 + 11, y * 10 + 11, game.getCurrentPlayer());
        flipEnemyDisks(x, y);
        game.changePlayer();
        markLegalMoves();
    }

    private boolean moveIsNotLegal(int x, int y) {
        boolean allowMove = false;

        for (LegalMoveMark legalMoveMark : legalMoveMarks) {
            if (mouseToBoard((int) legalMoveMark.x) == x && mouseToBoard((int) legalMoveMark.y) == y) {
                allowMove = true;
                break;
            }
        }

        return !allowMove;
    }

    private void flipEnemyDisks(int diskX, int diskY) {
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (x == 0 && y == 0) continue;
                flipInDirection(diskX, diskY, x, y);
            }
        }
    }

    private void flipInDirection(int diskX, int diskY, int dirX, int dirY) {
        LinkedList<Disk> line = getLineToFlip(diskX, diskY, dirX, dirY);

        if (!line.isEmpty()) {
            line.forEach(Disk::flip);
        }
    }

    private void markLegalMoves() {
        legalMoveMarks.clear();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (disks[y][x] != null) continue;
                if (checkIfMoveIsLegal(x, y)) {
                    legalMoveMarks.add(new LegalMoveMark(x * 10 + 15, y * 10 + 15));
                }
            }
        }
        legalMoveMarks.forEach(GameObject::draw);
    }

    private boolean checkIfMoveIsLegal(int posX, int posY) {
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (x == 0 && y == 0) {
                    continue;
                }

                if (checkInDirection(posX, posY, x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkInDirection(int posX, int posY, int dirX, int dirY) {
        LinkedList<Disk> line = getLineToFlip(posX, posY, dirX, dirY);
        return !line.isEmpty();
    }

    private LinkedList<Disk> getLineToFlip(int diskX, int diskY, int dirX, int dirY) {
        // Keeps adding disks in the line until it hits the player's disk or the edge.
        // If it meets player's disk, returns the line. Otherwise, returns an empty line (nothing).

        LinkedList<Disk> line = new LinkedList<>();

        diskX += dirX;
        diskY += dirY;

        while (!isOutOfBoard(diskX, diskY)) {
            Disk disk = disks[diskY][diskX];

            if (disk == null) {
                break;
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

    private boolean isOutOfBoard(int x, int y) {
        return (x < 0 || x > 7 || y < 0 || y > 7);
    }

    /*
     * Getters
     */

    public Disk[][] getDisks() {
        return disks;
    }

}