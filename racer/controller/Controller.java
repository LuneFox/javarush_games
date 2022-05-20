package com.javarush.games.racer.controller;

import com.javarush.engine.cell.Key;
import com.javarush.games.racer.RacerGame;

public class Controller {
    private final ControlStrategy controlStrategy;

    public Controller(RacerGame game) {
        this.controlStrategy = new RaceStrategy(game);
    }

    public final void pressKey(Key key) {
        if (key == Key.UP) controlStrategy.pressUp();
        else if (key == Key.DOWN) controlStrategy.pressDown();
        else if (key == Key.LEFT) controlStrategy.pressLeft();
        else if (key == Key.RIGHT) controlStrategy.pressRight();
        else if (key == Key.SPACE) controlStrategy.pressSpace();
    }

    public final void releaseKey(Key key) {
        if (key == Key.UP) controlStrategy.releaseUp();
        else if (key == Key.DOWN) controlStrategy.releaseDown();
        else if (key == Key.LEFT) controlStrategy.releaseLeft();
        else if (key == Key.RIGHT) controlStrategy.releaseRight();
        else if (key == Key.SPACE) controlStrategy.releaseSpace();
    }
}
