package com.javarush.games.snake.controller.strategies;

import com.javarush.games.snake.controller.ControlStrategy;
import com.javarush.games.snake.view.MenuSelector;
import com.javarush.games.snake.model.Phase;

/** Управление на экране «Управление». */
public class ControlsMenuControlStrategy implements ControlStrategy {
    private static ControlsMenuControlStrategy instance;

    /** Возвращает единственный экземпляр. */
    public static ControlsMenuControlStrategy getInstance() {
        if (instance == null) instance = new ControlsMenuControlStrategy();
        return instance;
    }

    /** Возвращает в главное меню. */
    @Override
    public void pressEscape() {
        MenuSelector.loadLastPointerPosition();
        Phase.proceed(Phase.MAIN_MENU);
    }
}
