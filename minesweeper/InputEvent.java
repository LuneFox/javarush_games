package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.Screen.ScreenType;
import com.javarush.games.minesweeper.graphics.Button.ButtonID;
import com.javarush.games.minesweeper.view.View;

import java.util.Date;

/**
 * Separate class for processing various input events.
 */

public class InputEvent {
    final private MinesweeperGame game;
    public static double lastClickInShopTime;

    InputEvent(MinesweeperGame game) {
        this.game = game;
    }

    final void leftClick(int x, int y) {
        if (clickOutsideScreen(x, y) || View.gameOver.displayDelay > 0) return;
        leftClickAction(x, y, Screen.getType());
    }

    final void rightClick(int x, int y) {
        if (clickOutsideScreen(x, y) || View.gameOver.displayDelay > 0) return;
        rightClickAction(x, y, Screen.getType());
        // System.out.printf("%d %d%n", x, y);
    }

    private void leftClickAction(int x, int y, ScreenType screenType) {
        game.allowCountMoves = true;
        game.allowFlagExplosion = false;
        game.hideDice();
        switch (screenType) {
            case MAIN_MENU:
                if (View.BUTTONS.get(ButtonID.START).covers(x, y)) {
                    game.createGame();
                } else if (View.BUTTONS.get(ButtonID.OPTIONS).covers(x, y)) {
                    View.options.display();
                } else if (View.BUTTONS.get(ButtonID.ABOUT).covers(x, y)) {
                    View.about.display();
                } else if (View.BUTTONS.get(ButtonID.RECORDS).covers(x, y)) {
                    View.records.display();
                }
                break;
            case GAME_BOARD:
                if (game.isStopped) {
                    View.gameOver.display(game.lastResultIsVictory, 0);
                    Screen.setType(ScreenType.GAME_OVER);
                    return;
                }
                Cell cell = game.field[y / 10][x / 10];
                if (!cell.isFlagged || game.shop.scanner.isActivated()) {
                    game.openCell(x / 10, y / 10);
                }
                break;
            case SHOP:
                if (clickOutsideShop(x, y)) {
                    View.board.display();
                }
                ShopItem item = game.shop.getClickedItem(x, y);
                if (item == null) return;
                InputEvent.lastClickInShopTime = new Date().getTime();
                game.shop.sell(item);
                View.shop.lastClickedItemNumber = item.number;
                break;
            case ITEM_HELP:
                if (View.BUTTONS.get(ButtonID.CONFIRM).covers(x, y)) {
                    View.board.display();
                    View.shop.display();
                }
                break;
            case GAME_OVER:
                if (View.BUTTONS.get(ButtonID.CLOSE).covers(x, y)) {
                    game.redrawAllCells();
                    Screen.setType(ScreenType.GAME_BOARD);
                } else if (View.BUTTONS.get(ButtonID.RETURN).covers(x, y)) {
                    View.main.display();
                } else if (View.BUTTONS.get(ButtonID.AGAIN).covers(x, y)) {
                    game.createGame();
                } else if (View.gameOver.scoreArea.covers(x, y)) {
                    View.score.display();
                }
                break;
            case OPTIONS:
                if (View.BUTTONS.get(ButtonID.BACK).covers(x, y)) {
                    View.main.display();
                } else if (View.options.difficultyDownArea.covers(x, y)) {
                    View.options.changeDifficulty(false);
                } else if (View.options.difficultyUpArea.covers(x, y)) {
                    View.options.changeDifficulty(true);
                } else if (View.options.autoBuyFlagsArea.covers(x, y)) {
                    View.options.switchAutoBuyFlags();
                } else if (View.options.switchGameTimerArea.covers(x, y)) {
                    View.options.switchGameTimer();
                }
                break;
            case SCORE:
                if (View.BUTTONS.get(ButtonID.CONFIRM).covers(x, y)) {
                    View.board.display();
                    View.gameOver.display(game.lastResultIsVictory, 0);
                }
                break;
            case ABOUT:
                if (View.BUTTONS.get(ButtonID.BACK).covers(x, y)) {
                    View.main.display();
                } else if (View.about.prevPageArrowArea.covers(x, y)) {
                    View.about.prevPage();
                } else if (View.about.nextPageArrowArea.covers(x, y)) {
                    View.about.nextPage();
                }
                break;
            case RECORDS:
                if (View.BUTTONS.get(ButtonID.BACK).covers(x, y)) {
                    View.main.display();
                }
                break;
            default:
                break;
        }
    }

    private void rightClickAction(int x, int y, ScreenType screenType) {
        game.allowCountMoves = true;
        switch (screenType) {
            case GAME_BOARD:
                if (game.isStopped) {
                    View.gameOver.display(game.lastResultIsVictory, 0);
                    Screen.setType(ScreenType.GAME_OVER);
                } else { // only one will work - actions don't interfere
                    game.setFlag(x / 10, y / 10, true); // works only if tile is closed
                    game.openRest(x / 10, y / 10);                // works only if tile is open
                }
                break;
            case SHOP:
                ShopItem item = game.shop.getClickedItem(x, y);
                if (item == null) return;
                View.itemHelp.display(item);
                break;
            default:
                break;
        }
    }

    final void keyPressAction(Key key) {
        if (View.gameOver.displayDelay > 0) return;
        ScreenType screen = Screen.getType();
        switch (key) {
            case SPACE:
                switch (screen) {
                    case GAME_BOARD:
                        if (!game.isStopped) {
                            View.shop.display();
                            View.shop.moneyOnDisplay = game.inventory.money;
                        } else {
                            View.gameOver.display(game.lastResultIsVictory, 0);
                            Screen.setType(ScreenType.GAME_OVER);
                        }
                        break;
                    case SHOP:
                        View.board.display();
                        break;
                    default:
                        break;
                }
                break;
            case ESCAPE:
                switch (screen) {
                    case GAME_BOARD:
                        if (game.isStopped) {
                            View.gameOver.display(game.lastResultIsVictory, 0);
                            Screen.setType(ScreenType.GAME_OVER);
                            break;
                        }
                    case OPTIONS:
                    case ABOUT:
                    case RECORDS:
                        View.main.display();
                        break;
                    case MAIN_MENU:
                        if (!game.isStopped) {
                            View.board.display();
                        }
                        break;
                    case SHOP:
                        View.board.display();
                        break;
                    case ITEM_HELP:
                        View.board.display();
                        View.shop.display();
                        break;
                    default:
                        break;
                }
                break;
            case LEFT:
                switch (screen) {
                    case ABOUT:
                        View.about.prevPage();
                        break;
                    case OPTIONS:
                        View.options.changeDifficulty(false);
                        break;
                    default:
                        break;
                }
                break;
            case RIGHT:
                switch (screen) {
                    case ABOUT:
                        View.about.nextPage();
                        break;
                    case OPTIONS:
                        View.options.changeDifficulty(true);
                        break;
                    default:
                        break;
                }
                break;
            default:
                if (screen == ScreenType.GAME_BOARD && game.isStopped) {
                    View.gameOver.display(game.lastResultIsVictory, 0);
                    Screen.setType(ScreenType.GAME_OVER);
                }
                break;
        }
    }

    private boolean clickOutsideScreen(int x, int y) {
        return (x > 99 || y > 99 || x < 0 || y < 0);
    }

    private boolean clickOutsideShop(int x, int y) {
        boolean horizontal = (x >= 0 && x <= 9 || x >= 90 && x <= 99);
        boolean vertical = (y >= 0 && y <= 10 || y >= 91 && y <= 99);
        return (horizontal || vertical);
    }
}
