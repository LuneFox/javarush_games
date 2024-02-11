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

/**
 * Version 1.1
 */

public class TicTacToeGame extends Game {
    private static TicTacToeGame instance;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    private Controller controller;
    private Display display;
    private Field field;

    private Side currentPlayer;
    private String victoryMessage;
    private boolean isComputerTurn;
    private boolean isStarted;
    private long cpuThinkingTime;

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
        isComputerTurn = false;
        field = new Field();
        isStarted = false;
    }

    public void changePlayer() {
        if (currentPlayer == Side.BLACK) {
            currentPlayer = Side.WHITE;
        } else {
            currentPlayer = Side.BLACK;
        }

        isComputerTurn = !isComputerTurn;
    }

    /*
     * Time flow
     */

    @Override
    public void onTurn(int step) {
        field.draw();
        printTextInfo();
        cpuMove();
        printVictoryMessage();
        display.draw();
    }

    private void printTextInfo() {
        Printer.print("<РЕВЕРСИ>", Color.LIGHTSLATEGRAY, 1, 0, TextAlign.CENTER);
        Printer.print("1.1", Color.DARKOLIVEGREEN, 100, 0, TextAlign.RIGHT);
                Printer.print(String.valueOf(field.countDisks(Side.BLACK)), Color.BLACK, 1, 46, TextAlign.LEFT);
        Printer.print(String.valueOf(field.countDisks(Side.WHITE)), Color.WHITE, 100, 46, TextAlign.RIGHT);

        if (!isStarted){
            Printer.print("ИГРА ЗА БЕЛЫХ - ENTER", Color.SLATEGRAY, 1, 91, TextAlign.CENTER);
        }
    }

    private void cpuMove() {
        if (isComputerTurn) {
            if (cpuThinkingTime < 100) cpuThinkingTime++;
            if (cpuThinkingTime < 15) Printer.print(" ", Color.YELLOW, 50, 89, TextAlign.CENTER);
            else if (cpuThinkingTime < 30) Printer.print(".", Color.YELLOW, 50, 89, TextAlign.CENTER);
            else if (cpuThinkingTime < 45) Printer.print(". .", Color.YELLOW, 50, 89, TextAlign.CENTER);
            else if (cpuThinkingTime < 60) Printer.print(". . .", Color.YELLOW, 50, 89, TextAlign.CENTER);
            field.makeCpuMove();
        }
    }

    private void printVictoryMessage() {
        if (field.noMovesLeft()) {
            checkWinner();
            Printer.print(victoryMessage, Color.YELLOW, 1, 91, TextAlign.CENTER);
            isComputerTurn = false;
        }
    }

    private void checkWinner() {
        int countBlack = field.countDisks(Side.BLACK);
        int countWhite = field.countDisks(Side.WHITE);

        if (countBlack > countWhite) {
            victoryMessage = "Победили чёрные!";
        } else if (countWhite > countBlack) {
            victoryMessage = "Победили белые!";
        } else {
            victoryMessage = "Ничья!";
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
     * Getters
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

    public Side getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isComputerTurn() {
        return isComputerTurn;
    }

    public void setComputerTurn(boolean computerTurn) {
        isComputerTurn = computerTurn;
    }

    public long getCpuThinkingTime() {
        return cpuThinkingTime;
    }

    public void setCpuThinkingTime(long cpuThinkingTime) {
        this.cpuThinkingTime = cpuThinkingTime;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
