package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.Text;

import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;

/** Управляет вводом на экране настроек. */
public class ControlOptions implements ControlStrategy {

    /** Обрабатывает нажатие вправо. */
    @Override
    public void pressRight() {
        Options.getDifficultySelector().difficultyUp();
    }

    /** Обрабатывает нажатие влево. */
    @Override
    public void pressLeft() {
        Options.getDifficultySelector().difficultyDown();
    }

    /** Обрабатывает нажатие Escape. */
    @Override
    public void pressEsc() {
        Phase.setActive(Phase.MAIN);
        PopUpMessage.show(Text.OPTIONS_SAVED.getText());
    }
}
