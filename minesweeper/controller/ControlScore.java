package com.javarush.games.minesweeper.controller;

import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.player.Score;

public class ControlScore implements ControlStrategy {
    @Override
    public void pressSpace() {
        Phase.setActive(Phase.GAME_OVER);
    }

    @Override
    public void pressEsc() {
        Phase.setActive(Phase.GAME_OVER);
    }

    @Override
    public void pressRight() {
        Score.Table.pageSelector.nextPage();
    }

    @Override
    public void pressLeft() {
        Score.Table.pageSelector.prevPage();
    }
}
