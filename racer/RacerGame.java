package com.javarush.games.racer;

import com.javarush.engine.cell.*;
import com.javarush.games.racer.road.RoadManager;

public class RacerGame extends Game {
    public final static int WIDTH = 64;
    public final static int HEIGHT = 64;
    public final static int CENTER_X = WIDTH / 2;
    public final static int ROADSIDE_WIDTH = 14;

    private RoadMarking roadMarking;
    private PlayerCar player;
    private RoadManager roadManager;

    private boolean isGameStopped;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int step) {
        if (roadManager.checkCrush(player)) {
            gameOver();
            drawScene();
            return;
        }
        roadManager.generateNewRoadObjects(this);
        moveAll();
        drawScene();
    }

    private void createGame() {
        roadMarking = new RoadMarking();
        player = new PlayerCar();
        roadManager = new RoadManager();
        drawScene();
        setTurnTimer(40);
        isGameStopped = false;
    }

    private void drawScene() {
        drawField();
        roadMarking.draw(this);
        roadManager.draw(this);
        player.draw(this);
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
        player.move();
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
