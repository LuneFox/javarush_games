package com.javarush.games.game2048.model;

import com.javarush.engine.cell.Color;
import com.javarush.games.game2048.Game2048;

import java.util.ArrayList;
import java.util.Arrays;

public class Field {
    public static final int SIDE = 7;
    private static final int EMPTY = 0;
    private static final int WHITE_BALL = 16;
    private static final int MAX_BALL = 15;
    private static final int RANDOM_BALL = -1;

    private final Game2048 game;
    private int[][] field;
    private final int width;
    private final int height;
    private ArrayList<Pocket> pockets;
    private int fieldRotationDegrees;
    private boolean isWhiteBallSet;

    public Field(Game2048 game) {
        this.game = game;
        field = new int[SIDE][SIDE];
        width = field[0].length;
        height = field.length;
        isWhiteBallSet = false;
        createPockets();
        putBallToRandomEmptyCell(RANDOM_BALL);
        putBallToRandomEmptyCell(RANDOM_BALL);
    }

    private void createPockets() {
        pockets = new ArrayList<>(Arrays.asList(
                new Pocket(0, 1), new Pocket(0, 5),
                new Pocket(6, 1), new Pocket(6, 5),
                new Pocket(3, 0), new Pocket(3, 6))
        );
    }

    private void putBallToRandomEmptyCell(int value) {
        int y = game.getRandomNumber(SIDE - 2) + 1;
        int x = game.getRandomNumber(SIDE - 2) + 1;

        if (value == RANDOM_BALL) {
            value = (game.getRandomNumber(10) > 8) ? 2 : 1;
        }

        if (field[y][x] != 0) {
            putBallToRandomEmptyCell(value);
        } else {
            field[y][x] = (value);
        }
    }

    public void draw() {
        drawBalls();
        drawPockets();
    }

