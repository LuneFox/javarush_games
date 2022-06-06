package com.javarush.games.snake.model;

import com.javarush.games.snake.view.View;
import com.javarush.games.snake.view.impl.*;

import java.util.Arrays;
import java.util.LinkedList;

public enum Phase {
    CONTROLS_MENU, GAME_FIELD, HELP_MENU, MAIN_MENU, MAP_EDITOR, OPTIONS_MENU;

    private static final LinkedList<Phase> PHASES;

    static {
        PHASES = new LinkedList<>();
        PHASES.addAll(Arrays.asList(Phase.values()));
    }

    public static void proceed(Phase phase) {
        change(phase);
        updateView(phase);
    }

    private static void change(Phase phase) {
        if (phase == getCurrentPhase()) return;
        PHASES.remove(phase);
        PHASES.addFirst(phase);
    }

    private static void updateView(Phase phase) {
        View view = getView(phase);
        view.update();
    }

    private static View getView(Phase phase) {
        switch (phase) {
            case MAIN_MENU:
                return MainMenuView.getInstance();
            case HELP_MENU:
                return HelpMenuView.getInstance();
            case MAP_EDITOR:
                return MapEditorView.getInstance();
            case OPTIONS_MENU:
                return OptionsMenuView.getInstance();
            case CONTROLS_MENU:
                return ControlsMenuView.getInstance();
            case GAME_FIELD:
                return GameFieldView.getInstance();
            default:
                throw new IllegalArgumentException("Unimplemented view");
        }
    }

    public static boolean is(Phase phase) {
        return (getCurrentPhase() == phase);
    }

    public static Phase getCurrentPhase() {
        return PHASES.peekFirst();
    }
}
