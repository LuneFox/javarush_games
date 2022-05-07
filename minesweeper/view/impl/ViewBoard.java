package com.javarush.games.minesweeper.view.impl;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.View;

public class ViewBoard extends View {
    public ViewBoard(MinesweeperGame game) {
        super(game);
    }

    @Override
    public void update() {
        if (!(Phase.isActive(Phase.SHOP))) game.setInterlacedEffect(true);
        View.setGameOverShowDelay(0);
        game.drawGameBoard();
        super.update();
    }
}
