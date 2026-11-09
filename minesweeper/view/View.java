package com.javarush.games.minesweeper.view;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.controller.Click;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.InteractiveObject;

import java.util.ArrayList;
import java.util.List;

/** Базовый экран игры. */
public abstract class View {
    /** Ссылка на игру. */
    protected final MinesweeperGame game;
    /** Задержка показа экрана завершения. */
    private static int gameOverShowDelay;
    /** Интерактивные объекты экрана. */
    private final List<InteractiveObject> linkedObjects;

    /** Создаёт экземпляр View. */
    public View(MinesweeperGame game) {
        this.linkedObjects = new ArrayList<>();
        this.game = game;
    }

    /** Обновляет состояние. */
    public void update() {
        PopUpMessage.drawInstance();
    }

    /** Обрабатывает клик. */
    public void click(int x, int y, Click click) {
        for (InteractiveObject object : linkedObjects) {
            if (object.covers(x, y)) {
                object.click(x, y, click);
                return; // кликаем только первый объект, если они случайно наложились
            }
        }
    }

    /** Привязывает объект к экрану. */
    public void linkObject(InteractiveObject interactiveObject) {
        linkedObjects.add(interactiveObject);
    }

    /** Возвращает задержку показа экрана завершения. */
    public static int getGameOverShowDelay() {
        return gameOverShowDelay;
    }

    /** Задаёт задержку показа экрана завершения. */
    public static void setGameOverShowDelay(int gameOverShowDelay) {
        View.gameOverShowDelay = gameOverShowDelay;
    }

    /** Уменьшает задержку показа экрана завершения. */
    public static void decreaseGameOverShowDelay() {
        View.gameOverShowDelay--;
    }
}