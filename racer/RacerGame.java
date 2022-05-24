package com.javarush.games.racer;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.racer.controller.Control;
import com.javarush.games.racer.controller.Controller;
import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.model.road.RoadManager;
import com.javarush.games.racer.view.Display;
import com.javarush.games.racer.view.overlay.Overlay;
import com.javarush.games.racer.view.printer.SymbolImage;

/**
 * Version 1.03
 */

public class RacerGame extends Game {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    private static final int ENDING_ANIMATION_LENGTH_IN_FRAMES = 150;
    private static final int MILLISECONDS_PER_FRAME = 40;

    private final Display display = new Display(this);
    private Controller controller;
    private DeLorean delorean;
    private RoadManager roadManager;
    private Overlay overlay;
    private int raceTime;
    private boolean isStopped;
    private int endingAnimationFrames;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);

        GameObject.setGame(this);
        SymbolImage.setDisplay(display);

        createGame();
        showOpeningDialogue();
    }

    @Control(Key.SPACE)
    public void createGame() {
        createNewGameObjects();
        resetValues();
    }

    private void showOpeningDialogue() {
        try {
            showMessageDialog(Color.BLACK,
                    "Святые угодники! Мы на российском шоссе 21 века, Марти!\n" +
                            "Нужно вернуться в Россию будущего, там дороги не нужны!\n" +
                            "Собери 1.21 ГВт энергии, чтобы разогнать Делореан до 88 миль в час!\n" +
                            "Главное — не попадай в лужи, а тем более в ямы!", Color.YELLOW, 15);
        } catch (NullPointerException ignored) {

        }
    }

    private void createNewGameObjects() {
        delorean = new DeLorean();
        controller = new Controller(this);
        roadManager = new RoadManager(this);
        overlay = new Overlay(this);
    }

    private void resetValues() {
        endingAnimationFrames = 0;
        raceTime = 0;
        isStopped = false;
        setTurnTimer(MILLISECONDS_PER_FRAME);
    }

    @Override
    public void onTurn(int step) {
        roadManager.generateNewRoadObjects(delorean);
        roadManager.checkOverlapsWithCar(delorean);
        moveAll();
        drawAll();
        checkGameOver();
        countTime();
        countEndingAnimationFrames();
    }

    private void moveAll() {
        delorean.move();
        roadManager.moveObjects(delorean.getSpeed());
    }

    private void drawAll() {
        roadManager.drawObjects();
        delorean.draw();
        overlay.draw();
        display.draw();
    }

    private void checkGameOver() {
        if (delorean.passedPortal()) {
            delorean.stop();
            isStopped = true;
        }
    }

    private void countTime() {
        if (isStopped) return;
        if (delorean.getDistance() <= 0) return;

        raceTime += MILLISECONDS_PER_FRAME;
    }

    private void countEndingAnimationFrames() {
        if (!isStopped) return;
        if (isEndingAnimationFinished()) return;

        endingAnimationFrames++;
    }

    public boolean isEndingAnimationFinished() {
        return endingAnimationFrames > ENDING_ANIMATION_LENGTH_IN_FRAMES;
    }

    public boolean deloreanHasMaxEnergy() {

        return delorean.hasMaxEnergy();
    }


    /*
     * Controls
     */

    @Override
    public void onKeyPress(Key key) {
        controller.pressKey(key);
    }

    @Override
    public void onKeyReleased(Key key) {
        controller.releaseKey(key);
    }


    /*
     * Getters, setters
     */

    public Display getDisplay() {
        return display;
    }

    public DeLorean getDelorean() {
        return delorean;
    }

    public int getRaceTime() {
        return raceTime;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public int getEndingAnimationFrames() {
        return endingAnimationFrames;
    }
}
