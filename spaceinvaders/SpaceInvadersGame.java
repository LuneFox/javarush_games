package com.javarush.games.spaceinvaders;

import com.javarush.engine.cell.*;
import com.javarush.games.spaceinvaders.gameobjects.*;
import com.javarush.games.spaceinvaders.gameobjects.decorations.FloorTile;
import com.javarush.games.spaceinvaders.shapes.DecoShape;
import com.javarush.games.spaceinvaders.shapes.ObjectShape;

import java.util.ArrayList;
import java.util.List;

public class SpaceInvadersGame extends Game {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    public static final int COMPLEXITY = 5;
    private static final int PLAYER_BULLETS_MAX = 5;

    public Display display;
    private List<Bullet> enemyBullets;
    private List<Bullet> playerBullets;
    private List<Brick> bricks;
    private EnemyFleet enemyFleet;
    private Mario mario;
    private FloorTile floorTile;
    private int animationsCount;
    private int score;
    private boolean isGameStopped;


    // -------- INITIALIZATION

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        display = new Display(this);
        createGame();
    }

    private void createGame() {
        score = 0;
        enemyFleet = new EnemyFleet();
        mario = new Mario();
        enemyBullets = new ArrayList<>();
        playerBullets = new ArrayList<>();
        bricks = new ArrayList<>();
        createBricks();
        setTurnTimer(40);
        animationsCount = 0;
        isGameStopped = false;
        drawScene();
    }

    @Override
    public void onTurn(int step) {
        moveSpaceObjects();
        check();
        Bullet bullet = enemyFleet.fire(this);
        if (bullet != null) {
            enemyBullets.add(bullet);
        }
        setScore(score);
        drawScene();
        display.draw();
    }

    private void createBricks() {
        int height = HEIGHT - Mario.JUMP_HEIGHT_LIMIT - ObjectShape.BRICK.length;
        bricks.add(new QuestionBrick(10, height));
        bricks.add(new QuestionBrick(80, height));
        bricks.add(new Brick(0, height));
        bricks.add(new Brick(20, height));
        bricks.add(new Brick(30, height));
        bricks.add(new Brick(60, height));
        bricks.add(new Brick(70, height));
        bricks.add(new Brick(90, height));
    }


    // -------- GRAPHICS

    private void drawScene() {
        drawField();
        enemyFleet.draw(this, false);
        mario.draw(this);
        playerBullets.forEach(bullet -> bullet.draw(this, false));
        drawBricks();
        enemyBullets.forEach(bullet -> bullet.draw(this, false));
        drawFloor();
    }

    private void drawField() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                display.setCellValueEx(x, y, Color.DEEPSKYBLUE, "");
            }
        }
    }

    private void drawFloor() {
        floorTile = new FloorTile(0, HEIGHT - DecoShape.FLOOR.length);
        for (int i = 0; i < 7; i++) {
            floorTile.x = i * floorTile.width;
            floorTile.draw(this, false);
        }
    }

    private void drawBricks() {
        bricks.forEach(brick -> brick.draw(this, false));
    }

    @Override
    public void setCellValueEx(int x, int y, Color cellColor, String value) {
        if (x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT) {
            super.setCellValueEx(x, y, cellColor, value);
        }
    }


    // -------- UTILITIES

    private void moveSpaceObjects() {
        enemyFleet.move();
        enemyBullets.forEach(Bullet::move);
        playerBullets.forEach(Bullet::move);
        bricks.forEach(brick -> brick.jump(this));
        mario.move();
    }

    private void removeDeadBullets() {
        ArrayList<Bullet> enemyBulletsClone = new ArrayList<>(enemyBullets);
        enemyBulletsClone.forEach(bullet -> {
            if (bullet.y >= HEIGHT - 1 || !bullet.isAlive) {
                enemyBullets.remove(bullet);
            }
        });

        ArrayList<Bullet> playerBulletsClone = new ArrayList<>(playerBullets);
        playerBulletsClone.forEach(bullet -> {
            if (bullet.y + bullet.height < 0 || !bullet.isAlive) {
                playerBullets.remove(bullet);
            }
        });
    }

    public void addBullet(Bullet bullet) {
        if (bullet != null && playerBullets.size() < PLAYER_BULLETS_MAX) {
            playerBullets.add(bullet);
        }
    }

    private void check() {
        mario.verifyHit(enemyBullets);
        score += enemyFleet.verifyHit(playerBullets);
        enemyFleet.deleteHiddenShips();
        bricks.forEach(brick -> brick.verifyTouch(mario, this));
        removeDeadBullets();
        if (enemyFleet.getBottomBorder() >= mario.y) {
            mario.kill();
        }
        if (!mario.isAlive) {
            stopGameWithDelay();
        }
        if (enemyFleet.getShipsCount() == 0) {
            mario.win();
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
            stopGame(mario.isAlive);
        }
    }


    // -------- CONTROLS

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case SPACE:
                if (isGameStopped) {
                    createGame();
                }
                break;
            case LEFT:
                if (!isGameStopped) {
                    mario.setDirection(Direction.LEFT);
                    break;
                }
            case RIGHT:
                if (!isGameStopped) {
                    mario.setDirection(Direction.RIGHT);
                    break;
                }
            case UP:
                if (!isGameStopped) {
                    if (!mario.isJumping) {
                        mario.jump();
                    }
                    break;
                }
            default:
                break;
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        switch (key) {
            case LEFT:
                if (mario.getDirection() == Direction.LEFT) {
                    mario.setDirection(Direction.UP);
                }
                break;
            case RIGHT:
                if (mario.getDirection() == Direction.RIGHT) {
                    mario.setDirection(Direction.UP);
                }
                break;
        }
    }
}
