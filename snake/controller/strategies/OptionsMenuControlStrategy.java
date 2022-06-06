package com.javarush.games.snake.controller.strategies;

import com.javarush.games.snake.controller.ControlStrategy;
import com.javarush.games.snake.model.Options;
import com.javarush.games.snake.model.map.StageManager;
import com.javarush.games.snake.view.MenuSelector;
import com.javarush.games.snake.model.Phase;
import com.javarush.games.snake.view.Sign;

public class OptionsMenuControlStrategy implements ControlStrategy {
    private static OptionsMenuControlStrategy instance;

    public static OptionsMenuControlStrategy getInstance() {
        if (instance == null) instance = new OptionsMenuControlStrategy();
        return instance;
    }

    @Override
    public void pressUp() {
        MenuSelector.movePointerUp();
        Phase.proceed(Phase.OPTIONS_MENU);
    }

    @Override
    public void pressDown() {
        MenuSelector.movePointerDown();
        Phase.proceed(Phase.OPTIONS_MENU);
    }

    @Override
    public void pressRight() {
        if (MenuSelector.isPointingAt("SYMBOLS")) {
            Sign.switchSet();
        } else if (MenuSelector.isPointingAt("MAP")) {
            StageManager.selectNextStage();
        } else if (MenuSelector.isPointingAt("ACCELERATION")) {
            Options.switchAccelerationEnabled();
        }
        Phase.proceed(Phase.OPTIONS_MENU);
    }

    @Override
    public void pressLeft() {
        if (MenuSelector.isPointingAt("SYMBOLS")) {
            Sign.switchSet();
        } else if (MenuSelector.isPointingAt("MAP")) {
            StageManager.selectPreviousStage();
        } else if (MenuSelector.isPointingAt("ACCELERATION")) {
            Options.switchAccelerationEnabled();
        }
        Phase.proceed(Phase.OPTIONS_MENU);
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
        MenuSelector.loadLastPointerPosition();
        Phase.proceed(Phase.MAIN_MENU);
    }
}
