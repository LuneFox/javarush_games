package com.javarush.games.minesweeper.controller;

import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.controller.strategy.*;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.Util;
import com.javarush.games.minesweeper.view.graphics.Button;

import java.util.HashMap;
import java.util.Map;

/**
 * Separate class for processing various input events.
 */

public class Controller {
    private ControlStrategy strategy;
    private static final Map<Screen, ControlStrategy> strategyMap = new HashMap<>();

    static {
        strategyMap.put(Screen.ABOUT, new ControlAbout());
        strategyMap.put(Screen.BOARD, new ControlBoard());
        strategyMap.put(Screen.GAME_OVER, new ControlGameOver());
        strategyMap.put(Screen.ITEM_HELP, new ControlItemHelp());
        strategyMap.put(Screen.MAIN, new ControlMain());
        strategyMap.put(Screen.OPTIONS, new ControlOptions());
        strategyMap.put(Screen.RECORDS, new ControlRecords());
        strategyMap.put(Screen.SCORE, new ControlScore());
        strategyMap.put(Screen.SHOP, new ControlShop());
        strategyMap.put(null, new ControlStrategy() {
        });
    }

    public final void leftClick(int x, int y) {
        selectStrategy(x, y);
        strategy.leftClick(x, y);
    }

    public final void rightClick(int x, int y) {
        selectStrategy(x, y);
        strategy.rightClick(x, y);
        // System.out.printf("%d %d%n", x, y);
    }

    public final void pressKey(Key key) {
        selectStrategy(0, 0); // Keyboard buttons never miss the screen
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
        if (!Util.isWithinScreen(x, y) // Disable controls during awaiting of the next screen
                || Screen.gameOver.getShowDelay() > 0
                || Button.pressedTime > Button.POST_PRESS_DELAY) {
            this.strategy = strategyMap.get(null);
            return;
        }
        this.strategy = strategyMap.get(Screen.getActive());
    }
}
