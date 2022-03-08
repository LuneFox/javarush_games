package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.model.options.Options;
import com.javarush.games.minesweeper.view.graphics.*;

/**
 * Shows the OPTIONS screen.
 */

public final class ViewOptions extends View {
    public ViewOptions() {
        this.screen = Screen.OPTIONS;
    }

    @Override
    public void update() {
        Cache.get(VisualElement.WIN_MENU).draw();
        Printer.print("настройки", Color.YELLOW, Printer.CENTER, 2);

        Printer.print("сложность", 2, Options.difficultySelector.y - 1);
        Options.difficultySelector.draw();

        Printer.print("покупка флажков", 2, Options.autoBuyFlagsSelector.y - 1);
        Options.autoBuyFlagsSelector.draw();

        Printer.print("игра на время", 2, Options.timerEnabledSelector.y - 1);
        Options.timerEnabledSelector.draw();

        Printer.print("тема: " + Theme.getCurrentName(), 2, Options.themeSelector.y);
        Options.themeSelector.draw();

        Cache.get(Button.ButtonID.GENERAL_BACK).draw();
        super.update();
    }
}
