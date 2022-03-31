package com.javarush.games.moonlander.controller;

import com.javarush.engine.cell.Key;
import com.javarush.games.moonlander.MoonLanderGame;
import com.javarush.games.moonlander.model.Phase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Separate class for processing various input events.
 */

public class Controller {
    private ControlStrategy strategy;
    private static final Map<Phase, ControlStrategy> strategyMap = new HashMap<>();

    static {
        ControlStrategyFactory factory = new ControlStrategyFactory();
        Arrays.stream(Phase.values()).forEach(phase -> strategyMap.put(phase, factory.createStrategy(phase)));
    }

    public final void leftClick(int x, int y) {
        if (clickOutOfBounds(x, y)) return;
        selectStrategy(x, y);
        strategy.leftClick(x, y);
        Phase.updateView();
    }

    public final void rightClick(int x, int y) {
        if (clickOutOfBounds(x, y)) return;
        selectStrategy(x, y);
        strategy.rightClick(x, y);
        Phase.updateView();
    }

    public final void pressKey(Key key) {
        selectStrategy(0, 0); // Key presses never miss the screen
        if (key == Key.UP) strategy.pressUp();
        else if (key == Key.DOWN) strategy.pressDown();
        else if (key == Key.LEFT) strategy.pressLeft();
        else if (key == Key.RIGHT) strategy.pressRight();
        else if (key == Key.ENTER) strategy.pressEnter();
        else if (key == Key.PAUSE) strategy.pressPause();
        else if (key == Key.SPACE) strategy.pressSpace();
        else if (key == Key.ESCAPE) strategy.pressEsc();
        else strategy.pressOther();
        Phase.updateView();
    }

    private void selectStrategy(int x, int y) {
        // System.out.printf("%d %d%n", x, y);
        this.strategy = strategyMap.get(Phase.getActive());
    }

    public static boolean clickOutOfBounds(int x, int y) {
        return (x < 0 || y < 0 || x >= MoonLanderGame.WIDTH || y >= MoonLanderGame.HEIGHT);
    }
}
