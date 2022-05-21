package com.javarush.games.racer;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.racer.controller.Control;
import com.javarush.games.racer.controller.Controller;
import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.decor.Marty;
import com.javarush.games.racer.model.decor.Portal;
import com.javarush.games.racer.model.decor.TireFlame;
import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.model.road.RoadManager;
import com.javarush.games.racer.model.road.RoadMarking;
import com.javarush.games.racer.view.Display;
import com.javarush.games.racer.view.printer.Printer;
import com.javarush.games.racer.view.printer.SymbolImage;
import com.javarush.games.racer.view.printer.TextAlign;

public class RacerGame extends Game {
    public final static String VERSION = "1.01";
    public final static int WIDTH = 100;
    public final static int HEIGHT = 100;

    public final Display display = new Display(this);
    public final Controller controller = new Controller(this);

    public DeLorean delorean;
    public Portal portal;
    public TireFlame rightTireFlame;
    public TireFlame leftTireFlame;
    public Marty marty;
    public RoadMarking roadMarking;
    public RoadManager roadManager;

    public static boolean allowCountTime;
    public int finishTimeOut;
    public boolean isStopped;
    private int time;


    // GAME MECHANICS

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        GameObject.setGame(this);
        SymbolImage.setGame(this);
        createGame();
        try {
            showMessageDialog(Color.BLACK, "Святые угодники! Мы на российском шоссе 21 века, Марти!\n" +
                    "Нужно вернуться в Россию будущего, там дороги не нужны!\n" +
                    "Собери 1.21 ГВт энергии, чтобы разогнать Делореан до 88 миль в час!\n" +
                    "Главное — не попадай в лужи, а тем более в ямы!", Color.YELLOW, 15);
        } catch (NullPointerException ignored) {

        }
    }


    @Override
    public void onTurn(int step) {
        checkGameOver();
        roadManager.generateNewRoadObjects(this, delorean);
        roadManager.checkCross(delorean);
        countTime();
        moveAll();
        drawScene();
    }

    private void checkGameOver() {
        if (delorean.x > portal.x + 5) {
            delorean.setSpeed(0);
            isStopped = true;
        }
    }

    private void countTime() {
        if (!isStopped && allowCountTime) {
            time += 40;
        }
    }

    @Control(Key.SPACE)
    public void createGame() {
        delorean = new DeLorean();
        portal = new Portal();
        rightTireFlame = new TireFlame(TireFlame.Side.RIGHT);
        leftTireFlame = new TireFlame(TireFlame.Side.LEFT);
        marty = new Marty();
        roadMarking = new RoadMarking();
        roadManager = new RoadManager();
        finishTimeOut = 100;
        time = 0;
        setTurnTimer(40);
        isStopped = false;
        allowCountTime = false;
    }

    private void drawScene() {
        drawField();
        roadMarking.draw();
        roadManager.draw();
        delorean.draw();
        portal.draw();
        rightTireFlame.draw();
        leftTireFlame.draw();
        drawEnding();
        drawSpeed();
        drawEnergy();
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
                    display.drawPixel(x, y, Color.SIENNA);
                } else if (y == HEIGHT / 2) {
                    display.drawPixel(x, y, Color.SNOW);
                } else {
                    display.drawPixel(x, y, Color.DARKGRAY);
                }

            }
        }
    }

    private void drawSpeed() {
        if (isStopped) {
            Printer.print("<88 МВЧ>", Color.WHITE, 2, 0);
        } else {
            Printer.print("<" + (int) (delorean.getSpeed() * 10) + " МВЧ>", Color.WHITE, 2, 0, TextAlign.LEFT);
        }
    }

    private void drawEnergy() {
        Printer.print("<" + delorean.getEnergy() + " ГВТ>", Color.YELLOW, WIDTH - 1, 0, TextAlign.RIGHT);
    }

    private void drawEnding() {
        if (isStopped && finishTimeOut > 0) {
            finishTimeOut--;
        }
        if (finishTimeOut <= 50) {
            marty.draw();
            if (finishTimeOut <= 30) {
                Printer.print("<ВРЕМЯ: " + (time / 1000) + "' " + (time % 1000) / 10 + "\">", 2, HEIGHT - 9);
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
        controller.pressKey(key);
    }

    @Override
    public void onKeyReleased(Key key) {
        controller.releaseKey(key);
    }

    /*
     * Getters, setters
     */

    public static void allowCountTime() {
        RacerGame.allowCountTime = true;
    }

    public Display getDisplay() {
        return display;
    }
}
