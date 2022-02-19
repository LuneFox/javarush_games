package com.javarush.games.minesweeper.controller;

import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.*;
import com.javarush.games.minesweeper.controller.strategy.*;
import com.javarush.games.minesweeper.utility.Util;
import com.javarush.games.minesweeper.view.View;

/**
 * Separate class for processing various input events.
 */

public class Controller {
    private ControlStrategy strategy;
    private static final ControlStrategy CONTROL_ABOUT = new ControlAbout();
    private static final ControlStrategy CONTROL_BOARD = new ControlBoard();
    private static final ControlStrategy CONTROL_GAME_OVER = new ControlGameOver();
    private static final ControlStrategy CONTROL_ITEM_HELP = new ControlItemHelp();
    private static final ControlStrategy CONTROL_MAIN = new ControlMain();
    private static final ControlStrategy CONTROL_OPTIONS = new ControlOptions();
    private static final ControlStrategy CONTROL_RECORDS = new ControlRecords();
    private static final ControlStrategy CONTROL_SCORE = new ControlScore();
    private static final ControlStrategy CONTROL_SHOP = new ControlShop();
    private static final ControlStrategy CONTROL_DISABLED = new ControlStrategy() {
    };

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
        // Keyboard presses always pass the outside screen check
        selectStrategy(0, 0);
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

    public void setStrategy(ControlStrategy strategy) {
        this.strategy = strategy;
    }

    private void selectStrategy(int x, int y) {

        // Disable controls under these conditions
        if (!Util.isWithinScreen(x, y) || View.gameOver.popUpTimer > 0) {
            setStrategy(CONTROL_DISABLED);
            return;
        }

        switch (Screen.get()) {
            case ABOUT:
                setStrategy(CONTROL_ABOUT);
                break;
            case BOARD:
                setStrategy(CONTROL_BOARD);
                break;
            case GAME_OVER:
                setStrategy(CONTROL_GAME_OVER);
                break;
            case ITEM_HELP:
                setStrategy(CONTROL_ITEM_HELP);
                break;
            case MAIN:
                setStrategy(CONTROL_MAIN);
                break;
            case OPTIONS:
                setStrategy(CONTROL_OPTIONS);
                break;
            case RECORDS:
                setStrategy(CONTROL_RECORDS);
                break;
            case SCORE:
                setStrategy(CONTROL_SCORE);
                break;
            case SHOP:
                setStrategy(CONTROL_SHOP);
                break;
            default:
                setStrategy(CONTROL_DISABLED);
                break;
        }
    }
}
