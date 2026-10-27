package com.javarush.games.snake;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.snake.controller.Controller;
import com.javarush.games.snake.controller.strategies.GameFieldControlStrategy;
import com.javarush.games.snake.model.Phase;
import com.javarush.games.snake.model.Score;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.map.Coordinate;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.model.map.StageManager;
import com.javarush.games.snake.view.impl.GameFieldView;

import java.util.Date;

public class SnakeGame extends Game {
    public static final int SIZE = 32;
    public static final int HUD_ROWS = 4;
    public static final int PLAYFIELD_MIN_X = 0;
    public static final int PLAYFIELD_MIN_Y = HUD_ROWS;
    public static final int PLAYFIELD_MAX_X = SIZE - 1;
    public static final int PLAYFIELD_MAX_Y = SIZE - 1;

    private static final int MAX_TURN_DELAY = 300;
    private static final int MIN_TURN_DELAY = 100;
    private static final int TURN_DELAY_STEP = 10;
    private static final int MAX_ORB_BONUS = 300;
    private static final int GAME_OVER_TEXT_SIZE = 27;

    private static SnakeGame instance;

    private final Score score = new Score();

    private Controller controller;
    private Snake snake;
    private Stage stage;
    private Strings gameOverReason;
    private int turnDelay;
    private boolean isStopped;
    private boolean isPaused;

    public static SnakeGame getInstance() {
        return instance;
    }

    public void initialize() {
        // Other game classes capture getInstance() in their static fields,
        // so the instance must be published before they are loaded.
        instance = this;
        controller = new Controller();

        showGrid(false);
        setScreenSize(SIZE, SIZE);
        loadStage();
        Phase.proceed(Phase.MAIN_MENU);
    }

    public final void createGame() {
        loadStage();
        createSnake();
        resetGameState();
        setTurnTimer(turnDelay);
    }

    public void loadStage() {
        stage = StageManager.getCurrentStage();
    }

    private void createSnake() {
        final Coordinate start = stage.getSnakeStartPlace();

        snake = new Snake(this, start.x, start.y, stage.getSnakeStartDirection());
    }

    private void resetGameState() {
        score.reset();
        stage.initialize();
        gameOverReason = null;
        turnDelay = MAX_TURN_DELAY;
        isStopped = false;
        isPaused = false;
        GameFieldControlStrategy.getInstance().reset();
    }

    public void onTurn(int step) {
        if (isStopped || isPaused) return;

        if (!stage.isStarted()) {
            stage.showBriefingMessage();
            return;
        }

        performTurn();

        if (isStopped) return;

        setTurnTimer(turnDelay);
        Phase.proceed(Phase.GAME_FIELD);
    }

    private void performTurn() {
        snake.move();

        // A dead snake must not eat orbs or change the world anymore.
        if (!snake.isAlive()) {
            checkGameState();
            return;
        }

        stage.collectOrbs(snake);
        applyPassiveTerrainEffects();
        checkGameState();
    }

    private void applyPassiveTerrainEffects() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                stage.getTerrain(x, y).applyPassiveEffect();
            }
        }
    }

    private void checkGameState() {
        if (!snake.isAlive()) {
            gameOver();
        } else if (stage.isCompleted()) {
            win();
        }
    }

    private void gameOver() {
        isStopped = true;
        stopTurnTimer();
        Phase.proceed(Phase.GAME_FIELD);

        final Strings reason = gameOverReason == null ? Strings.GAME_OVER_UNKNOWN : gameOverReason;
        showMessageDialog(Color.YELLOW, reason.get(), Color.RED, GAME_OVER_TEXT_SIZE);
    }

    private void win() {
        isStopped = true;
        stopTurnTimer();
        Phase.proceed(Phase.GAME_FIELD);
        stage.showCompleteMessage();
        StageManager.selectNextStage();
    }

    public void applyNormalTurnDelay() {
        this.turnDelay = getNormalTurnDelay();
    }

    public int getNormalTurnDelay() {
        int delay = MAX_TURN_DELAY - snake.getLength() * TURN_DELAY_STEP;
        return Math.max(delay, MIN_TURN_DELAY);
    }

    public void togglePause() {
        isPaused = !isPaused;

        if (isPaused) {
            stopTurnTimer();
            GameFieldView.getInstance().drawSleepingLabel();
        } else {
            setTurnTimer(turnDelay);
        }
    }

    public long calculateOrbBonus() {
        long secondsInStage = (currentTimeMillis() - stage.getStartTimeMillis()) / 1000;
        return Math.max(MAX_ORB_BONUS - secondsInStage, 0);
    }

    public static boolean isOutOfBounds(int x, int y) {
        return x < PLAYFIELD_MIN_X || x > PLAYFIELD_MAX_X
                || y < PLAYFIELD_MIN_Y || y > PLAYFIELD_MAX_Y;
    }

    private static long currentTimeMillis() {
        return new Date().getTime();
    }

    public boolean isStopped() {
        return isStopped;
    }

    public Snake getSnake() {
        return snake;
    }

    public Score getScore() {
        return score;
    }

    public void setTurnDelay(int turnDelay) {
        this.turnDelay = turnDelay;
    }

    public void setGameOverReason(Strings reason) {
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
