package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;

public class ControlOptions implements ControlStrategy {
    @Override
    public void pressRight() {
        Options.getDifficultySelector().difficultyUp();
    }

    @Override
    public void pressLeft() {
        Options.getDifficultySelector().difficultyDown();
    }

    @Override
    public void pressEsc() {
        Phase.setActive(Phase.MAIN);
        PopUpMessage.show(Options.OPTIONS_SAVED);
    }
}
