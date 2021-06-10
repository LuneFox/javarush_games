package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.Screen.ScreenType;
import com.javarush.games.minesweeper.graphics.*;
import com.javarush.games.minesweeper.graphics.Button.ButtonID;
import com.javarush.games.minesweeper.graphics.Image.Mirror;

import java.util.*;

/**
 * Class for displaying various menus on the screen.
 */

class Menu {
    final private MinesweeperGame game;
    final static HashMap<Bitmap, Image> IMAGES = new HashMap<>();
    final static HashMap<ButtonID, Button> BUTTONS = new HashMap<>();
    final private static LinkedList<String> QUOTES = new LinkedList<>();
    final static LinkedList<String> DIFFICULTY_NAMES = new LinkedList<>();
    private static String quote;
    private static Date lastQuoteDate;
    public int gameOverDisplayDelay;  // defines how soon will game over screen show up
    public int lastClickedItemNumber; // defines what frame in the shop was pushed last time
    public int moneyOnDisplay;        // for smooth animation, runs towards real money
    public int shakingAnimationMaxTurns = 15;                             // number of turns to shake the item
    public int shakingAnimationCurrentTurn = shakingAnimationMaxTurns;    // counts towards zero

    static {
        Collections.addAll(DIFFICULTY_NAMES, Strings.DIFFICULTY_NAMES);
        Collections.addAll(QUOTES, Strings.QUOTES);
        lastQuoteDate = new Date();
        quote = QUOTES.get(0);
    }

    Menu(MinesweeperGame game) {
        this.game = game;
        this.gameOverDisplayDelay = 0;
        loadImages();
        loadButtons();
    }


    // MAIN

    void displayMain() {
        Screen.setType(ScreenType.MAIN_MENU);
        IMAGES.get(Bitmap.WINDOW_MENU).draw();
        IMAGES.get(Bitmap.PICTURE_LOGO).floatAnimation(2.8, -1, 8);
        BUTTONS.get(ButtonID.OPTIONS).draw();
        BUTTONS.get(ButtonID.ABOUT).draw();
        BUTTONS.get(ButtonID.START).draw();
        BUTTONS.get(ButtonID.RECORDS).draw();
        game.print(Strings.VERSION, Color.DARKRED, 85, 0, false);
        printTopScore();
        printRandomQuote();
    }

    private void printRandomQuote() {
        if (new Date().getTime() - lastQuoteDate.getTime() > 30000) {
            quote = QUOTES.get(game.getRandomNumber(QUOTES.size()));
            lastQuoteDate = new Date();
        }
        game.print(quote, Color.DARKRED, 5, 44, false); // shadow
        game.print(quote, Color.SALMON, 4, 44, false);
    }

    private void printTopScore() {
        if (game.player.topScore > 0) {
            game.print("счёт: " + game.player.topScore + "\n" + game.player.topScoreTitle,
                    Color.LIGHTGOLDENRODYELLOW, 4, 65, false);
        }
    }

    // ABOUT

    final void displayAbout() {
        Screen.setType(ScreenType.ABOUT);
        IMAGES.get(Bitmap.WINDOW_MENU).draw();
        game.print(Strings.getAbout()[0], Color.YELLOW, 24, 2, false);
        game.print(Strings.getAbout()[1], Color.WHITE, 3, 13, false);
        BUTTONS.get(ButtonID.BACK).draw();
        BUTTONS.get(ButtonID.FORWARD).draw();
    }


    // RECORDS

    final void displayRecords() {
        Screen.setType(ScreenType.RECORDS);
        IMAGES.get(Bitmap.WINDOW_MENU).draw();
        game.print(Strings.RECORDS[0], Color.YELLOW, 17, 2, false);
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
        game.print(Strings.RECORDS[1], colors[1], 18, 19, false);
        game.print(Strings.RECORDS[2], colors[0], 94, 28, true);
        game.print(Strings.RECORDS[3], colors[2], 18, 39, false);
        game.print(Strings.RECORDS[4], colors[0], 94, 48, true);
        game.print(Strings.RECORDS[5], colors[3], 18, 59, false);
        game.print(Strings.RECORDS[6], colors[0], 94, 68, true);
    }


    // OPTIONS

