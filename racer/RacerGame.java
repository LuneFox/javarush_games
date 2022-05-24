package com.javarush.games.racer;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.racer.controller.Control;
import com.javarush.games.racer.controller.Controller;
import com.javarush.games.racer.model.GameObjectManager;
import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.decor.Portal;
import com.javarush.games.racer.model.decor.RoadMarkingManager;
import com.javarush.games.racer.model.decor.TireFlame;
import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.model.road.RoadManager;
import com.javarush.games.racer.view.Display;
import com.javarush.games.racer.view.Overlay;
import com.javarush.games.racer.view.printer.SymbolImage;

import java.util.stream.Stream;

/**
 * Version 1.02
 */

public class RacerGame extends Game {
    public static final int ENDING_ANIMATION_LENGTH = 150;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    private static final int MILLISECONDS_PER_FRAME = 40;

    private final Display display = new Display(this);
    private final Controller controller = new Controller(this);

    public DeLorean delorean;
    private Portal portal;
    private TireFlame rightTireFlame;
    private TireFlame leftTireFlame;

    private RoadMarkingManager roadMarkingManager;
    private RoadManager roadManager;
    private Overlay overlay;

    public int raceTime;
    public boolean isStopped;
    public int framesAfterStop;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);

        GameObject.setGame(this);
        SymbolImage.setDisplay(display);

        createGame();

        try {
            showMessageDialog(Color.BLACK, "Святые угодники! Мы на российском шоссе 21 века, Марти!\n" +
                    "Нужно вернуться в Россию будущего, там дороги не нужны!\n" +
                    "Собери 1.21 ГВт энергии, чтобы разогнать Делореан до 88 миль в час!\n" +
                    "Главное — не попадай в лужи, а тем более в ямы!", Color.YELLOW, 15);
        } catch (NullPointerException ignored) {

        }
    }


    @Control(Key.SPACE)
    public void createGame() {
        createNewGameObjects();
        resetValues();
    }

    private void createNewGameObjects() {
        delorean = new DeLorean();
        portal = new Portal();
        rightTireFlame = new TireFlame(TireFlame.Side.RIGHT);
        leftTireFlame = new TireFlame(TireFlame.Side.LEFT);

        roadMarkingManager = new RoadMarkingManager();
        roadManager = new RoadManager(this);
        overlay = new Overlay(this);
    }

    private void resetValues() {
        framesAfterStop = 0;
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

        countTime();
        checkGameOver();
    }

    private void moveAll() {
        delorean.move();
        Stream.of(roadMarkingManager, roadManager)
                .forEach(manager -> manager.moveObjects(delorean.getSpeed()));
    }

    private void drawAll() {
        drawRoad();
        Stream.of(roadMarkingManager, roadManager)
                .forEach(GameObjectManager::drawObjects);
        Stream.of(delorean, portal, rightTireFlame, leftTireFlame)
                .forEach(GameObject::draw);
        overlay.draw();
        display.draw();
    }

    private void drawRoad() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Color color;

                if (y < RoadManager.UPPER_BORDER || y >= RoadManager.LOWER_BORDER) {
                    color = Color.SIENNA;
                } else if (y == HEIGHT / 2) {
                    color = Color.SNOW;
                } else {
                    color = Color.DARKGRAY;
                }

                display.drawPixel(x, y, color);
            }
        }
    }

    private void countTime() {
        if (isStopped) return;
        if (delorean.getDistance() <= 0) return;

        raceTime += MILLISECONDS_PER_FRAME;
    }

    private void checkGameOver() {
        if (deloreanPassedPortal()) {
            delorean.stop();
            isStopped = true;
        }
    }

    private boolean deloreanPassedPortal() {
        return delorean.x - portal.x > 5;
    }

    public boolean deloreanHasMaxEnergy() {
        return delorean.getEnergy() >= DeLorean.MAX_ENERGY;
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
}
