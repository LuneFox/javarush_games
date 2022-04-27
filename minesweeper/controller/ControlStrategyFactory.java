package com.javarush.games.minesweeper.controller;

import com.javarush.games.minesweeper.controller.impl.*;
import com.javarush.games.minesweeper.model.Phase;

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
                return new ControlDisabled();
        }
    }
}
