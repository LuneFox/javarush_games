package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.impl.ViewScore;

/** Управляет вводом на экране счёта. */
public class ControlScore implements ControlStrategy {

    /** Обрабатывает нажатие Escape. */
    @Override
    public void pressEsc() {
        Phase.setActive(Phase.GAME_OVER);
    }

    /** Обрабатывает нажатие вправо. */
    @Override
    public void pressRight() {
        ViewScore.pageSelector.selectNextPage();
    }

    /** Обрабатывает нажатие влево. */
    @Override
    public void pressLeft() {
        ViewScore.pageSelector.selectPrevPage();
    }
}
