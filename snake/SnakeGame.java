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
import com.javarush.games.snake.model.enums.Direction;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.model.map.StageManager;
import com.javarush.games.snake.model.orbs.NeutralOrb;
import com.javarush.games.snake.model.orbs.Orb;
import com.javarush.games.snake.model.terrain.FieldTerrain;
import com.javarush.games.snake.model.terrain.Terrain;
import com.javarush.games.snake.model.terrain.WaterTerrain;
import com.javarush.games.snake.model.terrain.WoodTerrain;
import com.javarush.games.snake.view.impl.GameFieldView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SnakeGame extends Game {
    public static final int SIZE = 32;
    private static final int MAX_TURN_DELAY = 300;
    private static SnakeGame instance;

    private Controller controller;
    private Snake snake;
    private Stage stage;
    private ArrayList<Orb> orbs;
    private String gameOverReason;
    private Date stageStartTimeStamp;
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
        Phase.set(Phase.MAIN_MENU);
    }

    private void initializeStartupParameters() {
        instance = this;
        controller = new Controller();
    }

    public final void createGame() {
        stage = StageManager.getCurrentStage();
        createGameObjects();
        resetGameValues();
        setTurnTimer(turnDelay);
    }

    private void createGameObjects() {
        stage.initialize();
        final int snakeX = stage.getSnakeStartPlace().x;
        final int snakeY = stage.getSnakeStartPlace().y;
        final Direction snakeDirection = stage.getSnakeStartDirection();
        snake = new Snake(snakeX, snakeY, this, snakeDirection);
        orbs = stage.getOrbs();
    }

    private void createNeutralOrb() {
        Terrain terrain = getTerrainForNeutralOrb();
        Orb neutralOrb = Orb.create(terrain.x, terrain.y, Element.NEUTRAL);
        orbs.add(neutralOrb);
    }

    private Terrain getTerrainForNeutralOrb() {
        List<Terrain> applicableTerrains = Arrays.stream(stage.getTerrainMatrix())
                .flatMap(Arrays::stream)
                .filter(terrain -> !snake.collidesWithObject(terrain))
                .filter(terrain -> terrain instanceof FieldTerrain
                        || terrain instanceof WaterTerrain
                        || terrain instanceof WoodTerrain)
                .collect(Collectors.toList());

        if (!snake.canUseElement(Element.WATER) && !snake.canUseElement(Element.ALMIGHTY)) {
            applicableTerrains = applicableTerrains.stream()
                    .filter(terrain -> terrain instanceof FieldTerrain)
                    .collect(Collectors.toList());
        }

        int random = getRandomNumber(applicableTerrains.size() - 1);
        return applicableTerrains.get(random);
    }

    private void resetGameValues() {
        Score.reset();
        turnDelay = MAX_TURN_DELAY;
        isStopped = false;
        isPaused = false;
        stageStartTimeStamp = new Date();
        GameFieldControlStrategy.getInstance().reset();
    }

    public void onTurn(int step) {

        if (!stage.isStarted()) {
            stage.showBriefingMessage();
            return;
        }

        snake.move();
        collectOrbs();
        checkGameOver();
        setTurnTimer(turnDelay);
        processPassiveTerrainEffects();
        Phase.set(Phase.GAME_FIELD);
    }

    private void processPassiveTerrainEffects() {
        for (int x = 0; x < SnakeGame.SIZE; x++) {
            for (int y = 0; y < SnakeGame.SIZE; y++) {
                stage.getTerrainMatrix()[y][x].processPassiveEffects();
            }
        }
    }

    public long calculateBonusPoints() {
        long stageTimePassed = (new Date().getTime() - stageStartTimeStamp.getTime()) / 1000;
        return Math.max(300 - stageTimePassed, 0);
    }

    private void collectOrbs() {
        orbs.forEach(orb -> orb.collect(snake));
        orbs.removeIf(Orb::isCollected);

        if (orbs.stream().noneMatch(orb -> orb instanceof NeutralOrb)) {
            createNeutralOrb();
        }
    }

    private void checkGameOver() {
        if (!snake.isAlive()) gameOver();
        if (stage.isCompleted()) win();
    }

    private void win() {
        stopTurnTimer();
        StageManager.selectNextStage();
        Phase.set(Phase.GAME_FIELD);
        isStopped = true;
        stage.showCompleteMessage();
    }

    private void gameOver() {
        stopTurnTimer();
        Phase.set(Phase.GAME_FIELD);
        isStopped = true;
        showMessageDialog(Color.YELLOW, gameOverReason, Color.RED, 27);
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

    public int getSnakeLength() {
        return snake.getLength();
    }

    public static boolean outOfBounds(int x, int y) {
        return (x < 0 || y < 4 || x > SIZE - 1 || y > SIZE - 1);
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

    public ArrayList<Orb> getOrbs() {
        return orbs;
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
