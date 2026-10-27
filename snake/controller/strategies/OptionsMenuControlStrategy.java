package com.javarush.games.snake.controller.strategies;

import com.javarush.games.snake.controller.ControlStrategy;
import com.javarush.games.snake.model.Options;
import com.javarush.games.snake.model.Phase;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.map.StageManager;
import com.javarush.games.snake.view.MenuSelector;
import com.javarush.games.snake.view.Sign;

/** Управление экраном настроек. */
public class OptionsMenuControlStrategy implements ControlStrategy {
    private static OptionsMenuControlStrategy instance;

    /** Возвращает единственный экземпляр. */
    public static OptionsMenuControlStrategy getInstance() {
        if (instance == null) instance = new OptionsMenuControlStrategy();
        return instance;
    }

    /** Двигает указатель настроек вверх. */
    @Override
    public void pressUp() {
        MenuSelector.movePointerUp();
        Phase.proceed(Phase.OPTIONS_MENU);
    }

    /** Двигает указатель настроек вниз. */
    @Override
    public void pressDown() {
        MenuSelector.movePointerDown();
        Phase.proceed(Phase.OPTIONS_MENU);
    }

    /** Меняет выбранную настройку вперёд. */
    @Override
    public void pressRight() {
        if (MenuSelector.isPointingAt(Strings.MENU_SYMBOLS)) {
            Sign.switchSet();
        } else if (MenuSelector.isPointingAt(Strings.MENU_MAP)) {
            StageManager.selectNextStage();
        } else if (MenuSelector.isPointingAt(Strings.MENU_ACCELERATION)) {
            Options.toggleAcceleration();
        } else if (MenuSelector.isPointingAt(Strings.MENU_LANGUAGE)) {
            Options.toggleLanguage();
        }

        Phase.proceed(Phase.OPTIONS_MENU);
    }

    /** Меняет выбранную настройку назад. */
    @Override
    public void pressLeft() {
        if (MenuSelector.isPointingAt(Strings.MENU_SYMBOLS)) {
            Sign.switchSet();
        } else if (MenuSelector.isPointingAt(Strings.MENU_MAP)) {
            StageManager.selectPreviousStage();
        } else if (MenuSelector.isPointingAt(Strings.MENU_ACCELERATION)) {
            Options.toggleAcceleration();
        } else if (MenuSelector.isPointingAt(Strings.MENU_LANGUAGE)) {
            Options.toggleLanguage();
        }

        Phase.proceed(Phase.OPTIONS_MENU);
    }

    /** Меняет выбранную настройку. */
    @Override
    public void pressSpace() {
        pressRight();
    }

    /** Меняет выбранную настройку. */
    @Override
    public void pressEnter() {
        pressRight();
    }

    /** Возвращает в главное меню. */
    @Override
    public void pressEscape() {
        MenuSelector.loadLastPointerPosition();
        Phase.proceed(Phase.MAIN_MENU);
    }
}
