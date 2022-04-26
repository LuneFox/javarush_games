package com.javarush.games.minesweeper.controller;

import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Util;
import com.javarush.games.minesweeper.controller.impl.ControlBoard;
import com.javarush.games.minesweeper.controller.impl.ControlMain;
import com.javarush.games.minesweeper.controller.impl.ControlShop;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.View;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Separate class for processing various input events.
 */

public class Controller {
    private final MinesweeperGame game;
    private ControlStrategy strategy;
    private static final Map<Phase, ControlStrategy> strategyMap = new HashMap<>();
    private static int lastClickY;

    public enum Click {
        LEFT, RIGHT
    }

    public Controller(MinesweeperGame game) {
        this.game = game;
        ControlMain.setGame(game);
        ControlShop.setGame(game);
        ControlBoard.setGame(game);
    }

    static {
        // Fill strategy map
        ControlStrategyFactory factory = new ControlStrategyFactory();
        Arrays.stream(Phase.values()).forEach(phase -> strategyMap.put(phase, factory.createStrategy(phase)));
        strategyMap.put(null, new ControlStrategy() { // disabled controls option

            @Override
            public void leftClick(int x, int y) {
                // do nothing
            }

            @Override
            public void rightClick(int x, int y) {
                // do nothing
            }
        });
    }

    public final void leftClick(int x, int y) {
        game.setRecursiveMove(false);
        game.setFlagExplosionAllowed(false);
        selectStrategy(x, y);
        strategy.leftClick(x, y);
    }

    public final void rightClick(int x, int y) {
        game.setRecursiveMove(false);
        selectStrategy(x, y);
        strategy.rightClick(x, y);
    }

    public final void pressKey(Key key) {
        selectStrategy(0, 99); // Key presses never miss the screen
        if (key == Key.UP) strategy.pressUp();
        else if (key == Key.DOWN) strategy.pressDown();
        else if (key == Key.LEFT) strategy.pressLeft();
        else if (key == Key.RIGHT) strategy.pressRight();
        else if (key == Key.ENTER) strategy.pressEnter();
        else if (key == Key.PAUSE) strategy.pressPause();
        else if (key == Key.SPACE) strategy.pressSpace();
        else if (key == Key.ESCAPE) strategy.pressEsc();
        else strategy.pressOther();
    }

    private void selectStrategy(int x, int y) {
        // System.out.printf("%d %d%n", x, y);
        lastClickY = y;

        if (!Util.isWithinScreen(x, y)
                || View.getGameOverShowDelay() > 0
                || Button.pressedTimeCounter > Button.POST_PRESS_DELAY) {
            // Disable controls during awaiting of the next screen
            this.strategy = strategyMap.get(null);
            return;
        }

        this.strategy = strategyMap.get(Phase.getActive());
    }

    public static boolean clickedOnUpperHalf() {
        return lastClickY < 50;
    }
}
