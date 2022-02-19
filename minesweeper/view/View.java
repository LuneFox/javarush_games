package com.javarush.games.minesweeper.view;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.utility.Util;
import com.javarush.games.minesweeper.view.graphics.*;

import java.util.EnumMap;

/**
 * Class for displaying various menus on the screen.
 */

public class View {
    protected MinesweeperGame game = MinesweeperGame.getInstance();
    public Screen screen;
    public static Screen pendingScreen;
    public static ViewMain main;
    public static ViewAbout about;
    public static ViewRecords records;
    public static ViewOptions options;
    public static ViewBoard board;
    public static ViewShop shop;
    public static ViewItemHelp itemHelp;
    public static ViewGameOver gameOver;
    public static ViewScore score;
    public static final EnumMap<VisualElement, Image> IMAGES_CACHE = new EnumMap<>(VisualElement.class);
    public static final EnumMap<Button.ButtonID, Button> BUTTONS_CACHE = new EnumMap<>(Button.ButtonID.class);

    public View() {
        cacheStaticElements();
    }

    public void createSubViews() {
        main = new ViewMain(game);
        about = new ViewAbout(game);
        records = new ViewRecords(game);
        options = new ViewOptions(game);
        board = new ViewBoard(game);
        shop = new ViewShop(game);
        itemHelp = new ViewItemHelp(game);
        gameOver = new ViewGameOver(game);
        score = new ViewScore(game);
    }

    public void display() {
        if (Button.pressedTime > 0) {
            if (Button.pressedTime == Button.PRESS_DURATION) pendingScreen = this.screen;
            Button.pressedTime--;
            if (Button.pressedTime <= 0) Screen.set(pendingScreen);
            return;
        }
        Screen.set(this.screen);
    }

    // Load constant stuff into static hashmaps once, no need to generate all these images again

    private void cacheImage(VisualElement visualElement) {
        IMAGES_CACHE.put(visualElement, new Image(visualElement, 0, 0));
    }

    public final void cacheStaticElements() { // load images once at launch and just re-use them all the time
        VisualElement.getElementsByPrefixes("MENU_", "PIC_", "SHOP_", "WIN_", "SPR_").forEach(this::cacheImage);
        for (Button.ButtonID b : Button.ButtonID.values()) {
            BUTTONS_CACHE.put(b, new Button(game, b.posX, b.posY, b.width, b.height, b.label));
        }
    }

    public final void rebuildCache() {  // generates and loads all images again, used to change color theme
        cacheStaticElements();
        game.recolorAllCells();
    }

    public static class Area {
        private final int[] coords;

        public Area(int left, int right, int top, int bottom) {
            this.coords = new int[]{left, right, top, bottom};
        }

        public boolean covers(int x, int y) {
            return Util.inside(x, coords[0], coords[1]) && Util.inside(y, coords[2], coords[3]);
        }
    }

}












































