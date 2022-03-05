package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Cache;

public class ControlScore implements ControlStrategy {

    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GENERAL_CONFIRM).checkTouch(x, y)) {
            Screen.setCurrentView(Screen.board); // crutch
            goBack();
        }
    }

    @Override
    public void pressSpace() {
        goBack();
    }

    @Override
    public void pressEsc() {
        goBack();
    }

    private void goBack() {
        Screen.board.update();
        Screen.set(Screen.GAME_OVER);
    }
}
