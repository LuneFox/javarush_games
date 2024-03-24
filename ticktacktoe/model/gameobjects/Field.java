package com.javarush.games.ticktacktoe.model.gameobjects;

import com.javarush.games.ticktacktoe.view.shapes.Shape;

import java.util.ArrayList;

public class Field extends GameObject {
    private final static GameObject CELL = new GameObject();
    private final static GameObject TABLE = new GameObject();
    private final Disk[][] disks;
    private final ArrayList<LegalMoveMark> legalMoveMarks;
    private final LastMoveMark lastMoveMark;
    private boolean noMovesLeft;


    static {
        CELL.setStaticView(Shape.FIELD_CELL_SHAPE);
        TABLE.setStaticView(Shape.TABLE);
    }

    public Field() {
        this.disks = new Disk[8][8];
        legalMoveMarks = new ArrayList<>();
        lastMoveMark = new LastMoveMark();
        noMovesLeft = false;
    }

    @Override
    public void draw() {
        TABLE.draw();
        drawBoard();
        drawDisks();
        drawMoveMarks();
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
        getAllDisks().forEach(GameObject::draw);
    }

    private void drawMoveMarks() {
        legalMoveMarks.forEach(GameObject::draw);
        lastMoveMark.draw();
    }

    public void placeLastMoveMark(int x, int y) {
        lastMoveMark.setPosition(x * 10 + 15, y * 10 + 14);
    }

    public boolean moveIsLegal(int moveX, int moveY) {
        boolean legal = false;

        for (LegalMoveMark legalMoveMark : legalMoveMarks) {
            if (legalMoveMark.getBoardX() == moveX && legalMoveMark.getBoardY() == moveY) {
                legal = true;
                break;
            }
        }

        return legal;
    }

    public void checkAvailableMoves() {
        if (legalMoveMarks.isEmpty()) {
            game.changePlayer();

            if (legalMoveMarks.isEmpty()) {
                noMovesLeft = true;
            }
        }
    }

    public int countDisks(Side side) {
        return (int) getAllDisks().stream().filter(disk -> disk.getSide() == side).count();
    }

    private ArrayList<Disk> getAllDisks() {
        ArrayList<Disk> result = new ArrayList<>();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (disks[y][x] != null) {
                    result.add(disks[y][x]);
                }
            }
        }
        return result;
    }

    public boolean isOutOfBoard(int x, int y) {
        return (x < 0 || x > 7 || y < 0 || y > 7);
    }

    public boolean noMovesLeft() {
        return noMovesLeft;
    }

    public boolean noLegalMoves() {
        return legalMoveMarks.isEmpty();
    }

    public Disk[][] getDisks() {
        return disks;
    }

    public ArrayList<LegalMoveMark> getLegalMoveMarks() {
        return legalMoveMarks;
    }
}