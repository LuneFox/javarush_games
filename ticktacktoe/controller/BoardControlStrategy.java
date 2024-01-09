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

        game.getField().clickOnBoard(x, y, click);
    }

    @Override
    public void pressSpace() {
        game.createNewGame();
    }

    private boolean isClickOffBoard(int x, int y) {
        return (x < 10 || y < 10 || x > 90 || y > 90);
    }
}
