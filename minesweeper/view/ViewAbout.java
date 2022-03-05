package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.model.Strings;
import com.javarush.games.minesweeper.model.options.Options;
import com.javarush.games.minesweeper.view.graphics.*;

/**
 * Shows the ABOUT section of the game.
 */

public final class ViewAbout extends View {
    public ViewAbout() {
        this.screen = Screen.ABOUT;
    }

    @Override
    public void update() {
        super.update();
        Cache.get(VisualElement.WIN_MENU).draw();
        Options.aboutPageSelector.draw();
        Printer.print(Strings.ABOUT_HEAD[Options.aboutPageSelector.getCurrentPage()], Color.YELLOW, Printer.CENTER, 2);
        Printer.print(Strings.ABOUT_BODY[Options.aboutPageSelector.getCurrentPage()], 3, 13);
        Cache.get(Button.ButtonID.GENERAL_BACK).draw();
    }
}
