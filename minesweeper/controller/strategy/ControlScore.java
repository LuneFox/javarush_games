package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Cache;

public class ControlScore implements ControlStrategy {

    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GENERAL_CONFIRM).checkLeftTouch(x, y)) {
            goBack();
        }
        Screen.score.pageSelector.checkLeftTouch(x, y);
    }

    @Override
    public void pressSpace() {
        goBack();
    }

    @Override
    public void pressEsc() {
        goBack();
    }

    @Override
    public void pressRight() {
        Screen.score.pageSelector.nextPage();
    }

    @Override
    public void pressLeft() {
        Screen.score.pageSelector.prevPage();
    }

    private void goBack() {
        Screen.board.update();
        Screen.setActive(Screen.GAME_OVER);
    }


}
