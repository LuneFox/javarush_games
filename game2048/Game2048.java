package com.javarush.games.game2048;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.game2048.controller.Controller;
import com.javarush.games.game2048.model.Direction;
import com.javarush.games.game2048.model.Field;
import com.javarush.games.game2048.model.Result;
import com.javarush.games.game2048.view.View;

public class Game2048 extends Game {
    public static final String VERSION = "v0.97";
    private static final int SIDE = Field.SIDE;

    private final Controller controller = new Controller(this);
    private final View view = new View(this);
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
        view.drawBackground();
        field.draw();
        view.drawInfo();
    }

    public void finishIfResultIsAchieved() {
        if (result == Result.WIN) {
            win(resultMessage);
        } else if (result == Result.LOSE) {
            lose(resultMessage);
        }
    }

    private void win(String message) {
        result = Result.WIN;
        isStopped = true;
        score = (field.sumPocketsScore() * 100) / turnCount;
        view.showResultDialog(result, message + "\nСчёт: " + score);
    }

    private void lose(String message) {
        result = Result.LOSE;
        isStopped = true;
        view.showResultDialog(result, "Вы проиграли!\n" + message);
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
        view.showHelpDialog();
    }

    public boolean isStopped() {
        return isStopped;
    }

    public int getTurnCount() {
        return turnCount;
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