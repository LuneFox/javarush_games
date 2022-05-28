package com.javarush.games.snake.controller.strategies;

import com.javarush.games.snake.controller.ControlStrategy;
import com.javarush.games.snake.model.MenuSelector;

public class OptionsMenuControlStrategy implements ControlStrategy {
    private static OptionsMenuControlStrategy instance;

    public static OptionsMenuControlStrategy getInstance() {
        if (instance == null) instance = new OptionsMenuControlStrategy();
        return instance;
    }

    @Override
    public void pressUp() {
        MenuSelector.movePointerUp();
        game.menu.displayOptions();
    }

    @Override
    public void pressDown() {
        MenuSelector.movePointerDown();
        game.menu.displayOptions();
    }

    @Override
    public void pressRight() {
        if (MenuSelector.isPointingAt("SYMBOLS")) {
            game.menu.switchSymbolSet();
        } else if (MenuSelector.isPointingAt("MAP")) {
            game.menu.selectStageUp();
        } else if (MenuSelector.isPointingAt("ACCELERATION")) {
            game.isAccelerationEnabled = !game.isAccelerationEnabled;
        }
        game.menu.displayOptions();
    }

    @Override
    public void pressLeft() {
        if (MenuSelector.isPointingAt("SYMBOLS")) {
            game.menu.switchSymbolSet();
        } else if (MenuSelector.isPointingAt("MAP")) {
            game.menu.selectStageDown();
        } else if (MenuSelector.isPointingAt("ACCELERATION")) {
            game.isAccelerationEnabled = !game.isAccelerationEnabled;
        }
        game.menu.displayOptions();
    }

    @Override
    public void pressSpace() {
        pressRight();
    }

    @Override
    public void pressEnter() {
        pressRight();
    }

    @Override
    public void pressEscape() {
        MenuSelector.setPointerPosition(game.menu.lastPointerPosition);
        game.menu.displayMain();
    }
}