    private void drawBalls() {
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                final int cell = field[y][x];
                final String text = (cell == 0) ? "" : String.valueOf(BallUtils.BALL_SYMBOLS[cell]);
                final Color color = BallUtils.getColorForBallNumber(cell);
                final int textSize = 75;

                game.setCellValueEx(x, y, Color.GREEN, text, color, textSize);
            }
        }
    }

    private void drawPockets() {
        for (Pocket pocket : pockets) {
            String text = pocket.getScore() == 0 ? Pocket.ICON : String.valueOf(pocket.getScore());
            Color color = pocket.getScore() == 0 ? Color.BLACK : Color.WHITE;
            int textSize = pocket.getScore() == 0 ? 75 : 40;

            game.setCellValueEx(pocket.x, pocket.y, Color.NONE, text, color, textSize);
        }
    }

    public int sumPocketsScore() {
        return pockets.stream()
                .mapToInt(Pocket::getScore)
                .sum();
    }

    public final void moveUp() {
        rotateClockwise(3);
        moveLeft();
        rotateClockwise(1);
    }

    public final void moveRight() {
        rotateClockwise(2);
        moveLeft();
        rotateClockwise(2);
    }

    public final void moveDown() {
        rotateClockwise(1);
        moveLeft();
        rotateClockwise(3);
    }

    public final void moveLeft() {
        int[][] fieldBeforeMove = copyField();

        pocketRow();

        for (int i = 0; i < field[0].length; i++) {
            compressRow(field[i]);
            mergeRow(field[i]);
            compressRow(field[i]);
        }

        boolean nothingChanged = fieldIsEqualTo(fieldBeforeMove);

        if (nothingChanged) return;

        putBallToRandomEmptyCell(RANDOM_BALL);
        game.increaseTurnCount();
        game.finishIfResultIsKnown();
    }

    private void pocketRow() {
        if (isWhiteBallSet) {
            final int whiteBallRowNumber = getWhiteBallRowNumber();
            final int[] row = field[whiteBallRowNumber];

            if ((fieldRotationDegrees == 0) && (whiteBallRowNumber == 1 || whiteBallRowNumber == 5)) {
                pocket(row, pockets.get(whiteBallRowNumber == 1 ? 0 : 1));
            } else if ((fieldRotationDegrees == 90) && (whiteBallRowNumber == 3)) {
                pocket(row, pockets.get(5));
            } else if ((fieldRotationDegrees == 180) && (whiteBallRowNumber == 1 || whiteBallRowNumber == 5)) {
                pocket(row, pockets.get(whiteBallRowNumber == 1 ? 3 : 2));
            } else if ((fieldRotationDegrees == 270) && (whiteBallRowNumber == 3)) {
                pocket(row, pockets.get(4));
            }
        }
    }

    private int getWhiteBallRowNumber() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (field[y][x] == WHITE_BALL) {
                    return y;
                }
            }
        }
        return 0;
    }

    private void pocket(int[] row, Pocket pocket) {
        boolean eightBallIsPocketed = false;

        if (pocket.isOpen()) {
            for (int i = 1; i < row.length; i++) {

                if (row[i] == WHITE_BALL) {
                    row[i] = EMPTY;
                    row[1] = WHITE_BALL;
                    break;
                }

                if (row[i] == 8) {
                    eightBallIsPocketed = true;
                }

                pocket.addScore(row[i]);
                row[i] = EMPTY;
            }
        }

        checkPocketingResult(eightBallIsPocketed);
    }

    private void checkPocketingResult(boolean eightBallIsPocketed) {
        if (eightBallIsPocketed && !allPocketsClosed()) {
            game.setResult(Result.LOSE, "8-й шар забит слишком рано!");
        } else if (eightBallIsPocketed && allPocketsClosed()) {
            game.setResult(Result.WIN, "Вы выиграли!");
        } else if (!eightBallIsPocketed && allPocketsClosed()) {
            game.setResult(Result.LOSE, "В последней лузе нет 8-го шара!");
        }
    }

    private boolean allPocketsClosed() {
        return pockets.stream().noneMatch(Pocket::isOpen);
    }

    private void compressRow(int[] row) {
        int[][] fieldBeforeCompress;

        do {
            fieldBeforeCompress = copyField();
            movePositiveNumbersToZerosOnTheLeft(row);
        } while (!fieldIsEqualTo(fieldBeforeCompress));
    }

    private void movePositiveNumbersToZerosOnTheLeft(int[] row) {
        for (int i = 1; i < row.length - 2; i++) {
            if (row[i] != EMPTY) continue;
            if (row[i + 1] == EMPTY) continue;

            row[i] = row[i + 1];
            row[i + 1] = EMPTY;
        }
    }

    private void mergeRow(int[] row) {
        for (int i = 1; i < row.length - 2; i++) {
            if (row[i] == EMPTY) continue;
            if (row[i] >= MAX_BALL) continue;
            if (row[i] != row[i + 1]) continue;

            row[i] += 1;
            row[i + 1] = 0;
        }
    }

    private void rotateClockwise(int times) {
        for (int i = 0; i < times; i++) {
            fieldRotationDegrees = (fieldRotationDegrees == 270 ? 0 : fieldRotationDegrees + 90);
            int[][] rotatedGameField = new int[SIDE][SIDE];

            for (int y = 1; y < field.length - 1; y++) {
                for (int x = 1; x < field[0].length - 1; x++) {
                    rotatedGameField[x][(field.length - 1) - y] = field[y][x];
                }
            }

            field = rotatedGameField;
        }
    }

    public final void emptyPocket(int pocketNumber) {
        Pocket pocket = pockets.get(pocketNumber);

        while (pocket.getScore() > 0) {
            if (pocket.getScore() <= MAX_BALL) {
                if (hasNoEmptySpace()) break;
                extractScoreAndPlaceBall(pocket, pocket.getScore() / 2);
                if (hasNoEmptySpace()) break;
                extractScoreAndPlaceBall(pocket, pocket.getScore());
            } else {
                putBallToRandomEmptyCell(MAX_BALL);
                pocket.removeScore(MAX_BALL);
            }
        }
    }

    private boolean hasNoEmptySpace() {
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                if (field[y][x] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private void extractScoreAndPlaceBall(Pocket pocket, int amount) {
        pocket.removeScore(amount);
        putBallToRandomEmptyCell(amount);
    }

    public void placeOrRemoveWhiteBall(int x, int y) {
        if (isWhiteBallSet && field[y][x] == WHITE_BALL) {
            field[y][x] = EMPTY;
            isWhiteBallSet = false;
        } else if (!isWhiteBallSet && field[y][x] == EMPTY) {
            field[y][x] = WHITE_BALL;
            isWhiteBallSet = true;
        }
    }

    private int[][] copyField() {
        int[][] copy = new int[height][width];
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                copy[y][x] = field[y][x];
            }
        }
        return copy;
    }

    private boolean fieldIsEqualTo(int[][] anotherField) {
        if (field.length != anotherField.length) return false;
        if (field[0].length != anotherField[0].length) return false;

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                if (field[y][x] != anotherField[y][x]) return false;
            }
        }

        return true;
    }

    public boolean isMovePossible() {
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {

                final int cell = field[y][x];
                final int belowCell = field[y + 1][x];
                final int rightCell = field[y][x + 1];

                if (cell == EMPTY || cell == WHITE_BALL) {
                    return true;
                } else {
                    if (cell == MAX_BALL) continue;

                    if (isNotLastLine(height, y)) {
                        if (isMergePossible(cell, belowCell)) return true;
                        if (isNotLastLine(width, x)) {
                            if (isMergePossible(cell, rightCell)) return true;
                        }
                    }

                    if (isNotLastLine(width, x)) {
                        if (isMergePossible(cell, rightCell)) return true;
                        if (isNotLastLine(height, y)) {
                            if (isMergePossible(cell, belowCell)) return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean isNotLastLine(int dimension, int line) {
        return line != (dimension - 2);
    }

    private static boolean isMergePossible(int cell1, int cell2) {
        return cell1 == cell2;
    }
}
