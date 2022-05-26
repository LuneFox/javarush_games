package com.javarush.games.game2048;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.game2048.controller.Controller;
import com.javarush.games.game2048.model.BallUtils;
import com.javarush.games.game2048.model.FieldUtils;
import com.javarush.games.game2048.model.Pocket;
import com.javarush.games.game2048.model.Result;

import java.util.ArrayList;
import java.util.Arrays;

public class Game2048 extends Game {
    public static final int EMPTY = 0;
    public static final int WHITE_BALL = 16;
    public static final int MAX_BALL = 15;

    public static final String VERSION = "v0.97";
    private static final int SIDE = 7;

    private final Controller controller = new Controller(this);
    private ArrayList<Pocket> pockets;
    private Result result;
    private String resultMessage;

    private int[][] field;
    private int width;
    private int height;
    private int fieldRotationDegrees;
    private int score;
    private int turnCount;

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
                new Pocket(3, 0), new Pocket(3, 6))
        );
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
                setCellColor(x, y, Color.SADDLEBROWN);
            }
        }
    }

    private void drawBalls() {
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                final int cell = field[y][x];
                final String text = (cell == 0) ? "" : String.valueOf(BallUtils.BALL_SYMBOLS[cell]);
                final Color color = BallUtils.getColorForBallNumber(cell);
                final int textSize = 75;

                setCellValueEx(x, y, Color.GREEN, text, color, textSize);
            }
        }
    }

    private void drawPockets() {
        for (Pocket pocket : pockets) {
            String text = pocket.getScore() == 0 ? Pocket.ICON : String.valueOf(pocket.getScore());
            Color color = pocket.getScore() == 0 ? Color.BLACK : Color.WHITE;
            int textSize = pocket.getScore() == 0 ? 75 : 40;

            setCellValueEx(pocket.x, pocket.y, Color.NONE, text, color, textSize);
        }
    }

    private void drawInfo() {
        final int FONT_SIZE = 15;
        setCellValueEx(6, 0, Color.NONE, "Ходы: " + turnCount, Color.LAWNGREEN, FONT_SIZE);
        setCellValueEx(6, 6, Color.NONE, VERSION, Color.YELLOW, FONT_SIZE);
        setCellValueEx(0, 0, Color.NONE, "?", Color.WHITE, 75);
    }

    public void finishIfResultIsKnown() {
        if (result == Result.WIN) {
            win(resultMessage);
        } else if (result == Result.LOSE) {
            lose(resultMessage);
        }
    }

    private void win(String message) {
        result = Result.WIN;
        isStopped = true;
        score = sumPocketsScore();
        showMessageDialog(Color.BLACK,
                message + "\nСчёт: " + ((score * 100) / turnCount) + "\n(Пробел - начать заново)",
                Color.PALEGOLDENROD, 20);
    }

    private int sumPocketsScore() {
        return pockets.stream()
                .mapToInt(Pocket::getScore)
                .sum();
    }

    public void lose(String message) {
        result = Result.LOSE;
        isStopped = true;
        showMessageDialog(Color.BLACK,
                "Вы проиграли!\n" + message + "\n(Пробел - начать заново)",
                Color.RED, 20);
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
        int[][] fieldBeforeMove = FieldUtils.copyField(field);

        pocketRow();

        for (int i = 0; i < field[0].length; i++) {
            compressRow(field[i]);
            mergeRow(field[i]);
            compressRow(field[i]);
        }

        boolean nothingChanged = FieldUtils.fieldsAreEqual(field, fieldBeforeMove);

        if (nothingChanged) return;

        createNewBall();
        turnCount++;

        finishIfResultIsKnown();
    }

    private void pocketRow() {
        if (isWhiteBallSet) {
            final int whiteBallRowNumber = FieldUtils.getWhiteBallRowNumber(field);
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

        checkResult(eightBallIsPocketed);
    }

    private void checkResult(boolean eightBallIsPocketed) {
        if (eightBallIsPocketed && !allPocketsClosed()) {
            setResult(Result.LOSE, "8-й шар забит слишком рано!");
        } else if (eightBallIsPocketed && allPocketsClosed()) {
            setResult(Result.WIN, "Вы выиграли!");
        } else if (!eightBallIsPocketed && allPocketsClosed()) {
            setResult(Result.LOSE, "В последней лузе нет 8-го шара!");
        }
    }

    private void setResult(Result result, String resultMessage) {
        this.result = result;
        this.resultMessage = resultMessage;
    }

    private boolean allPocketsClosed() {
        return pockets.stream().noneMatch(Pocket::isOpen);
    }

    private void compressRow(int[] row) {
        int[][] fieldBeforeCompress;

        do {
            fieldBeforeCompress = FieldUtils.copyField(field);
            movePositiveNumbersToZerosOnTheLeft(row);
        } while (!FieldUtils.fieldsAreEqual(field, fieldBeforeCompress));
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
                if (FieldUtils.hasNoEmptySpace(field)) break;
                extractScoreAndPlaceBall(pocket, pocket.getScore() / 2);
                if (FieldUtils.hasNoEmptySpace(field)) break;
                extractScoreAndPlaceBall(pocket, pocket.getScore());
            } else {
                placeBall(MAX_BALL);
                pocket.removeScore(MAX_BALL);
            }
        }
    }

    private void extractScoreAndPlaceBall(Pocket pocket, int amount) {
        pocket.removeScore(amount);
        placeBall(amount);
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

    public void showHelpDialog() {
        String helpMessage = "Эта версия 2048 сделана по мотивам американского бильярда." +
                "\nШары складываются по правилам 2048. Цель - забить шар #8 в последнюю открытую лузу." +
                "\nПравый клик по пустой клетке установит биток, проталкивающий шары в лузы." +
                "\nЧем меньше сделаете ходов, и чем больше очков в лузах, тем больше будет ваш счёт." +
                "\nЕсли в последней лузе не будет шара #8 или если вы забьёте его раньше времени," +
                "\nто проиграете. Клик по лузе высвободит шары, разбив счёт лузы на части." +
                "\nЗажав клавишу ENTER можно продолжительно совершать случайные ходы. Удачи! :)";
        showMessageDialog(Color.YELLOW, helpMessage, Color.BLACK, 10);
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