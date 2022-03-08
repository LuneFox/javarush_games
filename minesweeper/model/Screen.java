package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.options.PageSelector;
import com.javarush.games.minesweeper.view.*;
import com.javarush.games.minesweeper.view.graphics.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public enum Screen {
    MAIN, GAME_OVER, SHOP, BOARD, OPTIONS, ABOUT, ITEM_HELP, SCORE, RECORDS;

    private static final MinesweeperGame game = MinesweeperGame.getInstance();
    private static final List<Screen> screens = new LinkedList<>(Arrays.asList(Screen.values()));
    public static final List<View> views = new ArrayList<>();
    public static final ViewAbout about = new ViewAbout();
    public static final ViewBoard board = new ViewBoard();
    public static final ViewGameOver gameOver = new ViewGameOver();
    public static final ViewItemHelp itemHelp = new ViewItemHelp();
    public static final ViewMain main = new ViewMain();
    public static final ViewOptions options = new ViewOptions();
    public static final ViewRecords records = new ViewRecords();
    public static final ViewScore score = new ViewScore();
    public static final ViewShop shop = new ViewShop();
    private static View pendingView;
    private static View currentView = main;


    // Screen at index 0 is considered active
    public static void setActive(Screen screen) {
        // Put on "top"
        screens.remove(screen);
        screens.add(0, screen);
        // Pending view is a view linked to given screen
        views.stream()
                .filter(view -> view.screen == screen)
                .forEach(view -> pendingView = view);
    }

    public static void updateView() {
        // Give time for buttons to animate before changing views
        if (Button.pressedTime <= Button.POST_PRESS_DELAY) {
            if (currentView != pendingView) {
                onViewChange();
            }
            currentView = pendingView;
        } else {
            Button.pressedTime--;
        }
        currentView.update();
    }

    private static void onViewChange() {
        // Things that happen right before the pending view is applied
        game.display.setInterlaceEnabled(false);
        PageSelector.allSelectors.forEach(PageSelector::reset);
        if (game.shop.header != null) {
            game.shop.header.setDisplayMoney(game.player.inventory.money);
        }
    }

    public static Screen getActive() {
        return screens.get(0);
    }

    public static boolean isActive(Screen screen) {
        return (screens.get(0) == screen);
    }
}
