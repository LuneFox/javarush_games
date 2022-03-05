package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.model.Strings;
import com.javarush.games.minesweeper.view.graphics.*;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

/**
 * Title (main) screen of the game.
 */

public final class ViewMain extends View {
    private static String quote;
    private final static String resume = "Игра приостановлена\nESC - продолжить";
    private static Date lastQuoteDate;
    private final static LinkedList<String> QUOTES = new LinkedList<>();

    public ViewMain() {
        this.screen = Screen.MAIN;
        Collections.addAll(QUOTES, Strings.QUOTES);
        lastQuoteDate = new Date();
        quote = QUOTES.get(0);
    }

    @Override
    public void update() {
        super.update();
        Cache.get(VisualElement.WIN_MENU).draw();
        Printer.print("JavaRush", Theme.MAIN_MENU_VERSION.getColor(), Image.CENTER, 2);
        ((FloatingImage) Cache.get(VisualElement.FLO_LOGO)).draw(2.8, Image.CENTER, 8);
        Cache.get(Button.ButtonID.MAIN_MENU_OPTIONS).draw();
        Cache.get(Button.ButtonID.MAIN_MENU_ABOUT).draw();
        Cache.get(Button.ButtonID.MAIN_MENU_RECORDS).draw();
        Printer.print(Strings.VERSION, Theme.MAIN_MENU_VERSION.getColor(), 85, 0);
        printTopScore();

        Button startButton = Cache.get(Button.ButtonID.MAIN_MENU_START);
        if (game.isStopped || game.isFirstMove) {
            printRandomQuote();
            if (!startButton.getText().equals("старт")) startButton.replaceText(36, "старт");
        } else {
            printResumeGame();
            if (!startButton.getText().equals("заново")) startButton.replaceText(36, "заново");
        }

        Cache.get(Button.ButtonID.MAIN_MENU_START).draw();
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
        Printer.print(resume, Theme.MAIN_MENU_QUOTE_BACK.getColor(), 5, 44); // shadow
        Printer.print(resume, Theme.LABEL.getColor(), 4, 44);
    }

    private void printTopScore() {
        if (game.player.score.getTopScore() > 0) {
            Printer.print("счёт: " + game.player.score.getTopScore() + "\n" + game.player.getTitle(),
                    Color.LIGHTGOLDENRODYELLOW, 4, 65);
        }
    }
}
