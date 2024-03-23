package com.javarush.games.ticktacktoe;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

import com.javarush.engine.cell.Key;
import com.javarush.games.ticktacktoe.controller.Click;
import com.javarush.games.ticktacktoe.model.Computer;
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
    private static final String VERSION = "1.2";

    private Controller controller;
    private Display display;
    private Field field;
    private Computer computer;

    private Side currentPlayer;
    private boolean isComputerTurn;
    private boolean isStarted;

    /*
     * Game start
     */

    @Override
    public void initialize() {
        setupJavaRushGamesEnvironment();
        createAndLinkAssets();
        setupNewGame();
    }

    private void setupJavaRushGamesEnvironment() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        setTurnTimer(40);
    }

    private void createAndLinkAssets() {
        instance = this;
        display = new Display(this);
        controller = new Controller(this);
        computer = new Computer(this, 5);
        GameObject.setGame(this);
        SymbolImage.setDisplay(display);
    }

    public void setupNewGame() {
        currentPlayer = Side.BLACK;
        isComputerTurn = false;
        field = new Field();
        isStarted = false;
    }

    public void changePlayer() {
        currentPlayer = (currentPlayer == Side.BLACK ? Side.WHITE : Side.BLACK);
        isComputerTurn = !isComputerTurn;
    }

    /*
     * Time flow
     */

    @Override
    public void onTurn(int step) {
        field.draw();

        if (isComputerTurn) {
            computer.takeTurn();
        }

        printTextInfo();
        display.draw();
    }

    private void printTextInfo() {
        Printer.print("<РЕВЕРСИ>", Color.LIGHTSLATEGRAY, 1, 0, TextAlign.CENTER);
        Printer.print(VERSION, Color.DARKOLIVEGREEN, 100, 0, TextAlign.RIGHT);
        Printer.print(String.valueOf(field.countDisks(Side.BLACK)), Color.BLACK, 1, 46, TextAlign.LEFT);
        Printer.print(String.valueOf(field.countDisks(Side.WHITE)), Color.WHITE, 100, 46, TextAlign.RIGHT);

        if (computer.showSpeedSetting()) {
            return;
        }

        if (!isStarted) {
            Printer.print("ИГРА ЗА БЕЛЫХ - ENTER", Color.SLATEGRAY, 1, 91, TextAlign.CENTER);
        }

        if (field.noMovesLeft()) {
            Printer.print(getVictoryMessage(), Color.YELLOW, 1, 91, TextAlign.CENTER);
        }

    }

    private String getVictoryMessage() {
        int countBlack = field.countDisks(Side.BLACK);
        int countWhite = field.countDisks(Side.WHITE);

        if (countBlack > countWhite) {
            return "Победили чёрные!";
        } else if (countWhite > countBlack) {
            return "Победили белые!";
        } else {
            return "Ничья!";
        }
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
     * Getters and setters
     */

    public static TicTacToeGame getInstance() {
        return instance;
    }

    public Display getDisplay() {
        return display;
    }

    public Field getField() {
        return field;
    }

    public Computer getComputer() {
        return computer;
    }

    public Side getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void start() {
        isStarted = true;
    }

    public boolean isComputerTurn() {
        return isComputerTurn;
    }

    public void setComputerTurn(boolean computerTurn) {
        isComputerTurn = computerTurn;
    }
}
