package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.Screen.ScreenType;
import com.javarush.games.minesweeper.graphics.*;
import com.javarush.games.minesweeper.graphics.Button.ButtonID;

import java.util.*;

/**
 * Class for displaying various menus on the screen.
 */

class Menu {
    final private MinesweeperGame GAME;
    final private Text TEXT_WRITER;
    final static HashMap<Bitmap, Image> IMAGES = new HashMap<>();
    final static HashMap<ButtonID, Button> BUTTONS = new HashMap<>();
    final private static LinkedList<String> QUOTES = new LinkedList<>();
    final static LinkedList<String> DIFFICULTY_NAMES = new LinkedList<>();
    private static String quote;
    private static Date lastQuoteDate;
    int gameOverDisplayDelay;

    static {
        Collections.addAll(DIFFICULTY_NAMES, Strings.DIFFICULTY_NAMES);
        Collections.addAll(QUOTES, Strings.QUOTES);
        lastQuoteDate = new Date();
        quote = QUOTES.get(0);
    }

    Menu(MinesweeperGame game) {
        this.GAME = game;
        this.TEXT_WRITER = GAME.getTextWriter();
        this.gameOverDisplayDelay = 0;
    }


    // MAIN

    void displayMain() {
        Screen.set(ScreenType.MAIN_MENU);
        IMAGES.get(Bitmap.WINDOW_MENU).draw();
        IMAGES.get(Bitmap.PICTURE_LOGO).animateFloating(2.8);
        BUTTONS.get(ButtonID.OPTIONS).draw();
        BUTTONS.get(ButtonID.ABOUT).draw();
        BUTTONS.get(ButtonID.START).draw();
        BUTTONS.get(ButtonID.RECORDS).draw();
        TEXT_WRITER.write(Strings.VERSION, Color.DARKRED, 85, 0, false);
        printTopScore();
        printRandomQuote();
    }

    private void printRandomQuote() {
        if (new Date().getTime() - lastQuoteDate.getTime() > 30000) {
            quote = QUOTES.get(GAME.getRandomNumber(QUOTES.size()));
            lastQuoteDate = new Date();
        }
        TEXT_WRITER.write(quote, Color.DARKRED, 5, 44, false); // shadow
        TEXT_WRITER.write(quote, Color.SALMON, 4, 44, false);
    }

    private void printTopScore() {
        if (GAME.topScore > 0) {
            TEXT_WRITER.write("счёт: " + GAME.topScore + "\n" + GAME.topScoreTitle,
                    Color.LIGHTGOLDENRODYELLOW, 4, 65, false);
        }
    }


    // ABOUT

    final void displayAbout() {
        Screen.set(ScreenType.ABOUT);
        IMAGES.get(Bitmap.WINDOW_MENU).draw();
        TEXT_WRITER.write(Strings.ABOUT[0], Color.YELLOW, 24, 2, false);
        TEXT_WRITER.write(Strings.ABOUT[1], Color.WHITE, 3, 13, false);
        BUTTONS.get(ButtonID.BACK).draw();
    }


    // RECORDS

    final void displayRecords() {
        Screen.set(ScreenType.RECORDS);
        IMAGES.get(Bitmap.WINDOW_MENU).draw();
        TEXT_WRITER.write(Strings.RECORDS[0], Color.YELLOW, 17, 2, false);
        BUTTONS.get(ButtonID.BACK).draw();
        drawPrizeCups();
        drawRecordEntries();
    }

