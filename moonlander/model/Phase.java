package com.javarush.games.moonlander.model;

import com.javarush.games.moonlander.view.View;
import com.javarush.games.moonlander.view.ViewFactory;

import java.util.*;

public enum Phase {
    MAIN, PAINTER_EDITOR;
    private static final List<Phase> PHASES;
    private static final Map<Phase, View> PHASE_VIEW_MAP;

    static {
        PHASES = new LinkedList<>(Arrays.asList(Phase.values()));
        PHASE_VIEW_MAP = new HashMap<>();
        ViewFactory factory = new ViewFactory();
        PHASES.forEach(phase -> PHASE_VIEW_MAP.put(phase, factory.createView(phase)));
    }

    public static void setActive(Phase phase) {
        PHASES.remove(phase);
        PHASES.add(0, phase);
        updateView();
    }

    public static void updateView() {
        PHASE_VIEW_MAP.get(getActive()).update();
    }

    public static Phase getActive() {
        return PHASES.get(0);
    }

    public static boolean isActive(Phase phase) {
        return (PHASES.get(0) == phase);
    }
}
