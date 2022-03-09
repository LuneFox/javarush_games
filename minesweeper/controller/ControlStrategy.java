package com.javarush.games.minesweeper.controller;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Message;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.options.Options;
import com.javarush.games.minesweeper.model.player.Score;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Cache;

import java.util.stream.Stream;

import static com.javarush.games.minesweeper.Util.inside;

/**
 * Strategy pattern for different controls on different screens.
 */

public interface ControlStrategy {
    default void leftClick(int x, int y) {
    }

    default void rightClick(int x, int y) {
    }

    default void pressUp() {
    }

    default void pressDown() {
    }

    default void pressRight() {
    }

    default void pressLeft() {
    }

    default void pressEnter() {
    }

    default void pressPause() {
    }

    default void pressSpace() {
    }

    default void pressEsc() {
    }

    default void pressOther() {
    }
}