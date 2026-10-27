package com.javarush.games.snake.controller;

import com.javarush.engine.cell.Key;
import com.javarush.games.snake.controller.strategies.ControlsMenuControlStrategy;
import com.javarush.games.snake.controller.strategies.GameFieldControlStrategy;
import com.javarush.games.snake.controller.strategies.HelpMenuControlStrategy;
import com.javarush.games.snake.controller.strategies.MainMenuControlStrategy;
import com.javarush.games.snake.controller.strategies.MapEditorControlStrategy;
import com.javarush.games.snake.controller.strategies.OptionsMenuControlStrategy;
import com.javarush.games.snake.model.Phase;

public class Controller {

    public final void leftClick(int x, int y) {
        selectStrategy().leftClick(x, y);
    }

    public final void rightClick(int x, int y) {
        selectStrategy().rightClick(x, y);
    }

    public final void pressKey(Key key) {
        final ControlStrategy strategy = selectStrategy();

        switch (key) {
            case UP:
                strategy.pressUp();
                break;
            case DOWN:
                strategy.pressDown();
                break;
            case LEFT:
                strategy.pressLeft();
                break;
            case RIGHT:
                strategy.pressRight();
                break;
            case SPACE:
                strategy.pressSpace();
                break;
            case ENTER:
                strategy.pressEnter();
                break;
            case ESCAPE:
                strategy.pressEscape();
                break;
            case PAUSE:
                strategy.pressPause();
                break;
            default:
                break;
        }
    }

    public final void releaseKey(Key key) {
        final ControlStrategy strategy = selectStrategy();

        switch (key) {
            case UP:
                strategy.releaseUp();
                break;
            case DOWN:
                strategy.releaseDown();
                break;
            case LEFT:
                strategy.releaseLeft();
                break;
            case RIGHT:
                strategy.releaseRight();
                break;
            default:
                break;
        }
    }

    private ControlStrategy selectStrategy() {
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
