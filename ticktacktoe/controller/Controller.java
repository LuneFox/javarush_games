package com.javarush.games.ticktacktoe.controller;

import com.javarush.engine.cell.Key;
import com.javarush.games.ticktacktoe.TicTacToeGame;


public class Controller {
    private final ControlStrategy controlStrategy;

    public Controller(TicTacToeGame game) {
        this.controlStrategy = new BoardControlStrategy(game);
    }

    public final void click(int x, int y, Click click) {
        controlStrategy.click(x, y, click);
    }

    public final void pressKey(Key key) {
        if (key == Key.UP) controlStrategy.pressUp();
        else if (key == Key.DOWN) controlStrategy.pressDown();
        else if (key == Key.LEFT) controlStrategy.pressLeft();
        else if (key == Key.RIGHT) controlStrategy.pressRight();
        else if (key == Key.SPACE) controlStrategy.pressSpace();
        else if (key == Key.ENTER) controlStrategy.pressEnter();
    }

    public final void releaseKey(Key key) {
        if (key == Key.UP) controlStrategy.releaseUp();
        else if (key == Key.DOWN) controlStrategy.releaseDown();
        else if (key == Key.LEFT) controlStrategy.releaseLeft();
        else if (key == Key.RIGHT) controlStrategy.releaseRight();
        else if (key == Key.SPACE) controlStrategy.releaseSpace();
        else if (key == Key.ENTER) controlStrategy.releaseEnter();
    }
}
