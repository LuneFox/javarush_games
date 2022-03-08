package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.model.Message;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.model.options.Options;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Cache;

import java.util.stream.Stream;

public class ControlOptions implements ControlStrategy {

    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GENERAL_CLOSE).checkLeftTouch(x, y)) {
            Screen.setActive(Screen.MAIN);
            Message.show("Сохранено");
        }

        Stream.of(
                Options.difficultySelector,
                Options.autoBuyFlagsSelector,
                Options.timerEnabledSelector,
                Options.displayMessageSelector,
                Options.themeSelector
        ).forEach(drawableObject -> drawableObject.checkLeftTouch(x, y));
    }

    @Override
    public void pressRight() {
        Options.difficultySelector.difficultyUp();
    }

    @Override
    public void pressLeft() {
        Options.difficultySelector.difficultyDown();
    }

    @Override
    public void pressEsc() {
        Screen.setActive(Screen.MAIN);
        Message.show("Сохранено");
    }
}
