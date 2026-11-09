package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.model.Phase;

/** Управляет вводом в главном меню. */
public class ControlMain implements ControlStrategy {
    /** Ссылка на игру. */
    private static MinesweeperGame game;

    /** Обрабатывает нажатие Escape. */
    @Override
    public void pressEsc() {
        if (game.isStopped()) return;
        Phase.setActive(Phase.BOARD);
    }

    /** Задаёт игру. */
    public static void setGame(MinesweeperGame game) {
        ControlMain.game = game;
    }
}