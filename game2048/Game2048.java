package com.javarush.games.game2048;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.game2048.controller.Controller;
import com.javarush.games.game2048.model.Direction;
import com.javarush.games.game2048.model.Field;
import com.javarush.games.game2048.model.Result;

public class Game2048 extends Game {
    public static final String VERSION = "v0.97";
    private static final int SIDE = 7;

    private final Controller controller = new Controller(this);
    private Field field;
    private Result result;
    private String resultMessage;

    private int score;
    private int turnCount;
    private boolean isStopped;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(SIDE, SIDE);
        createNewGame();
    }

    public void createNewGame() {
        field = new Field(this);
        resetValues();
        drawScene();
    }

    private void resetValues() {
        result = Result.NONE;
        isStopped = false;
        score = 0;
        turnCount = 0;
    }

    private void drawScene() {
        drawBackground();
        field.draw();
        drawInfo();
    }

    private void drawBackground() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                setCellColor(x, y, Color.SADDLEBROWN);
            }
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
        score = field.sumPocketsScore();
        showMessageDialog(Color.BLACK,
                message + "\nСчёт: " + ((score * 100) / turnCount) + "\n(Пробел - начать заново)",
                Color.PALEGOLDENROD, 20);
    }

    public void lose(String message) {
        result = Result.LOSE;
        isStopped = true;
        showMessageDialog(Color.BLACK,
                "Вы проиграли!\n" + message + "\n(Пробел - начать заново)",
                Color.RED, 20);
    }

    public void setResult(Result result, String resultMessage) {
        this.result = result;
        this.resultMessage = resultMessage;
    }

    public void increaseTurnCount() {
        turnCount++;
    }

    public void move(Direction direction) {
        if (direction == Direction.UP) field.moveUp();
        else if (direction == Direction.RIGHT) field.moveRight();
        else if (direction == Direction.DOWN) field.moveDown();
        else if (direction == Direction.LEFT) field.moveLeft();
        drawScene();
    }

    public void emptyPocket(int pocketNumber) {
        field.emptyPocket(pocketNumber);
        drawScene();
    }

    public void placeOrRemoveWhiteBall(int x, int y) {
        field.placeOrRemoveWhiteBall(x, y);
        drawScene();
    }

    public boolean isMovePossible() {
        return field.isMovePossible();
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

    public boolean isStopped() {
        return isStopped;
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
