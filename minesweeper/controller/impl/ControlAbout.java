package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.impl.ViewAbout;

/** Управляет вводом на экране «Об игре». */
public class ControlAbout implements ControlStrategy {

    /** Обрабатывает нажатие вправо. */
    @Override
    public void pressRight() {
        ViewAbout.pageSelector.selectNextPage();
    }

    /** Обрабатывает нажатие влево. */
    @Override
    public void pressLeft() {
        ViewAbout.pageSelector.selectPrevPage();
    }

    /** Обрабатывает нажатие Escape. */
    @Override
    public void pressEsc() {
        Phase.setActive(Phase.MAIN);
    }
}
