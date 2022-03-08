package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.view.graphics.Cache;
import com.javarush.games.minesweeper.view.graphics.VisualElement;

/**
 * Shows the main game board.
 */

public final class ViewBoard extends View {
    public ViewBoard() {
        this.screen = Screen.BOARD;
    }

    public void update() {
        if (!(Screen.isActive(Screen.SHOP))) game.display.setInterlaceEnabled(true);
        game.checkTimeOut();
        game.field.draw();
        if (game.shop.allItems.get(1).isActivated()) {
            Cache.get(VisualElement.WIN_BOARD_TRANSPARENT_FRAME).replaceColor(Color.BLUE, 3);
            Cache.get(VisualElement.WIN_BOARD_TRANSPARENT_FRAME).draw();
        } else if (game.shop.allItems.get(5).isActivated()) {
            Cache.get(VisualElement.WIN_BOARD_TRANSPARENT_FRAME).replaceColor(Color.RED, 3);
            Cache.get(VisualElement.WIN_BOARD_TRANSPARENT_FRAME).draw();
        }
        game.timer.draw();
        game.shop.goldenShovel.statusBar.draw();
        game.shop.luckyDice.statusBar.draw();
        super.update();
    }
}
