package com.javarush.games.minesweeper.controller;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.player.Score;

import static com.javarush.games.minesweeper.Util.inside;

public class ControlStrategyFactory {
    public ControlStrategy createStrategy(Phase phase) {
        switch (phase) {
            case ABOUT:
                return new ControlAbout();
            case BOARD:
                return new ControlBoard();
            case GAME_OVER:
                return new ControlGameOver();
            case ITEM_HELP:
                return new ControlItemHelp();
            case MAIN:
                return new ControlMain();
            case OPTIONS:
                return new ControlOptions();
            case RECORDS:
                return new ControlRecords();
            case SCORE:
                return new ControlScore();
            case SHOP:
                return new ControlShop();
            default:
                return new ControlStrategy() {
                };
        }
    }
}
