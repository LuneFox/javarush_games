package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.Strings;
import com.javarush.games.minesweeper.graphics.VisualElement;
import com.javarush.games.minesweeper.graphics.Button;
import com.javarush.games.minesweeper.graphics.Theme;

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

    public ViewMain(MinesweeperGame game) {
        this.game = game;
        this.screen = Screen.MAIN_MENU;
        Collections.addAll(QUOTES, Strings.QUOTES);
        lastQuoteDate = new Date();
        quote = QUOTES.get(0);
    }

    @Override
    public void display() {
        super.display();
        IMAGES_CACHE.get(VisualElement.WIN_MENU).draw();
        IMAGES_CACHE.get(VisualElement.PIC_LOGO).animateFloating(2.8, -1, 8);
        BUTTONS_CACHE.get(Button.ButtonID.OPTIONS).draw();
        BUTTONS_CACHE.get(Button.ButtonID.ABOUT).draw();
        BUTTONS_CACHE.get(Button.ButtonID.RECORDS).draw();
        game.print(Strings.VERSION, Theme.MAIN_MENU_VERSION.getColor(), 85, 0);
        printTopScore();

        Button startButton = BUTTONS_CACHE.get(Button.ButtonID.START);
        if (game.isStopped || game.isFirstMove) {
            printRandomQuote();
            if (!startButton.getText().equals("старт")) startButton.replaceText(36, "старт");
        } else {
            printResumeGame();
            if (!startButton.getText().equals("заново")) startButton.replaceText(36, "заново");
        }

        BUTTONS_CACHE.get(Button.ButtonID.START).draw();
    }

    private void printRandomQuote() {
        if (new Date().getTime() - lastQuoteDate.getTime() > 30000) {
            quote = QUOTES.get(game.getRandomNumber(QUOTES.size()));
            lastQuoteDate = new Date();
        }
        game.print(quote, Theme.MAIN_MENU_QUOTE_BACK.getColor(), 5, 44); // shadow
        game.print(quote, Theme.MAIN_MENU_QUOTE_FRONT.getColor(), 4, 44);
    }

    private void printResumeGame() {
        game.print(resume, Theme.MAIN_MENU_QUOTE_BACK.getColor(), 5, 44); // shadow
        game.print(resume, Theme.LABEL.getColor(), 4, 44);
    }

    private void printTopScore() {
        if (game.player.score.getTopScore() > 0) {
            game.print("счёт: " + game.player.score.getTopScore() + "\n" + game.player.getTitle(),
                    Color.LIGHTGOLDENRODYELLOW, 4, 65);
        }
    }
}
