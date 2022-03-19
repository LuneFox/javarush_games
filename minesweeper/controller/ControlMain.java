package com.javarush.games.minesweeper.controller;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Phase;

public class ControlMain implements ControlStrategy {
    @Override
    public void pressEsc() {
        final MinesweeperGame game = MinesweeperGame.getInstance();
        if (game.isStopped) return;
        Phase.setActive(Phase.BOARD);
    }
}
