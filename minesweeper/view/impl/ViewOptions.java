package com.javarush.games.minesweeper.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.*;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.View;

public class ViewOptions extends View {

    private final Button closeButton = new Button(88, 2, 0, 0, "x", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.MAIN);
            PopUpMessage.show(Options.OPTIONS_SAVED);
        }
    };
    final Image background = Image.cache.get(ImageType.GUI_BACKGROUND);
    final DifficultySelector difficultySelector = Options.difficultySelector.linkView(this);
    final SwitchSelector autoBuyFlagsSelector = Options.autoBuyFlagsSelector.linkView(this);
    final SwitchSelector timerEnabledSelector = Options.timerEnabledSelector.linkView(this);
    final SwitchSelector displayMessageSelector = Options.displayMessageSelector.linkView(this);
    final ThemeSelector themeSelector = Options.themeSelector.linkView(this);

    @Override
    public void update() {
        background.draw();
        Printer.print("<настройки>", Color.YELLOW, Printer.CENTER, 2);

        Printer.print("сложность", 3, Options.difficultySelector.y - 1);
        difficultySelector.draw();

        Printer.print("покупка флажков", 3, Options.autoBuyFlagsSelector.y - 1);
        autoBuyFlagsSelector.draw();

        Printer.print("игра на время", 3, Options.timerEnabledSelector.y - 1);
        timerEnabledSelector.draw();

        Printer.print("Сообщения:", 3, Options.displayMessageSelector.y - 1);
        displayMessageSelector.draw();

        Printer.print("тема: " + Theme.getCurrentName(), 3, Options.themeSelector.y);
        themeSelector.draw();

        closeButton.draw();
        super.update();
    }
}
