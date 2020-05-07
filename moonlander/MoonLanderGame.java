package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;

public class MoonLanderGame extends Game {

    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    private Rocket rocket;
    private GameObject landscape;
    private GameObject platform;
    private Moon moon;
    private Lander lander;
    private int score;
    private boolean isUpPressed;
    private boolean isDownPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed;
    private boolean isSpacePressed;
    private boolean isGameStopped;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int step) {
        lander.move(isSpacePressed, isLeftPressed, isRightPressed, isUpPressed, isDownPressed);
        drawScene();
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if (x < 0 || y < 0 || x > WIDTH - 1 || y > HEIGHT - 1) {
            return;
        }
        super.setCellColor(x, y, color);
    }

    private void createGame() {
        setTurnTimer(50);
        createGameObjects();
        drawScene();
        isUpPressed = false;
        isLeftPressed = false;
        isRightPressed = false;
        isGameStopped = false;
        score = 1000;
        drawInterfaceBackground();
    }

    private void createGameObjects() {
        moon = new Moon(this, getRandomNumber(63), getRandomNumber(63));
        lander = new Lander(this,
                32 - ShapeMatrix.LANDER[0].length / 2,
                32 - ShapeMatrix.LANDER.length / 2,
                moon);
    }

    private void drawScene() {
        drawGameBackground();
        moon.draw();
        lander.draw(this);
    }

    public void drawGameBackground() {
        for (int y = 0; y < 64; y++) {
            for (int x = 0; x < 64; x++) {
                setCellColor(x, y, Color.BLACK);
            }
        }
    }

    public void drawInterfaceBackground() {
        for (int y = 64; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
                setCellColor(x, y, Color.GRAY);
            }
        }
        for (int y = 0; y < 64; y++) {
            for (int x = 64; x < 100; x++) {
                setCellColor(x, y, Color.GRAY);
            }
        }
    }

    // GAME MECHANICS

    private void check() {
        if (rocket.isCollision(platform) && rocket.isStopped()) {
            win();
        } else if (rocket.isCollision(landscape)) {
            gameOver();
        }
    }

    private void win() {
        isGameStopped = true;
        rocket.land();
        showMessageDialog(Color.LIGHTGOLDENRODYELLOW, "Hello Moon!", Color.BLACK, 75);
        stopTurnTimer();
    }

    private void gameOver() {
        score = 0;
        rocket.crash();
        isGameStopped = true;
        showMessageDialog(Color.LIGHTGOLDENRODYELLOW, "Goodbye Lander!", Color.DARKRED, 75);
        stopTurnTimer();
    }

    // CONTROLS

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case UP: {
                isUpPressed = true;
                isDownPressed = false;
                break;
            }
            case DOWN: {
                isDownPressed = true;
                isUpPressed = false;
                break;
            }
            case LEFT: {
                isRightPressed = false;
                isLeftPressed = true;
                break;
            }
            case RIGHT: {
                isLeftPressed = false;
                isRightPressed = true;
                break;
            }
            case SPACE: {
                isSpacePressed = true;
                break;
            }
            case ENTER: {
                //if (isGameStopped) {
                createGame();
                return;
                //}
                //break;
            }
            default: {
                break;
            }
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        switch (key) {
            case UP: {
                isUpPressed = false;
                break;
            }
            case DOWN: {
                isDownPressed = false;
                break;
            }
            case LEFT: {
                isLeftPressed = false;
                break;
            }
            case RIGHT: {
                isRightPressed = false;
                break;
            }
            case SPACE: {
                isSpacePressed = false;
                break;
            }
            default: {
                break;
            }
        }
    }
}
