package com.javarush.games.ticktacktoe.controller;


import com.javarush.games.ticktacktoe.TicTacToeGame;

public class BoardControlStrategy implements ControlStrategy {
    private final TicTacToeGame game;

    public BoardControlStrategy(TicTacToeGame game) {
        this.game = game;
    }

    @Override
    public void click(int x, int y, Click click) {
        if (click == Click.LEFT) {
            int logicalX = (x - 10) / 10;
            int logicalY = (y - 10) / 10;
            System.out.println("logicalX = " + logicalX);
            System.out.println("logicalY = " + logicalY);
            game.getField().putDisk(logicalX, logicalY);

        }
    }
}
