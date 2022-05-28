package com.javarush.games.snake.controller.strategies;

import com.javarush.games.snake.controller.ControlStrategy;
import com.javarush.games.snake.model.Menu;

public class MainMenuControlStrategy implements ControlStrategy {
    private static MainMenuControlStrategy instance;

    public static MainMenuControlStrategy getInstance() {
        if (instance == null) instance = new MainMenuControlStrategy();
        return instance;
    }

    @Override
    public void pressUp() {
        Menu.Selector.selectUp();
        game.menu.displayMain();
    }

    @Override
    public void pressDown() {
        Menu.Selector.selectDown();
        game.menu.displayMain();
    }

    @Override
    public void pressSpace() {
        pressEnter();
    }

    @Override
    public void pressEnter() {
        if (Menu.Selector.nowAt("START")) {
            game.menu.lastPointerPosition = Menu.Selector.getPointer();
            game.menu.startGame();
        } else if (Menu.Selector.nowAt("OPTIONS")) {
            game.menu.lastPointerPosition = Menu.Selector.getPointer();
            Menu.Selector.setPointer(0);
            game.menu.displayOptions();
        } else if (Menu.Selector.nowAt("CONTROLS")) {
            game.menu.lastPointerPosition = Menu.Selector.getPointer();
            Menu.Selector.setPointer(0);
            game.menu.displayControls();
        } else if (Menu.Selector.nowAt("HELP")) {
            game.menu.lastPointerPosition = Menu.Selector.getPointer();
            Menu.Selector.setPointer(0);
            game.menu.displayHelp();
        } else if (Menu.Selector.nowAt("EDIT")) {
            game.menu.lastPointerPosition = Menu.Selector.getPointer();
            Menu.Selector.setPointer(0);
            game.setMap(game.getStage());
            game.menu.displayMapEditor();
        }
    }
}
