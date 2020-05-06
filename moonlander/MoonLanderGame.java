package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;

public class MoonLanderGame extends Game {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    private Rocket rocket;
    private GameObject landscape;
    private boolean isUpPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int step) {
        rocket.move(isUpPressed, isLeftPressed, isRightPressed);
        check();
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
    }

    private void createGameObjects() {
        rocket = new Rocket(WIDTH / 2, 0);
        landscape = new GameObject(0, 25, ShapeMatrix.LANDSCAPE);
    }

    private void drawScene() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                setCellColor(x, y, Color.DARKBLUE);
            }
        }
        rocket.draw(this);
        landscape.draw(this);
    }

    // GAME MECHANICS

    private void check() {

    }

    private void win() {

    }

    private void gameOver() {

    }

    // CONTROLS

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case UP: {
                isUpPressed = true;
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
            case LEFT: {
                isLeftPressed = false;
                break;
            }
            case RIGHT: {
                isRightPressed = false;
            }
            default: {
                break;
            }
        }
    }
}
