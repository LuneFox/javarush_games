package com.javarush.games.snake.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.view.MenuSelector;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.view.Message;
import com.javarush.games.snake.view.View;

public class MainMenuView extends View {
    private static MainMenuView instance;

    public static MainMenuView getInstance() {
        if (instance == null) instance = new MainMenuView();
        return instance;
    }

    @Override
    public void update() {
        drawBlackBackground();
        game.loadStage();
        Message.print(-1, 5, "✡                     ✡", Color.YELLOW);
        Message.print(-1, 5, "✡                   ✡", Color.CYAN);
        Message.print(-1, 5, "✡                 ✡", Color.LAWNGREEN);
        Message.print(-1, 5, "✡               ✡", Color.RED);
        Message.print(-1, 5, "ALCHEMY SNAKE", Color.FUCHSIA);
        Message.print(-1, 7, "MASTER OF ELEMENTS", Color.PINK);
        Message.print(-1, 30, "VER " + Strings.VERSION, Color.BLUE);

        MenuSelector.setMenuEntries("START", "OPTIONS", "CONTROLS", "HELP");
        // MenuSelector.setMenuEntries("START", "OPTIONS", "CONTROLS", "HELP", "EDIT");

        Stage stage = game.getStage();
        Message.print(16, 12, "(" + stage.getName() + ")", stage.isClear() ? Color.LAWNGREEN : Color.LIGHTSKYBLUE);

        MenuSelector.drawMenuEntriesWithPointer(10, 12);
    }
}
