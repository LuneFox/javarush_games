package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.model.Phase;

public class ControlGameOver implements ControlStrategy {

    @Override
    public void pressSpace() {
        Phase.setActive(Phase.BOARD);
    }

    @Override
    public void pressEsc() {
        Phase.setActive(Phase.BOARD);
    }
}
