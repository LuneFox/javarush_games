package com.javarush.games.minesweeper.controller;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Message;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.options.Options;
import com.javarush.games.minesweeper.model.player.Score;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Cache;

import java.util.stream.Stream;

import static com.javarush.games.minesweeper.Util.inside;

public class ControlStrategyFactory {
    public ControlStrategy createStrategy(Screen screen) {
        switch (screen) {
            case ABOUT:
                return new ControlStrategy() {
                    @Override
                    public void leftClick(int x, int y) {
                        if (Cache.get(Button.ButtonID.GENERAL_CLOSE).checkLeftTouch(x, y)) {
                            Screen.setActive(Screen.MAIN);
                        }
                        Options.aboutPageSelector.checkLeftTouch(x, y);
                    }

                    @Override
                    public void pressRight() {
                        Options.aboutPageSelector.nextPage();
                    }

                    @Override
                    public void pressLeft() {
                        Options.aboutPageSelector.prevPage();
                    }

                    @Override
                    public void pressEsc() {
                        Screen.setActive(Screen.MAIN);
                    }
                };

            case BOARD:
                return new ControlStrategy() {
                    final private MinesweeperGame game = MinesweeperGame.getInstance();

                    @Override
                    public void leftClick(int x, int y) {
                        int gridX = x / 10;
                        int gridY = y / 10;

                        if (game.isStopped) {
                            Screen.setActive(Screen.GAME_OVER);
                            return;
                        }
                        Cell cell = game.field.get()[gridY][gridX];
                        if (!cell.isFlagged || game.shop.scanner.isActivated()) {
                            game.openCell(gridX, gridY);
                        }
                        game.shop.deactivateExpiredItems();
                    }

                    @Override
                    public void rightClick(int x, int y) {
                        int gridX = x / 10;
                        int gridY = y / 10;

                        if (game.isStopped) {
                            Screen.setActive(Screen.GAME_OVER);
                            return;
                        }
                        game.setFlag(gridX, gridY, true);  // works only on closed tiles
                        game.openSurroundingCells(gridX, gridY);       // works only on open tiles
                        game.shop.deactivateExpiredItems();
                    }

                    @Override
                    public void pressSpace() {
                        if (!game.isStopped) {
                            Screen.setActive(Screen.SHOP);
                        } else {
                            Screen.setActive(Screen.GAME_OVER);
                        }
                    }

                    @Override
                    public void pressEsc() {
                        if (game.isStopped) {
                            Screen.setActive(Screen.GAME_OVER);
                        } else {
                            Screen.setActive(Screen.MAIN);
                        }
                    }

                    @Override
                    public void pressPause() {
                        pressEsc();
                    }

                    @Override
                    public void pressOther() {
                        if (game.isStopped) {
                            Screen.setActive(Screen.GAME_OVER);
                        }
                    }
                };

            case GAME_OVER:
                return new ControlStrategy() {
                    final private MinesweeperGame game = MinesweeperGame.getInstance();

                    @Override
                    public void leftClick(int x, int y) {
                        if (Cache.get(Button.ButtonID.GAME_OVER_HIDE).checkLeftTouch(x, y)) {
                            Screen.setActive(Screen.BOARD);
                        } else if (Cache.get(Button.ButtonID.GAME_OVER_RETURN).checkLeftTouch(x, y)) {
                            Screen.setActive(Screen.MAIN);
                        } else if (Cache.get(Button.ButtonID.GAME_OVER_QUESTION).checkLeftTouch(x, y)) {
                            Screen.setActive(Screen.SCORE);
                        } else if (Cache.get(Button.ButtonID.GAME_OVER_AGAIN).checkLeftTouch(x, y)) {
                            game.startNewGame();
                        }
                    }

                    @Override
                    public void pressSpace() {
                        Screen.setActive(Screen.SCORE);
                    }

                    @Override
                    public void pressEsc() {
                        Screen.setActive(Screen.BOARD);
                    }
                };

            case ITEM_HELP:
                return new ControlStrategy() {
                    @Override
                    public void leftClick(int x, int y) {
                        if (Cache.get(Button.ButtonID.GENERAL_CLOSE).checkLeftTouch(x, y)) {
                            Screen.setActive(Screen.SHOP);
                        }
                    }

                    @Override
                    public void pressEsc() {
                        Screen.setActive(Screen.SHOP);
                    }
                };

            case MAIN:
                return new ControlStrategy() {
                    final private MinesweeperGame game = MinesweeperGame.getInstance();

                    @Override
                    public void leftClick(int x, int y) {
                        if (Cache.get(Button.ButtonID.MAIN_MENU_START).checkLeftTouch(x, y)) {
                            game.startNewGame();
                        } else if (Cache.get(Button.ButtonID.MAIN_MENU_OPTIONS).checkLeftTouch(x, y)) {
                            Screen.setActive(Screen.OPTIONS);
                        } else if (Cache.get(Button.ButtonID.MAIN_MENU_ABOUT).checkLeftTouch(x, y)) {
                            Screen.setActive(Screen.ABOUT);
                        } else if (Cache.get(Button.ButtonID.MAIN_MENU_RECORDS).checkLeftTouch(x, y)) {
                            Screen.setActive(Screen.RECORDS);
                        }
                    }

                    @Override
                    public void pressEsc() {
                        if (!game.isStopped) {
                            Screen.setActive(Screen.BOARD);
                        }
                    }
                };

            case OPTIONS:
                return new ControlStrategy() {
                    @Override
                    public void leftClick(int x, int y) {
                        if (Cache.get(Button.ButtonID.GENERAL_CLOSE).checkLeftTouch(x, y)) {
                            Screen.setActive(Screen.MAIN);
                            Message.show("Сохранено");
                        }

                        Stream.of(
                                Options.difficultySelector,
                                Options.autoBuyFlagsSelector,
                                Options.timerEnabledSelector,
                                Options.displayMessageSelector,
                                Options.themeSelector
                        ).forEach(drawableObject -> drawableObject.checkLeftTouch(x, y));
                    }

                    @Override
                    public void pressRight() {
                        Options.difficultySelector.difficultyUp();
                    }

                    @Override
                    public void pressLeft() {
                        Options.difficultySelector.difficultyDown();
                    }

                    @Override
                    public void pressEsc() {
                        Screen.setActive(Screen.MAIN);
                        Message.show("Сохранено");
                    }
                };

            case RECORDS:
                return new ControlStrategy() {
                    @Override
                    public void leftClick(int x, int y) {
                        if (Cache.get(Button.ButtonID.GENERAL_CLOSE).checkLeftTouch(x, y)) {
                            Screen.setActive(Screen.MAIN);
                        }
                    }

                    @Override
                    public void pressEsc() {
                        Screen.setActive(Screen.MAIN);
                    }
                };

            case SCORE:
                return new ControlStrategy() {
                    @Override
                    public void leftClick(int x, int y) {
                        if (Cache.get(Button.ButtonID.GENERAL_CONFIRM).checkLeftTouch(x, y)) {
                            Screen.setActive(Screen.GAME_OVER);
                        }
                        Score.Table.pageSelector.checkLeftTouch(x, y);
                    }

                    @Override
                    public void pressSpace() {
                        Screen.setActive(Screen.GAME_OVER);
                    }

                    @Override
                    public void pressEsc() {
                        Screen.setActive(Screen.GAME_OVER);
                    }

                    @Override
                    public void pressRight() {
                        Score.Table.pageSelector.nextPage();
                    }

                    @Override
                    public void pressLeft() {
                        Score.Table.pageSelector.prevPage();
                    }
                };

            case SHOP:
                return new ControlStrategy() {
                    final private MinesweeperGame game = MinesweeperGame.getInstance();

                    @Override
                    public void leftClick(int x, int y) {
                        if (clickedOutsideShopWindow(x, y)) {
                            Screen.setActive(Screen.BOARD);
                            return;
                        }
                        game.shop.showCase.checkLeftTouch(x, y);
                        game.shop.showCase.header.checkLeftTouch(x, y);
                    }

                    @Override
                    public void rightClick(int x, int y) {
                        game.shop.showCase.checkRightTouch(x, y);
                        game.shop.showCase.header.checkRightTouch(x, y);
                    }

                    @Override
                    public void pressSpace() {
                        Screen.setActive(Screen.BOARD);
                    }

                    @Override
                    public void pressEsc() {
                        Screen.setActive(Screen.BOARD);
                    }

                    private boolean clickedOutsideShopWindow(int x, int y) {
                        boolean horizontal = inside(x, 0, 9) || inside(x, 90, 99);
                        boolean vertical = inside(y, 0, 10) || inside(y, 91, 99);
                        return (horizontal || vertical);
                    }
                };

            default:
                return new ControlStrategy() {

                };
        }
    }
}
