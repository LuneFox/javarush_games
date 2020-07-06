package com.javarush.games.minesweeper;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.graphics.*;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.Screen.*;
import com.javarush.games.minesweeper.graphics.Button.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Class for displaying various menus on the screen.
 */

class Menu {
    final static HashMap<Bitmap, Image> IMAGES = new HashMap<>();
    final static HashMap<ButtonID, Button> BUTTONS = new HashMap<>();
    final private MinesweeperGame GAME;
    final static LinkedList<String> TITLE_NAMES = new LinkedList<>();
    final private static LinkedList<String> QUOTES = new LinkedList<>();
    final private Text TEXT_WRITER;
    private boolean firstLoad = true;

    static {
        TITLE_NAMES.addAll(Arrays.asList("хомячок", "новичок", "любитель", "опытный", "эксперт",
                "отчаянный", "безумец", "бессмертный", "чак норрис"));
        QUOTES.add("Самая взрывная\nголоволомка!");
        QUOTES.add("Осторожно, игра\nзаминирована!");
        QUOTES.add("Просто бомба!");
        QUOTES.add("Здесь не бывает\nкислых мин!");
        QUOTES.add("Не собери их все!");
        QUOTES.add("Главное - не бомбить");
        QUOTES.add("Не приводи детей\nна работу!");
        QUOTES.add("В лопате нет\nничего смешного!");
        QUOTES.add("Втыкая флаг,\nне задень мину!");
        QUOTES.add("Какой идиот\nзакопал цифры?!");
    }

    Menu(MinesweeperGame game) {
        this.GAME = game;
        this.TEXT_WRITER = GAME.getTextWriter();
    }


    // MAIN

    void displayMain() {
        Screen.set(ScreenType.MAIN_MENU);
        IMAGES.get(Bitmap.WINDOW_MENU).draw();
        IMAGES.get(Bitmap.PICTURE_LOGO).draw();
        BUTTONS.get(ButtonID.OPTIONS).draw();
        BUTTONS.get(ButtonID.ABOUT).draw();
        BUTTONS.get(ButtonID.START).draw();

        TEXT_WRITER.write(MinesweeperGame.VERSION, Color.DARKRED, 85, 0, false);
        if (GAME.topScore > 0) {
            TEXT_WRITER.write("рекорд:\n" + GAME.topScore + "\n" + GAME.topScoreTitle,
                    Color.LIGHTGOLDENRODYELLOW, 4, 71, false);
        }
        printRandomQuote();
        if (firstLoad) {
            GAME.display.draw();
            firstLoad = false;
        }
    }

    private void printRandomQuote() {
        String quote = QUOTES.get(GAME.getRandomNumber(QUOTES.size()));
        TEXT_WRITER.write(quote, Color.DARKRED, 5, 44, false); // shadow
        TEXT_WRITER.write(quote, Color.SALMON, 4, 44, false);
    }

