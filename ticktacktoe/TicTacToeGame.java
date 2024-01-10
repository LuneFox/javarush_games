package com.javarush.games.ticktacktoe;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

import com.javarush.engine.cell.Key;
import com.javarush.games.ticktacktoe.controller.Click;
import com.javarush.games.ticktacktoe.model.gameobjects.Field;
import com.javarush.games.ticktacktoe.model.gameobjects.GameObject;
import com.javarush.games.ticktacktoe.controller.Controller;
import com.javarush.games.ticktacktoe.model.gameobjects.Side;
import com.javarush.games.ticktacktoe.view.*;
import com.javarush.games.ticktacktoe.view.printer.*;

public class TicTacToeGame extends Game {
    private static TicTacToeGame instance;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    private Controller controller;
    private Display display;
    private Field field;

    private Side currentPlayer;
    private boolean isStopped;

    /*
     * Game start
     */

    @Override
    public void initialize() {
        setupEnvironment();
        bindAssets();
        createNewGame();
    }

    private void setupEnvironment() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        setTurnTimer(40);
    }

    private void bindAssets() {
        instance = this;
        display = new Display(this);
        controller = new Controller(this);
        GameObject.setGame(this);
        SymbolImage.setDisplay(display);
    }

    public void createNewGame() {
        currentPlayer = Side.BLACK;
        field = new Field();
        isStopped = false;
    }

    public void changePlayer() {
        if (currentPlayer == Side.BLACK) {
            currentPlayer = Side.WHITE;
        } else currentPlayer = Side.BLACK;
    }

    /*
     * Time flow
     */

    @Override
    public void onTurn(int step) {
        field.draw();
        Printer.print("РЕВЕРСИ", Color.SLATEGRAY, 1, 1, TextAlign.CENTER);
        Printer.print(String.valueOf(field.countDisks(Side.BLACK)), Color.BLACK, 1, 46, TextAlign.LEFT);
        Printer.print(String.valueOf(field.countDisks(Side.WHITE)), Color.WHITE, 100, 46, TextAlign.RIGHT);
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

    public static TicTacToeGame getInstance() {
        return instance;
    }

    public Display getDisplay() {
        return display;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public Field getField() {
        return field;
    }

    public Side getCurrentPlayer() {
        return currentPlayer;
    }
}
