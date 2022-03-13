package com.javarush.games.minesweeper.controller;

import com.javarush.games.minesweeper.model.Phase;

public class ControlRecords implements ControlStrategy {
    @Override
    public void pressEsc() {
        Phase.setActive(Phase.MAIN);
    }
}
