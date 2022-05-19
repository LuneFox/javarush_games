package com.javarush.games.spaceinvaders;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.spaceinvaders.controller.Controller;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.Movable;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Battler;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.EnemyArmy;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.CoinBullet;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bricks.Brick;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bricks.QuestionBrick;
import com.javarush.games.spaceinvaders.view.Display;
import com.javarush.games.spaceinvaders.view.Flash;
import com.javarush.games.spaceinvaders.view.Scenery;
import com.javarush.games.spaceinvaders.view.shapes.BrickShape;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * VERSION 1.05
 */

public class SpaceInvadersGame extends Game {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    public static final int COIN_BULLETS_MAX = 2;
    public static final int FLOOR_HEIGHT = 4;
    public static final int DIFFICULTY = 5;

    private Controller controller;
    private Display display;
    private Scenery scenery;
    private Flash flash;

    private EnemyArmy enemyArmy;
    private Mario mario;

    private List<Bullet> enemyBullets;
    private List<Bullet> playerBullets;
    private List<Brick> bricks;

    private int gameOverDelay;
    private boolean isEndingDisplayed;
    private boolean isStopped;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);

        GameObject.setGame(this);
        Battler.setGame(this);

        scenery = new Scenery(this);
        flash = new Flash(this);
        display = new Display(this);
        controller = new Controller(this);

        startNewGame();
    }

    public void startNewGame() {
        resetValues();
        createAssets();
        drawStage();
    }

    private void resetValues() {
        Score.reset();
        gameOverDelay = 0;
        isStopped = false;
        isEndingDisplayed = false;
        setTurnTimer(40);
    }

    private void createAssets() {
        mario = new Mario();
        enemyArmy = new EnemyArmy(this);
        enemyBullets = new ArrayList<>();
        playerBullets = new ArrayList<>();
        createBricks();
    }

    private void createBricks() {
        bricks = new ArrayList<>();
        int y = HEIGHT - 30 - BrickShape.BRICK.length;

        IntStream.of(0, 20, 30, 60, 70, 90)
                .forEach(x -> bricks.add(new Brick(x, y)));
        IntStream.of(10, 80)
                .forEach(x -> bricks.add(new QuestionBrick(x, y)));
    }

    @Override
    public void onTurn(int step) {
        moveObjects();
        verifyCollisions();
        checkGameOverConditions();
        removeDeadObjects();

        drawStage();
        setScore(Score.get());
    }

    private void moveObjects() {
        mario.move();
        enemyArmy.move();
        enemyArmy.attack();
        Stream.of(enemyBullets, playerBullets, bricks)
                .forEach(list -> list.forEach(Movable::move));
    }

    private void verifyCollisions() {
        mario.verifyHit(enemyBullets);
        enemyArmy.verifyHit(playerBullets);
        enemyArmy.verifyHit(enemyBullets);
        bricks.forEach(brick -> brick.verifyTouch(mario));
    }

    private void removeDeadObjects() {
        enemyArmy.removeDeadTanks();
        Stream.of(enemyBullets, playerBullets)
                .forEach(list -> list.removeIf(bullet -> bullet.isOffScreen() || !bullet.isAlive));
    }

    private void checkGameOverConditions() {
        if (enemyArmy.reachedLine(bricks.get(0).y)) {
            mario.kill();
        }

        if (!mario.isAlive) {
            mario.playDeathAnimation();
            stopGameWithEndingAfterDelay();
        }

        if (enemyArmy.getTanksCount() == 0) {
            mario.playVictoryAnimation();
            stopGameWithEndingAfterDelay();
        }
    }

    private void drawStage() {
        Stream.of(scenery, enemyArmy).forEach(Drawable::draw);
        Stream.of(playerBullets, bricks, enemyBullets)
                .forEach(list -> list.forEach(Drawable::draw));
        Stream.of(mario, flash, display).forEach(Drawable::draw);
    }

    public void addPlayerBullet(Bullet bulletToAdd) {
        if (bulletToAdd instanceof CoinBullet && countCoinBullets() >= COIN_BULLETS_MAX) return;
        playerBullets.add(bulletToAdd);
    }

    private long countCoinBullets() {
        return playerBullets.stream()
                .filter(bullet -> bullet instanceof CoinBullet)
                .count();
    }

    private void stopGameWithEndingAfterDelay() {
        isStopped = true;
        gameOverDelay++;
        if (gameOverDelay < 60) return;
        displayEnding(mario.isAlive);
    }

    private void displayEnding(boolean isVictory) {
        stopTurnTimer();
        if (isVictory) {
            String message = "SCORE: " + Score.get() + " ~ THANK YOU, MARIO!\nBUT OUR PRINCESS IS IN ANOTHER GAME!";
            showMessageDialog(Color.NONE, message, Color.WHITE, 20);
        } else {
            String message = "MAMMA MIA!";
            showMessageDialog(Color.NONE, message, Color.RED, 75);
        }
        isEndingDisplayed = true;
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

    /*
     * Getters
     */

    public Flash getFlash() {
        return flash;
    }

    public Mario getMario() {
        return mario;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public List<Bullet> getEnemyBullets() {
        return enemyBullets;
    }

    public Display getDisplay() {
        return display;
    }

    public boolean isEndingDisplayed() {
        return isEndingDisplayed;
    }

    public boolean isStopped() {
        return isStopped;
    }
}
