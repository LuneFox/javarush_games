package com.javarush.games.spaceinvaders;

import com.javarush.engine.cell.*;
import com.javarush.games.spaceinvaders.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.gameobjects.battlers.EnemyFleet;
import com.javarush.games.spaceinvaders.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.gameobjects.items.Brick;
import com.javarush.games.spaceinvaders.gameobjects.items.QuestionBrick;
import com.javarush.games.spaceinvaders.gameobjects.items.QuestionBrick.*;
import com.javarush.games.spaceinvaders.shapes.DecoShape;
import com.javarush.games.spaceinvaders.shapes.ObjectShape;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * VERSION 1.02
 */

public class SpaceInvadersGame extends Game {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    public static final int COMPLEXITY = 5;
    private static final int PLAYER_BULLETS_MAX = 2;

    public Display display;
    private List<Bullet> enemyBullets;
    private List<Bullet> playerBullets;
    private List<Brick> bricks;
    private List<Bonus> bonuses;
    private EnemyFleet enemyFleet;
    private Mario mario;
    private Date startTime;
    private int animationsCount;
    private int score;
    private boolean flashColor;
    private boolean isGameStopped;
    private boolean displayedEnding;
    private boolean showFlash = false;


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
        bonuses = new ArrayList<>();
        createBricks();
        setTurnTimer(40);
        animationsCount = 0;
        isGameStopped = false;
        displayedEnding = false;
        drawScene();
        startTime = new Date();
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
        drawSky();
        drawClouds();
        drawHills();
        drawBushes();
        enemyFleet.draw(this, false);
        playerBullets.forEach(bullet -> bullet.draw(this, false));
        bonuses.forEach(bonus -> bonus.draw(this, false));
        drawBricks();
        enemyBullets.forEach(bullet -> bullet.draw(this, false));
        drawFloor();
        mario.draw(this);
        drawFlash();
    }

    private void drawSky() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                display.setCellValueEx(x, y, (y > 60) ? Color.DEEPSKYBLUE : Color.BLACK, "");
            }
        }
    }

    private void drawFloor() {
        GameObject floorTile = new GameObject(0, HEIGHT - DecoShape.FLOOR.length) {
            {
                setStaticView(DecoShape.FLOOR);
            }
        };
        for (int i = 0; i < 7; i++) {
            floorTile.x = i * floorTile.width;
            floorTile.draw(this, false);
        }
    }

    private void drawBushes() {
        GameObject bush = new GameObject(0, HEIGHT - DecoShape.FLOOR.length - DecoShape.BUSH.length) {
            {
                setStaticView(DecoShape.BUSH);
            }
        };
        for (int i = 0; i < 3; i++) {
            bush.x = 2 * i * bush.width;
            bush.draw(this, false);
        }
    }

    private void drawHills() {
        GameObject hill = new GameObject(0, HEIGHT - DecoShape.FLOOR.length - DecoShape.HILL.length) {
            {
                setStaticView(DecoShape.HILL);
            }
        };
        for (int i = 0; i < 2; i++) {
            hill.x = 1.8 * i * hill.width + 14;
            hill.draw(this, false);
        }
    }

    private void drawClouds() {
        class Cloud extends GameObject {
            Cloud(double x, double y) {
                super(x, y);
                setStaticView(DecoShape.CLOUD);
            }
        }
        new Cloud(5, 65).draw(this, false);
        new Cloud(38, 57).draw(this, false);
        new Cloud(81, 72).draw(this, true);
    }

    private void drawBricks() {
        bricks.forEach(brick -> brick.draw(this, false));
    }

    private void drawFlash() {
        if (showFlash) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    if (y % 2 == 0 && x % 2 == 0) {
                        flashColor = !flashColor;
                        display.setCellValueEx(x, y, flashColor ? Color.WHITE : Color.YELLOW, "");
                    }
                }
            }
            showFlash = false;
        }
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
        bonuses.forEach(Bonus::move);
        bricks.forEach(brick -> brick.jump(this));
        mario.move();
    }

    private void removeDeadObjects() {
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

        ArrayList<Bonus> bonusesClone = new ArrayList<>(bonuses);
        bonusesClone.forEach(bonus -> {
            if (bonus.x + bonus.width < 0 || bonus.x > WIDTH || bonus.isCollected) {
                bonus.isCollected = true;
                bonuses.remove(bonus);
            }
        });
    }

    public void addPlayerBullet(Bullet bullet) {
        if (bullet != null && (playerBullets.size() < (PLAYER_BULLETS_MAX + countFireBalls())
                || bullet.getClass().getName().contains("FireBall"))) {
            playerBullets.add(bullet);
        }
    }

    private int countFireBalls() {
        final int[] count = {0};
        playerBullets.forEach(bullet -> {
            if (bullet.getClass().getName().contains("FireBall")) {
                count[0]++;
            }
        });
        return count[0];
    }

    public void addBonus(Bonus bonus) {
        bonuses.add(bonus);
    }

    private int getMultiplier() {
        Date now = new Date();
        long millis = now.getTime() - startTime.getTime();
        int seconds = (int) millis / 1000;
        return Math.max(100 - seconds, 0);
    }

    public void increaseScore(int amount) {
        score += amount * getMultiplier();
    }

    private void check() {
        mario.verifyHit(enemyBullets);
        score += enemyFleet.verifyHit(playerBullets) * getMultiplier();
        score += enemyFleet.verifyHit(enemyBullets) * getMultiplier();
        enemyFleet.deleteHiddenShips();

        bricks.forEach(brick -> {
            brick.verifyTouch(mario, this);
            brick.check(this);
        });

        bonuses.forEach(bonus -> {
            bonus.verifyTouch(mario, this);
            bonus.verifyHit(enemyBullets);
        });

        removeDeadObjects();

        if (enemyFleet.getBottomBorder() >= bricks.get(0).y) {
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
        stopTurnTimer();
        if (isWin) {
            showMessageDialog(Color.NONE,
                    "SCORE: " + score + " ~ THANK YOU, MARIO!\nBUT OUR PRINCESS IS IN ANOTHER GAME!",
                    Color.WHITE, 20);
        } else {
            showMessageDialog(Color.NONE,
                    "MAMMA MIA!",
                    Color.RED, 75);
        }
    }

    private void stopGameWithDelay() {
        isGameStopped = true;
        animationsCount++;
        if (animationsCount >= 20) {
            stopGame(mario.isAlive);
            displayedEnding = true;
        }
    }


    // -------- CONTROLS

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case SPACE:
                if (isGameStopped && displayedEnding) {
                    createGame();
                } else {
                    Bullet bullet = mario.fire();
                    addPlayerBullet(bullet);
                    if (mario.wipeEnemyBullets()) {
                        showFlash = true;
                        score += enemyBullets.size() * 5 * getMultiplier();
                        enemyBullets.clear();
                    }
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
