package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.view.graphics.VisualElement;

/**
 * Shows the main game board.
 */

public final class ViewBoard extends View {
    public ViewBoard(MinesweeperGame game) {
        this.game = game;
        this.screen = Screen.BOARD;
    }

    @Override
    public void display() {
        super.display();
        refresh();
    }

    public void refresh() {
        game.redrawAllCells();
        if (game.shop.allItems.get(1).isActivated()) {
            IMAGES_CACHE.get(VisualElement.WIN_BOARD_TRANSPARENT_FRAME).replaceColor(Color.BLUE, 3);
            IMAGES_CACHE.get(VisualElement.WIN_BOARD_TRANSPARENT_FRAME).draw();
        } else if (game.shop.allItems.get(5).isActivated()) {
            IMAGES_CACHE.get(VisualElement.WIN_BOARD_TRANSPARENT_FRAME).replaceColor(Color.RED, 3);
            IMAGES_CACHE.get(VisualElement.WIN_BOARD_TRANSPARENT_FRAME).draw();
        }
        game.timer.draw();
        game.shop.goldenShovel.statusBar.draw();
        game.shop.luckyDice.statusBar.draw();
    }
}
