package com.javarush.games.snake;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.snake.controller.Controller;
import com.javarush.games.snake.controller.strategies.GameFieldControlStrategy;
import com.javarush.games.snake.model.Phase;
import com.javarush.games.snake.model.Score;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.enums.Direction;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.model.map.StageManager;
import com.javarush.games.snake.view.impl.GameFieldView;

import java.util.Date;

public class SnakeGame extends Game {
    public static final int SIZE = 32;
    private static final int MAX_TURN_DELAY = 300;
    private static SnakeGame instance;

    private Controller controller;
    private Snake snake;
    private Stage stage;
    private String gameOverReason;
    private int turnDelay;
    private boolean isStopped;
    private boolean isPaused;

    public static SnakeGame getInstance() {
        return instance;
    }

    public void initialize() {
        showGrid(false);
        setScreenSize(SIZE, SIZE);
        initializeStartupParameters();
        loadStage();
        Phase.proceed(Phase.MAIN_MENU);
    }

    private void initializeStartupParameters() {
        instance = this;
        controller = new Controller();
    }

    public final void createGame() {
        loadStage();
        createSnake();
        resetValues();
        setTurnTimer(turnDelay);
    }

    public void loadStage() {
        stage = StageManager.getCurrentStage();
    }

    private void createSnake() {
        final int snakeX = stage.getSnakeStartPlace().x;
        final int snakeY = stage.getSnakeStartPlace().y;
        final Direction snakeDirection = stage.getSnakeStartDirection();

        snake = new Snake(this, snakeX, snakeY, snakeDirection);
    }

    private void resetValues() {
        Score.reset();
        stage.initialize();
        turnDelay = MAX_TURN_DELAY;
        isStopped = false;
        isPaused = false;
        GameFieldControlStrategy.getInstance().reset();
    }

    public void onTurn(int step) {
        if (stage.isCompleted()) {
            stage.showCompleteMessage();
            return;
        }

        if (stage.isNotStarted()) {
            stage.showBriefingMessage();
            return;
        }

        makeTurn();
        setTurnTimer(turnDelay);
        Phase.proceed(Phase.GAME_FIELD);
    }

    private void makeTurn() {
        snake.move();
        stage.checkOrbCollision(snake);
        processPassiveTerrainEffects();
        checkGameOver();
    }

    private void processPassiveTerrainEffects() {
        for (int x = 0; x < SnakeGame.SIZE; x++) {
            for (int y = 0; y < SnakeGame.SIZE; y++) {
                stage.getTerrain(x, y).processPassiveEffects();
            }
        }
    }

    private void checkGameOver() {
        if (!snake.isAlive()) gameOver();
        else if (stage.isCompleted()) win();
    }

    private void gameOver() {
        Phase.proceed(Phase.GAME_FIELD);
        stopTurnTimer();
        isStopped = true;
        showMessageDialog(Color.YELLOW, gameOverReason, Color.RED, 27);
    }

    private void win() {
        Phase.proceed(Phase.GAME_FIELD);
        stopTurnTimer();
        StageManager.selectNextStage();
        isStopped = true;
    }

    public void setNormalTurnDelay() {
        this.turnDelay = getNormalTurnDelay();
    }

    public int getNormalTurnDelay() {
        return Math.max(SnakeGame.MAX_TURN_DELAY - (snake.getLength() * 10), 100);
    }

    public void pause() {
        isPaused = !isPaused;

        if (isPaused) {
            stopTurnTimer();
            GameFieldView.getInstance().drawSleepingLabel();
        } else {
            setTurnTimer(turnDelay);
        }
    }

    public long calculateBonusPoints() {
        long stageTimePassed = (getCurrentTime() - stage.getStartTime()) / 1000;
        return Math.max(300 - stageTimePassed, 0);
    }

    public static boolean outOfBounds(int x, int y) {
        return (x < 0 || y < 4 || x > SIZE - 1 || y > SIZE - 1);
    }

    public long getCurrentTime() {
        return new Date().getTime();
    }

    public boolean isStopped() {
        return isStopped;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setTurnDelay(int turnDelay) {
        this.turnDelay = turnDelay;
    }

    public void setGameOverReason(String reason) {
        this.gameOverReason = reason;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
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
