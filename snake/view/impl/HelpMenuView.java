package com.javarush.games.snake.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.view.HelpPage;
import com.javarush.games.snake.view.Message;
import com.javarush.games.snake.view.View;

import java.util.ArrayList;
import java.util.Collections;

/** Экран помощи с листанием. */
public class HelpMenuView extends View {
    private static final int PAGINATION_Y = 30;

    private static HelpMenuView instance;

    private final ArrayList<HelpPage> helpPages = new ArrayList<>();
    private int currentPageIndex;

    /** Возвращает единственный экземпляр. */
    public static HelpMenuView getInstance() {
        if (instance == null) instance = new HelpMenuView();
        return instance;
    }

    /** Собирает страницы и рисует текущую. */
    @Override
    public void update() {
        // Страницы пересобираются при обновлении, чтобы учитывать язык и набор символов.
        helpPages.clear();
        Collections.addAll(helpPages,
                HelpPage.getGoals(),
                HelpPage.getSnakeAbilities(),
                HelpPage.getTypesOfTerrain(),
                HelpPage.getTips1(),
                HelpPage.getTips2());

        drawBlackBackground();
        helpPages.get(currentPageIndex).draw(game);

        final String pagination = Strings.HELP_PAGE.format(currentPageIndex + 1, helpPages.size());
        Message.print(-1, PAGINATION_Y, pagination, Color.WHITE);
    }

    /** Листает вперёд. */
    public void showNextPage() {
        if (currentPageIndex + 1 < helpPages.size()) {
            currentPageIndex++;
        }
    }

    /** Листает назад. */
    public void showPreviousPage() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
        }
    }
}
