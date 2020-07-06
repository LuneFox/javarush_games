package com.javarush.games.minesweeper;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.graphics.Button.*;
import com.javarush.games.minesweeper.Screen.*;

/**
 * Separate class for processing various input events.
 */

class InputEvent {
    final private MinesweeperGame GAME;
    final private Menu MENU;

    InputEvent(MinesweeperGame game) {
        this.GAME = game;
        this.MENU = GAME.getMenu();
    }

    // GENERAL ATTITUDE

    final void leftClick(int x, int y) {
        if (clickOutsideScreen(x, y)) {
            return;
        }
        ScreenType screen = Screen.get();
        GAME.allowCountMoves = true;
        GAME.allowFlagExplosion = false;
        switch (screen) {
            case MAIN_MENU:
                leftClickInMenu(x, y);
                break;
            case GAME_BOARD:
                leftClickOnGameBoard(x, y);
                break;
            case SHOP:
                leftClickInShop(x, y);
                break;
            case ITEM_HELP:
                leftClickInItemHelp(x, y);
                break;
            case GAME_OVER:
                leftClickInGameOver(x, y);
                break;
            case OPTIONS:
                leftClickInOptions(x, y);
                break;
            case SCORE_DETAIL:
                leftClickInScoreDetail(x, y);
                break;
            case ABOUT:
                leftClickInAbout(x, y);
                break;
            default:
                break;
        }
        GAME.DISPLAY.draw();
    }

    final void rightClick(int x, int y) {
        ScreenType screen = Screen.get();
        if (clickOutsideScreen(x, y)) {
            return;
        }
        GAME.allowCountMoves = true;
        switch (screen) {
            case GAME_BOARD:
                rightClickOnGameBoard(x, y);
                break;
            case SHOP:
                rightClickInShop(x, y);
                break;
            default:
                break;
        }
        GAME.DISPLAY.draw();
        // System.out.println(String.format("%d %d", x, y));
    }

    final void keyPress(Key key) {
        ScreenType screen = Screen.get();

        switch (key) {
            case SPACE:
                switch (screen) {
                    case GAME_BOARD:
                        if (!GAME.isStopped) {
                            MENU.displayShop();
                        } else {
                            MENU.displayGameOver(GAME.lastResultIsVictory);
                            Screen.set(ScreenType.GAME_OVER);
                        }
                        break;
                    case SHOP:
                        MENU.displayGameBoard();
                        break;
                    default:
                        break;
                }
                break;
            case ESCAPE:
                switch (screen) {
                    case GAME_BOARD:
                        if (GAME.isStopped) {
                            MENU.displayGameOver(GAME.lastResultIsVictory);
                            Screen.set(ScreenType.GAME_OVER);
                            break;
                        }
                    case OPTIONS:
                    case ABOUT:
                        MENU.displayMain();
                        break;
                    case MAIN_MENU:
                        if (!GAME.isStopped) {
                            MENU.displayGameBoard();
                        }
                        break;
                    case SHOP:
                        MENU.displayGameBoard();
                        break;
                    case ITEM_HELP:
                        MENU.displayGameBoard();
                        MENU.displayShop();
                        break;
                    default:
                        break;
                }
                break;
            default:
                if (screen == ScreenType.GAME_BOARD && GAME.isStopped) {
                    MENU.displayGameOver(GAME.lastResultIsVictory);
                    Screen.set(ScreenType.GAME_OVER);
                }
                break;
        }
        GAME.DISPLAY.draw();
    }

    // MENU

    private void leftClickInMenu(int x, int y) {
        if (Menu.BUTTONS.get(ButtonID.START).has(x, y)) {
            GAME.createGame();
        } else if (Menu.BUTTONS.get(ButtonID.OPTIONS).has(x, y)) {
            MENU.displayOptions();
        } else if (Menu.BUTTONS.get(ButtonID.ABOUT).has(x, y)) {
            MENU.displayAbout();
        }
    }

    // BOARD

    private void leftClickOnGameBoard(int x, int y) {
        if (GAME.isStopped) {
            MENU.displayGameOver(GAME.lastResultIsVictory);
            Screen.set(ScreenType.GAME_OVER);
            return;
        }
        Tile cell = GAME.FIELD[y / 10][x / 10];
        if (!cell.isFlag || GAME.getShopScanner().isActivated) {
            GAME.openTile(x / 10, y / 10);
        }
    }

