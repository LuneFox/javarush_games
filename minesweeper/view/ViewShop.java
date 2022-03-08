package com.javarush.games.minesweeper.view;

import com.javarush.games.minesweeper.model.Screen;

/**
 * Shows the shop over the game board.
 */

public final class ViewShop extends View {
    public ViewShop() {
        this.screen = Screen.SHOP;
    }

    @Override
    public void update() {
        super.update();
        Screen.board.update();
        game.checkTimeOut();
        game.shop.showCase.draw();
        game.shop.header.draw();
        game.shop.footer.draw();
        game.timer.draw();
        game.shop.goldenShovel.statusBar.draw();
        game.shop.luckyDice.statusBar.draw();
    }
}
