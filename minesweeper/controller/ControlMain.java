package com.javarush.games.minesweeper.controller;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Phase;

public class ControlMain implements ControlStrategy {
    @Override
    public void pressEsc() {
        if (!MinesweeperGame.getInstance().isStopped) {
            Phase.setActive(Phase.BOARD);
        }
    }
}
