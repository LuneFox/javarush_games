package com.javarush.games.snake;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.snake.controller.Controller;
import com.javarush.games.snake.model.*;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.model.orbs.Orb;
import com.javarush.games.snake.view.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class SnakeGame extends Game {
    private static SnakeGame instance;
    public static final int SIZE = 32;
    private static final int MAX_TURN_DELAY = 300;

    private Controller controller;
    private String gameOverReason;
    private Date stageStartTimeStamp;
    private int turnDelay;
    public int score;
    private int lifetime;
    private int stage;
    private boolean isStopped;
    private boolean isPaused;
    public boolean isAccelerationEnabled;
    public boolean isDelayBeforeAccelerationNeeded;

    public Snake snake;
    public Map map;
    public Orb neutralOrb;
    public ArrayList<Orb> orbs;

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
        isAccelerationEnabled = true;
    }

    public final void createGame() {
        createGameObjects();
        resetGameValues();
        setTurnTimer(turnDelay);
    }

    private void createGameObjects() {
        map = new Map(stage, this);
        snake = new Snake(map.snakeStartPlace.x, map.snakeStartPlace.y, this, map.snakeStartDirection);
        orbs = new ArrayList<>();
        importElementalOrbs();
        createNeutralOrb();
    }

    private void importElementalOrbs() {
        orbs.addAll(Arrays.asList(map.orbs.get(0), map.orbs.get(1), map.orbs.get(2), map.orbs.get(3), map.orbs.get(4)));
    }

    private void createNeutralOrb() {
        int x;
        int y;

        do {
            x = getRandomNumber(SIZE);
            y = getRandomNumber(SIZE - 4) + 4;
            neutralOrb = Orb.create(x, y, Element.NEUTRAL);
        } while (isBadPlaceForNeutralOrb(x, y));

        orbs.add(neutralOrb);
    }

    private boolean isBadPlaceForNeutralOrb(int x, int y) {
        if (snake.canUse(Element.WATER) || snake.canUse(Element.ALMIGHTY)) {
            return isBadPlaceWhenSnakeCanSwim(x, y);
        } else {
            return isBadPlaceWhenSnakeCannotSwim(x, y);
        }
    }

    private boolean isBadPlaceWhenSnakeCanSwim(int x, int y) {
        return snake.checkCollision(neutralOrb)
                || (map.getLayoutNode(x, y).getTerrain() != Node.Terrain.FIELD
                && map.getLayoutNode(x, y).getTerrain() != Node.Terrain.WATER
                && map.getLayoutNode(x, y).getTerrain() != Node.Terrain.WOOD);
    }

    private boolean isBadPlaceWhenSnakeCannotSwim(int x, int y) {
        return snake.checkCollision(neutralOrb)
                || map.getLayoutNode(x, y).getTerrain() != Node.Terrain.FIELD;
    }

    private void resetGameValues() {
        lifetime = 301;
        score = 0;
        turnDelay = MAX_TURN_DELAY;
        isDelayBeforeAccelerationNeeded = false;
        isStopped = false;
        isPaused = false;
        stageStartTimeStamp = new Date();
    }

    public void onTurn(int step) {
        snake.move();
        collectOrbs();
        checkGameOver();
        setTurnTimer(turnDelay);
        Phase.set(Phase.GAME_FIELD);
    }

    public long calculateBonusPoints() {
        long stageTimePassed = (new Date().getTime() - stageStartTimeStamp.getTime()) / 1000;
        return Math.max(300 - stageTimePassed, 0);
    }

    private void collectOrbs() {
        orbs.forEach(orb -> orb.collect(snake));
        orbs.removeIf(Orb::isCollected);

        if (neutralOrb.isCollected()) {
            createNeutralOrb();
        }
    }

    private void checkGameOver() {
        if (!snake.isAlive()) gameOver();
        if (lifetime <= 0) win();
    }

    private void win() {
        stopTurnTimer();
        Phase.set(Phase.GAME_FIELD);
        isStopped = true;
        showMessageDialog(Color.YELLOW, Strings.VICTORY + score, Color.GREEN, 27);
    }

    private void gameOver() {
        stopTurnTimer();
        Phase.set(Phase.GAME_FIELD);
        isStopped = true;
        showMessageDialog(Color.YELLOW, gameOverReason, Color.RED, 27);
    }


    public void setTurnDelay(int turnDelay) {
        this.turnDelay = turnDelay;
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
            drawSleepingLabel();
        } else {
            setTurnTimer(turnDelay);
        }
    }

    private void drawSleepingLabel() {
        Color color = Color.WHITE;
        Message.print(-1, 15, "             ", color);
        Message.print(-1, 16, " SLEEPING... ", color);
        Message.print(-1, 17, "             ", color);
    }

    public void addScore(long score) {
        this.score += score;
    }

    public void decreaseLifetime() {
        this.lifetime -= 1;
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

    public boolean outOfBounds(int x, int y) {
        return (x < 0 || y < 4 || x > SIZE - 1 || y > SIZE - 1);
    }

    public boolean isStopped() {
        return isStopped;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setGameOverReason(String reason) {
        this.gameOverReason = reason;
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
        this.map = new Map(stage, this);
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
