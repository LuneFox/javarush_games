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

    // GENERAL ATTITUDE

    final void leftClick(int x, int y) {
        if (clickOutsideScreen(x, y) || menu.gameOverDisplayDelay > 0) {
            return;
        }
        ScreenType screen = Screen.get();
        game.allowCountMoves = true;
        game.allowFlagExplosion = false;
        game.hideDice();
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
    }

    final void rightClick(int x, int y) {
        ScreenType screen = Screen.get();
        if (clickOutsideScreen(x, y)) {
            return;
        }
        game.allowCountMoves = true;
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
        // System.out.println(String.format("%d %d", x, y));
    }

    final void keyPress(Key key) {
        ScreenType screen = Screen.get();

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

    // MENU

    private void leftClickInMenu(int x, int y) {
        if (Menu.BUTTONS.get(ButtonID.START).has(x, y)) {
            game.createGame();
        } else if (Menu.BUTTONS.get(ButtonID.OPTIONS).has(x, y)) {
            menu.displayOptions();
        } else if (Menu.BUTTONS.get(ButtonID.ABOUT).has(x, y)) {
            menu.displayAbout();
        }
    }

    // BOARD

    private void leftClickOnGameBoard(int x, int y) {
        if (game.isStopped) {
            menu.displayGameOver(game.lastResultIsVictory, 0);
            Screen.set(ScreenType.GAME_OVER);
            return;
        }
        Tile cell = game.field[y / 10][x / 10];
        if (!cell.isFlag || game.getShopScanner().isActivated) {
            game.openTile(x / 10, y / 10);
        }
    }

    private void rightClickOnGameBoard(int x, int y) {
        if (game.isStopped) {
            menu.displayGameOver(game.lastResultIsVictory, 0);
            Screen.set(ScreenType.GAME_OVER);
        } else {
            game.markTile(x / 10, y / 10, true); // works only if tile is closed
            game.openRest(x / 10, y / 10);                       // works only if tile is open
            // above two actions don't interfere, only one will work
        }
    }

    // SHOP

    private void leftClickInShop(int x, int y) {
        if (x >= 15 && x <= 34 && y >= 31 && y <= 50) {
            game.buyShield();
        } else if (x >= 40 && x <= 59 && y >= 31 && y <= 50) {
            game.buyScanner();
        } else if (x >= 65 && x <= 84 && y >= 31 && y <= 50) {
            game.buyFlag();
        } else if (x >= 15 && x <= 34 && y >= 56 && y <= 75) {
            game.buyGoldenShovel();
        } else if (x >= 40 && x <= 59 && y >= 56 && y <= 75) {
            game.buyLuckyDice();
        } else if (x >= 65 && x <= 84 && y >= 56 && y <= 75) {
            game.buyMiniBomb();
        } else if (clickOutsideShop(x, y)) {
            menu.displayGameBoard();
        }
    }

    private boolean clickOutsideShop(int x, int y) {
        boolean horizontal = (x >= 0 && x <= 9 || x >= 90 && x <= 99);
        boolean vertical = (y >= 0 && y <= 10 || y >= 91 && y <= 99);
        return (horizontal || vertical);
    }

    private void rightClickInShop(int x, int y) {
        if (x >= 15 && x <= 34 && y >= 31 && y <= 50) {
            menu.displayItemHelp(game.getAllShopItems().get(0));
        } else if (x >= 40 && x <= 59 && y >= 31 && y <= 50) {
            menu.displayItemHelp(game.getAllShopItems().get(1));
        } else if (x >= 65 && x <= 84 && y >= 31 && y <= 50) {
            menu.displayItemHelp(game.getAllShopItems().get(2));
        } else if (x >= 15 && x <= 34 && y >= 56 && y <= 75) {
            menu.displayItemHelp(game.getAllShopItems().get(3));
        } else if (x >= 40 && x <= 59 && y >= 56 && y <= 75) {
            menu.displayItemHelp(game.getAllShopItems().get(4));
        } else if (x >= 65 && x <= 84 && y >= 56 && y <= 75) {
            menu.displayItemHelp(game.getAllShopItems().get(5));
        }
    }

    private void leftClickInItemHelp(int x, int y) {
        if (Menu.BUTTONS.get(ButtonID.CONFIRM).has(x, y)) {
            menu.displayGameBoard();
            menu.displayShop();
        }
    }

    // GAME OVER

    private void leftClickInGameOver(int x, int y) {
        if (Menu.BUTTONS.get(ButtonID.CLOSE).has(x, y)) {
            game.redrawAllTiles();
            Screen.set(ScreenType.GAME_BOARD);
        } else if (Menu.BUTTONS.get(ButtonID.RETURN).has(x, y)) {
            menu.displayMain();
        } else if (Menu.BUTTONS.get(ButtonID.AGAIN).has(x, y)) {
            game.createGame();
        } else if (x >= 18 && x <= 37 && y >= 60 && y <= 64) {
            menu.displayScoreDetail();
        }
    }

    // OPTIONS

    private void leftClickInOptions(int x, int y) {
        if (Menu.BUTTONS.get(ButtonID.BACK).has(x, y)) {
            menu.displayMain();
        } else if (x >= 49 && x <= 53 && y >= 22 && y <= 28) {
            menu.changeDifficulty(false);
        } else if (x >= 93 && x <= 97 && y >= 22 && y <= 28) {
            menu.changeDifficulty(true);
        } else if (x >= 80 && x <= 91 && y >= 51 && y <= 57) {
            menu.switchAutoBuyFlags();
        }
    }

    // ABOUT

    private void leftClickInAbout(int x, int y) {
        if (Menu.BUTTONS.get(ButtonID.BACK).has(x, y)) {
            menu.displayMain();
        }
    }

    // SCORE DETAIL

    private void leftClickInScoreDetail(int x, int y) {
        if (Menu.BUTTONS.get(ButtonID.CONFIRM).has(x, y)) {
            menu.displayGameBoard();
            menu.displayGameOver(game.lastResultIsVictory, 0);
        }
    }

    // MISC

    private boolean clickOutsideScreen(int x, int y) {
        return (x > 99 || y > 99 || x < 0 || y < 0);
    }
}
