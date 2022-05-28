package com.javarush.games.snake.controller.strategies;

import com.javarush.games.snake.controller.ControlStrategy;
import com.javarush.games.snake.model.Menu;

public class OptionsMenuControlStrategy implements ControlStrategy {
    private static OptionsMenuControlStrategy instance;

    public static OptionsMenuControlStrategy getInstance() {
        if (instance == null) instance = new OptionsMenuControlStrategy();
        return instance;
    }

    @Override
    public void pressUp() {
        Menu.Selector.selectUp();
        game.menu.displayOptions();
    }

    @Override
    public void pressDown() {
        Menu.Selector.selectDown();
        game.menu.displayOptions();
    }

    @Override
    public void pressRight() {
        if (Menu.Selector.nowAt("SYMBOLS")) {
            game.menu.switchSymbolSet();
        } else if (Menu.Selector.nowAt("MAP")) {
            game.menu.selectStageUp();
        } else if (Menu.Selector.nowAt("ACCELERATION")) {
            game.isAccelerationEnabled = !game.isAccelerationEnabled;
        }
        game.menu.displayOptions();
    }

    @Override
    public void pressLeft() {
        if (Menu.Selector.nowAt("SYMBOLS")) {
            game.menu.switchSymbolSet();
        } else if (Menu.Selector.nowAt("MAP")) {
            game.menu.selectStageDown();
        } else if (Menu.Selector.nowAt("ACCELERATION")) {
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
        Menu.Selector.setPointer(game.menu.lastPointerPosition);
        game.menu.displayMain();
    }
}
