package com.javarush.games.ticktacktoe.model;

import com.javarush.games.ticktacktoe.TicTacToeGame;
import com.javarush.games.ticktacktoe.model.gameobjects.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class BoardManager {
    private final TicTacToeGame game;
    private final Field field;
    private final Disk[][] disks;

    public BoardManager(TicTacToeGame game, Field field) {
        this.game = game;
        this.field = field;
        this.disks = field.getDisks();
    }

    public void setupNewGame() {
        putStartingDisks();
        markLegalMoves();
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

    public void doManualTurn(int x, int y) {
        if (!field.moveIsLegal(x, y)) return;
        if (game.isComputerTurn()) return;

        makeTurn(x, y);
    }

    public void doAutomaticTurn() {
        if (field.noLegalMoves()) return;

        final Move move = getOptimalMove();
        makeTurn(move.getX(), move.getY());
    }

    private Move getOptimalMove() {
        // does random placements except when corners are available
        final ArrayList<LegalMoveMark> bestMoveMarks = getBestMoveMarks();
        final ArrayList<LegalMoveMark> legalMoveMarks = field.getLegalMoveMarks();
        LegalMoveMark optimalMove =
                bestMoveMarks.isEmpty() ?
                        getRandomMark(legalMoveMarks) : getRandomMark(bestMoveMarks);
        return new Move(optimalMove.getBoardX(), optimalMove.getBoardY());
    }

    private ArrayList<LegalMoveMark> getBestMoveMarks() {
        return field.getLegalMoveMarks().stream()
                .filter(legalMoveMark -> (
                        legalMoveMark.getBoardX() == 0 && legalMoveMark.getBoardY() == 0)
                        || (legalMoveMark.getBoardX() == 0 && legalMoveMark.getBoardY() == 7)
                        || (legalMoveMark.getBoardX() == 7 && legalMoveMark.getBoardY() == 0)
                        || (legalMoveMark.getBoardX() == 7 && legalMoveMark.getBoardY() == 7))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public LegalMoveMark getRandomMark(ArrayList<LegalMoveMark> marks) {
        int availableMovesNumber = marks.size();
        int randomMove = (int) (Math.random() * availableMovesNumber);
        return marks.get(randomMove);
    }

    private void makeTurn(int x, int y) {
        game.start();
        putDiskAndFlip(x, y);
        game.changePlayer();
        field.placeLastMoveMark(x, y);
        field.checkAvailableMoves();
    }

    private void putDiskAndFlip(int x, int y) {
        putDisk(x, y);
        flipEnemyDisks(x, y);
    }

    private void putDisk(int x, int y) {
        if (disks[y][x] != null) return;

        Side diskSide = game.getCurrentPlayer();
        disks[y][x] = new Disk(x * 10 + 11, y * 10 + 11, diskSide);
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

    public void markLegalMoves() {
        ArrayList<LegalMoveMark> marks = field.getLegalMoveMarks();
        marks.clear();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (disks[y][x] != null) continue;
                if (checkIfMoveIsLegal(x, y)) {
                    marks.add(new LegalMoveMark(x * 10 + 15, y * 10 + 15));
                }
            }
        }

        marks.forEach(mark -> {
            mark.matchPlayerColor();
            mark.draw();
        });
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

        while (!field.isOutOfBoard(diskX, diskY)) {
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

    private static class Move {
        private final int x;
        private final int y;

        public Move(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
