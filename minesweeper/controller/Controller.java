package com.javarush.games.minesweeper.controller;

import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.*;
import com.javarush.games.minesweeper.controller.strategy.*;
import com.javarush.games.minesweeper.view.View;

/**
 * Separate class for processing various input events.
 */

public class Controller {
    private ControlStrategy strategy;
    private final ControlStrategy controlDisabled = new ControlDisabled();
    private final ControlStrategy controlAbout = new ControlAbout();
    private final ControlStrategy controlBoard = new ControlBoard();
    private final ControlStrategy controlGameOver = new ControlGameOver();
    private final ControlStrategy controlItemHelp = new ControlItemHelp();
    private final ControlStrategy controlMain = new ControlMain();
    private final ControlStrategy controlOptions = new ControlOptions();
    private final ControlStrategy controlRecords = new ControlRecords();
    private final ControlStrategy controlScore = new ControlScore();
    private final ControlStrategy controlShop = new ControlShop();

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
        selectStrategy();
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
            setStrategy(controlDisabled);
            return;
        }

        switch (Screen.get()) {
            case ABOUT:
                setStrategy(controlAbout);
                break;
            case BOARD:
                setStrategy(controlBoard);
                break;
            case GAME_OVER:
                setStrategy(controlGameOver);
                break;
            case ITEM_HELP:
                setStrategy(controlItemHelp);
                break;
            case MAIN_MENU:
                setStrategy(controlMain);
                break;
            case OPTIONS:
                setStrategy(controlOptions);
                break;
            case RECORDS:
                setStrategy(controlRecords);
                break;
            case SCORE:
                setStrategy(controlScore);
                break;
            case SHOP:
                setStrategy(controlShop);
                break;
            default:
                break;
        }
    }

    private void selectStrategy() {
        selectStrategy(0, 0);
    }
}
