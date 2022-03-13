package com.javarush.games.minesweeper.controller;

import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.Strings;

public class ControlOptions implements ControlStrategy {
    @Override
    public void pressRight() {
        Options.difficultySelector.difficultyUp();
    }

    @Override
    public void pressLeft() {
        Options.difficultySelector.difficultyDown();
    }

    @Override
    public void pressEsc() {
        Phase.setActive(Phase.MAIN);
        PopUpMessage.show(Strings.OPTIONS_SAVED);
    }
}