    private void rightClickOnGameBoard(int x, int y) {
        if (GAME.isStopped) {
            MENU.displayGameOver(GAME.lastResultIsVictory);
            Screen.set(ScreenType.GAME_OVER);
        } else {
            GAME.markTile(x / 10, y / 10); // works only if tile is closed
            GAME.openRest(x / 10, y / 10); // works only if tile is open
            // above two actions don't interfere, only one will work
        }
    }

    // SHOP

    private void leftClickInShop(int x, int y) {
        if (x >= 15 && x <= 34 && y >= 31 && y <= 50) {
            GAME.buyShield();
        } else if (x >= 40 && x <= 59 && y >= 31 && y <= 50) {
            GAME.buyScanner();
        } else if (x >= 65 && x <= 84 && y >= 31 && y <= 50) {
            GAME.buyFlag();
        } else if (x >= 15 && x <= 34 && y >= 56 && y <= 75) {
            GAME.buyGoldenShovel();
        } else if (x >= 40 && x <= 59 && y >= 56 && y <= 75) {
            GAME.buyLuckyDice();
        } else if (x >= 65 && x <= 84 && y >= 56 && y <= 75) {
            GAME.buyMiniBomb();
        } else if (clickOutsideShop(x, y)) {
            MENU.displayGameBoard();
        }
    }

    private boolean clickOutsideShop(int x, int y) {
        boolean horizontal = (x >= 0 && x <= 9 || x >= 90 && x <= 99);
        boolean vertical = (y >= 0 && y <= 10 || y >= 91 && y <= 99);
        return (horizontal || vertical);
    }

    private void rightClickInShop(int x, int y) {
        if (x >= 15 && x <= 34 && y >= 31 && y <= 50) {
            MENU.displayItemHelp(GAME.getAllShopItems().get(0));
        } else if (x >= 40 && x <= 59 && y >= 31 && y <= 50) {
            MENU.displayItemHelp(GAME.getAllShopItems().get(1));
        } else if (x >= 65 && x <= 84 && y >= 31 && y <= 50) {
            MENU.displayItemHelp(GAME.getAllShopItems().get(2));
        } else if (x >= 15 && x <= 34 && y >= 56 && y <= 75) {
            MENU.displayItemHelp(GAME.getAllShopItems().get(3));
        } else if (x >= 40 && x <= 59 && y >= 56 && y <= 75) {
            MENU.displayItemHelp(GAME.getAllShopItems().get(4));
        } else if (x >= 65 && x <= 84 && y >= 56 && y <= 75) {
            MENU.displayItemHelp(GAME.getAllShopItems().get(5));
        }
    }

    private void leftClickInItemHelp(int x, int y) {
        if (Menu.BUTTONS.get(ButtonID.CONFIRM).has(x, y)) {
            MENU.displayGameBoard();
            MENU.displayShop();
        }
    }

    // GAME OVER

    private void leftClickInGameOver(int x, int y) {
        if (Menu.BUTTONS.get(ButtonID.CLOSE).has(x, y)) {
            GAME.redrawAllTiles();
            Screen.set(ScreenType.GAME_BOARD);
        } else if (Menu.BUTTONS.get(ButtonID.RETURN).has(x, y)) {
            MENU.displayMain();
        } else if (Menu.BUTTONS.get(ButtonID.AGAIN).has(x, y)) {
            GAME.createGame();
        } else if (x >= 18 && x <= 37 && y >= 60 && y <= 64) {
            MENU.displayScoreDetail();
        }
    }

    // OPTIONS

    private void leftClickInOptions(int x, int y) {
        if (Menu.BUTTONS.get(ButtonID.BACK).has(x, y)) {
            MENU.displayMain();
        } else if (x >= 49 && x <= 53 && y >= 22 && y <= 28) {
            MENU.changeDifficulty(false);
        } else if (x >= 93 && x <= 97 && y >= 22 && y <= 28) {
            MENU.changeDifficulty(true);
        } else if (x >= 80 && x <= 91 && y >= 51 && y <= 57) {
            MENU.switchAutoBuyFlags();
        }
    }

    // ABOUT

    private void leftClickInAbout(int x, int y) {
        if (Menu.BUTTONS.get(ButtonID.BACK).has(x, y)) {
            MENU.displayMain();
        }
    }

    // SCORE DETAIL

    private void leftClickInScoreDetail(int x, int y) {
        if (Menu.BUTTONS.get(ButtonID.CONFIRM).has(x, y)) {
            MENU.displayGameBoard();
            MENU.displayGameOver(GAME.lastResultIsVictory);
        }
    }

    // MISC

    private boolean clickOutsideScreen(int x, int y) {
        return (x > 99 || y > 99 || x < 0 || y < 0);
    }
}
