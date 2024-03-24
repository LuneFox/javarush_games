package com.javarush.games.ticktacktoe.controller;


import com.javarush.games.ticktacktoe.TicTacToeGame;

public class BoardControlStrategy implements ControlStrategy {
    private final TicTacToeGame game;

    public BoardControlStrategy(TicTacToeGame game) {
        this.game = game;
    }

    @Override
    public void click(int x, int y, Click click) {
        if (isClickOffBoard(x, y)) return;
        if (game.isComputerTurn()) return;

        game.clickOnBoard(x, y, click);
    }

    @Override
    public void pressSpace() {
        if (game.isComputerTurn()) return;
        game.setupNewGame();
    }

    @Override
    public void pressEnter() {
        if (!game.isStarted()) {
            game.setComputerTurn(false);
            game.start();
        }
    }

    @Override
    public void pressRight() {
        game.getComputer().increaseSpeed();
    }

    @Override
    public void pressLeft() {
        game.getComputer().decreaseSpeed();
    }

    private boolean isClickOffBoard(int x, int y) {
        return (x < 10 || y < 10 || x >= 90 || y >= 90);
    }
}
