package com.javarush.games.minesweeper.view.impl;

import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.View;

public class ViewBoard extends View {
    @Override
    public void update() {
        if (!(Phase.isActive(Phase.SHOP))) game.display.setInterlaceEnabled(true);
        game.boardManager.drawField();
        super.update();
    }
}
