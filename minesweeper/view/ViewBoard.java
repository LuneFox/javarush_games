package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.graphics.Bitmap;

public final class ViewBoard extends View {
    public ViewBoard(MinesweeperGame game) {
        this.game = game;
        this.screenType = Screen.ScreenType.GAME_BOARD;
    }

    @Override
    public void display() {
        super.display();
        game.redrawAllCells();
        if (game.shop.allItems.get(1).isActivated()) {
            IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).replaceColor(Color.BLUE, 3);
            IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).draw();
        } else if (game.shop.allItems.get(5).isActivated()) {
            IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).replaceColor(Color.RED, 3);
            IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).draw();
        }
        game.timer.draw();
    }
}
