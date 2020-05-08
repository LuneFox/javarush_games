package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;

public class MoonLanderGame extends Game {

    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    private Rocket rocket;
    private GameObject landscape;
    private GameObject platform;
    public GameObject stars;
    public GameObject earth;
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


    private void createGame() {
        setTurnTimer(50);
        createGameObjects();
        drawScene();
        isUpPressed = false;
        isLeftPressed = false;
        isRightPressed = false;
        isGameStopped = false;
        score = 1000;
    }

    private void createGameObjects() {
        moon = new Moon(this, getRandomNumber(63), getRandomNumber(63));
        lander = new Lander(this,
                32 - ShapeMatrix.LANDER[0].length / 2,
                32 - ShapeMatrix.LANDER.length / 2,
                moon);
        stars = new GameObject(-4, -4, new int[40][40]);
        earth = new GameObject(getRandomNumber(53), getRandomNumber(53), ShapeMatrix.EARTH);
        createStarMap();
    }


    // DRAW
    private void drawScene() {
        drawGameBackground();
        drawStarMap();
        earth.draw(this);
        moon.draw();
        lander.draw(this);
        drawInterfaceBackground();
    }

    public void drawGameBackground() {
        for (int y = 0; y < 64; y++) {
            for (int x = 0; x < 64; x++) {
                setCellValueEx(x, y, Color.BLACK, "");
            }
        }
    }

    public void drawInterfaceBackground() {
        for (int y = 64; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
                setCellValueEx(x, y, Color.GRAY, "");
            }
        }

        for (int y = 0; y < 64; y++) {
            for (int x = 64; x < 100; x++) {
                setCellValueEx(x, y, Color.GRAY, "");
            }
        }
    }

    private void drawStarMap() {
        for (int y = 0; y < stars.matrix.length; y++) {
            for (int x = 0; x < stars.matrix[0].length; x++) {
                int starX = (int) stars.x + x * 2;
                int starY = (int) stars.y + y * 2;
                if (stars.matrix[y][x] == 1) {
                    if (getRandomNumber(5) != 1) {
                        setCellTextColor(starX, starY, Color.PALEGOLDENROD);
                    } else {
                        setCellTextColor(starX, starY, Color.ORANGE);
                    }
                    setCellValue(starX, starY, "ж");
                } else if (stars.matrix[y][x] == 2) {
                    if (getRandomNumber(10) != 1) {
                        setCellTextColor(starX, starY, Color.WHITE);
                    } else {
                        setCellTextColor(starX, starY, Color.BLUE);
                    }
                    setCellValue(starX, starY, "*");
                }
            }
        }
    }

    private void createStarMap() {
        for (int y = 0; y < stars.matrix.length; y++) {
            for (int x = 0; x < stars.matrix[0].length; x++) {
                int random = getRandomNumber(25);
                if (random == 1) {
                    stars.matrix[y][x] = 1;
                } else if (random == 2) {
                    stars.matrix[y][x] = 2;
                }
            }
        }
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if (x < 0 || y < 0 || x > WIDTH - 1 || y > HEIGHT - 1) {
            return;
        }
        super.setCellColor(x, y, color);
    }

    @Override
    public void setCellValueEx(int x, int y, Color cellColor, String value, Color textColor) {
        if (x < 0 || y < 0 || x > WIDTH - 1 || y > HEIGHT - 1) {
            return;
        }
        super.setCellValueEx(x, y, cellColor, value, textColor);
    }

    @Override
    public void setCellValueEx(int x, int y, Color cellColor, String value) {
        if (x < 0 || y < 0 || x > WIDTH - 1 || y > HEIGHT - 1) {
            return;
        }
        super.setCellValueEx(x, y, cellColor, value);
    }

    @Override
    public void setCellValue(int x, int y, String value) {
        if (x < 0 || y < 0 || x > WIDTH - 1 || y > HEIGHT - 1) {
            return;
        }
        super.setCellValue(x, y, value);
    }

    @Override
    public void setCellTextColor(int x, int y, Color color) {
        if (x < 0 || y < 0 || x > WIDTH - 1 || y > HEIGHT - 1) {
            return;
        }
        super.setCellTextColor(x, y, color);
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
                lander.startLanding();
                break;
            }
            case ESCAPE: {
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
