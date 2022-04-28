package com.javarush.games.minesweeper.view.impl;

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

public class ViewMain extends View {
    private final String[] quotes = new String[]{
            "Самая взрывная\nголоволомка!",
            "Осторожно, игра\nзаминирована!",
            "Просто бомба!",
            "Здесь не бывает\nкислых мин!",
            "Не собери их все!",
            "Главное - не бомбить",
            "Не приводи детей\nна работу!",
            "В лопате нет\nничего смешного!",
            "Втыкая флаг,\nне задень мину!",
            "Какой идиот\nзакопал цифры?!"
    };
    private String quote;
    private Date lastQuoteDate;

    final Button optionsButton = new Button(61, 64, 36, 9, "опции", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.OPTIONS);
        }
    };
    final Button aboutButton = new Button(61, 76, 36, 9, "об игре", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.ABOUT);
        }
    };
    final Button recordsButton = new Button(2, 88, "рекорды", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.RECORDS);
        }
    };
    final Button startButton = new Button(61, 88, 36, 9, "старт", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            game.startNewGame();
        }
    };
    final FloatingImage logo = new FloatingImage(ImageType.PICTURE_MAIN_LOGO, this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            if (Options.developerModeCounter < 9) {
                Options.developerModeCounter++;
                PopUpMessage.show("Версия: " + MinesweeperGame.VERSION);
            } else {
                Options.isDeveloperModeEnabled = true;
                PopUpMessage.show("Вы разработчик!");
            }
        }
    };
    final Image background = Image.cache.get(ImageType.GUI_BACKGROUND);

    public ViewMain(MinesweeperGame game) {
        super(game);
        lastQuoteDate = new Date();
        quote = quotes[0];
    }

    @Override
    public void update() {
        background.draw();
        Printer.print(Options.isDeveloperModeEnabled ? "Developer Mode" : "JavaRush",
                Theme.MAIN_MENU_LABEL.getColor(), Image.CENTER, 2);
        logo.draw(2.8, Image.CENTER, 8);
        drawMenuButtons();
        printTextUnderLogo();
        printTopScore();
        super.update();
    }

    private void drawMenuButtons() {
        optionsButton.draw();
        aboutButton.draw();
        recordsButton.draw();
        replaceStartButtonText();
        startButton.draw();
    }

    private void printTextUnderLogo() {
        if (game.isStopped() || game.isFirstMove()) {
            printRandomQuote();
        } else {
            printResumeGame();
        }
    }

    private void replaceStartButtonText() {
        if (game.isStopped() || game.isFirstMove()) {
            if (!startButton.getLabelText().equals("старт")) startButton.replaceText(36, "старт");
        } else {
            if (!startButton.getLabelText().equals("заново")) startButton.replaceText(36, "заново");
        }
    }

    private void printRandomQuote() {
        if (new Date().getTime() - lastQuoteDate.getTime() > 30000) {
            quote = quotes[game.getRandomNumber(quotes.length)];
            lastQuoteDate = new Date();
        }
        Printer.print(quote, Theme.TEXT_SHADOW.getColor(), 5, 44); // shadow
        Printer.print(quote, Theme.MAIN_MENU_QUOTE_FRONT.getColor(), 4, 44);
    }

    private void printResumeGame() {
        String resume = "Игра приостановлена\nESC - продолжить";
        Printer.print(resume, Theme.TEXT_SHADOW.getColor(), 5, 44); // shadow
        Printer.print(resume, Theme.LABEL.getColor(), 4, 44);
    }

    private void printTopScore() {
        final int topScore = game.getPlayer().getScore().getTopScore();
        if (topScore > 0) {
            Printer.print("счёт: " + topScore + "\n" + game.getPlayer().getTitle(),
                    Options.isDeveloperModeEnabled ? Color.RED : Color.LIGHTGOLDENRODYELLOW, 4, 65);
        }
    }
}
