package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.Screen.ScreenType;
import com.javarush.games.minesweeper.graphics.Button.ButtonID;

import java.util.Date;

/**
 * Separate class for processing various input events.
 */

class InputEvent {
    final private MinesweeperGame game;
    public static double lastClickInShopTime;

    InputEvent(MinesweeperGame game) {
        this.game = game;
    }

    final void leftClick(int x, int y) {
        if (clickOutsideScreen(x, y) || game.menu.gameOverDisplayDelay > 0) {
            return;
        }
        leftClickAction(x, y, Screen.getType());
    }

    final void rightClick(int x, int y) {
        if (clickOutsideScreen(x, y)) {
            return;
        }
        rightClickAction(x, y, Screen.getType());
        // System.out.println(String.format("%d %d", x, y));
    }

    private void leftClickAction(int x, int y, ScreenType screenType) {
        game.allowCountMoves = true;
        game.allowFlagExplosion = false;
        game.hideDice();
        switch (screenType) {
            case MAIN_MENU:
                if (Menu.BUTTONS.get(ButtonID.START).has(x, y)) {
                    game.createGame();
                } else if (Menu.BUTTONS.get(ButtonID.OPTIONS).has(x, y)) {
                    game.menu.displayOptions();
                } else if (Menu.BUTTONS.get(ButtonID.ABOUT).has(x, y)) {
                    game.menu.displayAbout();
                } else if (Menu.BUTTONS.get(ButtonID.RECORDS).has(x, y)) {
                    game.menu.displayRecords();
                }
                break;
            case GAME_BOARD:
                if (game.isStopped) {
                    game.menu.displayGameOver(game.lastResultIsVictory, 0);
                    Screen.set(ScreenType.GAME_OVER);
                    return;
                }
                Cell cell = game.field[y / 10][x / 10];
                if (!cell.isFlagged || game.shop.scanner.isActivated()) {
                    game.openCell(x / 10, y / 10);
                }
                break;
            case SHOP:
                lastClickInShopTime = new Date().getTime();
                if (x >= 15 && x <= 34 && y >= 31 && y <= 50) {
                    game.shop.sell(game.shop.shield);
                    game.menu.pushedItemFrameNumber = 0;
                } else if (x >= 40 && x <= 59 && y >= 31 && y <= 50) {
                    game.shop.sell(game.shop.scanner);
                    game.menu.pushedItemFrameNumber = 1;
                } else if (x >= 65 && x <= 84 && y >= 31 && y <= 50) {
                    game.shop.sell(game.shop.flag);
                    game.menu.pushedItemFrameNumber = 2;
                } else if (x >= 15 && x <= 34 && y >= 56 && y <= 75) {
                    game.shop.sell(game.shop.goldenShovel);
                    game.menu.pushedItemFrameNumber = 3;
                } else if (x >= 40 && x <= 59 && y >= 56 && y <= 75) {
                    game.shop.sell(game.shop.luckyDice);
                    game.menu.pushedItemFrameNumber = 4;
                } else if (x >= 65 && x <= 84 && y >= 56 && y <= 75) {
                    game.shop.sell(game.shop.miniBomb);
                    game.menu.pushedItemFrameNumber = 5;
                } else if (clickOutsideShop(x, y)) {
                    game.menu.displayGameBoard();
                }
                break;
            case ITEM_HELP:
                if (Menu.BUTTONS.get(ButtonID.CONFIRM).has(x, y)) {
                    game.menu.displayGameBoard();
                    game.menu.displayShop();
                }
                break;
            case GAME_OVER:
                if (Menu.BUTTONS.get(ButtonID.CLOSE).has(x, y)) {
                    game.redrawAllCells();
                    Screen.set(ScreenType.GAME_BOARD);
                } else if (Menu.BUTTONS.get(ButtonID.RETURN).has(x, y)) {
                    game.menu.displayMain();
                } else if (Menu.BUTTONS.get(ButtonID.AGAIN).has(x, y)) {
                    game.createGame();
                } else if (x >= 18 && x <= 37 && y >= 60 && y <= 64) {
                    game.menu.displayScoreDetail();
                }
                break;
            case OPTIONS:
                if (Menu.BUTTONS.get(ButtonID.BACK).has(x, y)) {
                    game.menu.displayMain();
                } else if (x >= 49 && x <= 53 && y >= 22 && y <= 28) {
                    game.menu.changeDifficulty(false);
                } else if (x >= 93 && x <= 97 && y >= 22 && y <= 28) {
                    game.menu.changeDifficulty(true);
                } else if (x >= 80 && x <= 91 && y >= 51 && y <= 57) {
                    game.menu.switchAutoBuyFlags();
                }
                break;
            case SCORE_DETAIL:
                if (Menu.BUTTONS.get(ButtonID.CONFIRM).has(x, y)) {
                    game.menu.displayGameBoard();
                    game.menu.displayGameOver(game.lastResultIsVictory, 0);
                }
                break;
            case ABOUT:
                if (Menu.BUTTONS.get(ButtonID.BACK).has(x, y)) {
                    game.menu.displayMain();
                } else if (Menu.BUTTONS.get(ButtonID.FORWARD).has(x, y)) {
                    // TODO: Shift Page
                    if (Strings.currentAboutPage < 5) {
                        Strings.currentAboutPage++;
                    } else {
                        Strings.currentAboutPage = 0;
                    }
                    game.menu.displayAbout();
                }
                break;
            case RECORDS:
                if (Menu.BUTTONS.get(ButtonID.BACK).has(x, y)) {
                    game.menu.displayMain();
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
                    game.menu.displayGameOver(game.lastResultIsVictory, 0);
                    Screen.set(ScreenType.GAME_OVER);
                } else {
                    game.setFlag(x / 10, y / 10, true); // works only if tile is closed
                    game.openRest(x / 10, y / 10);                       // works only if tile is open
                    // above two actions don't interfere, only one will work
                }
                break;
            case SHOP:
                if (x >= 15 && x <= 34 && y >= 31 && y <= 50) {
                    game.menu.displayItemHelp(game.shop.allItems.get(0));
                } else if (x >= 40 && x <= 59 && y >= 31 && y <= 50) {
                    game.menu.displayItemHelp(game.shop.allItems.get(1));
                } else if (x >= 65 && x <= 84 && y >= 31 && y <= 50) {
                    game.menu.displayItemHelp(game.shop.allItems.get(2));
                } else if (x >= 15 && x <= 34 && y >= 56 && y <= 75) {
                    game.menu.displayItemHelp(game.shop.allItems.get(3));
                } else if (x >= 40 && x <= 59 && y >= 56 && y <= 75) {
                    game.menu.displayItemHelp(game.shop.allItems.get(4));
                } else if (x >= 65 && x <= 84 && y >= 56 && y <= 75) {
                    game.menu.displayItemHelp(game.shop.allItems.get(5));
                }
                break;
            default:
                break;
        }
    }

