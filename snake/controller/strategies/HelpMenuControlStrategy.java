package com.javarush.games.snake.controller.strategies;

import com.javarush.games.snake.controller.ControlStrategy;
import com.javarush.games.snake.model.MenuSelector;
import com.javarush.games.snake.model.Phase;
import com.javarush.games.snake.view.impl.HelpMenuView;

public class HelpMenuControlStrategy implements ControlStrategy {
    private static HelpMenuControlStrategy instance;

    public static HelpMenuControlStrategy getInstance() {
        if (instance == null) instance = new HelpMenuControlStrategy();
        return instance;
    }

    @Override
    public void pressRight() {
        HelpMenuView.getInstance().nextHelpPage();
        Phase.set(Phase.HELP_MENU);
    }

    @Override
    public void pressLeft() {
        HelpMenuView.getInstance().previousHelpPage();
        Phase.set(Phase.HELP_MENU);
    }

    @Override
    public void pressEscape() {
        MenuSelector.loadLastPointerPosition();
        Phase.set(Phase.MAIN_MENU);
    }
}
