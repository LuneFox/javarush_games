package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.Strings;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Button;
import com.javarush.games.minesweeper.graphics.Theme;
import com.javarush.games.minesweeper.graphics.ThemeElement;

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
        this.screenType = Screen.ScreenType.MAIN_MENU;
        Collections.addAll(QUOTES, Strings.QUOTES);
        lastQuoteDate = new Date();
        quote = QUOTES.get(0);
    }

    @Override
    public void display() {
        super.display();
        IMAGES.get(Bitmap.WIN_MENU).draw();
        IMAGES.get(Bitmap.PIC_LOGO).floatAnimation(2.8, -1, 8);
        BUTTONS.get(Button.ButtonID.OPTIONS).draw();
        BUTTONS.get(Button.ButtonID.ABOUT).draw();
        BUTTONS.get(Button.ButtonID.RECORDS).draw();
        game.print(Strings.VERSION, Theme.current.getColor(ThemeElement.MAIN_MENU_VERSION), 85, 0);
        printTopScore();
        if (game.isStopped || Button.startTimeOut > 0) {
            if (Button.startTimeOut > 0) Button.startTimeOut--;
            printRandomQuote();
            BUTTONS.get(Button.ButtonID.START).replaceText(36, "старт");
        } else {
            printResumeGame();
            BUTTONS.get(Button.ButtonID.START).replaceText(36, "заново");
            ;
        }
        BUTTONS.get(Button.ButtonID.START).draw();
    }

    private void printRandomQuote() {
        if (new Date().getTime() - lastQuoteDate.getTime() > 30000) {
            quote = QUOTES.get(game.getRandomNumber(QUOTES.size()));
            lastQuoteDate = new Date();
        }
        game.print(quote, Theme.current.getColor(ThemeElement.MAIN_MENU_QUOTE_BACK), 5, 44); // shadow
        game.print(quote, Theme.current.getColor(ThemeElement.MAIN_MENU_QUOTE_FRONT), 4, 44);
    }

    private void printResumeGame() {
        game.print(resume, Theme.current.getColor(ThemeElement.MAIN_MENU_QUOTE_BACK), 5, 44); // shadow
        game.print(resume, Theme.current.getColor(ThemeElement.LABEL), 4, 44);
    }

    private void printTopScore() {
        if (game.player.topScore > 0) {
            game.print("счёт: " + game.player.topScore + "\n" + game.player.topScoreTitle,
                    Color.LIGHTGOLDENRODYELLOW, 4, 65);
        }
    }
}
