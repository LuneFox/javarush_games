package com.javarush.games.racer;

import com.javarush.engine.cell.*;
import com.javarush.games.racer.graphics.Bitmap;
import com.javarush.games.racer.graphics.Text;
import com.javarush.games.racer.road.RoadManager;
import com.javarush.games.racer.road.RoadMarking;

public class RacerGame extends Game {
    public final static int WIDTH = 100;
    public final static int HEIGHT = 100;

    public final Display display = new Display(this);
    public final InputEvent inputEvent = new InputEvent(this);
    public final Text text = new Text(Bitmap.NONE, this);

    public DeLorean delorean;
    public Portal portal;
    public TireFlame tireFlame;
    public Marty marty;
    public RoadMarking roadMarking;
    public RoadManager roadManager;

    public int finishTimeOut;
    public boolean isStopped;
    private int time;


    // GAME MECHANICS

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        text.loadAlphabet();
        createGame();
    }

    @Override
    public void onTurn(int step) {
        roadManager.generateNewRoadObjects(this);
        roadManager.checkCross(delorean);
        if (!isStopped) {
            time += 40;
        }
        moveAll();
        drawScene();
    }

    public void createGame() {
        delorean = new DeLorean();
        portal = new Portal();
        tireFlame = new TireFlame();
        marty = new Marty();
        roadMarking = new RoadMarking();
        roadManager = new RoadManager();
        finishTimeOut = 100;
        time = 0;
        setTurnTimer(40);
        isStopped = false;
    }

    private void drawScene() {
        drawField();
        roadMarking.draw(this);
        roadManager.draw(this);
        delorean.animate(this, (int) (10 / delorean.getSpeed() + 0.0001));
        portal.animate(this, delorean);
        tireFlame.animate(this, delorean);
        drawEnding();
        drawSpeed();
        display.draw();
    }

    private void moveAll() {
        delorean.steer();
        delorean.gas();
        roadManager.move(delorean.getSpeed());
        roadMarking.move(delorean.getSpeed());
    }


    // VISUALS & GRAPHICS

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

    private void drawSpeed(){
        if (isStopped) {
            text.write("88 MPH", Color.WHITE, 2, 0, false);
        } else {
            text.write((int) (delorean.getSpeed() * 10) + " MPH", Color.WHITE, 2, 0, false);
        }
    }

    private void drawEnding(){
        if (isStopped && finishTimeOut > 0) {
            finishTimeOut--;
        }
        if (finishTimeOut <= 50) {
            marty.draw(this);
            if (finishTimeOut <= 30) {
                text.write("ВРЕМЯ: " + (time / 1000) + "' " + (time % 1000) / 10 + "\"",
                        Color.WHITE, 3, HEIGHT - 9, false);
            }
        }
    }


    // OVERRIDES

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

    // GETTERS

    public Portal getPortal() {
        return portal;
    }
}
