package com.javarush.games.minesweeper.view;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.utility.Util;
import com.javarush.games.minesweeper.view.graphics.*;

import java.util.EnumMap;

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

        if (!Screen.is(Screen.SHOP)) { // animate money only when buying items
            Screen.shop.moneyOnDisplay = game.inventory.money;
        }
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












































