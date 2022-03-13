package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.*;
import com.javarush.games.minesweeper.gui.image.*;
import com.javarush.games.minesweeper.gui.interactive.*;
import com.javarush.games.minesweeper.model.*;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

public class ViewMain extends View {
    private String quote;
    private Date lastQuoteDate;
    private final LinkedList<String> QUOTES = new LinkedList<>();

    Button optionsButton = new Button(61, 64, 36, 9, "опции", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.OPTIONS);
        }
    };
    Button aboutButton = new Button(61, 76, 36, 9, "об игре", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.ABOUT);
        }
    };
    Button recordsButton = new Button(2, 88, 0, 0, "рекорды", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.RECORDS);
        }
    };
    Button startButton = new Button(61, 88, 36, 9, "старт", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            game.startNewGame();
        }
    };
    FloatingImage logo = new FloatingImage(ImageType.FLO_LOGO, this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            if (Options.developerCounter < 9) {
                Options.developerCounter++;
                PopUpMessage.show("Версия: " + Strings.VERSION);
            } else {
                Options.developerMode = true;
                PopUpMessage.show("Вы разработчик!");
            }
        }
    };
    Image background = Image.cache.get(ImageType.WIN_MENU);

    public ViewMain(Phase phase) {
        super(phase);
        Collections.addAll(QUOTES, Strings.QUOTES);
        lastQuoteDate = new Date();
        quote = QUOTES.get(0);
    }

    @Override
    public void update() {
        background.draw();
        Printer.print(Options.developerMode ? "Developer Mode" : "JavaRush", Theme.MAIN_MENU_VERSION.getColor(), Image.CENTER, 2);
        logo.draw(2.8, Image.CENTER, 8);

        if (game.isStopped || game.isFirstMove) {
            printRandomQuote();
            if (!startButton.getText().equals("старт")) startButton.replaceText(36, "старт");
        } else {
            printResumeGame();
            if (!startButton.getText().equals("заново")) startButton.replaceText(36, "заново");
        }

        optionsButton.draw();
        aboutButton.draw();
        recordsButton.draw();
        startButton.draw();
        printTopScore();
        super.update();
    }

    private void printRandomQuote() {
        if (new Date().getTime() - lastQuoteDate.getTime() > 30000) {
            quote = QUOTES.get(game.getRandomNumber(QUOTES.size()));
            lastQuoteDate = new Date();
        }
        Printer.print(quote, Theme.MAIN_MENU_QUOTE_BACK.getColor(), 5, 44); // shadow
        Printer.print(quote, Theme.MAIN_MENU_QUOTE_FRONT.getColor(), 4, 44);
    }

    private void printResumeGame() {
        String resume = "Игра приостановлена\nESC - продолжить";
        Printer.print(resume, Theme.MAIN_MENU_QUOTE_BACK.getColor(), 5, 44); // shadow
        Printer.print(resume, Theme.LABEL.getColor(), 4, 44);
    }

    private void printTopScore() {
        if (game.player.score.getTopScore() > 0) {
            Printer.print("счёт: " + game.player.score.getTopScore() + "\n" + game.player.getTitle(),
                    Options.developerMode ? Color.RED : Color.LIGHTGOLDENRODYELLOW, 4, 65);
        }
    }
}
