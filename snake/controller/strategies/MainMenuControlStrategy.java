package com.javarush.games.snake.controller.strategies;

import com.javarush.games.snake.controller.ControlStrategy;
import com.javarush.games.snake.model.map.StageManager;
import com.javarush.games.snake.view.MenuSelector;
import com.javarush.games.snake.model.Phase;

public class MainMenuControlStrategy implements ControlStrategy {
    private static MainMenuControlStrategy instance;

    public static MainMenuControlStrategy getInstance() {
        if (instance == null) instance = new MainMenuControlStrategy();
        return instance;
    }

    @Override
    public void pressUp() {
        MenuSelector.movePointerUp();
        Phase.proceed(Phase.MAIN_MENU);
    }

    @Override
    public void pressDown() {
        MenuSelector.movePointerDown();
        Phase.proceed(Phase.MAIN_MENU);
    }

    @Override
    public void pressSpace() {
        pressEnter();
    }

    @Override
    public void pressEnter() {
        if (MenuSelector.isPointingAt("START")) {
            game.createGame();
            Phase.proceed(Phase.GAME_FIELD);
        } else if (MenuSelector.isPointingAt("OPTIONS")) {
            MenuSelector.saveLastPointerPosition();
            MenuSelector.setPointerPosition(0);
            Phase.proceed(Phase.OPTIONS_MENU);
        } else if (MenuSelector.isPointingAt("CONTROLS")) {
            MenuSelector.saveLastPointerPosition();
            MenuSelector.setPointerPosition(0);
            Phase.proceed(Phase.CONTROLS_MENU);
        } else if (MenuSelector.isPointingAt("HELP")) {
            MenuSelector.saveLastPointerPosition();
            MenuSelector.setPointerPosition(0);
            Phase.proceed(Phase.HELP_MENU);
        } else if (MenuSelector.isPointingAt("EDIT")) {
            MenuSelector.saveLastPointerPosition();
            MenuSelector.setPointerPosition(0);
            game.setStage(StageManager.getEmptyStage());
            Phase.proceed(Phase.MAP_EDITOR);
        }
    }
}
