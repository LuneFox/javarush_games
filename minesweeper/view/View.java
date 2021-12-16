package com.javarush.games.minesweeper.view;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.graphics.*;
import com.javarush.games.minesweeper.graphics.Button.ButtonID;

import java.util.HashMap;

import static com.javarush.games.minesweeper.Util.inside;

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
    public static final HashMap<VisualElement, Image> IMAGES = new HashMap<>();
    public static final HashMap<ButtonID, Button> BUTTONS = new HashMap<>();

    public View() {
        loadStaticImages();
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

    private void loadImage(VisualElement visualElement) {
        IMAGES.put(visualElement, new Image(visualElement, 0, 0));
    }

    public final void loadStaticImages() { // load images once at launch and just re-use them all the time
        VisualElement.getBitmapsByPrefixes("BUTTON_", "MENU_", "PIC_", "SHOP_", "WIN_", "SPR_").forEach(this::loadImage);
        for (ButtonID b : ButtonID.values()) {
            BUTTONS.put(b, new Button(game, b.posX, b.posY, b.width, b.height, b.label));
        }
    }

    public final void reload() {  // generates and loads all images again, used to change color theme
        game.view.loadStaticImages();
        game.recolorAllCells();
    }

    public static class Area {
        private final int[] coords;

        public Area(int[] coordinates) {
            this.coords = coordinates;
        }

        public boolean covers(int x, int y) {
            return inside(x, coords[0], coords[1]) && inside(y, coords[2], coords[3]);
        }
    }

}












































