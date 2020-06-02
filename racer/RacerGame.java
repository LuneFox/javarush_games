package com.javarush.games.racer;

import com.javarush.engine.cell.*;
import com.javarush.games.racer.road.RoadManager;

public class RacerGame extends Game {
    public final static int WIDTH = 64;
    public final static int HEIGHT = 64;
    public final static int CENTER_X = WIDTH / 2;
    public final static int ROADSIDE_WIDTH = 14;
    private final static int RACE_GOAL_CARS_COUNT = 40;

    private RoadMarking roadMarking;
    private PlayerCar player;
    private RoadManager roadManager;
    private FinishLine finishLine;
    private ProgressBar progressBar;

    private boolean isGameStopped;
    private int score;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int step) {
        score -= 5;
        setScore(score);
        if (roadManager.checkCrush(player)) {
            gameOver();
            drawScene();
            return;
        } else if (finishLine.isCrossed(player)) {
            win();
            drawScene();
            return;
        }
        if (roadManager.getPassedCarsCount() >= RACE_GOAL_CARS_COUNT) {
            finishLine.show();
        }
        roadManager.generateNewRoadObjects(this);
        moveAll();
        drawScene();
    }

    private void createGame() {
        score = 3500;
        roadMarking = new RoadMarking();
        player = new PlayerCar();
        roadManager = new RoadManager();
        finishLine = new FinishLine();
        progressBar = new ProgressBar(RACE_GOAL_CARS_COUNT);
        drawScene();
        setTurnTimer(40);
        isGameStopped = false;
    }

    private void drawScene() {
        drawField();
        finishLine.draw(this);
        roadMarking.draw(this);
        roadManager.draw(this);
        player.draw(this);
        progressBar.draw(this);
    }

    private void drawField() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (x == CENTER_X) {
                    setCellColor(x, y, Color.WHITE);
                } else if (x < ROADSIDE_WIDTH || x >= WIDTH - ROADSIDE_WIDTH) {
                    setCellColor(x, y, Color.DARKGREEN);
                } else {
                    setCellColor(x, y, Color.GRAY);
                }
            }
        }
    }

    private void moveAll() {
        roadMarking.move(player.speed);
        roadManager.move(player.speed);
        finishLine.move(player.speed);
        progressBar.move(roadManager.getPassedCarsCount());
        player.move();
    }

    private void win() {
        isGameStopped = true;
        showMessageDialog(Color.DARKBLUE, "CONGRATULATIONS!", Color.WHITE, 55);
        stopTurnTimer();
    }

    private void gameOver() {
        isGameStopped = true;
        showMessageDialog(Color.DARKBLUE, "RACE OVER", Color.WHITE, 75);
        stopTurnTimer();
        player.stop();
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if (x < 0 || y < 0 || x > WIDTH - 1 || y > HEIGHT - 1) {
            return;
        }
        super.setCellColor(x, y, color);
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case LEFT:
                player.setDirection(Direction.LEFT);
                break;
            case RIGHT:
                player.setDirection(Direction.RIGHT);
                break;
            case UP:
                player.speed = 2;
                break;
            case SPACE:
                if (isGameStopped) {
                    createGame();
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
                if (player.getDirection() == Direction.LEFT) {
                    player.setDirection(Direction.NONE);
                }
                break;
            case RIGHT:
                if (player.getDirection() == Direction.RIGHT) {
                    player.setDirection(Direction.NONE);
                }
                break;
            case UP:
                player.speed = 1;
                break;
            default:
                break;
        }
    }
}
