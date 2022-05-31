package com.javarush.games.snake.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Options;
import com.javarush.games.snake.model.map.StageManager;
import com.javarush.games.snake.view.MenuSelector;
import com.javarush.games.snake.model.orbs.Orb;
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
        Message.print(17, 12, StageManager.getCurrentStage().getName(), Color.WHITE);
        Message.print(17, 14, Sign.getUsedType().toString(), Color.WHITE);
        Orb.create(23, 14, Element.WATER).draw(game);
        Message.print(17, 16, (Options.isAccelerationEnabled()) ? "ENABLED" : "DISABLED", Color.WHITE);
    }
}
