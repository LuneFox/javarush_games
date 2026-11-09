package com.javarush.games.minesweeper.view.impl;

import com.javarush.games.minesweeper.Text;

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

/** Экран настроек. */
public class ViewOptions extends View {

    /** Кнопка закрытия экрана. */
    private final Button closeButton = new Button(88, 2, Text.BUTTON_CLOSE.getText(), this) {
        /** Обрабатывает левый клик. */
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.MAIN);
            PopUpMessage.show(Text.OPTIONS_SAVED.getText());
        }
    };
    /** Фоновое изображение. */
    final Image background = Image.cache.get(ImageType.GUI_BACKGROUND);
    /** Селектор сложности. */
    final DifficultySelector difficultySelector = Options.getDifficultySelector().linkView(this);
    /** Переключатель авто-покупки флажков. */
    final SwitchSelector autoBuyFlagsSelector = Options.getAutoBuyFlagsSelector().linkView(this);
    /** Переключатель режима на время. */
    final SwitchSelector timerEnabledSelector = Options.getTimerEnabledSelector().linkView(this);
    /** Переключатель показа сообщений. */
    final SwitchSelector displayMessageSelector = Options.getDisplayMessageSelector().linkView(this);
    /** Селектор темы. */
    final ThemeSelector themeSelector = Options.getThemeSelector().linkView(this);

    /** Создаёт экземпляр ViewOptions. */
    public ViewOptions(MinesweeperGame game) {
        super(game);
    }

    /** Обновляет состояние. */
    @Override
    public void update() {
        background.draw();
        final int leftPadding = 2;
        Printer.print(Text.OPTIONS_TITLE.getText(), Theme.LABEL.getColor(), Printer.CENTER, 2);

        Printer.print(Text.OPTIONS_DIFFICULTY.getText(), leftPadding, Options.getDifficultySelector().y - 1);
        difficultySelector.draw();

        Printer.print(Text.OPTIONS_BUY_FLAGS.getText(), leftPadding, Options.getAutoBuyFlagsSelector().y - 1);
        autoBuyFlagsSelector.draw();

        Printer.print(Text.OPTIONS_TIMER.getText(), leftPadding, Options.getTimerEnabledSelector().y - 1);
        timerEnabledSelector.draw();

        Printer.print(Text.OPTIONS_MESSAGES.getText(), leftPadding, Options.getDisplayMessageSelector().y - 1);
        displayMessageSelector.draw();

        Printer.print(Text.OPTIONS_THEME_PREFIX.getText() + Theme.getCurrentName(), leftPadding, Options.getThemeSelector().y);
        themeSelector.draw();

        closeButton.draw();
        super.update();
    }
}
