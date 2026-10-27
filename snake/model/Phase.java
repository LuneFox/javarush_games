package com.javarush.games.snake.model;

import com.javarush.games.snake.view.ViewFactory;

/** Фаза игры (экран) и переходы между ними. */
public enum Phase {
    CONTROLS_MENU, GAME_FIELD, HELP_MENU, MAIN_MENU, MAP_EDITOR, OPTIONS_MENU;

    private static Phase currentPhase = MAIN_MENU;

    /** Переключает фазу и обновляет её экран. */
    public static void proceed(Phase phase) {
        currentPhase = phase;
        ViewFactory.getView(phase).update();
    }

    /** Возвращает текущую фазу. */
    public static Phase getCurrentPhase() {
        return currentPhase;
    }
}
