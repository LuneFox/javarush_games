package com.javarush.games.minesweeper.view;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.Util;

/**
 * Class for displaying various menus on the screen.
 */

public class View {
    protected MinesweeperGame game = MinesweeperGame.getInstance();
    public Screen screen;

    public View() {
        Screen.views.add(this);
    }

    // Global updates go into this parent method, other views start updating by calling super
    public void update() {
        game.display.setInterlaceEnabled(false);

        if (!Screen.isActive(Screen.SHOP)) { // money approach slowly only when they change at shop screen
            if (game.shop.header != null) {
                game.shop.header.setDisplayMoney(game.player.inventory.money);
            }
        }
    }
}












































