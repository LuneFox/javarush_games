package com.javarush.games.snake.controller;

import com.javarush.engine.cell.Key;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.controller.strategies.*;
import com.javarush.games.snake.model.Phase;

public class Controller {
    protected static SnakeGame game = SnakeGame.getInstance();
    private ControlStrategy controlStrategy;

    public final void leftClick(int x, int y) {
        controlStrategy = selectStrategyAccordingToPhase();
        controlStrategy.leftClick(x, y);
    }

    public final void rightClick(int x, int y) {
        controlStrategy = selectStrategyAccordingToPhase();
        controlStrategy.rightClick(x, y);
    }

    public final void pressKey(Key key) {
        controlStrategy = selectStrategyAccordingToPhase();

        if (key == Key.UP) controlStrategy.pressUp();
        else if (key == Key.DOWN) controlStrategy.pressDown();
        else if (key == Key.LEFT) controlStrategy.pressLeft();
        else if (key == Key.RIGHT) controlStrategy.pressRight();
        else if (key == Key.SPACE) controlStrategy.pressSpace();
        else if (key == Key.ENTER) controlStrategy.pressEnter();
        else if (key == Key.ESCAPE) controlStrategy.pressEscape();
        else if (key == Key.PAUSE) controlStrategy.pressPause();
    }

    public final void releaseKey(Key key) {
        controlStrategy = selectStrategyAccordingToPhase();

        if (key == Key.UP) controlStrategy.releaseUp();
        else if (key == Key.DOWN) controlStrategy.releaseDown();
        else if (key == Key.LEFT) controlStrategy.releaseLeft();
        else if (key == Key.RIGHT) controlStrategy.releaseRight();
    }

    private ControlStrategy selectStrategyAccordingToPhase() {
        switch (Phase.getCurrentPhase()) {
            case GAME_FIELD:
                return GameFieldControlStrategy.getInstance();
            case HELP_MENU:
                return HelpMenuControlStrategy.getInstance();
            case OPTIONS_MENU:
                return OptionsMenuControlStrategy.getInstance();
            case CONTROLS_MENU:
                return ControlsMenuControlStrategy.getInstance();
            case MAIN_MENU:
                return MainMenuControlStrategy.getInstance();
            case MAP_EDITOR:
                return MapEditorControlStrategy.getInstance();
            default:
                throw new IllegalArgumentException("Unimplemented strategy");
        }
    }
}
