package com.javarush.games.snake.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.view.HelpPage;
import com.javarush.games.snake.view.Message;
import com.javarush.games.snake.view.View;

import java.util.ArrayList;
import java.util.Collections;

public class HelpMenuView extends View {
    private static final int PAGINATION_Y = 30;

    private static HelpMenuView instance;

    private final ArrayList<HelpPage> helpPages = new ArrayList<>();
    private int currentPageIndex;

    public static HelpMenuView getInstance() {
        if (instance == null) instance = new HelpMenuView();
        return instance;
    }

    @Override
    public void update() {
        // Pages are rebuilt on every update, so they follow the current language and symbol set.
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

    public void showNextPage() {
        if (currentPageIndex + 1 < helpPages.size()) {
            currentPageIndex++;
        }
    }

    public void showPreviousPage() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
        }
    }
}
