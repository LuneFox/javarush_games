package com.javarush.games.ticktacktoe.controller;

import com.javarush.engine.cell.Key;
import com.javarush.games.ticktacktoe.TicTacToeGame;

/**
 * Контроллер для управления игрой.
 *
 * @author LuneFox, 2024
 */
public class Controller {
    /** Стратегия, задающая конкретное управление */
    private final ControlStrategy controlStrategy;

    public Controller(TicTacToeGame game) {
        this.controlStrategy = new BoardControlStrategy(game);
    }

    /**
     * Действие при клике мышью
     *
     * @param x координата x в пикселях
     * @param y координата y в пикселях
     * @param click левый или правый клик
     */
    public final void click(int x, int y, Click click) {
        controlStrategy.click(x, y, click);
    }

    /**
     * Действие при нажатии на клавишу
     *
     * @param key клавиша
     */
    public final void pressKey(Key key) {
        if (key == Key.UP) controlStrategy.pressUp();
        else if (key == Key.DOWN) controlStrategy.pressDown();
        else if (key == Key.LEFT) controlStrategy.pressLeft();
        else if (key == Key.RIGHT) controlStrategy.pressRight();
        else if (key == Key.SPACE) controlStrategy.pressSpace();
        else if (key == Key.ENTER) controlStrategy.pressEnter();
    }

    /**
     * Действие при отпускании клавиши
     * @param key клавиша
     */
    public final void releaseKey(Key key) {
        if (key == Key.UP) controlStrategy.releaseUp();
        else if (key == Key.DOWN) controlStrategy.releaseDown();
        else if (key == Key.LEFT) controlStrategy.releaseLeft();
        else if (key == Key.RIGHT) controlStrategy.releaseRight();
        else if (key == Key.SPACE) controlStrategy.releaseSpace();
        else if (key == Key.ENTER) controlStrategy.releaseEnter();
    }
}
