package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.model.Phase;

/** Управляет вводом на экране завершения партии. */
public class ControlGameOver implements ControlStrategy {

    /** Обрабатывает нажатие пробела. */
    @Override
    public void pressSpace() {
        Phase.setActive(Phase.BOARD);
    }

    /** Обрабатывает нажатие Escape. */
    @Override
    public void pressEsc() {
        Phase.setActive(Phase.BOARD);
    }
}
