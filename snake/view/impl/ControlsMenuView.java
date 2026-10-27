package com.javarush.games.snake.view.impl;

import com.javarush.games.snake.view.HelpPage;
import com.javarush.games.snake.view.View;

/** Экран «Управление». */
public class ControlsMenuView extends View {
    private static ControlsMenuView instance;

    /** Возвращает единственный экземпляр. */
    public static ControlsMenuView getInstance() {
        if (instance == null) instance = new ControlsMenuView();
        return instance;
    }

    /** Рисует страницу управления. */
    @Override
    public void update() {
        drawBlackBackground();
        HelpPage.getControls().draw(game);
    }
}
