package com.javarush.games.minesweeper.view.impl;

import com.javarush.games.minesweeper.Text;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.FloatingImage;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.View;

import java.util.Date;

/** Главное меню игры. */
public class ViewMain extends View {
    /** Цитаты главного меню. */
    private final String[] quotes = new String[]{
            Text.QUOTE_1.getText(), Text.QUOTE_2.getText(), Text.QUOTE_3.getText(),
            Text.QUOTE_4.getText(), Text.QUOTE_5.getText(), Text.QUOTE_6.getText(),
            Text.QUOTE_7.getText(), Text.QUOTE_8.getText(), Text.QUOTE_9.getText(),
            Text.QUOTE_10.getText()
    };
    /** Текущая цитата меню. */
    private String quote;
    /** Время последней смены цитаты. */
    private Date lastQuoteDate;

    /** Кнопка открытия настроек. */
    final Button optionsButton = new Button(61, 64, 36, 9, Text.BUTTON_OPTIONS.getText(), this) {
        /** Обрабатывает левый клик. */
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.OPTIONS);
        }
    };
    /** Кнопка «Об игре». */
    final Button aboutButton = new Button(61, 76, 36, 9, Text.BUTTON_ABOUT.getText(), this) {
        /** Обрабатывает левый клик. */
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.ABOUT);
        }
    };
    /** Кнопка «Рекорды». */
    final Button recordsButton = new Button(2, 88, Text.BUTTON_RECORDS.getText(), this) {
        /** Обрабатывает левый клик. */
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.RECORDS);
        }
    };
    /** Кнопка старта. */
    final Button startButton = new Button(61, 88, 36, 9, Text.BUTTON_START.getText(), this) {
        /** Обрабатывает левый клик. */
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            game.startNewGame();
        }
    };
    /** Логотип игры. */
    final FloatingImage logo = new FloatingImage(ImageType.PICTURE_MAIN_LOGO, this) {
        /** Обрабатывает левый клик. */
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            if (Options.getDeveloperModeCounter() < 9) {
                Options.setDeveloperModeCounter(Options.getDeveloperModeCounter() + 1);
                PopUpMessage.show(Text.MAIN_VERSION_PREFIX.getText() + MinesweeperGame.VERSION);
            } else {
                Options.setDeveloperModeEnabled(true);
                PopUpMessage.show(Text.MAIN_DEV_WELCOME.getText());
            }
        }
    };
    /** Фоновое изображение. */
    final Image background = Image.cache.get(ImageType.GUI_BACKGROUND);

    /** Создаёт экземпляр ViewMain. */
    public ViewMain(MinesweeperGame game) {
        super(game);
        lastQuoteDate = new Date();
        quote = quotes[0];
    }

    /** Обновляет состояние. */
    @Override
    public void update() {
        View.setGameOverShowDelay(0); // устраняет рассинхронизацию и блокировку управления

        background.draw();
        Printer.print(Options.isDeveloperModeEnabled() ? Text.MAIN_DEV_MODE.getText() : Text.MAIN_BRAND.getText(),
                Theme.MAIN_MENU_LABEL.getColor(), Image.CENTER, 2);
        logo.draw(2.8, Image.CENTER, 8);
        drawMenuButtons();
        printTextUnderLogo();
        printTopScore();
        super.update();
    }

    /** Рисует кнопки главного меню. */
    private void drawMenuButtons() {
        optionsButton.draw();
        aboutButton.draw();
        recordsButton.draw();
        replaceStartButtonText();
        startButton.draw();
    }

    /** Печатает текст под логотипом. */
    private void printTextUnderLogo() {
        if (game.isStopped() || game.isFirstMove()) {
            printRandomQuote();
        } else {
            printResumeGame();
        }
    }

    /** Обновляет текст кнопки старта. */
    private void replaceStartButtonText() {
        if (game.isStopped() || game.isFirstMove()) {
            if (!startButton.getLabelText().equals(Text.BUTTON_START.getText())) startButton.replaceText(36, Text.BUTTON_START.getText());
        } else {
            if (!startButton.getLabelText().equals(Text.BUTTON_RESTART.getText())) startButton.replaceText(36, Text.BUTTON_RESTART.getText());
        }
    }

    /** Печатает случайную цитату. */
    private void printRandomQuote() {
        if (new Date().getTime() - lastQuoteDate.getTime() > 30000) {
            quote = quotes[game.getRandomNumber(quotes.length)];
            lastQuoteDate = new Date();
        }
        Printer.print(quote, Theme.TEXT_SHADOW.getColor(), 5, 44); // тень
        Printer.print(quote, Theme.MAIN_MENU_QUOTE_FRONT.getColor(), 4, 44);
    }

    /** Печатает подсказку о продолжении игры. */
    private void printResumeGame() {
        String resume = Text.MAIN_RESUME.getText();
        Printer.print(resume, Theme.TEXT_SHADOW.getColor(), 5, 44); // тень
        Printer.print(resume, Theme.LABEL.getColor(), 4, 44);
    }

    /** Печатает лучший счёт. */
    private void printTopScore() {
        final long topScore = game.getPlayer().getScore().getTopScore();
        if (topScore > 0) {
            Printer.print(Text.SCORE_PREFIX.getText() + topScore + "\n" + game.getPlayer().getTitle(),
                    Options.isDeveloperModeEnabled() ? Color.RED : Color.LIGHTGOLDENRODYELLOW, 4, 65);
        }
    }
}
