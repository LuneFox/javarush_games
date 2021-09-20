package com.javarush.games.minesweeper.view;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.Screen.ScreenType;
import com.javarush.games.minesweeper.graphics.*;
import com.javarush.games.minesweeper.graphics.Button.ButtonID;

import java.util.HashMap;

import static com.javarush.games.minesweeper.Util.inside;

/**
 * Class for displaying various menus on the screen.
 */

public class View {
    protected MinesweeperGame game;
    public ScreenType screenType;
    public static ScreenType pendingScreenType;
    public static ViewMain main;
    public static ViewAbout about;
    public static ViewRecords records;
    public static ViewOptions options;
    public static ViewBoard board;
    public static ViewShop shop;
    public static ViewItemHelp itemHelp;
    public static ViewGameOver gameOver;
    public static ViewScore score;
    public static final HashMap<Bitmap, Image> IMAGES = new HashMap<>();
    public static final HashMap<ButtonID, Button> BUTTONS = new HashMap<>();

    public View() {

    }

    public View(MinesweeperGame game) {
        this.game = game;
        preloadResources();
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
            if (Button.pressedTime == 5) pendingScreenType = this.screenType;
            Button.pressedTime--;
            if (Button.pressedTime <= 0) Screen.setType(pendingScreenType);
            return;
        }
        Screen.setType(this.screenType);
    }

    // Load constant stuff into static hashmaps once, no need to generate all these images again

    private void preloadSprite(Bitmap bitmap) {
        IMAGES.put(bitmap, new Sprite(bitmap, game, 0, 0));
    }

    private void preloadPicture(Bitmap bitmap) {
        IMAGES.put(bitmap, new Picture(bitmap, game, 0, 0));
    }

    public final void preloadResources() { // load images once at launch and just re-use them all the time
        Bitmap.getBitmapsByPrefixes("BUTTON_", "MENU_", "PIC_", "SHOP_", "WIN_").forEach(this::preloadPicture);
        Bitmap.getBitmapsByPrefixes("SPR_").forEach(this::preloadSprite);
        for (ButtonID b : ButtonID.values()) {
            BUTTONS.put(b, new Button(game, b.posX, b.posY, b.width, b.height, b.label));
        }
    }

    public final void reload() {  // generates and loads all images again, used to change color theme
        game.view.preloadResources();
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












