    final void drawPrizeCups() {
        Image prizeCup = IMAGES.get(Bitmap.CUP);
        Color[] colors;
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    colors = new Color[]{Color.GOLD, Color.WHITE};
                    break;
                case 1:
                    colors = new Color[]{Color.SILVER, Color.WHITE};
                    break;
                case 2:
                    colors = new Color[]{Color.DARKGOLDENROD, Color.PALEGOLDENROD};
                    break;
                default:
                    colors = new Color[]{Color.BLACK, Color.WHITE};
            }
            prizeCup.replaceColor(colors[0], 1);
            prizeCup.replaceColor(colors[1], 2);
            prizeCup.setPosition(2, 20 + (20 * i));
            prizeCup.draw();
        }
    }

    final void drawRecordEntries() {
        Color[] colors = new Color[]{Color.WHITE, Color.GOLD, Color.SILVER, Color.PALEGOLDENROD};
        TEXT_WRITER.write(Strings.RECORDS[1], colors[1], 18, 19, false);
        TEXT_WRITER.write(Strings.RECORDS[2], colors[0], 94, 28, true);
        TEXT_WRITER.write(Strings.RECORDS[3], colors[2], 18, 39, false);
        TEXT_WRITER.write(Strings.RECORDS[4], colors[0], 94, 48, true);
        TEXT_WRITER.write(Strings.RECORDS[5], colors[3], 18, 59, false);
        TEXT_WRITER.write(Strings.RECORDS[6], colors[0], 94, 68, true);
    }


    // OPTIONS

    final void displayOptions() {
        Screen.set(ScreenType.OPTIONS);

        IMAGES.get(Bitmap.WINDOW_MENU).draw();
        TEXT_WRITER.write("настройки", Color.YELLOW, 28, 2, false);

        IMAGES.get(Bitmap.MENU_ARROW).setPosition(93, 21);
        IMAGES.get(Bitmap.MENU_ARROW).draw();
        IMAGES.get(Bitmap.MENU_ARROW).setPosition(49, 21);
        IMAGES.get(Bitmap.MENU_ARROW).draw(true);
        displayDifficultyBar();
        TEXT_WRITER.write("сложность", Color.WHITE, 2, 20, false);
        TEXT_WRITER.write(DIFFICULTY_NAMES.get(GAME.difficultyInOptionsScreen / 5 - 1),
                Color.SALMON, 93, 30, true);
        TEXT_WRITER.write("покупка\nфлажков", Color.WHITE, 2, 50, false);
        if (GAME.allowAutoBuyFlags) {
            IMAGES.get(Bitmap.MENU_SWITCH).replaceColor(Color.GREEN, 1);
            IMAGES.get(Bitmap.MENU_SWITCH).setPosition(88, 50);
            TEXT_WRITER.write("авто", Color.SALMON, 93, 60, true);
        } else {
            IMAGES.get(Bitmap.MENU_SWITCH).replaceColor(Color.RED, 1);
            IMAGES.get(Bitmap.MENU_SWITCH).setPosition(80, 50);
            TEXT_WRITER.write("вручную", Color.SALMON, 91, 60, true);

        }
        IMAGES.get(Bitmap.MENU_SWITCH_RAIL).draw();
        IMAGES.get(Bitmap.MENU_SWITCH).draw();
        BUTTONS.get(ButtonID.BACK).draw();
    }

    private void displayDifficultyBar() {
        LinkedList<Picture> difficulty = new LinkedList<>();
        for (int i = 0; i < GAME.difficultyInOptionsScreen / 5; i++) {
            Picture bar = new Picture(Bitmap.MENU_DIFFICULTY_BAR, GAME, (i * 4) + 56, 21);
            if (i > 1) {
                bar.replaceColor(Color.YELLOW, 1);
            }
            if (i > 3) {
                bar.replaceColor(Color.ORANGE, 1);
            }
            if (i > 5) {
                bar.replaceColor(Color.RED, 1);
            }
            difficulty.add(bar);
        }
        for (Picture p : difficulty) {
            p.draw();
        }
    }

    final void changeDifficulty(boolean harder) {
        if (harder) {
            if (GAME.difficultyInOptionsScreen < 45) {
                GAME.difficultyInOptionsScreen += 5;
            }
        } else {
            if (GAME.difficultyInOptionsScreen > 5) {
                GAME.difficultyInOptionsScreen -= 5;
            }
        }
        displayOptions();
    }

    final void switchAutoBuyFlags() {
        GAME.allowAutoBuyFlags = !GAME.allowAutoBuyFlags;
        displayOptions();
    }


    // GAME BOARD

    final void displayGameBoard() {
        Screen.set(ScreenType.GAME_BOARD);
        GAME.redrawAllCells();
        if (GAME.getAllShopItems().get(1).isActivated()) {
            IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).replaceColor(Color.BLUE, 3);
            IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).draw();
        } else if (GAME.getAllShopItems().get(5).isActivated()) {
            IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).replaceColor(Color.RED, 3);
            IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).draw();
        }
    }

    final void displayShop() {
        Screen.set(ScreenType.SHOP);
        IMAGES.get(Bitmap.WINDOW_SHOP).draw();
        IMAGES.get(Bitmap.WINDOW_SHOP_PANEL).setPosition(-1, 10);
        IMAGES.get(Bitmap.WINDOW_SHOP_PANEL).draw();
        IMAGES.get(Bitmap.WINDOW_SHOP_PANEL).setPosition(-1, 78);
        IMAGES.get(Bitmap.WINDOW_SHOP_PANEL).draw();
        IMAGES.get(Bitmap.BOARD_MINE).draw();
        TEXT_WRITER.write("" + GAME.countMinesOnField, Color.WHITE, 22, 12, false);
        IMAGES.get(Bitmap.BOARD_FLAG).draw();
        TEXT_WRITER.write("" + GAME.inventoryFlags, Color.WHITE, 49, 12, false);
        IMAGES.get(Bitmap.SHOP_COIN).setPosition(69, 13);
        IMAGES.get(Bitmap.SHOP_COIN).draw();
        TEXT_WRITER.write("" + GAME.inventoryMoney, Color.WHITE, 75, 12, false);

        TEXT_WRITER.write("магазин", Color.YELLOW, 33, 22, false);
        for (int y = 0; y < 2; y++) {
            int dy = y * 25;
            for (int x = 0; x < 3; x++) {
                int dx = x * 25;
                ShopItem item = GAME.getAllShopItems().get(x + y * 3);
                Picture frame = (Picture) IMAGES.get(Bitmap.ITEM_FRAME);

                if (item.cost > GAME.inventoryMoney || item.getCount() <= 0) {
                    frame.replaceColor(Color.RED, 3);
                } else {
                    frame.replaceColor(Color.GREEN, 3);
                }
                if (item.isActivated()) {
                    frame.replaceColor(Color.BLUE, 3);
                }

                frame.setPosition(15 + dx, 30 + dy);
                frame.draw();
                item.icon.setPosition(16 + dx, 31 + dy);
                item.icon.draw();

                if (item.getCount() > 0 && !item.isActivated()) {
                    TEXT_WRITER.write("" + item.cost, Color.YELLOW, 30 + dx, 41 + dy, true);
                } else if (item.isActivated()) {
                    if (item.id == ShopItem.ID.SHOVEL || item.id == ShopItem.ID.DICE) {
                        TEXT_WRITER.write(Integer.toString(item.expireMove - GAME.countMoves),
                                Color.MAGENTA, 30 + (x * 25), 30 + (y * 25), true);
                    }
                    TEXT_WRITER.write("АКТ",
                            Color.YELLOW, 30 + (x * 25), 41 + (y * 25), true);
                } else {
                    TEXT_WRITER.write("НЕТ",
                            Color.RED, 30 + (x * 25), 41 + (y * 25), true);
                }   // draw prices
            }
        }

        TEXT_WRITER.write("очки:" + GAME.score, Color.LIGHTCYAN, 13, 80, false);
        TEXT_WRITER.write("шаги:" + GAME.countMoves, Color.LIGHTBLUE, 84, 80, true);
    }

    final void displayItemHelp(ShopItem item) {
        Screen.set(ScreenType.ITEM_HELP);
        IMAGES.get(Bitmap.WINDOW_ITEM_HELP).draw();
        item.icon.setPosition(5, 5);
        item.icon.draw();
        TEXT_WRITER.write(item.name, Color.YELLOW, 25, 9, false);
        TEXT_WRITER.write(item.description, Color.WHITE, 4, 25, false);
        BUTTONS.get(ButtonID.CONFIRM).draw();
    }


    // GAME OVER

    final void displayGameOver(boolean victory, int delay) {
        Screen.set(ScreenType.GAME_OVER);
        gameOverDisplayDelay = delay;
        if (gameOverDisplayDelay > 0) {
            return;
        }
        if (victory) {
            IMAGES.get(Bitmap.WINDOW_VICTORY).draw();
            IMAGES.get(Bitmap.PICTURE_FACE_HAPPY).draw();
            TEXT_WRITER.write("победа!", Color.YELLOW, 18, 33, false);
        } else {
            IMAGES.get(Bitmap.WINDOW_GAME_OVER).draw();
            IMAGES.get(Bitmap.PICTURE_FACE_SAD).draw();
            TEXT_WRITER.write("не повезло!", Color.YELLOW, 18, 33, false);
        }
        TEXT_WRITER.write("счёт: " + GAME.score, Color.LIGHTGOLDENRODYELLOW, 18, 57, false);
        BUTTONS.get(ButtonID.AGAIN).draw();
        BUTTONS.get(ButtonID.RETURN).draw();
        BUTTONS.get(ButtonID.CLOSE).draw();
    }

    final void displayScoreDetail() {
        Screen.set(ScreenType.SCORE_DETAIL);
        IMAGES.get(Bitmap.WINDOW_MENU).draw();
        int minesScore = GAME.countMinesOnField * 20 * GAME.difficulty;
        String minesScoreDetail = 20 * GAME.difficulty + "*" + GAME.countMinesOnField + " = ";
        int moneyScore = GAME.inventoryMoney * GAME.difficulty;
        String moneyScoreDetail = GAME.inventoryMoney + "*" + GAME.difficulty + " = ";
        String cellScoreDetail = GAME.countOpenCells + "*" + GAME.difficulty + " = ";

        TEXT_WRITER.write("подробно", Color.YELLOW, 29, 2, false);
        TEXT_WRITER.write(
                "Ячейки:\n" +
                        "Кубик:\n" +
                        "Золото:\n" +
                        "Мины:\n" +
                        "Щиты:\n\n" +
                        "Итого:",
                Color.WHITE, 3, 13, false);
        TEXT_WRITER.write(
                GAME.score +
                        "\n\n" + GAME.scoreLost +
                        "\n" + (GAME.lastResultIsVictory ? (minesScoreDetail + minesScore) : "вы проиграли") +
                        "\n" + (GAME.lastResultIsVictory ? (moneyScoreDetail + moneyScore) : "вы проиграли") +
                        "\n" + GAME.scoreDice +
                        "\n" + cellScoreDetail + GAME.scoreCell,
                Color.YELLOW, 94, 13, true);

        BUTTONS.get(ButtonID.CONFIRM).draw();
    }

    // LOAD RESOURCES

    final void loadImages() { // pre-load images with default position
        IMAGES.put(Bitmap.WINDOW_MENU, new Picture(Bitmap.WINDOW_MENU, GAME, 0, 0));
        IMAGES.put(Bitmap.PICTURE_LOGO, new Picture(Bitmap.PICTURE_LOGO, GAME, -1, 8));
        IMAGES.put(Bitmap.BUTTON_MENU, new Picture(Bitmap.BUTTON_MENU, GAME, 61, 88));
        IMAGES.put(Bitmap.WINDOW_VICTORY, new Picture(Bitmap.WINDOW_VICTORY, GAME, -1, -1));
        IMAGES.put(Bitmap.WINDOW_GAME_OVER, new Picture(Bitmap.WINDOW_GAME_OVER, GAME, -1, -1));
        IMAGES.put(Bitmap.PICTURE_FACE_HAPPY, new Picture(Bitmap.PICTURE_FACE_HAPPY, GAME, -1, -1));
        IMAGES.put(Bitmap.PICTURE_FACE_SAD, new Picture(Bitmap.PICTURE_FACE_SAD, GAME, -1, -1));
        IMAGES.put(Bitmap.WINDOW_SHOP, new Picture(Bitmap.WINDOW_SHOP, GAME, -1, -1));
        IMAGES.put(Bitmap.WINDOW_SHOP_PANEL, new Picture(Bitmap.WINDOW_SHOP_PANEL, GAME, -1, 10));
        IMAGES.put(Bitmap.BOARD_MINE, new Sprite(Bitmap.BOARD_MINE, GAME, 10, 10));
        IMAGES.put(Bitmap.BOARD_FLAG, new Sprite(Bitmap.BOARD_FLAG, GAME, 39, 11));
        IMAGES.put(Bitmap.SHOP_COIN, new Picture(Bitmap.SHOP_COIN, GAME, 69, 13));
        IMAGES.put(Bitmap.ITEM_FRAME, new Picture(Bitmap.ITEM_FRAME, GAME, 14, 30));
        IMAGES.put(Bitmap.MENU_ARROW, new Picture(Bitmap.MENU_ARROW, GAME, 93, 21));
        IMAGES.put(Bitmap.MENU_DIFFICULTY_BAR, new Picture(Bitmap.MENU_DIFFICULTY_BAR, GAME, 0, 0));
        IMAGES.put(Bitmap.BUTTON_OK, new Picture(Bitmap.BUTTON_OK, GAME, 0, 0));
        IMAGES.put(Bitmap.BUTTON_CLOSE, new Picture(Bitmap.BUTTON_CLOSE, GAME, 0, 0));
        IMAGES.put(Bitmap.ITEM_SHIELD, new Picture(Bitmap.ITEM_SHIELD, GAME, 0, 0));
        IMAGES.put(Bitmap.ITEM_SCANNER, new Picture(Bitmap.ITEM_SCANNER, GAME, 0, 0));
        IMAGES.put(Bitmap.ITEM_FLAG, new Picture(Bitmap.ITEM_FLAG, GAME, 0, 0));
        IMAGES.put(Bitmap.ITEM_SHOVEL, new Picture(Bitmap.ITEM_SHOVEL, GAME, 0, 0));
        IMAGES.put(Bitmap.ITEM_DICE, new Picture(Bitmap.ITEM_DICE, GAME, 0, 0));
        IMAGES.put(Bitmap.ITEM_BOMB, new Picture(Bitmap.ITEM_BOMB, GAME, 0, 0));
        IMAGES.put(Bitmap.WINDOW_ITEM_HELP, new Picture(Bitmap.WINDOW_ITEM_HELP, GAME, 0, 0));
        IMAGES.put(Bitmap.MENU_SWITCH, new Picture(Bitmap.MENU_SWITCH, GAME, 0, 0));
        IMAGES.put(Bitmap.MENU_SWITCH_RAIL, new Picture(Bitmap.MENU_SWITCH_RAIL, GAME, 80, 52));
        IMAGES.put(Bitmap.BOARD_ACTIVE_FRAME, new Picture(Bitmap.BOARD_ACTIVE_FRAME, GAME, 0, 0));
        IMAGES.put(Bitmap.DICE_1, new Picture(Bitmap.DICE_1, GAME, 0, 0));
        IMAGES.put(Bitmap.DICE_2, new Picture(Bitmap.DICE_2, GAME, 0, 0));
        IMAGES.put(Bitmap.DICE_3, new Picture(Bitmap.DICE_3, GAME, 0, 0));
        IMAGES.put(Bitmap.DICE_4, new Picture(Bitmap.DICE_4, GAME, 0, 0));
        IMAGES.put(Bitmap.DICE_5, new Picture(Bitmap.DICE_5, GAME, 0, 0));
        IMAGES.put(Bitmap.DICE_6, new Picture(Bitmap.DICE_6, GAME, 0, 0));
        IMAGES.put(Bitmap.CUP, new Picture(Bitmap.CUP, GAME, 0, 0));
    }

    final void loadButtons() {
        BUTTONS.put(ButtonID.OPTIONS, new Button(GAME, 61, 64, 36, 9, "опции"));
        BUTTONS.put(ButtonID.ABOUT, new Button(GAME, 61, 76, 36, 9, "об игре"));
        BUTTONS.put(ButtonID.START, new Button(GAME, 61, 88, 36, 9, "старт"));
        BUTTONS.put(ButtonID.RECORDS, new Button(GAME, 2, 88, "рекорды"));
        BUTTONS.put(ButtonID.BACK, new Button(GAME, 61, 88, 36, 9, "назад"));
        BUTTONS.put(ButtonID.CONFIRM, new Button(GAME, 61, 88, 36, 9, "ясно"));
        BUTTONS.put(ButtonID.AGAIN, new Button(GAME, 57, 69, "снова"));
        BUTTONS.put(ButtonID.RETURN, new Button(GAME, 15, 69, "меню"));
        BUTTONS.put(ButtonID.CLOSE, new Button(GAME, 73, 35, "x"));
    }
}
