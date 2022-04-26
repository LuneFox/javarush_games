package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;

public class ControlAbout implements ControlStrategy {
    @Override
    public void pressRight() {
        Options.aboutPageSelector.selectNextPage();
    }

    @Override
    public void pressLeft() {
        Options.aboutPageSelector.selectPrevPage();
    }

    @Override
    public void pressEsc() {
        Phase.setActive(Phase.MAIN);
    }
}
