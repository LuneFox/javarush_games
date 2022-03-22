package com.javarush.games.minesweeper.view;

import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.impl.*;

public class ViewFactory {
    public View createView(Phase phase) {
        switch (phase) {
            case ABOUT:
                return new ViewAbout();
            case BOARD:
                return new ViewBoard();
            case GAME_OVER:
                return new ViewGameOver();
            case ITEM_HELP:
                return new ViewItemHelp();
            case MAIN:
                return new ViewMain();
            case OPTIONS:
                return new ViewOptions();
            case RECORDS:
                return new ViewRecords();
            case SCORE:
                return new ViewScore();
            case SHOP:
                return new ViewShop();
            default:
                return new View() {
                    @Override
                    public void update() {
                        super.update();
                    }
                };
        }
    }
}