    final void displayAbout() {
        Screen.set(ScreenType.ABOUT);
        IMAGES.get(Bitmap.WINDOW_MENU).draw();
        TEXT_WRITER.write("информация", Color.YELLOW, 24, 2, false);
        TEXT_WRITER.write(
                "В моей версии игры\n" +
                        "есть магазин вещей.\n" +
                        "Он поможет меньше\n" +
                        "полагаться на удачу,\n" +
                        "больше планировать.\n" +
                        "    На поле:\n" +
                        "Пробел - магазин\n" +
                        "ПКМ - инфо о вещах",
                Color.WHITE, 3, 13, false);
        BUTTONS.get(ButtonID.BACK).draw();
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
        TEXT_WRITER.write(TITLE_NAMES.get(GAME.difficultySetting / 5 - 1),
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
        for (int i = 0; i < GAME.difficultySetting / 5; i++) {
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
            if (GAME.difficultySetting < 45) {
                GAME.difficultySetting += 5;
            }
            displayOptions();
        } else {
            if (GAME.difficultySetting > 5) {
                GAME.difficultySetting -= 5;
            }
            displayOptions();
        }
    }

    final void switchAutoBuyFlags() {
        GAME.allowAutoBuyFlags = !GAME.allowAutoBuyFlags;
        displayOptions();
    }


    // GAME BOARD

    final void displayGameBoard() {
        Screen.set(ScreenType.GAME_BOARD);
        GAME.redrawAllTiles();
        if (GAME.getAllShopItems().get(1).isActivated) {
            IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).replaceColor(Color.BLUE, 3);
            IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).draw();
        } else if (GAME.getAllShopItems().get(5).isActivated) {
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
        TEXT_WRITER.write("" + GAME.countFlags, Color.WHITE, 49, 12, false);
        IMAGES.get(Bitmap.SHOP_COIN).setPosition(69, 13);
        IMAGES.get(Bitmap.SHOP_COIN).draw();
        TEXT_WRITER.write("" + GAME.countMoney, Color.WHITE, 75, 12, false);

        TEXT_WRITER.write("магазин", Color.YELLOW, 33, 22, false);
        for (int y = 0; y < 2; y++) {
            int dy = y * 25;
            for (int x = 0; x < 3; x++) {
                int dx = x * 25;
                ShopItem item = GAME.getAllShopItems().get(x + y * 3);
                Picture frame = (Picture) IMAGES.get(Bitmap.ITEM_FRAME);

                if (item.cost > GAME.countMoney || item.count <= 0) {
                    frame.replaceColor(Color.RED, 3);
                } else {
                    frame.replaceColor(Color.GREEN, 3);
                }
                if (item.isActivated) {
                    frame.replaceColor(Color.BLUE, 3);
                }

                frame.setPosition(15 + dx, 30 + dy);
                frame.draw();
                item.icon.setPosition(16 + dx, 31 + dy);
                item.icon.draw();

                if (item.count > 0 && !item.isActivated) {
                    TEXT_WRITER.write("" + item.cost, Color.YELLOW, 30 + dx, 41 + dy, true);
                } else if (item.isActivated) {
                    TEXT_WRITER.write("АКТ", Color.YELLOW, 30 + (x * 25), 41 + (y * 25), true);
                } else {
                    TEXT_WRITER.write("НЕТ", Color.YELLOW, 30 + (x * 25), 41 + (y * 25), true);
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

    final void displayGameOver(boolean victory) {
        Screen.set(ScreenType.GAME_OVER);
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
        int moneyScore = GAME.countMoney * GAME.difficulty;
        String moneyScoreDetail = GAME.countMoney + "*" + GAME.difficulty + " = ";
        String cellScoreDetail = GAME.openedCellsCount + "*" + GAME.difficulty + " = ";

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
        IMAGES.put(Bitmap.PICTURE_LOGO, new Picture(Bitmap.PICTURE_LOGO, GAME, -1, 10));
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
    }

    final void loadButtons() {
        BUTTONS.put(ButtonID.OPTIONS, new Button(GAME, 61, 64, 36, 9, "опции"));
        BUTTONS.put(ButtonID.ABOUT, new Button(GAME, 61, 76, 36, 9, "об игре"));
        BUTTONS.put(ButtonID.START, new Button(GAME, 61, 88, 36, 9, "старт"));
        BUTTONS.put(ButtonID.BACK, new Button(GAME, 61, 88, 36, 9, "назад"));
        BUTTONS.put(ButtonID.CONFIRM, new Button(GAME, 61, 88, 36, 9, "ясно"));
        BUTTONS.put(ButtonID.AGAIN, new Button(GAME, 57, 69, "снова"));
        BUTTONS.put(ButtonID.RETURN, new Button(GAME, 15, 69, "меню"));
        BUTTONS.put(ButtonID.CLOSE, new Button(GAME, 73, 35, "x"));
    }
}
