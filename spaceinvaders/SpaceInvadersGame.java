package com.javarush.games.spaceinvaders;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.spaceinvaders.controller.Controller;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
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
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
    public Display display;
    private Scenery scenery;
    public List<Bullet> enemyBullets;
    private List<Bullet> playerBullets;
    public List<Brick> bricks;
    private EnemyArmy enemyArmy;
    public Mario mario;
    public Flash flash;
    public int gameOverDelay;
    public boolean isEndingDisplayed;
    public boolean isStopped;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);

        GameObject.setGame(this);
        ObjectShape.setGame(this);
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
        drawScene();
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
        enemyArmy = new EnemyArmy();
        enemyBullets = new ArrayList<>();
        playerBullets = new ArrayList<>();
        createBricks();
    }

    private void createBricks() {
        bricks = new ArrayList<>();
        int height = HEIGHT - 30 - ObjectShape.BRICK.length;
        IntStream.of(0, 20, 30, 60, 70, 90).forEach(x -> bricks.add(new Brick(x, height)));
        IntStream.of(10, 80).forEach(x -> bricks.add(new QuestionBrick(x, height)));
    }

    @Override
    public void onTurn(int step) {
        moveObjects();
        checkCollisions();
        removeDeadObjects();
        checkGameOverConditions();
        enemyArmy.attack();

        drawScene();
        setScore(Score.get());
    }

    private void moveObjects() {
        mario.move();
        enemyArmy.move();
        enemyBullets.forEach(Bullet::move);
        playerBullets.forEach(Bullet::move);
        bricks.forEach(Brick::move);
    }

    private void checkCollisions() {
        mario.verifyHit(enemyBullets);
        enemyArmy.verifyHit(playerBullets);
        enemyArmy.verifyHit(enemyBullets);
        bricks.forEach(brick -> brick.verifyTouch(mario));
    }

    private void removeDeadObjects() {
        enemyBullets.removeIf(bullet -> bullet.y >= HEIGHT - 1 || !bullet.isAlive);
        playerBullets.removeIf(bullet -> bullet.y + bullet.getHeight() < 0 || !bullet.isAlive);
        enemyArmy.removeDeadTanks();
    }

    private void checkGameOverConditions() {
        if (enemyArmy.reachedLine(bricks.get(0).y)) {
            mario.kill();
        }

        if (!mario.isAlive) {
            mario.playDeathAnimation();
            stopGameWithDelayBeforeEnding();
        }

        if (enemyArmy.getTanksCount() == 0) {
            mario.playVictoryAnimation();
            stopGameWithDelayBeforeEnding();
        }
    }

    private void drawScene() {
        scenery.draw();
        enemyArmy.draw();
        playerBullets.forEach(GameObject::draw);
        bricks.forEach(GameObject::draw);
        enemyBullets.forEach(GameObject::draw);
        mario.draw();
        flash.draw();

        display.draw();
    }

    public void addPlayerBullet(Bullet bulletToAdd) {
        if (bulletToAdd instanceof CoinBullet) {
            addCoinBullet(bulletToAdd);
        } else {
            playerBullets.add(bulletToAdd);
        }
    }

    private void addCoinBullet(Bullet bulletToAdd) {
        long coinBulletsCount = playerBullets.stream().filter(bullet -> bullet instanceof CoinBullet).count();

        if (coinBulletsCount < COIN_BULLETS_MAX) {
            playerBullets.add(bulletToAdd);
        }
    }

    private void stopGameWithDelayBeforeEnding() {
        isStopped = true;
        gameOverDelay++;
        if (gameOverDelay >= 60) {
            showEnding(mario.isAlive);
        }
    }

    private void showEnding(boolean isVictory) {
        stopTurnTimer();
        if (isVictory) {
            showMessageDialog(Color.NONE,
                    "SCORE: " + Score.get() + " ~ THANK YOU, MARIO!\nBUT OUR PRINCESS IS IN ANOTHER GAME!",
                    Color.WHITE, 20);
        } else {
            showMessageDialog(Color.NONE,
                    "MAMMA MIA!",
                    Color.RED, 75);
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
}
