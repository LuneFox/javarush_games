package com.javarush.games.ticktacktoe;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

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

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        GameObject.setGame(this);

        display = new Display(this);
        controller = new Controller(this);

        SymbolImage.setDisplay(display);

        Printer.print("Hello, Tic-Tac-Toe!", Color.YELLOW, 15, 15, TextAlign.CENTER);
        display.draw();
    }

    public Display getDisplay() {
        return display;
    }

    public static TicTacToeGame getInstance() {
        return instance;
    }
}
