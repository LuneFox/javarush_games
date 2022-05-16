package com.javarush.games.spaceinvaders;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.spaceinvaders.controller.Controller;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.EnemyArmy;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.items.Brick;
import com.javarush.games.spaceinvaders.model.gameobjects.items.QuestionBrick;
import com.javarush.games.spaceinvaders.view.Display;
import com.javarush.games.spaceinvaders.view.shapes.DecoShape;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * VERSION 1.03
 */

public class SpaceInvadersGame extends Game {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    public static final int COMPLEXITY = 5;
    public static final int PLAYER_BULLETS_MAX = 2;
    public static final int FLOOR_HEIGHT = 4;

    private Controller controller;

    public Display display;
    public List<Bullet> enemyBullets;
    public List<Bullet> playerBullets;
    public List<Brick> bricks;
    public List<QuestionBrick.Bonus> bonuses;
    public EnemyArmy enemyArmy;
    public Mario mario;
    public int animationsCount;
    public boolean flashColor;
    public boolean isGameStopped;
    public boolean displayedEnding;
    public boolean showFlash = false;


    // -------- INITIALIZATION

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        GameObject.setGame(this);
        ObjectShape.setGame(this);
        display = new Display(this);
        controller = new Controller(this);
        createGame();
    }

    public void createGame() {
        Score.reset();
        enemyArmy = new EnemyArmy();
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
    }

    @Override
    public void onTurn(int step) {
        moveSpaceObjects();
        check();

        Optional<Bullet> bulletOptional = enemyArmy.fire(this);
        bulletOptional.ifPresent(bullet -> enemyBullets.add(bullet));

        setScore(Score.get());
        drawScene();
        display.draw();
    }

    private void createBricks() {
        int height = HEIGHT - 30 - ObjectShape.BRICK.length;
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
        enemyArmy.draw();
        playerBullets.forEach(GameObject::draw);
        bonuses.forEach(GameObject::draw);
        drawBricks();
        enemyBullets.forEach(GameObject::draw);
        drawFloor();
        mario.draw();
        drawFlash();
    }

    private void drawSky() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                display.drawPixel(x, y, (y > 60) ? Color.DEEPSKYBLUE : Color.BLACK);
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
            floorTile.x = i * floorTile.getWidth();
            floorTile.draw();
        }
    }

    private void drawBushes() {
        GameObject bush = new GameObject(0, HEIGHT - DecoShape.FLOOR.length - DecoShape.BUSH.length) {
            {
                setStaticView(DecoShape.BUSH);
            }
        };
        for (int i = 0; i < 3; i++) {
            bush.x = 2 * i * bush.getWidth();
            bush.draw();
        }
    }

    private void drawHills() {
        GameObject hill = new GameObject(0, HEIGHT - DecoShape.FLOOR.length - DecoShape.HILL.length) {
            {
                setStaticView(DecoShape.HILL);
            }
        };
        for (int i = 0; i < 2; i++) {
            hill.x = 1.8 * i * hill.getWidth() + 14;
            hill.draw();
        }
    }

    private void drawClouds() {
        class Cloud extends GameObject {
            Cloud(double x, double y) {
                super(x, y);
                setStaticView(DecoShape.CLOUD);
            }
        }
        new Cloud(5, 65).draw();
        new Cloud(38, 57).draw();
        new Cloud(81, 72).draw(Mirror.HORIZONTAL);
    }

    private void drawBricks() {
        bricks.forEach(GameObject::draw);
    }

    private void drawFlash() {
        if (showFlash) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    if (y % 2 == 0 && x % 2 == 0) {
                        flashColor = !flashColor;
                        display.drawPixel(x, y, flashColor ? Color.WHITE : Color.YELLOW);
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
        enemyArmy.move();
        enemyBullets.forEach(Bullet::move);
        playerBullets.forEach(Bullet::move);
        bonuses.forEach(QuestionBrick.Bonus::move);
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
            if (bullet.y + bullet.getHeight() < 0 || !bullet.isAlive) {
                playerBullets.remove(bullet);
            }
        });

        ArrayList<QuestionBrick.Bonus> bonusesClone = new ArrayList<>(bonuses);
        bonusesClone.forEach(bonus -> {
            if (bonus.x + bonus.getWidth() < 0 || bonus.x > WIDTH || bonus.isCollected) {
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

    public void addBonus(QuestionBrick.Bonus bonus) {
        bonuses.add(bonus);
    }

    public void increaseScore(int amount) {
        Score.add(amount);
    }

    private void check() {
        mario.verifyHit(enemyBullets);
        enemyArmy.verifyHit(playerBullets);
        enemyArmy.verifyHit(enemyBullets);
        enemyArmy.removeDeadTanks();

        bricks.forEach(brick -> {
            brick.verifyTouch(mario, this);
            brick.check(this);
        });

        bonuses.forEach(bonus -> {
            bonus.verifyTouch(mario, this);
            bonus.verifyHit(enemyBullets);
        });

        removeDeadObjects();

        if (enemyArmy.reachedLine(bricks.get(0).y)) {
            mario.kill();
        }
        if (!mario.isAlive) {
            stopGameWithDelay();
        }
        if (enemyArmy.getTanksCount() == 0) {
            mario.playVictoryAnimation();
            stopGameWithDelay();
        }
    }

    public void marioFire() {
        Optional<Bullet> bulletOptional = mario.fire();
        bulletOptional.ifPresent(this::addPlayerBullet);
        if (mario.wipeEnemyBullets()) {
            showFlash = true;
            Score.add(enemyBullets.size() * 5);
            enemyBullets.clear();
        }
    }

    private void stopGame(boolean isWin) {
        stopTurnTimer();
        if (isWin) {
            showMessageDialog(Color.NONE,
                    "SCORE: " + Score.get() + " ~ THANK YOU, MARIO!\nBUT OUR PRINCESS IS IN ANOTHER GAME!",
                    Color.WHITE, 20);
        } else {
            showMessageDialog(Color.NONE, "MAMMA MIA!", Color.RED, 75);
        }
    }

    private void stopGameWithDelay() {
        isGameStopped = true;
        animationsCount++;
        if (animationsCount >= 30) {
            stopGame(mario.isAlive);
            displayedEnding = true;
        }
    }

    // -------- CONTROLS

    @Override
    public void onKeyPress(Key key) {
        controller.pressKey(key);
    }

    @Override
    public void onKeyReleased(Key key) {
        controller.releaseKey(key);
    }
}
