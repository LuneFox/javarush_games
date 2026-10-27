package com.javarush.games.snake.view;

import com.javarush.games.snake.model.Phase;
import com.javarush.games.snake.view.impl.ControlsMenuView;
import com.javarush.games.snake.view.impl.GameFieldView;
import com.javarush.games.snake.view.impl.HelpMenuView;
import com.javarush.games.snake.view.impl.MainMenuView;
import com.javarush.games.snake.view.impl.MapEditorView;
import com.javarush.games.snake.view.impl.OptionsMenuView;

/**
 * Maps a game phase to the screen that renders it.
 */
public final class ViewFactory {

    private ViewFactory() {
    }

    public static View getView(Phase phase) {
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
                throw new IllegalArgumentException("Unimplemented view for phase " + phase);
        }
    }
}
