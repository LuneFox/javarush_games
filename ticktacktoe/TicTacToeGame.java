package com.javarush.games.ticktacktoe;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

import com.javarush.engine.cell.Key;
import com.javarush.games.ticktacktoe.controller.Click;
import com.javarush.games.ticktacktoe.model.gameobjects.Field;
import com.javarush.games.ticktacktoe.model.gameobjects.GameObject;
import com.javarush.games.ticktacktoe.controller.Controller;
import com.javarush.games.ticktacktoe.view.*;
import com.javarush.games.ticktacktoe.view.printer.*;

public class TicTacToeGame extends Game {
    private static TicTacToeGame instance;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    private Controller controller;
    private Display display;
    private Field field;
    private boolean isStopped;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        setTurnTimer(40);
        instance = this;
        display = new Display(this);
        controller = new Controller(this);
        GameObject.setGame(this);
        SymbolImage.setDisplay(display);
        createNewGame();
    }

    public void createNewGame() {
        field = new Field();
        isStopped = false;
    }

    @Override
    public void onTurn(int step) {
        field.draw();
        Printer.print("РЕВЕРСИ", Color.YELLOW, 1, 1, TextAlign.CENTER);
        display.draw();
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

    @Override
    public void onMouseLeftClick(int x, int y) {
        controller.click(x, y, Click.LEFT);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        controller.click(x, y, Click.RIGHT);
    }

    /*
     * Getters
     */

    public Display getDisplay() {
        return display;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public Field getField() {
        return field;
    }

    public static TicTacToeGame getInstance() {
        return instance;
    }
}
