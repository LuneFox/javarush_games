package com.javarush.games.minesweeper.view;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.impl.*;

/** Создаёт экран по фазе игры. */
public class ViewFactory {

    /** Создаёт экземпляр ViewFactory. */
    public ViewFactory(MinesweeperGame game) {
        this.game = game;
    }

    /** Ссылка на игру. */
    private final MinesweeperGame game;
    /** Создаёт экран. */
    public View createView(Phase phase) {
        switch (phase) {
            case ABOUT:
                return new ViewAbout(game);
            case BOARD:
                return new ViewBoard(game);
            case GAME_OVER:
                return new ViewGameOver(game);
            case ITEM_HELP:
                return new ViewItemHelp(game);
            case MAIN:
                return new ViewMain(game);
            case OPTIONS:
                return new ViewOptions(game);
            case RECORDS:
                return new ViewRecords(game);
            case SCORE:
                return new ViewScore(game);
            case SHOP:
                return new ViewShop(game);
            default:
                return new View(game) {
                };
        }
    }
}
