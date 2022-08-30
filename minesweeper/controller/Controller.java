package com.javarush.games.minesweeper.controller;

import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Util;
import com.javarush.games.minesweeper.controller.impl.ControlBoard;
import com.javarush.games.minesweeper.controller.impl.ControlDisabled;
import com.javarush.games.minesweeper.controller.impl.ControlMain;
import com.javarush.games.minesweeper.controller.impl.ControlShop;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.View;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    private static final Map<Phase, ControlStrategy> strategyMap = new HashMap<>();
    private static int lastClickY;

    private final MinesweeperGame game;
    private ControlStrategy strategy;

    public Controller(MinesweeperGame game) {
        this.game = game;
        ControlMain.setGame(game);
        ControlShop.setGame(game);
        ControlBoard.setGame(game);
    }

    static {
        ControlStrategyFactory factory = new ControlStrategyFactory();
        Arrays.stream(Phase.values()).forEach(phase -> strategyMap.put(phase, factory.createStrategy(phase)));
        strategyMap.put(null, new ControlDisabled());
    }

    public static boolean clickedOnUpperHalf() {
        return lastClickY < 50;
    }

    public final void click(int x, int y, Click click) {
        // System.out.printf(click + ": %d %d%n", x, y);
        if (isInvalidInput(x, y)) return;

        lastClickY = y;
        game.restrictFlagExplosion();
        game.stopRecursion();
        selectStrategyAccordingToActivePhase();

        if (click == Click.LEFT) {
            strategy.leftClick(x, y);
        } else {
            strategy.rightClick(x, y);
        }
    }

    public final void pressKey(Key key) {
        if (isInvalidInput(0, 0)) return;

        selectStrategyAccordingToActivePhase();

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

    private void selectStrategyAccordingToActivePhase() {
        strategy = strategyMap.get(Phase.getActive());
    }

    private boolean isInvalidInput(int x, int y) {
        return !(Button.isAnimationFinished()
                && Util.isWithinScreen(x, y)
                && View.getGameOverShowDelay() <= 0);
    }
}
