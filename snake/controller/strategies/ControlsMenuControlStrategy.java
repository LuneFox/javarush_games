package com.javarush.games.snake.controller.strategies;

import com.javarush.games.snake.controller.ControlStrategy;
import com.javarush.games.snake.model.MenuSelector;

public class ControlsMenuControlStrategy implements ControlStrategy {
    private static ControlsMenuControlStrategy instance;

    public static ControlsMenuControlStrategy getInstance() {
        if (instance == null) instance = new ControlsMenuControlStrategy();
        return instance;
    }

    @Override
    public void pressEscape() {
        MenuSelector.setPointerPosition(game.menu.lastPointerPosition);
        game.menu.displayMain();
    }
}
