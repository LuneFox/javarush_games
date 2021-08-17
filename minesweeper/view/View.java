package com.javarush.games.minesweeper.view;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.Screen.ScreenType;
import com.javarush.games.minesweeper.graphics.*;
import com.javarush.games.minesweeper.graphics.Button.ButtonID;

import java.util.Arrays;
import java.util.HashMap;

import static com.javarush.games.minesweeper.Util.inside;

/**
 * Class for displaying various menus on the screen.
 */

public class View {
    protected MinesweeperGame game;
    public ScreenType screenType;
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
        loadImages();
        loadButtons();
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
        Screen.setType(this.screenType);
    }

    private void loadSprite(Bitmap bitmap) {
        IMAGES.put(bitmap, new Sprite(bitmap, game, 0, 0));
    }

    private void loadSprite(Bitmap... bitmaps) {
        Arrays.asList(bitmaps).forEach(this::loadSprite);
    }

    private void loadPicture(Bitmap bitmap) {
        IMAGES.put(bitmap, new Picture(bitmap, game, 0, 0));
    }

    private void loadPicture(Bitmap... bitmaps) {
        Arrays.asList(bitmaps).forEach(this::loadPicture);
    }

    public final void loadImages() { // load images once at launch and just re-use them all the time
        loadSprite(
                Bitmap.BOARD_FLAG,
                Bitmap.BOARD_MINE);
        loadPicture(
                Bitmap.BOARD_ACTIVE_FRAME,
                Bitmap.BOARD_COIN,
                Bitmap.BUTTON_CLOSE,
                Bitmap.BUTTON_OK,
                Bitmap.CUP,
                Bitmap.DICE_1,
                Bitmap.DICE_2,
                Bitmap.DICE_3,
                Bitmap.DICE_4,
                Bitmap.DICE_5,
                Bitmap.DICE_6,
                Bitmap.FACE_HAPPY,
                Bitmap.FACE_SAD,
                Bitmap.ITEM_BOMB,
                Bitmap.ITEM_DICE,
                Bitmap.ITEM_FLAG,
                Bitmap.ITEM_FRAME,
                Bitmap.ITEM_FRAME_PRESSED,
                Bitmap.ITEM_SCANNER,
                Bitmap.ITEM_SHIELD,
                Bitmap.ITEM_SHOVEL,
                Bitmap.MENU_ARROW,
                Bitmap.MENU_DIFFICULTY_BAR,
                Bitmap.MENU_SWITCH,
                Bitmap.MENU_SWITCH_RAIL,
                Bitmap.PICTURE_LOGO,
                Bitmap.THEME_PALETTE,
                Bitmap.WINDOW_GAME_OVER,
                Bitmap.WINDOW_MENU,
                Bitmap.WINDOW_SHOP,
                Bitmap.WINDOW_SHOP_HEADER_FOOTER,
                Bitmap.WINDOW_VICTORY
        );
    }

    public final void loadButtons() {
        BUTTONS.put(ButtonID.OPTIONS, new Button(game, 61, 64, 36, 9, "опции"));
        BUTTONS.put(ButtonID.ABOUT, new Button(game, 61, 76, 36, 9, "об игре"));
        BUTTONS.put(ButtonID.START, new Button(game, 61, 88, 36, 9, "старт"));
        BUTTONS.put(ButtonID.NEW_GAME, new Button(game, 61, 88, 36, 9, "заново"));
        BUTTONS.put(ButtonID.RECORDS, new Button(game, 2, 88, "рекорды"));
        BUTTONS.put(ButtonID.BACK, new Button(game, 61, 88, 36, 9, "назад"));
        BUTTONS.put(ButtonID.FORWARD, new Button(game, 3, 88, 36, 9, "далее"));
        BUTTONS.put(ButtonID.CONFIRM, new Button(game, 61, 88, 36, 9, "ясно"));
        BUTTONS.put(ButtonID.AGAIN, new Button(game, 57, 69, "снова"));
        BUTTONS.put(ButtonID.RETURN, new Button(game, 15, 69, "меню"));
        BUTTONS.put(ButtonID.CLOSE, new Button(game, 73, 35, "x"));
    }

    public final void reload() {
        game.view.loadImages();
        game.view.loadButtons();
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












































