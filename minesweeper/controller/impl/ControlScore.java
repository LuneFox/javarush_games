package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.impl.ViewScore;

public class ControlScore implements ControlStrategy {
    @Override
    public void pressEsc() {
        Phase.setActive(Phase.GAME_OVER);
    }

    @Override
    public void pressRight() {
        ViewScore.pageSelector.selectNextPage();
    }

    @Override
    public void pressLeft() {
        ViewScore.pageSelector.selectPrevPage();
    }
}
