package com.javarush.games.minesweeper.view.impl;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.gui.interactive.DifficultySelector;
import com.javarush.games.minesweeper.gui.interactive.SwitchSelector;
import com.javarush.games.minesweeper.gui.interactive.ThemeSelector;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.View;

public class ViewOptions extends View {

    private final Button closeButton = new Button(88, 2, "x", this) {
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

    public ViewOptions(MinesweeperGame game) {
        super(game);
    }

    @Override
    public void update() {
        background.draw();
        final int leftPadding = 2;
        Printer.print("<настройки>", Theme.LABEL.getColor(), Printer.CENTER, 2);

        Printer.print("сложность", leftPadding, Options.difficultySelector.y - 1);
        difficultySelector.draw();

        Printer.print("покупка флажков", leftPadding, Options.autoBuyFlagsSelector.y - 1);
        autoBuyFlagsSelector.draw();

        Printer.print("игра на время", leftPadding, Options.timerEnabledSelector.y - 1);
        timerEnabledSelector.draw();

        Printer.print("Сообщения:", leftPadding, Options.displayMessageSelector.y - 1);
        displayMessageSelector.draw();

        Printer.print("тема: " + Theme.getCurrentName(), leftPadding, Options.themeSelector.y);
        themeSelector.draw();

        closeButton.draw();
        super.update();
    }
}
