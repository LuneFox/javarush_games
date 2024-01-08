package com.javarush.games.ticktacktoe.controller;


import com.javarush.games.ticktacktoe.TicTacToeGame;
import com.javarush.games.ticktacktoe.model.gameobjects.Side;

public class BoardControlStrategy implements ControlStrategy {
    private final TicTacToeGame game;

    public BoardControlStrategy(TicTacToeGame game) {
        this.game = game;
    }

    @Override
    public void click(int x, int y, Click click) {
        int logicalX = (x - 10) / 10;
        int logicalY = (y - 10) / 10;
        if (click == Click.LEFT) {
            game.getField().putDisk(logicalX, logicalY, Side.BLACK);
        } else {
            game.getField().putDisk(logicalX, logicalY, Side.WHITE);
        }
    }
}
