package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.graphics.Button.ButtonID;
import com.javarush.games.minesweeper.graphics.Theme;
import com.javarush.games.minesweeper.view.View;

import static com.javarush.games.minesweeper.Util.inside;

/**
 * Separate class for processing various input events.
 */

public class Controller {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    final void leftClick(int x, int y) {
        if (clickedOutsideScreen(x, y) || View.gameOver.popUpTimer > 0) return;
        leftClickAction(x, y, Screen.get());
    }

    final void rightClick(int x, int y) {
        if (clickedOutsideScreen(x, y) || View.gameOver.popUpTimer > 0) return;
        rightClickAction(x, y, Screen.get());
        // Uncomment this to check click coordinates
        // System.out.printf("%d %d%n", x, y);
    }

    private void leftClickAction(int x, int y, Screen screen) {
        game.allowCountMoves = true;
        game.allowFlagExplosion = false;
        switch (screen) {
            case MAIN_MENU:
                if (View.BUTTONS_CACHE.get(ButtonID.START).covers(x, y)) {
                    game.createGame();
                } else if (View.BUTTONS_CACHE.get(ButtonID.OPTIONS).covers(x, y)) {
                    View.options.display();
                } else if (View.BUTTONS_CACHE.get(ButtonID.ABOUT).covers(x, y)) {
                    View.about.display();
                } else if (View.BUTTONS_CACHE.get(ButtonID.RECORDS).covers(x, y)) {
                    View.records.display();
                }
                break;
            case BOARD:
                if (game.isStopped) {
                    View.gameOver.display(game.lastResultIsVictory, 0);
                    Screen.set(Screen.GAME_OVER);
                    return;
                }

                Cell cell = game.field[y / 10][x / 10];
                if (!cell.isFlagged || game.shop.scanner.isActivated()) {
                    game.openCell(x / 10, y / 10);
                }

                game.deactivateExpiredItems();

                break;
            case SHOP:
                if (clickedOutsideShop(x, y)) View.board.display();
                ShopItem item = game.shop.getClickedItem(x, y);
                game.shop.sellAndRememberLastClick(item);
                break;
            case ITEM_HELP:
                if (View.BUTTONS_CACHE.get(ButtonID.CONFIRM).covers(x, y)) {
                    View.board.refresh();
                    View.shop.display();
                }
                break;
            case GAME_OVER:
                if (View.BUTTONS_CACHE.get(ButtonID.CLOSE).covers(x, y)) {
                    View.board.display();
                } else if (View.BUTTONS_CACHE.get(ButtonID.RETURN).covers(x, y)) {
                    View.main.display();
                } else if (View.BUTTONS_CACHE.get(ButtonID.AGAIN).covers(x, y)) {
                    game.createGame();
                } else if (View.gameOver.scoreArea.covers(x, y)) {
                    View.score.display();
                }
                break;
            case OPTIONS:
                if (View.BUTTONS_CACHE.get(ButtonID.BACK).covers(x, y)) {
                    View.main.display();
                } else if (View.options.difficultyDownArea.covers(x, y)) {
                    View.options.changeDifficulty(false);
                    View.options.animateLeftArrow();
                } else if (View.options.difficultyUpArea.covers(x, y)) {
                    View.options.changeDifficulty(true);
                    View.options.animateRightArrow();
                } else if (View.options.autoBuyFlagsArea.covers(x, y)) {
                    View.options.switchAutoBuyFlags();
                } else if (View.options.switchGameTimerArea.covers(x, y)) {
                    View.options.switchGameTimer();
                } else if (View.options.redThemeArea.covers(x, y)) {
                    Theme.setTheme(Theme.USSR);
                } else if (View.options.greenThemeArea.covers(x, y)) {
                    Theme.setTheme(Theme.MINT);
                } else if (View.options.blueThemeArea.covers(x, y)) {
                    Theme.setTheme(Theme.SKY);
                }
                break;
            case SCORE:
                if (View.BUTTONS_CACHE.get(ButtonID.CONFIRM).covers(x, y)) {
                    View.board.refresh();
                    View.gameOver.display(game.lastResultIsVictory, 0);
                }
                break;
            case ABOUT:
                if (View.BUTTONS_CACHE.get(ButtonID.BACK).covers(x, y)) {
                    View.main.display();
                } else if (View.about.prevPageArrowArea.covers(x, y)) {
                    View.about.prevPage();
                    View.options.animateLeftArrow();
                } else if (View.about.nextPageArrowArea.covers(x, y)) {
                    View.about.nextPage();
                    View.options.animateRightArrow();
                }
                break;
            case RECORDS:
                if (View.BUTTONS_CACHE.get(ButtonID.BACK).covers(x, y)) {
                    View.main.display();
                }
                break;
            default:
                break;
        }
    }

    private void rightClickAction(int x, int y, Screen screen) {
        game.allowCountMoves = true;
        switch (screen) {
            case BOARD:
                if (game.isStopped) {
                    View.gameOver.display(game.lastResultIsVictory, 0);
                    Screen.set(Screen.GAME_OVER);
                } else { // only one will work - actions don't interfere
                    game.setFlag(x / 10, y / 10, true); // works only if tile is closed
                    game.openRest(x / 10, y / 10);                // works only if tile is open
                }
                game.deactivateExpiredItems();
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
        if (View.gameOver.popUpTimer > 0) return;
        Screen screen = Screen.get();
        switch (key) {
            case SPACE:
                switch (screen) {
                    case BOARD:
                        if (!game.isStopped) {
                            View.shop.display();
                        } else {
                            View.gameOver.display(game.lastResultIsVictory, 0);
                        }
                        break;
                    case SHOP:
                        View.board.display();
                        break;
                    case GAME_OVER:
                        View.score.display();
                        break;
                    case SCORE:
                        View.gameOver.display(game.lastResultIsVictory, 0);
                        break;
                    default:
                        break;
                }
                break;
            case ESCAPE:
                switch (screen) {
                    case BOARD:
                        if (game.isStopped) {
                            View.gameOver.display(game.lastResultIsVictory, 0);
                            Screen.set(Screen.GAME_OVER);
                        } else {
                            View.main.display();
                        }
                        break;
                    case GAME_OVER:
                    case SHOP:
                        View.board.display();
                        break;
                    case SCORE:
                        View.gameOver.display(game.lastResultIsVictory, 0);
                        Screen.set(Screen.GAME_OVER);
                        break;
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
                        View.options.animateLeftArrow();
                        break;
                    case OPTIONS:
                        View.options.changeDifficulty(false);
                        View.options.animateLeftArrow();
                        break;
                    default:
                        break;
                }
                break;
            case RIGHT:
                switch (screen) {
                    case ABOUT:
                        View.about.nextPage();
                        View.options.animateRightArrow();
                        break;
                    case OPTIONS:
                        View.options.changeDifficulty(true);
                        View.options.animateRightArrow();
                        break;
                    default:
                        break;
                }
                break;
            default:
                if (screen == Screen.BOARD && game.isStopped) {
                    View.gameOver.display(game.lastResultIsVictory, 0);
                    Screen.set(Screen.GAME_OVER);
                }
                break;
        }
    }

    private boolean clickedOutsideScreen(int x, int y) {
        return (!Util.isOnScreen(x, y));
    }

    private boolean clickedOutsideShop(int x, int y) {
        boolean horizontal = inside(x, 0, 9) || inside(x, 90, 99);
        boolean vertical = inside(y, 0, 10) || inside(y, 91, 99);
        return (horizontal || vertical);
    }
}
