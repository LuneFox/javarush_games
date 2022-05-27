package com.javarush.games.snake.view;

import java.util.Arrays;
import java.util.LinkedList;

public enum Phase {
    CONTROLS, GAME, HELP, MAIN_MENU, MAP_EDIT, OPTIONS;

    private static final LinkedList<Phase> PHASES;

    static {
        PHASES = new LinkedList<>();
        PHASES.addAll(Arrays.asList(Phase.values()));
    }

    public static void set(Phase phase) {
        PHASES.remove(phase);
        PHASES.addFirst(phase);
    }

    public static boolean is(Phase phase) {
        return (getCurrent() == phase);
    }

    public static Phase getCurrent() {
        return PHASES.peekFirst();
    }
}
