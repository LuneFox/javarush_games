package com.javarush.games.minesweeper.controller;

import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;

public class ControlAbout implements ControlStrategy {
    @Override
    public void pressRight() {
        Options.aboutPageSelector.nextPage();
    }

    @Override
    public void pressLeft() {
        Options.aboutPageSelector.prevPage();
    }

    @Override
    public void pressEsc() {
        Phase.setActive(Phase.MAIN);
    }
}
