package com.javarush.games.racer.controller;

import com.javarush.engine.cell.Key;
import com.javarush.games.racer.RacerGame;

/**
 * Translates the engine's raw key events into calls on a {@link ControlStrategy}. A new controller is created
 * for each race and always uses {@link RaceStrategy}, so the strategy can hold a direct reference to the
 * current car.
 */
public class Controller {
    private final ControlStrategy controlStrategy;

    /**
     * Creates a controller for the given game with a {@link RaceStrategy} as its strategy.
     *
     * @param game the game whose car and state the strategy will control
     */
    public Controller(RacerGame game) {
        this.controlStrategy = new RaceStrategy(game);
    }

    /**
     * Maps a pressed key to the matching strategy call: the arrow keys set a direction and SPACE attempts a
     * restart. Any other key is ignored.
     *
     * @param key the key that was pressed
     */
    public final void pressKey(Key key) {
        if (key == Key.UP) controlStrategy.pressUp();
        else if (key == Key.DOWN) controlStrategy.pressDown();
        else if (key == Key.LEFT) controlStrategy.pressLeft();
        else if (key == Key.RIGHT) controlStrategy.pressRight();
        else if (key == Key.SPACE) controlStrategy.pressSpace();
    }

    /**
     * Maps a released key to the matching {@code release*} strategy call, so a direction can be cleared when
     * its key goes up. Any other key is ignored.
     *
     * @param key the key that was released
     */
    public final void releaseKey(Key key) {
        if (key == Key.UP) controlStrategy.releaseUp();
        else if (key == Key.DOWN) controlStrategy.releaseDown();
        else if (key == Key.LEFT) controlStrategy.releaseLeft();
        else if (key == Key.RIGHT) controlStrategy.releaseRight();
        else if (key == Key.SPACE) controlStrategy.releaseSpace();
    }
}
