package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.model.Phase;

public class ControlMain implements ControlStrategy {
    private static MinesweeperGame game;

    @Override
    public void pressEsc() {
        if (game.isStopped()) return;
        Phase.setActive(Phase.BOARD);
    }

    public static void setGame(MinesweeperGame game) {
        ControlMain.game = game;
    }
}