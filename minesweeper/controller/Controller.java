package com.javarush.games.minesweeper.controller;

import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.*;
import com.javarush.games.minesweeper.controller.strategy.*;
import com.javarush.games.minesweeper.utility.Util;
import com.javarush.games.minesweeper.view.View;
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
        // Uncomment this to check click coordinates
        // System.out.printf("%d %d%n", x, y);
    }

    public final void pressKey(Key key) {
        selectStrategy(0, 0); // Keyboard presses always pass the outside screen check
        switch (key) {
            case UP:
                strategy.pressUp();
                break;
            case DOWN:
                strategy.pressDown();
                break;
            case LEFT:
                strategy.pressLeft();
                break;
            case RIGHT:
                strategy.pressRight();
                break;
            case ENTER:
                strategy.pressEnter();
                break;
            case PAUSE:
                strategy.pressPause();
                break;
            case SPACE:
                strategy.pressSpace();
                break;
            case ESCAPE:
                strategy.pressEsc();
                break;
            default:
                strategy.pressOther();
                break;
        }
    }

    private void selectStrategy(int x, int y) {
        if (!Util.isWithinScreen(x, y) // Disable controls under these conditions
                || Screen.gameOver.getShowDelay() > 0
                || Button.pressedTime > Button.POST_PRESS_DELAY) {
            this.strategy = strategyMap.get(null);
            return;
        }
        this.strategy = strategyMap.get(Screen.get());
    }
}
