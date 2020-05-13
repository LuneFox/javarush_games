package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;
import com.javarush.games.moonlander.graphics.Bitmap;
import com.javarush.games.moonlander.graphics.Text;

import java.util.Date;

public class MoonLanderGame extends Game {
    // Screen size
    static final int WIDTH = 100;
    static final int HEIGHT = 100;

    // Game objects
    GameObject stars;
    GameObject bigStars;
    GameObject earth;
    private Text writer;
    private Meter heightMeter;
    private Meter speedMeterX;
    private Meter speedMeterY;
    private Meter speedMeterZ;
    private Moon moon;
    private Lander lander;

    // Time and performance counters
    private long startTime;
    private long before = 0;
    private int frameCounter;
    private int fps;
    int turnTimer;

    // Key states
    private boolean isUpPressed;
    private boolean isDownPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed;
    private boolean isSpacePressed;

    // Graphics switches
    static boolean limitFPS = false;
    static boolean transparentMoon = false;
    private static boolean disableStars = false;
    private static boolean disableEarth = false;

    // BASE

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        createGame();
        //showMessageDialog(Color.YELLOW, "Это не готовая игра!\nЭто технический тест.", Color.RED, 35);
    }

    @Override
    public void onTurn(int step) {
        lander.move(isSpacePressed, isLeftPressed, isRightPressed, isUpPressed, isDownPressed);
        drawScene();
    }

    private void createGame() {
        frameCounter = 0;
        startTime = new Date().getTime();
        turnTimer = 50;
        setTurnTimer(turnTimer);
        createGameObjects();
        drawScene();
        isUpPressed = false;
        isLeftPressed = false;
        isRightPressed = false;
    }

    private void createGameObjects() {
        writer = new Text(Bitmap.NONE, this);
        writer.loadAlphabet();
        moon = new Moon(this, getRandomNumber(63), getRandomNumber(63));
        lander = new Lander(this,
                32 - (ShapeMatrix.LANDER[0].length / 2.0),
                32 - (ShapeMatrix.LANDER.length / 2.0),
                moon);
        stars = new GameObject(-4, -4, new int[40][40]);
        bigStars = new GameObject(stars.x, stars.y, new int[stars.matrix.length][stars.matrix[0].length]);
        earth = new GameObject(getRandomNumber(53), getRandomNumber(53), ShapeMatrix.EARTH);
        heightMeter = new Meter(66, 19, this);
        speedMeterX = new Meter(74, 19, this);
        speedMeterY = new Meter(78, 19, this);
        speedMeterZ = new Meter(82, 19, this);
        createStarMap();
    }


    // DRAW

    private void drawScene() {
        long time = (new Date().getTime() - startTime);
        drawGameBackground();

        if (!disableStars) {
            drawStarMap();
        }

        if (!disableEarth) {
            earth.draw(this);
        }

        moon.draw();
        lander.draw(this);
        drawInterfaceBackground();
        heightMeter.displayHeight(moon.radius);
        speedMeterX.displaySpeed(lander.speedX);
        speedMeterY.displaySpeed(lander.speedY);
        speedMeterZ.displaySpeed(lander.speedZ);

        // text
        writer.write("FPS " + (fps), Color.PINK, 66, 0, false);
        writer.write(("TIME " + (time / 1000)), Color.YELLOW, 66, 9, false);
        writer.write("s\np\ne\ne\nd", Color.LAWNGREEN, 90, 19, false);

        writer.write("пропуск кадров",
                (limitFPS ? Color.YELLOW : Color.WHITE), 2, 66, false);
        writer.write("прозрачная луна",
                (transparentMoon ? Color.YELLOW : Color.WHITE), 2, 74, false);
        writer.write("отключить звёзды",
                (disableStars ? Color.YELLOW : Color.WHITE), 2, 82, false);
        writer.write("отключить землю",
                (disableEarth ? Color.YELLOW : Color.WHITE), 2, 90, false);
        frameCounter++;
        long after = new Date().getTime();
        if (after - before > 999) {
            before = new Date().getTime();
            fps = frameCounter;
            frameCounter = 0;
        }
    }

    private void drawGameBackground() {
        for (int y = 0; y < 64; y++) {
            for (int x = 0; x < 64; x++) {
                setCellValueEx(x, y, Color.BLACK, "");
            }
        }
    }

    private void drawInterfaceBackground() {
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
                int bigStarX = (int) bigStars.x + x * 2;
                int bigStarY = (int) bigStars.y + y * 2;
                if (stars.matrix[y][x] == 1) {
                    if (getRandomNumber(10) != 1) {
                        setCellTextColor(starX, starY, Color.WHITE);
                    } else {
                        setCellTextColor(starX, starY, Color.BLUE);
                    }
                    setCellValue(starX, starY, "*");
                }
                if (bigStars.matrix[y][x] == 1) {
                    if (getRandomNumber(5) != 1) {
                        setCellTextColor(bigStarX, bigStarY, Color.PALEGOLDENROD);
                    } else {
                        setCellTextColor(bigStarX, bigStarY, Color.ORANGE);
                    }
                    setCellValue(bigStarX, bigStarY, "●");
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
                    bigStars.matrix[y][x] = 1;
                }
            }
        }
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
                lander.startLanding();
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

    @Override
    public void onMouseLeftClick(int x, int y) {
        if (y >= 66 && y < 72) {
            limitFPS = !limitFPS;
        } else if (y >= 74 && y < 82) {
            transparentMoon = !transparentMoon;
        } else if (y >= 82 && y < 90) {
            disableStars = !disableStars;
        } else if (y >= 90 && y < 98) {
            disableEarth = !disableEarth;
        }
    }

    // CELL FILLERS

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

}
