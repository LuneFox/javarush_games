package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.model.Phase;

/** Управляет вводом на игровом поле. */
public class ControlBoard implements ControlStrategy {
    /** Ссылка на игру. */
    private static MinesweeperGame game;

    /** Обрабатывает левый клик. */
    @Override
    public void leftClick(int x, int y) {
        Phase.leftClickOnCurrentView(x, y);

        if (game.isStopped()) {
            Phase.setActive(Phase.GAME_OVER);
            return;
        }

        game.leftClickOnBoard(x, y);
    }

    /** Обрабатывает правый клик. */
    @Override
    public void rightClick(int x, int y) {
        Phase.rightClickOnCurrentView(x, y);

        if (game.isStopped()) {
            Phase.setActive(Phase.GAME_OVER);
            return;
        }

        game.rightClickOnBoard(x, y);
    }

    /** Обрабатывает нажатие влево. */
    @Override
    @DeveloperOption
    public void pressLeft() {
        game.autoFlag();
    }

    /** Обрабатывает нажатие вправо. */
    @Override
    @DeveloperOption
    public void pressRight() {
        game.autoOpen();
    }

    /** Обрабатывает нажатие вниз. */
    @Override
    @DeveloperOption
    public void pressDown() {
        game.autoScan();
    }

    /** Обрабатывает нажатие вверх. */
    @Override
    @DeveloperOption
    public void pressUp() {
        game.autoSolve();
    }

    /** Обрабатывает нажатие пробела. */
    @Override
    public void pressSpace() {
        Phase.setActive(!game.isStopped() ? Phase.SHOP : Phase.GAME_OVER);
    }

    /** Обрабатывает нажатие Escape. */
    @Override
    public void pressEsc() {
        Phase.setActive(game.isStopped() ? Phase.GAME_OVER : Phase.MAIN);
    }

    /** Обрабатывает нажатие паузы. */
    @Override
    public void pressPause() {
        pressEsc();
    }

    /** Обрабатывает прочие клавиши. */
    @Override
    public void pressOther() {
        if (game.isStopped()) {
            Phase.setActive(Phase.GAME_OVER);
        }
    }

    /** Задаёт игру. */
    public static void setGame(MinesweeperGame game) {
        ControlBoard.game = game;
    }
}
