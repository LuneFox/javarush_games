package com.javarush.games.moonlander.controller;

import com.javarush.games.moonlander.controller.painter.PainterControl;
import com.javarush.games.moonlander.model.Phase;

public class ControlStrategyFactory {
    public ControlStrategy createStrategy(Phase phase) {
        switch (phase) {
            case PAINTER_EDITOR:
                return new PainterControl();
            default:
                return new ControlStrategy() {
                };
        }
    }
}
