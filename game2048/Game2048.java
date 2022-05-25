package com.javarush.games.game2048;

import com.javarush.engine.cell.*;
import com.javarush.games.game2048.controller.Controller;
import com.javarush.games.game2048.model.Result;
import com.javarush.games.game2048.model.Utils;
import com.javarush.games.game2048.model.Pocket;

import java.util.ArrayList;
import java.util.Arrays;

public class Game2048 extends Game {
    private static final String VERSION = "v0.96";
    private static final int SIDE = 7;

    private final Controller controller = new Controller(this);
    private ArrayList<Pocket> pockets;
    private Result result;

    private int[][] field;
    private int width;
    private int height;
    private int rotateDegree;
    private int score;
    private int turnCount;

    private boolean winFlag = false; // сначала даёт завершить ход, и только потом разрешает рисовать победу
    private boolean isWhiteBallSet = false;
    public boolean isStopped = false;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(SIDE, SIDE);
        createNewGame();
    }

    public void createNewGame() {
        resetValues();
        createField();
        createPockets();
        createNewBall();
        createNewBall();
        drawScene();
    }

    private void resetValues() {
        result = Result.NONE;
        winFlag = false;
        isStopped = false;
        isWhiteBallSet = false;
        score = 0;
        turnCount = 0;
    }

    private void createField() {
        field = new int[SIDE][SIDE];
        width = field[0].length;
        height = field.length;
    }

    private void createPockets() {
        pockets = new ArrayList<>(Arrays.asList(
                new Pocket(0, 1), new Pocket(0, 5),
                new Pocket(6, 1), new Pocket(6, 5),
                new Pocket(3, 0), new Pocket(3, 6)));
    }

    private void createNewBall() {
        int y = getRandomNumber(SIDE - 2) + 1;
        int x = getRandomNumber(SIDE - 2) + 1;

        if (field[y][x] != 0) {
            createNewBall();
        } else {
            field[y][x] = (getRandomNumber(10) > 8 ? 2 : 1);
        }
    }

    public final void drawScene() {
        drawBackground();
        drawBalls();
        drawPockets();
        drawInfo();
    }

    private void drawBackground() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                setCellColor(x, y, Color.DARKGREEN);
            }
        }
    }

    private void drawBalls() {
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                drawBall(x, y, field[y][x]);
            }
        }
    }

    private void drawBall(int x, int y, int ballNumber) {
        final int FONT_SIZE = 75;
        setCellValueEx(x, y, Color.GREEN, (ballNumber == 0
                        ? ""
                        : String.valueOf(Utils.BALL_SYMBOLS[ballNumber])),
                Utils.getColorForBallNumber(ballNumber), FONT_SIZE);
    }

    private void drawPockets() {
        for (Pocket pocket : pockets) {
            setCellValueEx(
                    pocket.x, pocket.y, Color.NONE,
                    pocket.score == 0 ? Pocket.ICON : String.valueOf(pocket.score),
                    pocket.score == 0 ? Color.BLACK : Color.WHITE,
                    pocket.score == 0 ? 75 : 40);
        }
    }

    private void drawInfo() {
        final int FONT_SIZE = 15;
        setCellValueEx(6, 0, Color.NONE, "Ходы: " + turnCount, Color.LAWNGREEN, FONT_SIZE);
        setCellValueEx(6, 6, Color.NONE, VERSION, Color.GREEN, FONT_SIZE);
    }

    public void finish(String message) {
        if (result == Result.WIN) {
            win();
        } else if (result == Result.LOSE) {
            lose(message);
        }
    }

    private void win() {
        result = Result.WIN;
        isStopped = true;
        score = sumPocketsScore();
        showMessageDialog(Color.BLACK, "Победа! Счёт: " + ((score * 100) / turnCount), Color.PALEGOLDENROD, 30);
    }

    private int sumPocketsScore() {
        return pockets.stream()
                .mapToInt(pocket -> pocket.score)
                .sum();
    }

    public void lose(String message) {
        result = Result.LOSE;
        isStopped = true;
        showMessageDialog(Color.BLACK, "Вы проиграли!\n" + message, Color.RED, 30);
    }

    private void placeBall(int value) {
        int y = getRandomNumber(SIDE - 2) + 1;
        int x = getRandomNumber(SIDE - 2) + 1;

        if (field[y][x] != 0) {
            placeBall(value);
        } else {
            field[y][x] = (value);
        }
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
        int[][] fieldBeforeMove = Utils.copyField(field);

        pocketRow();

        for (int i = 0; i < field[0].length; i++) {
            compressRow(field[i]);
            mergeRow(field[i]);
            compressRow(field[i]);
        }

        boolean nothingChanged = Utils.fieldsAreEqual(field, fieldBeforeMove);

        if (nothingChanged) return;

        createNewBall();
        turnCount++;

        if (winFlag) {
            win();
        }
    }

    private void pocketRow() {
        // в зависимости от угла поворота поля шары забиваются в разные лузы
        if (isWhiteBallSet) {
            int whiteBallRow = getWhiteBallRow();
            if ((rotateDegree == 0) && (whiteBallRow == 1 || whiteBallRow == 5)) {
                pocket(field[whiteBallRow], pockets.get(whiteBallRow == 1 ? 0 : 1));
            } else if ((rotateDegree == 90) && (whiteBallRow == 3)) {
                pocket(field[whiteBallRow], pockets.get(5));
            } else if ((rotateDegree == 180) && (whiteBallRow == 1 || whiteBallRow == 5)) {
                pocket(field[whiteBallRow], pockets.get(whiteBallRow == 1 ? 3 : 2));
            } else if ((rotateDegree == 270) && (whiteBallRow == 3)) {
                pocket(field[whiteBallRow], pockets.get(4));
            }
        }
    }

    private void pocket(int[] row, Pocket pocket) {
        boolean pocketHasEightBall = false;

        if (pocket.isOpen()) {
            for (int i = 1; i < row.length; i++) {

                if (row[i] == 16) {
                    row[i] = 0;
                    row[1] = 16;
                    break;
                }

                if (row[i] == 8) {
                    pocketHasEightBall = true;
                }

                pocket.score += row[i];
                row[i] = 0;

                if (pocket.score > 0) {
                    pocket.close();
                }

            }
        }

        checkGameOverFromPocketing(pocketHasEightBall);
    }

    private void checkGameOverFromPocketing(boolean pocketHasEightBall) {
        if (pocketHasEightBall && countOpenPockets() > 0) {
            lose("8-ка забита слишком рано!");
        } else if (pocketHasEightBall && countOpenPockets() == 0) {
            winFlag = true;
        } else if (!pocketHasEightBall && countOpenPockets() == 0) {
            lose("В последней лузе нет 8-ки!");
        }
    }

    private long countOpenPockets() {
        return pockets.stream().filter(Pocket::isOpen).count();
    }

    private void compressRow(int[] row) {
        int[][] fieldBeforeCompress;

        do {
            fieldBeforeCompress = Utils.copyField(field);
            movePositiveNumbersToZerosOnTheLeft(row);
        } while (!Utils.fieldsAreEqual(field, fieldBeforeCompress));
    }

    private void movePositiveNumbersToZerosOnTheLeft(int[] row) {
        for (int i = 1; i < row.length - 2; i++) {
            if (row[i] != 0) continue;
            if (row[i + 1] == 0) continue;

            row[i] = row[i + 1];
            row[i + 1] = 0;
        }
    }

    private void mergeRow(int[] row) {
        for (int i = 1; i < row.length - 2; i++) {
            if (row[i] == 0) continue;
            if (row[i] >= 15) continue;
            if (row[i] != row[i + 1]) continue;

            row[i] += 1;
            row[i + 1] = 0;
        }
    }

    private void rotateClockwise(int times) {
        for (int i = 0; i < times; i++) {
            rotateDegree = (rotateDegree == 270 ? 0 : rotateDegree + 90);
            int[][] rotatedGameField = new int[SIDE][SIDE];
            for (int y = 1; y < field.length - 1; y++) { // копирование матрицы с поворотом
                for (int x = 1; x < field[0].length - 1; x++) {
                    rotatedGameField[x][(field.length - 1) - y] = field[y][x];
                }
            }
            field = rotatedGameField; // замена оригинальной матрицы новой
        }
    }

    public final boolean canUserMove() {
        for (int y = 1; y < SIDE - 1; y++) {
            for (int x = 1; x < SIDE - 1; x++) {
                // пробегаемся по всем шарам построчно
                if (field[y][x] == 0 || field[y][x] == 16) {
                    // если ячейка пуста или на ней стоит белый шар (который можно убрать)
                    return true;
                } else {
                    if (field[y][x] == 15) {
                        // если шар 15-й, пропустить итерацию (его нельзя сливать ни с чем)
                        continue;
                    }
                    if (field[y + 1][x] == field[y][x]) {
                        // если шар ниже такой же, как и этот
                        if (y == SIDE - 2) {
                            // если шаров ниже нет, пропустить итерацию
                            continue;
                        }
                        return true;
                    }
                    if (field[y][x + 1] == field[y][x]) {
                        // если шар правее такой же, как и этот
                        if (x == SIDE - 2) {
                            // если шаров правее нет, пропустить итерацию
                            continue;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final void emptyPocket(int pocketNumber) {
        Pocket pocket = pockets.get(pocketNumber);

        while (pocket.score > 0 && hasEmptySpace()) {
            if (pocket.score <= 15) {
                placeBall(pocket.score / 2);
                pocket.score -= pocket.score / 2;

                if (!hasEmptySpace()) {
                    break;
                }

                placeBall(pocket.score);
                pocket.score -= pocket.score;
            } else {
                placeBall(15);
                pocket.score -= 15;
            }
        }

        if (pocket.score == 0) {
            pocket.open();
        }
    }

    private boolean hasEmptySpace() {
        for (int y = 1; y < SIDE - 1; y++) {
            for (int x = 1; x < SIDE - 1; x++) {
                if (field[y][x] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getWhiteBallRow() {
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[0].length; x++) {
                if (field[y][x] == 16) {
                    return y;
                }
            }
        }
        return 0;
    }

    public void placeOrRemoveWhiteBall(int x, int y) {
        if (isWhiteBallSet && field[y][x] == 16) {
            field[y][x] = 0;
            isWhiteBallSet = false;
        } else if (!isWhiteBallSet && field[y][x] == 0) {
            field[y][x] = 16;
            isWhiteBallSet = true;
        }
    }

    public int[][] getField() {
        return field;
    }

    /*
     * Controls
     */

    @Override
    public void onKeyPress(Key key) {
        controller.pressKey(key);
    }

    @Override
    public void onKeyReleased(Key key) {
        controller.releaseKey(key);
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        controller.leftClick(x, y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        controller.rightClick(x, y);
    }
}