    final void displayOptions() {
        Screen.setType(ScreenType.OPTIONS);

        IMAGES.get(Bitmap.WINDOW_MENU).draw();
        game.print("настройки", Color.YELLOW, 28, 2, false);

        IMAGES.get(Bitmap.MENU_ARROW).drawAt(93, 21, Mirror.NONE);
        IMAGES.get(Bitmap.MENU_ARROW).drawAt(49, 21, Mirror.HORIZONTAL);
        displayDifficultyBar();
        game.print("сложность", Color.WHITE, 2, 20, false);
        game.print(DIFFICULTY_NAMES.get(game.difficultyInOptionsScreen / 5 - 1),
                Color.SALMON, 93, 30, true);
        game.print("покупка\nфлажков", Color.WHITE, 2, 50, false);
        if (game.optionAutoBuyFlagsOn) {
            IMAGES.get(Bitmap.MENU_SWITCH).replaceColor(Color.GREEN, 1);
            IMAGES.get(Bitmap.MENU_SWITCH).setPosition(88, 50);
            game.print("авто", Color.SALMON, 93, 60, true);
        } else {
            IMAGES.get(Bitmap.MENU_SWITCH).replaceColor(Color.RED, 1);
            IMAGES.get(Bitmap.MENU_SWITCH).setPosition(80, 50);
            game.print("вручную", Color.SALMON, 91, 60, true);

        }
        IMAGES.get(Bitmap.MENU_SWITCH_RAIL).drawAt(80, 52);
        IMAGES.get(Bitmap.MENU_SWITCH).draw();
        BUTTONS.get(ButtonID.BACK).draw();
    }

    private void displayDifficultyBar() {
        LinkedList<Picture> difficulty = new LinkedList<>();
        for (int i = 0; i < game.difficultyInOptionsScreen / 5; i++) {
            Picture bar = new Picture(Bitmap.MENU_DIFFICULTY_BAR, game, (i * 4) + 56, 21);
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
            if (game.difficultyInOptionsScreen < 45) {
                game.difficultyInOptionsScreen += 5;
            }
        } else {
            if (game.difficultyInOptionsScreen > 5) {
                game.difficultyInOptionsScreen -= 5;
            }
        }
        displayOptions();
    }

    final void switchAutoBuyFlags() {
        game.optionAutoBuyFlagsOn = !game.optionAutoBuyFlagsOn;
        displayOptions();
    }


    // GAME BOARD

