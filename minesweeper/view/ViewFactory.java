package com.javarush.games.minesweeper.view;

import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.impl.*;

public class ViewFactory {
    public View createView(Phase phase) {
        switch (phase) {
            case ABOUT:
                return new ViewAbout(phase);
            case BOARD:
                return new ViewBoard(phase);
            case GAME_OVER:
                return new ViewGameOver(phase);
            case ITEM_HELP:
                return new ViewItemHelp(phase);
            case MAIN:
                return new ViewMain(phase);
            case OPTIONS:
                return new ViewOptions(phase);
            case RECORDS:
                return new ViewRecords(phase);
            case SCORE:
                return new ViewScore(phase);
            case SHOP:
                return new ViewShop(phase);
            default:
                return new View(phase) {
                    @Override
                    public void update() {
                        super.update();
                    }
                };
        }
    }
}
