package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.model.Phase;

/** Управляет вводом на экране рекордов. */
public class ControlRecords implements ControlStrategy {

    /** Обрабатывает нажатие Escape. */
    @Override
    public void pressEsc() {
        Phase.setActive(Phase.MAIN);
    }
}
