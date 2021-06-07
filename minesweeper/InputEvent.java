package com.javarush.games.minesweeper;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.graphics.Button.*;
import com.javarush.games.minesweeper.Screen.*;

/**
 * Separate class for processing various input events.
 */

class InputEvent {
    final private MinesweeperGame game;
    final private Menu menu;

    InputEvent(MinesweeperGame game) {
        this.game = game;
        this.menu = game.getMenu();
    }

    final void leftClick(int x, int y) {
        if (clickOutsideScreen(x, y) || menu.gameOverDisplayDelay > 0) {
            return;
        }
        game.allowCountMoves = true;
        game.allowFlagExplosion = false;
        game.hideDice();
        leftClickAction(x, y, Screen.getType());
    }

    final void rightClick(int x, int y) {
        if (clickOutsideScreen(x, y)) {
            return;
        }
        game.allowCountMoves = true;
        rightClickAction(x, y, Screen.getType());
        // System.out.println(String.format("%d %d", x, y));
    }

    private void leftClickAction(int x, int y, ScreenType screenType) {
        switch (screenType) {
            case MAIN_MENU:
                if (Menu.BUTTONS.get(ButtonID.START).has(x, y)) {
                    game.createGame();
                } else if (Menu.BUTTONS.get(ButtonID.OPTIONS).has(x, y)) {
                    menu.displayOptions();
                } else if (Menu.BUTTONS.get(ButtonID.ABOUT).has(x, y)) {
                    menu.displayAbout();
                } else if (Menu.BUTTONS.get(ButtonID.RECORDS).has(x, y)) {
                    menu.displayRecords();
                }
                break;
            case GAME_BOARD:
                if (game.isStopped) {
                    menu.displayGameOver(game.lastResultIsVictory, 0);
                    Screen.set(ScreenType.GAME_OVER);
                    return;
                }
                Cell cell = game.field[y / 10][x / 10];
                if (!cell.isFlagged || game.shop.scanner.isActivated()) {
                    game.openCell(x / 10, y / 10);
                }
                break;
            case SHOP:
                if (x >= 15 && x <= 34 && y >= 31 && y <= 50) {
                    game.shop.sell(game.shop.shield);
                } else if (x >= 40 && x <= 59 && y >= 31 && y <= 50) {
                    game.shop.sell(game.shop.scanner);
                } else if (x >= 65 && x <= 84 && y >= 31 && y <= 50) {
                    game.shop.sell(game.shop.flag);
                } else if (x >= 15 && x <= 34 && y >= 56 && y <= 75) {
                    game.shop.sell(game.shop.goldenShovel);
                } else if (x >= 40 && x <= 59 && y >= 56 && y <= 75) {
                    game.shop.sell(game.shop.luckyDice);
                } else if (x >= 65 && x <= 84 && y >= 56 && y <= 75) {
                    game.shop.sell(game.shop.miniBomb);
                } else if (clickOutsideShop(x, y)) {
                    menu.displayGameBoard();
                }
                break;
            case ITEM_HELP:
                if (Menu.BUTTONS.get(ButtonID.CONFIRM).has(x, y)) {
                    menu.displayGameBoard();
                    menu.displayShop();
                }
                break;
            case GAME_OVER:
                if (Menu.BUTTONS.get(ButtonID.CLOSE).has(x, y)) {
                    game.redrawAllCells();
                    Screen.set(ScreenType.GAME_BOARD);
                } else if (Menu.BUTTONS.get(ButtonID.RETURN).has(x, y)) {
                    menu.displayMain();
                } else if (Menu.BUTTONS.get(ButtonID.AGAIN).has(x, y)) {
                    game.createGame();
                } else if (x >= 18 && x <= 37 && y >= 60 && y <= 64) {
                    menu.displayScoreDetail();
                }
                break;
            case OPTIONS:
                if (Menu.BUTTONS.get(ButtonID.BACK).has(x, y)) {
                    menu.displayMain();
                } else if (x >= 49 && x <= 53 && y >= 22 && y <= 28) {
                    menu.changeDifficulty(false);
                } else if (x >= 93 && x <= 97 && y >= 22 && y <= 28) {
                    menu.changeDifficulty(true);
                } else if (x >= 80 && x <= 91 && y >= 51 && y <= 57) {
                    menu.switchAutoBuyFlags();
                }
                break;
            case SCORE_DETAIL:
                if (Menu.BUTTONS.get(ButtonID.CONFIRM).has(x, y)) {
                    menu.displayGameBoard();
                    menu.displayGameOver(game.lastResultIsVictory, 0);
                }
                break;
            case ABOUT:
            case RECORDS:
                if (Menu.BUTTONS.get(ButtonID.BACK).has(x, y)) {
                    menu.displayMain();
                }
                break;
            default:
                break;
        }
    }

    private void rightClickAction(int x, int y, ScreenType screenType) {
        switch (screenType) {
            case GAME_BOARD:
                if (game.isStopped) {
                    menu.displayGameOver(game.lastResultIsVictory, 0);
                    Screen.set(ScreenType.GAME_OVER);
                } else {
                    game.setFlag(x / 10, y / 10, true); // works only if tile is closed
                    game.openRest(x / 10, y / 10);                       // works only if tile is open
                    // above two actions don't interfere, only one will work
                }
                break;
            case SHOP:
                if (x >= 15 && x <= 34 && y >= 31 && y <= 50) {
                    menu.displayItemHelp(game.shop.allItems.get(0));
                } else if (x >= 40 && x <= 59 && y >= 31 && y <= 50) {
                    menu.displayItemHelp(game.shop.allItems.get(1));
                } else if (x >= 65 && x <= 84 && y >= 31 && y <= 50) {
                    menu.displayItemHelp(game.shop.allItems.get(2));
                } else if (x >= 15 && x <= 34 && y >= 56 && y <= 75) {
                    menu.displayItemHelp(game.shop.allItems.get(3));
                } else if (x >= 40 && x <= 59 && y >= 56 && y <= 75) {
                    menu.displayItemHelp(game.shop.allItems.get(4));
                } else if (x >= 65 && x <= 84 && y >= 56 && y <= 75) {
                    menu.displayItemHelp(game.shop.allItems.get(5));
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
                            menu.displayShop();
                        } else {
                            menu.displayGameOver(game.lastResultIsVictory, 0);
                            Screen.set(ScreenType.GAME_OVER);
                        }
                        break;
                    case SHOP:
                        menu.displayGameBoard();
                        break;
                    default:
                        break;
                }
                break;
            case ESCAPE:
                switch (screen) {
                    case GAME_BOARD:
                        if (game.isStopped) {
                            menu.displayGameOver(game.lastResultIsVictory, 0);
                            Screen.set(ScreenType.GAME_OVER);
                            break;
                        }
                    case OPTIONS:
                    case ABOUT:
                    case RECORDS:
                        menu.displayMain();
                        break;
                    case MAIN_MENU:
                        if (!game.isStopped) {
                            menu.displayGameBoard();
                        }
                        break;
                    case SHOP:
                        menu.displayGameBoard();
                        break;
                    case ITEM_HELP:
                        menu.displayGameBoard();
                        menu.displayShop();
                        break;
                    default:
                        break;
                }
                break;
            default:
                if (screen == ScreenType.GAME_BOARD && game.isStopped) {
                    menu.displayGameOver(game.lastResultIsVictory, 0);
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
