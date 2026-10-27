package com.javarush.games.snake.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.view.MenuSelector;
import com.javarush.games.snake.view.Message;
import com.javarush.games.snake.view.View;

/** Главное меню. */
public class MainMenuView extends View {
    private static final int TITLE_Y = 5;
    private static final int SUBTITLE_Y = 7;
    private static final int VERSION_Y = 30;
    private static final int MENU_X = 10;
    private static final int MENU_Y = 12;
    private static final int STAGE_NAME_X = 18;

    private static final String[] TITLE_FRAME = {
            "✡                     ✡",
            "✡                   ✡",
            "✡                 ✡",
            "✡               ✡"
    };
    private static final Color[] TITLE_FRAME_COLORS = {
            Color.YELLOW,
            Color.CYAN,
            Color.LAWNGREEN,
            Color.RED
    };

    private static MainMenuView instance;

    /** Возвращает единственный экземпляр. */
    public static MainMenuView getInstance() {
        if (instance == null) instance = new MainMenuView();
        return instance;
    }

    /** Рисует заголовок, пункты и название уровня. */
    @Override
    public void update() {
        drawBlackBackground();
        game.loadStage();
        drawTitleFrame();

        Message.print(-1, TITLE_Y, Strings.MAIN_TITLE.get(), Color.FUCHSIA);
        Message.print(-1, SUBTITLE_Y, Strings.MAIN_SUBTITLE.get(), Color.PINK);
        Message.print(-1, VERSION_Y, Strings.MAIN_VERSION.get() + Strings.VERSION.get(), Color.BLUE);

        MenuSelector.setMenuEntries(Strings.MENU_START, Strings.MENU_OPTIONS, Strings.MENU_CONTROLS, Strings.MENU_HELP);
        // MenuSelector.setMenuEntries(Strings.MENU_START, Strings.MENU_OPTIONS, Strings.MENU_CONTROLS, Strings.MENU_HELP, Strings.MENU_EDIT);

        final Stage stage = game.getStage();
        Message.print(STAGE_NAME_X, MENU_Y, "(" + stage.getName() + ")",
                stage.isClear() ? Color.LAWNGREEN : Color.LIGHTSKYBLUE);

        MenuSelector.drawMenuEntriesWithPointer(MENU_X, MENU_Y);
    }

    /** Рисует рамку заголовка. */
    private void drawTitleFrame() {
        for (int i = 0; i < TITLE_FRAME.length; i++) {
            Message.print(-1, TITLE_Y, TITLE_FRAME[i], TITLE_FRAME_COLORS[i]);
        }
    }
}