    final void displayGameBoard() {
        Screen.setType(ScreenType.GAME_BOARD);
        game.redrawAllCells();
        if (game.shop.allItems.get(1).isActivated()) {
            IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).replaceColor(Color.BLUE, 3);
            IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).draw();
        } else if (game.shop.allItems.get(5).isActivated()) {
            IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).replaceColor(Color.RED, 3);
            IMAGES.get(Bitmap.BOARD_ACTIVE_FRAME).draw();
        }
    }

    final void displayShop() {
        Screen.setType(ScreenType.SHOP);
        shakeAnimationCountDown();
        IMAGES.get(Bitmap.WINDOW_SHOP).drawAt(-1, -1);
        IMAGES.get(Bitmap.WINDOW_SHOP_PANEL).drawAt(-1, 10);
        IMAGES.get(Bitmap.WINDOW_SHOP_PANEL).drawAt(-1, 78);
        IMAGES.get(Bitmap.BOARD_MINE).drawAt(10, 10);
        IMAGES.get(Bitmap.BOARD_FLAG).drawAt(39, 11);
        IMAGES.get(Bitmap.BOARD_COIN).drawAt(69 + getMoneyShakeValue(), 13);
        makeDisplayMoneyApproachRealMoney();
        game.print("" + game.countAllCells(MinesweeperGame.Filter.MINED_AND_CLOSED), Color.WHITE, 22, 12, false);
        game.print("" + game.inventory.getCount(ShopItem.ID.FLAG), Color.WHITE, 49, 12, false);
        game.print("" + moneyOnDisplay, Color.WHITE, 75 + getMoneyShakeValue(), 12, false);
        game.print("* магазин *", Color.DARKSEAGREEN, 25, 22, false);
        game.print("очки:" + game.player.score, Color.LIGHTCYAN, 13, 80, false);
        game.print("шаги:" + game.player.countMoves, Color.LIGHTBLUE, 84, 80, true);
        displayShopItems();
    }

    private void displayShopItems() {
        int right = 30;
        int upper = 30;
        int bottom = 41;
        int currentFrame = -1; // to detect which frame is being drawn right now
        int shift;             // to shift pushed animation by this value when the button is pressed
        for (int y = 0; y < 2; y++) {
            int dy = y * 25;
            for (int x = 0; x < 3; x++) {
                int dx = x * 25;

                ShopItem item = game.shop.allItems.get(x + y * 3);

                currentFrame++;
                Picture frame;
                boolean lessThan100msPassed = (new Date().getTime() - InputEvent.lastClickInShopTime < 100);
                if (currentFrame == lastClickedItemNumber && lessThan100msPassed) {
                    frame = (Picture) IMAGES.get(Bitmap.ITEM_FRAME_PUSHED);
                    shift = 1;
                } else {
                    frame = (Picture) IMAGES.get(Bitmap.ITEM_FRAME);
                    shift = 0;
                }

                if (item.isUnobtainable()) {
                    frame.replaceColor(Color.RED, 3);
                } else {
                    frame.replaceColor(Color.GREEN, 3);
                }

                if (item.isActivated()) {
                    frame.replaceColor(Color.BLUE, 3);
                }

                frame.drawAt(15 + dx + shift, 30 + dy + shift);
                item.icon.drawAt(16 + dx + shift, 31 + dy + shift);

                if (item.inStock > 0 && !item.isActivated()) {
                    game.print("" + item.cost, Color.YELLOW, right + dx, bottom + dy, true);
                } else if (item.isActivated()) {
                    if (item.canExpire) {
                        game.print(item.remainingMoves(), Color.MAGENTA, right + dx, upper + dy, true);
                    }
                    game.print("АКТ", Color.YELLOW, right + dx - getActShakeValue(currentFrame), bottom + dy, true);
                } else {
                    game.print("НЕТ", Color.RED, right + dx, bottom + dy, true);
                }
            }
        }
    }

    private void makeDisplayMoneyApproachRealMoney() {
        if (moneyOnDisplay < game.inventory.money) {
            moneyOnDisplay++;
        } else if (moneyOnDisplay > game.inventory.money) {
            moneyOnDisplay--;
        }
    }

    private int getMoneyShakeValue() { // to shake money when you can't afford an item
        if (game.shop.isUnaffordableAnimationTrigger) {
            return (game.evenTurn) ? 1 : 0;
        }
        return 0;
    }

    private int getActShakeValue(int currentFrame) { // to shake ACT sign if the item is activated
        if (currentFrame != lastClickedItemNumber) { // shake only in current frame
            return 0;
        }
        if (game.shop.isAlreadyActivatedAnimationTrigger) {
            return (game.evenTurn) ? 1 : 0;
        }
        return 0;
    }

    public void shakeAnimationCountDown() { // helps to shake elements only for a certain amount of time
        if (shakingAnimationCurrentTurn > 0 && (game.shop.isAlreadyActivatedAnimationTrigger
                || game.shop.isUnaffordableAnimationTrigger)) {
            shakingAnimationCurrentTurn--;
        } else {
            game.shop.isUnaffordableAnimationTrigger = false;
            game.shop.isAlreadyActivatedAnimationTrigger = false;
            shakingAnimationCurrentTurn = shakingAnimationMaxTurns;
        }
    }

    final void displayItemHelp(ShopItem item) {
        Screen.setType(ScreenType.ITEM_HELP);
        Strings.generateNewShieldDescription();
        IMAGES.get(Bitmap.WINDOW_ITEM_HELP).draw();
        item.icon.setPosition(5, 5);
        item.icon.draw();
        if (item.id == ShopItem.ID.SHIELD) {
            item.description = Strings.generateNewShieldDescription().toString();
        }
        game.print(item.name, Color.YELLOW, 25, 9, false);
        game.print(item.description, Color.WHITE, 4, 25, false);
        BUTTONS.get(ButtonID.CONFIRM).draw();
    }


    // GAME OVER

    final void displayGameOver(boolean victory, int delay) {
        Screen.setType(ScreenType.GAME_OVER);
        gameOverDisplayDelay = delay;
        if (gameOverDisplayDelay > 0) {
            return;
        }
        if (victory) {
            IMAGES.get(Bitmap.WINDOW_VICTORY).drawAt(-1, -1);
            IMAGES.get(Bitmap.FACE_HAPPY).drawAt(-1, -1);
            game.print("победа!", Color.YELLOW, 18, 33, false);
        } else {
            IMAGES.get(Bitmap.WINDOW_GAME_OVER).drawAt(-1, -1);
            IMAGES.get(Bitmap.FACE_SAD).drawAt(-1, -1);
            game.print("не повезло!", Color.YELLOW, 18, 33, false);
        }
        game.print("счёт: " + game.player.score, Color.LIGHTGOLDENRODYELLOW, 18, 57, false);
        BUTTONS.get(ButtonID.AGAIN).draw();
        BUTTONS.get(ButtonID.RETURN).draw();
        BUTTONS.get(ButtonID.CLOSE).draw();
    }

    final void displayScoreDetail() {
        Screen.setType(ScreenType.SCORE_DETAIL);
        IMAGES.get(Bitmap.WINDOW_MENU).draw();
        int minesCount = game.countAllCells(MinesweeperGame.Filter.MINED);
        int minesScore = minesCount * 20 * game.difficulty;
        String minesScoreDetail = 20 * game.difficulty + "*" + minesCount + " = ";
        int moneyScore = game.inventory.money * game.difficulty;
        String moneyScoreDetail = game.inventory.money + "*" + game.difficulty + " = ";
        String cellScoreDetail = game.countAllCells(MinesweeperGame.Filter.OPEN) + "*" + game.difficulty + " = ";

        game.print("подробно", Color.YELLOW, 29, 2, false);
        game.print(
                "Ячейки:\n" +
                        "Кубик:\n" +
                        "Золото:\n" +
                        "Мины:\n" +
                        "Щиты:\n\n" +
                        "Итого:",
                Color.WHITE, 3, 13, false);
        game.print(
                game.player.score +
                        "\n\n" + game.player.scoreLost +
                        "\n" + (game.lastResultIsVictory ? (minesScoreDetail + minesScore) : "вы проиграли") +
                        "\n" + (game.lastResultIsVictory ? (moneyScoreDetail + moneyScore) : "вы проиграли") +
                        "\n" + game.player.scoreDice +
                        "\n" + cellScoreDetail + game.player.scoreCell,
                Color.YELLOW, 94, 13, true);

        BUTTONS.get(ButtonID.CONFIRM).draw();
    }

    // LOAD RESOURCES

    private void loadSprite(Bitmap bitmap) {
        loadSprite(bitmap, 0, 0);
    }

    private void loadSprite(Bitmap... bitmaps) {
        Arrays.asList(bitmaps).forEach(this::loadSprite);
    }

    private void loadSprite(Bitmap bitmap, int x, int y) {
        IMAGES.put(bitmap, new Sprite(bitmap, game, x, y));
    }

    private void loadPicture(Bitmap bitmap, int x, int y) {
        IMAGES.put(bitmap, new Picture(bitmap, game, x, y));
    }

    private void loadPicture(Bitmap bitmap) {
        loadPicture(bitmap, 0, 0);
    }

    private void loadPicture(Bitmap... bitmaps) {
        Arrays.asList(bitmaps).forEach(this::loadPicture);
    }

    final void loadImages() { // load images once at launch and just re-use them all the time
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
                Bitmap.ITEM_FRAME_PUSHED,
                Bitmap.ITEM_SCANNER,
                Bitmap.ITEM_SHIELD,
                Bitmap.ITEM_SHOVEL,
                Bitmap.MENU_ARROW,
                Bitmap.MENU_DIFFICULTY_BAR,
                Bitmap.MENU_SWITCH,
                Bitmap.MENU_SWITCH_RAIL,
                Bitmap.PICTURE_LOGO,
                Bitmap.WINDOW_GAME_OVER,
                Bitmap.WINDOW_ITEM_HELP,
                Bitmap.WINDOW_MENU,
                Bitmap.WINDOW_SHOP,
                Bitmap.WINDOW_SHOP_PANEL,
                Bitmap.WINDOW_VICTORY
        );
    }

    final void loadButtons() {
        BUTTONS.put(ButtonID.OPTIONS, new Button(game, 61, 64, 36, 9, "опции"));
        BUTTONS.put(ButtonID.ABOUT, new Button(game, 61, 76, 36, 9, "об игре"));
        BUTTONS.put(ButtonID.START, new Button(game, 61, 88, 36, 9, "старт"));
        BUTTONS.put(ButtonID.RECORDS, new Button(game, 2, 88, "рекорды"));
        BUTTONS.put(ButtonID.BACK, new Button(game, 61, 88, 36, 9, "назад"));
        BUTTONS.put(ButtonID.FORWARD, new Button(game, 3, 88, 36, 9, "далее"));
        BUTTONS.put(ButtonID.CONFIRM, new Button(game, 61, 88, 36, 9, "ясно"));
        BUTTONS.put(ButtonID.AGAIN, new Button(game, 57, 69, "снова"));
        BUTTONS.put(ButtonID.RETURN, new Button(game, 15, 69, "меню"));
        BUTTONS.put(ButtonID.CLOSE, new Button(game, 73, 35, "x"));
    }
}
