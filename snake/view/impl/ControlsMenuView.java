package com.javarush.games.snake.view.impl;

import com.javarush.games.snake.view.HelpPage;
import com.javarush.games.snake.view.View;

public class ControlsMenuView extends View {
    private static ControlsMenuView instance;

    public static ControlsMenuView getInstance() {
        if (instance == null) instance = new ControlsMenuView();
        return instance;
    }

    @Override
    public void update() {
        drawBlackBackground();
        HelpPage.getControls().draw(game);
    }
}
