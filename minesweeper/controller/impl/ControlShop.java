package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.Text;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.model.Phase;

import static com.javarush.games.minesweeper.Util.inside;

/** Управляет вводом в магазине. */
public class ControlShop implements ControlStrategy {
    /** Ссылка на игру. */
    private static MinesweeperGame game;

    /** Обрабатывает левый клик. */
    @Override
    public void leftClick(int x, int y) {
        Phase.leftClickOnCurrentView(x, y);

        if (clickedOutsideShopWindow(x, y)) {
            Phase.setActive(Phase.BOARD);
        }
    }

    /** Обрабатывает нажатие пробела. */
    @Override
    public void pressSpace() {
        Phase.setActive(Phase.BOARD);
    }

    /** Обрабатывает нажатие Escape. */
    @Override
    public void pressEsc() {
        Phase.setActive(Phase.BOARD);
    }

    /** Обрабатывает нажатие вверх. */
    @Override
    @DeveloperOption
    public void pressUp() {
        game.getPlayer().cheatMoney();
        game.showMessage(Text.DEV_GOLD_99.getText());
    }

    /** Обрабатывает нажатие вниз. */
    @Override
    @DeveloperOption
    public void pressDown() {
        game.getShop().cheatMoreTools();
    }

    /** Сообщает, был ли клик вне окна магазина. */
    private boolean clickedOutsideShopWindow(int x, int y) {
        boolean horizontal = inside(x, 0, 9) || inside(x, 90, 99);
        boolean vertical = inside(y, 0, 10) || inside(y, 91, 99);
        return (horizontal || vertical);
    }

    /** Задаёт игру. */
    public static void setGame(MinesweeperGame game) {
        ControlShop.game = game;
    }
}
