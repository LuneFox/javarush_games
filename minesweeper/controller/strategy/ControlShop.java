package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.ShopItem;
import com.javarush.games.minesweeper.view.View;

import static com.javarush.games.minesweeper.utility.Util.inside;

public class ControlShop implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (clickedOutsideShop(x, y)) View.board.display();
        ShopItem item = game.shop.getClickedItem(x, y);
        game.shop.sellAndRememberLastClick(item);
    }

    @Override
    public void rightClick(int x, int y) {
        ShopItem item = game.shop.getClickedItem(x, y);
        if (item == null) return;
        View.itemHelp.display(item);
    }

    @Override
    public void pressUp() {

    }

    @Override
    public void pressDown() {

    }

    @Override
    public void pressRight() {

    }

    @Override
    public void pressLeft() {

    }

    @Override
    public void pressSpace() {
        View.board.display();
    }

    @Override
    public void pressEsc() {
        View.board.display();
    }

    @Override
    public void pressOther() {

    }

    private boolean clickedOutsideShop(int x, int y) {
        boolean horizontal = inside(x, 0, 9) || inside(x, 90, 99);
        boolean vertical = inside(y, 0, 10) || inside(y, 91, 99);
        return (horizontal || vertical);
    }
}