    final void keyPressAction(Key key) {
        ScreenType screen = Screen.getType();

        switch (key) {
            case SPACE:
                switch (screen) {
                    case GAME_BOARD:
                        if (!game.isStopped) {
                            game.menu.displayShop();
                            game.menu.moneyOnDisplay = game.inventory.money;
                        } else {
                            game.menu.displayGameOver(game.lastResultIsVictory, 0);
                            Screen.set(ScreenType.GAME_OVER);
                        }
                        break;
                    case SHOP:
                        game.menu.displayGameBoard();
                        break;
                    default:
                        break;
                }
                break;
            case ESCAPE:
                switch (screen) {
                    case GAME_BOARD:
                        if (game.isStopped) {
                            game.menu.displayGameOver(game.lastResultIsVictory, 0);
                            Screen.set(ScreenType.GAME_OVER);
                            break;
                        }
                    case OPTIONS:
                    case ABOUT:
                    case RECORDS:
                        game.menu.displayMain();
                        break;
                    case MAIN_MENU:
                        if (!game.isStopped) {
                            game.menu.displayGameBoard();
                        }
                        break;
                    case SHOP:
                        game.menu.displayGameBoard();
                        break;
                    case ITEM_HELP:
                        game.menu.displayGameBoard();
                        game.menu.displayShop();
                        break;
                    default:
                        break;
                }
                break;
            default:
                if (screen == ScreenType.GAME_BOARD && game.isStopped) {
                    game.menu.displayGameOver(game.lastResultIsVictory, 0);
                    Screen.set(ScreenType.GAME_OVER);
                }
                break;
        }
    }

    // MISC

    private boolean clickOutsideScreen(int x, int y) {
        return (x > 99 || y > 99 || x < 0 || y < 0);
    }

    private boolean clickOutsideShop(int x, int y) {
        boolean horizontal = (x >= 0 && x <= 9 || x >= 90 && x <= 99);
        boolean vertical = (y >= 0 && y <= 10 || y >= 91 && y <= 99);
        return (horizontal || vertical);
    }
}
