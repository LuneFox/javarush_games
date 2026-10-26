package com.javarush.games.game2048.model;

import com.javarush.engine.cell.Color;
import com.javarush.games.game2048.Game2048;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The 7x7 game field with six pockets on the border. Holds the ball grid,
 * applies 2048-style moves (compress + merge toward the wall, implemented via
 * rotations around a left move), pockets balls pushed by the white ball,
 * evaluates win/loss after each pocketing, and exposes move-availability checks.
 *
 * <p>Cell values: 0 = empty, 1-15 = billiard balls, 16 = white (cue) ball.
 */
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
    private int rotationDegrees;
    private boolean isWhiteBallSet;

    /**
     * Creates a field with six border pockets and places two random balls
     * (mostly 1s, occasionally a 2) on it.
     *
     * @param game the game instance used for rendering and random numbers
     */
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

    /**
     * Creates the six pockets on the field border (corners skipped, midpoints of each side).
     */
    private void createPockets() {
        pockets = new ArrayList<>(Arrays.asList(
                new Pocket(0, 1), new Pocket(0, 5),
                new Pocket(6, 1), new Pocket(6, 5),
                new Pocket(3, 0), new Pocket(3, 6))
        );
    }

    /**
     * Places a ball of the given value into a random empty cell inside the
     * board (border cells belong to the pockets).
     *
     * @param value the ball value to place, or {@link #RANDOM_BALL} to pick a
     *              random value (2 with 20% chance, otherwise 1)
     */
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

    /**
     * Draws the balls and the pockets onto the board.
     */
    public void draw() {
        drawBalls();
        drawPockets();
    }

    /**
     * Draws every ball inside the board with its unicode symbol and billiard color.
     */
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

    /**
     * Draws each pocket: the round icon when open, its score when closed.
     */
    private void drawPockets() {
        for (Pocket pocket : pockets) {
            String text = pocket.getScore() == 0 ? Pocket.ICON : String.valueOf(pocket.getScore());
            Color color = pocket.getScore() == 0 ? Color.BLACK : Color.WHITE;
            int textSize = pocket.getScore() == 0 ? 75 : 40;

            game.setCellValueEx(pocket.x, pocket.y, Color.NONE, text, color, textSize);
        }
    }

    /**
     * @return the total score accumulated in all pockets
     */
    public int sumPocketsScore() {
        return pockets.stream()
                .mapToInt(Pocket::getScore)
                .sum();
    }

    /**
     * Moves all balls up by rotating the field so the top wall becomes the
     * left wall, performing a left move, and rotating back.
     */
    public final void moveUp() {
        rotateClockwise(3);
        moveLeft();
        rotateClockwise(1);
    }

    /**
     * Moves all balls right by rotating the field 180°, performing a left
     * move, and rotating back.
     */
    public final void moveRight() {
        rotateClockwise(2);
        moveLeft();
        rotateClockwise(2);
    }

    /**
     * Moves all balls down by rotating the field 90°, performing a left
     * move, and rotating back.
     */
    public final void moveDown() {
        rotateClockwise(1);
        moveLeft();
        rotateClockwise(3);
    }

    /**
     * Moves all balls to the left: pockets the row pushed by the white ball,
     * compresses and merges each row (2048 rules), and if the field changed —
     * spawns a new random ball, increments the turn count and checks the result.
     */
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
        game.finishIfResultIsAchieved();
    }

    /**
     * If the white ball is placed, pockets the balls of its row into the
     * pocket it is aimed at (depends on the current field rotation).
     */
    private void pocketRow() {
        if (isWhiteBallSet) {
            final int whiteBallRowNumber = getWhiteBallRowNumber();
            final int[] row = field[whiteBallRowNumber];

            if ((rotationDegrees == 0) && (whiteBallRowNumber == 1 || whiteBallRowNumber == 5)) {
                pocket(row, pockets.get(whiteBallRowNumber == 1 ? 0 : 1));
            } else if ((rotationDegrees == 90) && (whiteBallRowNumber == 3)) {
                pocket(row, pockets.get(5));
            } else if ((rotationDegrees == 180) && (whiteBallRowNumber == 1 || whiteBallRowNumber == 5)) {
                pocket(row, pockets.get(whiteBallRowNumber == 1 ? 3 : 2));
            } else if ((rotationDegrees == 270) && (whiteBallRowNumber == 3)) {
                pocket(row, pockets.get(4));
            }
        }
    }

    /**
     * @return the row index containing the white ball, or 0 if it is not placed
     */
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

    /**
     * Pockets all balls of the row into the given pocket if it is open (the
     * white ball is reset to the left edge of the row instead) and evaluates
     * the pocketing result with respect to the 8-ball.
     *
     * @param row    the row being moved left
     * @param pocket the pocket at the end of the row
     */
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

    /**
     * Evaluates the game outcome after the 8-ball has been pocketed: win if it
     * went into the last open pocket, loss if it was pocketed too early or the
     * last pocket was closed without it.
     *
     * @param eightBallIsPocketed whether the 8-ball was among the pocketed balls
     */
    private void checkPocketingResult(boolean eightBallIsPocketed) {
        if (eightBallIsPocketed && !allPocketsClosed()) {
            game.setResult(Result.LOSE, "8-й шар забит слишком рано!");
        } else if (eightBallIsPocketed && allPocketsClosed()) {
            game.setResult(Result.WIN, "Вы выиграли!");
        } else if (!eightBallIsPocketed && allPocketsClosed()) {
            game.setResult(Result.LOSE, "В последней лузе нет 8-го шара!");
        }
    }

    /**
     * @return whether every pocket holds at least one ball (score > 0)
     */
    private boolean allPocketsClosed() {
        return pockets.stream().noneMatch(Pocket::isOpen);
    }

    /**
     * Shifts all balls of the row to the left so that empty cells end up on the right.
     *
     * @param row the row to compress
     */
    private void compressRow(int[] row) {
        int[][] fieldBeforeCompress;

        do {
            fieldBeforeCompress = copyField();
            movePositiveNumbersToZerosOnTheLeft(row);
        } while (!fieldIsEqualTo(fieldBeforeCompress));
    }

    /**
     * Pass over the row shifting each ball one cell into an empty cell on its left.
     *
     * @param row the row to shift
     */
    private void movePositiveNumbersToZerosOnTheLeft(int[] row) {
        for (int i = 1; i < row.length - 2; i++) {
            if (row[i] != EMPTY) continue;
            if (row[i + 1] == EMPTY) continue;

            row[i] = row[i + 1];
            row[i + 1] = EMPTY;
        }
    }

    /**
     * Merges equal adjacent balls into a ball of the next value (2048 rules);
     * the max-value ball (15) never merges.
     *
     * @param row the row to merge
     */
    private void mergeRow(int[] row) {
        for (int i = 1; i < row.length - 2; i++) {
            if (row[i] == EMPTY) continue;
            if (row[i] >= MAX_BALL) continue;
            if (row[i] != row[i + 1]) continue;

            row[i] += 1;
            row[i + 1] = 0;
        }
    }

    /**
     * Rotates the field clockwise by 90° the given number of times and updates
     * the tracked rotation angle.
     *
     * @param times number of quarter-turns (1-3)
     */
    private void rotateClockwise(int times) {
        for (int i = 0; i < times; i++) {
            rotationDegrees = (rotationDegrees == 270 ? 0 : rotationDegrees + 90);
            int[][] rotatedGameField = new int[SIDE][SIDE];

            for (int y = 1; y < field.length - 1; y++) {
                for (int x = 1; x < field[0].length - 1; x++) {
                    rotatedGameField[x][(field.length - 1) - y] = field[y][x];
                }
            }

            field = rotatedGameField;
        }
    }

    /**
     * Returns all balls stored in the given pocket back onto the field as the
     * largest possible balls, splitting the pocket score in halves while space allows.
     *
     * @param pocketNumber the pocket index (0-5)
     */
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

    /**
     * @return whether there are no empty cells left inside the board
     */
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

    /**
     * Subtracts the given amount from the pocket score and places a ball of
     * that value onto a random empty cell.
     *
     * @param pocket the pocket to take the score from
     * @param amount the amount to take and the value of the placed ball
     */
    private void extractScoreAndPlaceBall(Pocket pocket, int amount) {
        pocket.removeScore(amount);
        putBallToRandomEmptyCell(amount);
    }

    /**
     * Toggles the white (cue) ball on the given cell: places it if none is
     * placed yet and the cell is empty, removes it if the cell holds the ball.
     *
     * @param x the column of the cell
     * @param y the row of the cell
     */
    public void placeOrRemoveWhiteBall(int x, int y) {
        if (isWhiteBallSet && field[y][x] == WHITE_BALL) {
            field[y][x] = EMPTY;
            isWhiteBallSet = false;
        } else if (!isWhiteBallSet && field[y][x] == EMPTY) {
            field[y][x] = WHITE_BALL;
            isWhiteBallSet = true;
        }
    }

    /**
     * @return a copy of the field with only the inner board cells copied
     */
    private int[][] copyField() {
        int[][] copy = new int[height][width];
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                copy[y][x] = field[y][x];
            }
        }
        return copy;
    }

    /**
     * @param anotherField the field to compare against
     * @return whether the inner board cells of both fields are equal
     */
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

    /**
     * @return whether any move is possible: an empty cell, the white ball or a
     *              mergeable pair of adjacent balls exists
     */
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

    /**
     * @param dimension the field dimension
     * @param line      the line index
     * @return whether the line is not the last playable one
     */
    private static boolean isNotLastLine(int dimension, int line) {
        return line != (dimension - 2);
    }

    /**
     * @param cell1 the first cell value
     * @param cell2 the second cell value
     * @return whether the two cells hold equal balls and can be merged
     */
    private static boolean isMergePossible(int cell1, int cell2) {
        return cell1 == cell2;
    }
}
