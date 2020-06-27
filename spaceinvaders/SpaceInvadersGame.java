package com.javarush.games.spaceinvaders;

import com.javarush.engine.cell.*;
import com.javarush.games.spaceinvaders.gameobjects.*;

import java.util.ArrayList;
import java.util.List;

public class SpaceInvadersGame extends Game {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public static final int COMPLEXITY = 5;

    private List<Star> stars;
    private List<Bullet> enemyBullets;
    private EnemyFleet enemyFleet;
    private PlayerShip playerShip;
    private int animationsCount;
    private boolean isGameStopped;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int step) {
        moveSpaceObjects();
        check();
        Bullet bullet = enemyFleet.fire(this);
        if (bullet != null) {
            enemyBullets.add(bullet);
        }
        drawScene();
    }

    private void createGame() {
        createStars();
        enemyFleet = new EnemyFleet();
        playerShip = new PlayerShip();
        enemyBullets = new ArrayList<>();
        setTurnTimer(40);
        animationsCount = 0;
        isGameStopped = false;
        drawScene();
    }

    private void drawScene() {
        drawField();
        enemyFleet.draw(this);
        playerShip.draw(this);
        enemyBullets.forEach(bullet -> bullet.draw(this));
    }

    private void drawField() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                setCellValueEx(x, y, Color.BLACK, "");
            }
        }
        stars.forEach(star -> star.draw(this));
    }

    private void createStars() {
        stars = new ArrayList<Star>();
        for (int i = 0; i < 8; i++) {
            int randomX = getRandomNumber(WIDTH);
            int randomY = getRandomNumber(HEIGHT);
            stars.add(new Star(randomX, randomY));
        }
    }

    private void moveSpaceObjects() {
        enemyFleet.move();
        enemyBullets.forEach(Bullet::move);
        playerShip.move();
    }

    private void removeDeadBullets() {
        ArrayList<Bullet> enemyBulletsClone = new ArrayList<>(enemyBullets);
        enemyBulletsClone.forEach(bullet -> {
            if (bullet.y >= HEIGHT - 1 || !bullet.isAlive) {
                enemyBullets.remove(bullet);
            }
        });
    }

    private void check() {
        playerShip.verifyHit(enemyBullets);
        removeDeadBullets();
        if (!playerShip.isAlive) {
            stopGameWithDelay();
        }
    }

    private void stopGame(boolean isWin) {
        isGameStopped = true;
        stopTurnTimer();
        if (isWin) {
            showMessageDialog(Color.GREY, "VICTORY", Color.GREEN, 75);
        } else {
            showMessageDialog(Color.GREY, "FAIL", Color.RED, 75);
        }
    }

    private void stopGameWithDelay() {
        animationsCount++;
        if (animationsCount >= 10) {
            stopGame(playerShip.isAlive);
        }
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case SPACE:
                if (isGameStopped) {
                createGame();
            }
            break;
            case LEFT:
                playerShip.setDirection(Direction.LEFT);
                break;
            case RIGHT:
                playerShip.setDirection(Direction.RIGHT);
                break;
            default:
                break;
        }
    }
}
