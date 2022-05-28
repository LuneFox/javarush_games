package com.javarush.games.snake.controller.strategies;

import com.javarush.games.snake.controller.ControlStrategy;
import com.javarush.games.snake.model.Menu;

public class HelpMenuControlStrategy implements ControlStrategy {
    private static HelpMenuControlStrategy instance;

    public static HelpMenuControlStrategy getInstance() {
        if (instance == null) instance = new HelpMenuControlStrategy();
        return instance;
    }

    @Override
    public void pressRight() {
        game.menu.nextHelpPage();
    }

    @Override
    public void pressLeft() {
        game.menu.previousHelpPage();
    }

    @Override
    public void pressEscape() {
        Menu.Selector.setPointer(game.menu.lastPointerPosition);
        game.menu.displayMain();
    }
}
