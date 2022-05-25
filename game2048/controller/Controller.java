package com.javarush.games.game2048.controller;

import com.javarush.engine.cell.Key;
import com.javarush.games.game2048.Game2048;

public class Controller {
    private final ControlStrategy controlStrategy;
    private final Game2048 game;

    public Controller(Game2048 game) {
        this.game = game;
        this.controlStrategy = new MainControlStrategy(game);
    }

    public final void leftClick(int x, int y) {
        controlStrategy.leftClick(x, y);
    }

    public final void rightClick(int x, int y) {
        controlStrategy.rightClick(x, y);
    }

    public final void pressKey(Key key) {
        if (key == Key.UP) controlStrategy.pressUp();
        else if (key == Key.DOWN) controlStrategy.pressDown();
        else if (key == Key.LEFT) controlStrategy.pressLeft();
        else if (key == Key.RIGHT) controlStrategy.pressRight();
        else if (key == Key.SPACE) controlStrategy.pressSpace();
        else if (key == Key.ENTER) controlStrategy.pressEnter();
        else controlStrategy.pressAnyOtherKey();
    }

    public final void releaseKey(Key key) {
        if (key == Key.UP) controlStrategy.releaseUp();
        else if (key == Key.DOWN) controlStrategy.releaseDown();
        else if (key == Key.LEFT) controlStrategy.releaseLeft();
        else if (key == Key.RIGHT) controlStrategy.releaseRight();
        else if (key == Key.SPACE) controlStrategy.releaseSpace();
        else if (key == Key.ENTER) controlStrategy.releaseEnter();
        else controlStrategy.pressAnyOtherKey();

        checkGameOver();
    }

    private void checkGameOver() {
        if (game.canUserMove()) return;
        game.lose("Невозможно совершить ход!");
    }
}
