package com.javarush.games.snake;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.snake.controller.Controller;
import com.javarush.games.snake.controller.strategies.GameFieldControlStrategy;
import com.javarush.games.snake.model.*;
import com.javarush.games.snake.model.enums.Element;
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
    private Map map;
    private ArrayList<Orb> orbs;
    private String gameOverReason;
    private Date stageStartTimeStamp;
    private int turnDelay;
    private int stage;
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
        stage = 0;
    }

    public final void createGame() {
        createGameObjects();
        resetGameValues();
        setTurnTimer(turnDelay);
    }

    private void createGameObjects() {
        map = new Map(stage);
        snake = new Snake(map.snakeStartPlace.x, map.snakeStartPlace.y, this, map.snakeStartDirection);
        orbs = new ArrayList<>();
        loadElementalOrbsFromMap();
        createNeutralOrb();
    }

    private void loadElementalOrbsFromMap() {
        orbs.addAll(Arrays.asList(map.orbs.get(0), map.orbs.get(1), map.orbs.get(2), map.orbs.get(3), map.orbs.get(4)));
    }

    private void createNeutralOrb() {
        Terrain terrain = getTerrainForNeutralOrb();
        Orb neutralOrb = Orb.create(terrain.x, terrain.y, Element.NEUTRAL);
        orbs.add(neutralOrb);
    }

    private Terrain getTerrainForNeutralOrb() {
        List<Terrain> applicableTerrains = Arrays.stream(map.getTerrainMatrix())
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
                map.getTerrainMatrix()[y][x].processPassiveEffects();
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
        if (snake.getAlmightyPower() <= 0) win();
    }

    private void win() {
        stopTurnTimer();
        selectNextStage();
        Phase.set(Phase.GAME_FIELD);
        isStopped = true;
        showMessageDialog(Color.YELLOW, Strings.VICTORY + Score.get(), Color.GREEN, 27);
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

    public void selectNextStage() {
        if (stage < (Map.stages.size() - 2)) {
            stage++;
        } else {
            stage = 0;
        }
    }

    public void selectPreviousStage() {
        if (stage > 0) {
            stage--;
        } else {
            stage = Map.stages.size() - 2;
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

    public int getStage() {
        return stage;
    }

    public Snake getSnake() {
        return snake;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(int stage) {
        this.map = new Map(stage);
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
