package com.javarush.games.minesweeper.controller;

import com.javarush.games.minesweeper.model.Phase;

public class ControlGameOver implements ControlStrategy {
    @Override
    public void pressSpace() {
        Phase.setActive(Phase.SCORE);
    }

    @Override
    public void pressEsc() {
        Phase.setActive(Phase.BOARD);
    }
}
