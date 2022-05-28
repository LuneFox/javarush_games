package com.javarush.games.snake.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.view.MenuSelector;
import com.javarush.games.snake.model.Orb;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Message;
import com.javarush.games.snake.view.Sign;
import com.javarush.games.snake.view.View;

public class OptionsMenuView extends View {
    private static OptionsMenuView instance;

    public static OptionsMenuView getInstance() {
        if (instance == null) instance = new OptionsMenuView();
        return instance;
    }

    @Override
    public void update() {
        drawBlackBackground();
        Message.print(-1, 7, "OPTIONS", Color.SKYBLUE);
        MenuSelector.setMenuEntries("MAP", "SYMBOLS", "ACCELERATION");
        MenuSelector.drawMenuEntriesWithPointer(2, 12);
        Message.print(17, 12, "STAGE " + (game.getStage() + 1), Color.WHITE);
        Message.print(17, 14, Sign.getUsedType().toString(), Color.WHITE);
        new Orb(23, 14, Element.WATER).draw(game);
        Message.print(17, 16, (game.isAccelerationEnabled) ? "ENABLED" : "DISABLED", Color.WHITE);
    }
}
