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
    public static boolean evenFrame;    // helps to animate shaking elements

    // Global updates go into this parent method, other views start updating by calling super
    public void update() {
        game.display.setInterlaceEnabled(false);

        if (!Screen.is(Screen.SHOP)) { // money approach slowly only when they change at shop screen
            game.shop.header.setDisplayMoney(game.player.inventory.money);
        }
    }

    protected void displayDice() {
        if (game.shop.luckyDice == null) return;
        int diceRemainingTurns = game.shop.luckyDice.expireMove - game.player.getMoves();
        if (Util.inside(diceRemainingTurns, 0, 2) && game.player.getMoves() != 0) game.shop.dice.draw();
    }

    public static class Area {
        private final int[] coords;

        public Area(int left, int right, int top, int bottom) {
            this.coords = new int[]{left, right, top, bottom};
        }

        public boolean covers(int x, int y) {
            return Util.inside(x, coords[0], coords[1]) && Util.inside(y, coords[2], coords[3]);
        }
    }
}












































