package com.javarush.games.snake.controller.strategies;

import com.javarush.games.snake.controller.ControlStrategy;
import com.javarush.games.snake.model.MenuSelector;

public class MainMenuControlStrategy implements ControlStrategy {
    private static MainMenuControlStrategy instance;

    public static MainMenuControlStrategy getInstance() {
        if (instance == null) instance = new MainMenuControlStrategy();
        return instance;
    }

    @Override
    public void pressUp() {
        MenuSelector.movePointerUp();
        game.menu.displayMain();
    }

    @Override
    public void pressDown() {
        MenuSelector.movePointerDown();
        game.menu.displayMain();
    }

    @Override
    public void pressSpace() {
        pressEnter();
    }

    @Override
    public void pressEnter() {
        if (MenuSelector.isPointingAt("START")) {
            game.menu.lastPointerPosition = MenuSelector.getPointerPosition();
            game.menu.startGame();
        } else if (MenuSelector.isPointingAt("OPTIONS")) {
            game.menu.lastPointerPosition = MenuSelector.getPointerPosition();
            MenuSelector.setPointerPosition(0);
            game.menu.displayOptions();
        } else if (MenuSelector.isPointingAt("CONTROLS")) {
            game.menu.lastPointerPosition = MenuSelector.getPointerPosition();
            MenuSelector.setPointerPosition(0);
            game.menu.displayControls();
        } else if (MenuSelector.isPointingAt("HELP")) {
            game.menu.lastPointerPosition = MenuSelector.getPointerPosition();
            MenuSelector.setPointerPosition(0);
            game.menu.displayHelp();
        } else if (MenuSelector.isPointingAt("EDIT")) {
            game.menu.lastPointerPosition = MenuSelector.getPointerPosition();
            MenuSelector.setPointerPosition(0);
            game.setMap(game.getStage());
            game.menu.displayMapEditor();
        }
    }
}
