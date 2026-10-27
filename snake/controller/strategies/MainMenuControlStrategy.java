package com.javarush.games.snake.controller.strategies;

import com.javarush.games.snake.controller.ControlStrategy;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.map.StageManager;
import com.javarush.games.snake.view.MenuSelector;
import com.javarush.games.snake.model.Phase;

/** Управление главным меню. */
public class MainMenuControlStrategy implements ControlStrategy {
    private static MainMenuControlStrategy instance;

    /** Возвращает единственный экземпляр. */
    public static MainMenuControlStrategy getInstance() {
        if (instance == null) instance = new MainMenuControlStrategy();
        return instance;
    }

    /** Двигает указатель меню вверх. */
    @Override
    public void pressUp() {
        MenuSelector.movePointerUp();
        Phase.proceed(Phase.MAIN_MENU);
    }

    /** Двигает указатель меню вниз. */
    @Override
    public void pressDown() {
        MenuSelector.movePointerDown();
        Phase.proceed(Phase.MAIN_MENU);
    }

    /** Выбирает текущий пункт меню. */
    @Override
    public void pressSpace() {
        pressEnter();
    }

    /** Выбирает текущий пункт меню. */
    @Override
    public void pressEnter() {
        if (MenuSelector.isPointingAt(Strings.MENU_START)) {
            game.createGame();
            Phase.proceed(Phase.GAME_FIELD);
        } else if (MenuSelector.isPointingAt(Strings.MENU_OPTIONS)) {
            MenuSelector.saveLastPointerPosition();
            MenuSelector.setPointerPosition(0);
            Phase.proceed(Phase.OPTIONS_MENU);
        } else if (MenuSelector.isPointingAt(Strings.MENU_CONTROLS)) {
            MenuSelector.saveLastPointerPosition();
            MenuSelector.setPointerPosition(0);
            Phase.proceed(Phase.CONTROLS_MENU);
        } else if (MenuSelector.isPointingAt(Strings.MENU_HELP)) {
            MenuSelector.saveLastPointerPosition();
            MenuSelector.setPointerPosition(0);
            Phase.proceed(Phase.HELP_MENU);
        } else if (MenuSelector.isPointingAt(Strings.MENU_EDIT)) {
            MenuSelector.saveLastPointerPosition();
            MenuSelector.setPointerPosition(0);
            game.setStage(StageManager.getEmptyStage());
            Phase.proceed(Phase.MAP_EDITOR);
        }
    }
}
