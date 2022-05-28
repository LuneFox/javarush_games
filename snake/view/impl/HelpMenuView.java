package com.javarush.games.snake.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.view.HelpPage;
import com.javarush.games.snake.view.Message;
import com.javarush.games.snake.view.View;

import java.util.ArrayList;

public class HelpMenuView extends View {
    private static HelpMenuView instance;
    private final ArrayList<HelpPage> helpPages = new ArrayList<>();
    int currentHelpPage;

    public static HelpMenuView getInstance() {
        if (instance == null) instance = new HelpMenuView();
        return instance;
    }

    @Override
    public void update() {
        helpPages.clear();
        helpPages.add(HelpPage.getGoals());
        helpPages.add(HelpPage.getSnakeAbilities());
        helpPages.add(HelpPage.getTypesOfTerrain());
        helpPages.add(HelpPage.getTips1());
        helpPages.add(HelpPage.getTips2());

        drawBlackBackground();
        helpPages.get(currentHelpPage).draw(game);

        String pagination = "← PAGE " + (currentHelpPage + 1) + "/" + helpPages.size() + " →";
        Message.print(-1, 30, pagination, Color.WHITE);
    }

    public void nextHelpPage() {
        if (currentHelpPage + 1 < helpPages.size()) {
            currentHelpPage++;
        }
    }

    public void previousHelpPage() {
        if (currentHelpPage > 0) {
            currentHelpPage--;
        }
    }
}
