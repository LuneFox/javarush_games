package com.javarush.games.minesweeper.controller;

import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Util;
import com.javarush.games.minesweeper.controller.impl.ControlBoard;
import com.javarush.games.minesweeper.controller.impl.ControlDisabled;
import com.javarush.games.minesweeper.controller.impl.ControlMain;
import com.javarush.games.minesweeper.controller.impl.ControlShop;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.View;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/** Преобразует события движка в вызовы стратегий управления. */
public class Controller {
    /** Стратегии управления по фазам. */
    private static final Map<Phase, ControlStrategy> strategyMap = new HashMap<>();
    /** Y последнего клика. */
    private static int lastClickY;

    /** Ссылка на игру. */
    private final MinesweeperGame game;
    /** Текущая стратегия управления. */
    private ControlStrategy strategy;

    /** Создаёт экземпляр Controller. */
    public Controller(MinesweeperGame game) {
        this.game = game;
        ControlMain.setGame(game);
        ControlShop.setGame(game);
        ControlBoard.setGame(game);
    }

    static {
        createStrategies();
    }

    /** Создаёт стратегии управления для всех фаз. */
    private static void createStrategies() {
        ControlStrategyFactory factory = new ControlStrategyFactory();
        Arrays.stream(Phase.values()).forEach(phase -> strategyMap.put(phase, factory.createStrategy(phase)));
        strategyMap.put(null, new ControlDisabled());
    }

    /** Сообщает, был ли последний клик в верхней половине экрана. */
    public static boolean clickedOnUpperHalf() {
        return lastClickY < 50;
    }

    /** Обрабатывает клик. */
    public final void click(int x, int y, Click click) {
        if (isInvalidInput(x, y)) return;

        lastClickY = y;
        game.restrictFlagExplosion();
        game.stopRecursion();
        selectStrategyAccordingToActivePhase();

        if (click == Click.LEFT) {
            strategy.leftClick(x, y);
        } else {
            strategy.rightClick(x, y);
        }
    }

    /** Обрабатывает нажатие клавиши. */
    public final void pressKey(Key key) {
        if (isInvalidInput(0, 0)) return;

        selectStrategyAccordingToActivePhase();

        if (key == Key.UP) strategy.pressUp();
        else if (key == Key.DOWN) strategy.pressDown();
        else if (key == Key.LEFT) strategy.pressLeft();
        else if (key == Key.RIGHT) strategy.pressRight();
        else if (key == Key.ENTER) strategy.pressEnter();
        else if (key == Key.PAUSE) strategy.pressPause();
        else if (key == Key.SPACE) strategy.pressSpace();
        else if (key == Key.ESCAPE) strategy.pressEsc();
        else strategy.pressOther();
    }

    /** Выбирает стратегию по активной фазе. */
    private void selectStrategyAccordingToActivePhase() {
        strategy = strategyMap.get(Phase.getActive());
    }

    /** Сообщает, является ли ввод недопустимым. */
    private boolean isInvalidInput(int x, int y) {
        return !(Button.isAnimationFinished()
                && Util.isWithinScreen(x, y)
                && View.getGameOverShowDelay() <= 0);
    }
}
