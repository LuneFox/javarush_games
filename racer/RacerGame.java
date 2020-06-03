package com.javarush.games.racer;

import com.javarush.engine.cell.*;

public class RacerGame extends Game {
    public final static int WIDTH = 100;
    public final static int HEIGHT = 100;

    public final Display display = new Display(this);
    public final InputEvent inputEvent = new InputEvent(this);

    public Delorean delorean;
    public RoadMarking roadMarking;


    // GAME MECHANICS

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int step) {
        delorean.steer();
        delorean.gas();
        roadMarking.move(delorean.getSpeed());
        drawScene();
    }

    private void createGame() {
        delorean = new Delorean();
        roadMarking = new RoadMarking();
        setTurnTimer(40);
    }

    private void drawScene() {
        drawField();
        roadMarking.draw(this);
        delorean.draw(this);
        display.draw();
    }


    // GRAPHICS

    private void drawField() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (y < RoadManager.UPPER_BORDER || y >= RoadManager.LOWER_BORDER) {
                    display.setCellColor(x, y, Color.SIENNA);
                } else if (y == HEIGHT / 2) {
                    display.setCellColor(x, y, Color.SNOW);
                } else {
                    display.setCellColor(x, y, Color.DARKGRAY);
                }

            }
        }
    }

    // REPLACED METHODS

    @Override
    public void setCellColor(int x, int y, Color color) {
        if (x < 0 || y < 0 || x > WIDTH - 1 || y > HEIGHT - 1) {
            return;
        }
        super.setCellColor(x, y, color);
    }


    // CONTROLS

    @Override
    public void onKeyPress(Key key) {
        inputEvent.keyPress(key);
    }

    @Override
    public void onKeyReleased(Key key) {
        inputEvent.keyRelease(key);
    }
}
