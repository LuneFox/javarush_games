package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.impl.ViewAbout;

public class ControlAbout implements ControlStrategy {

    @Override
    public void pressRight() {
        ViewAbout.pageSelector.selectNextPage();
    }

    @Override
    public void pressLeft() {
        ViewAbout.pageSelector.selectPrevPage();
    }

    @Override
    public void pressEsc() {
        Phase.setActive(Phase.MAIN);
    }
}
