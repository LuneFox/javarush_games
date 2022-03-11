package com.javarush.games.minesweeper.controller;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.gui.interactive.ButtonType;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.Strings;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.player.Score;

import java.util.stream.Stream;

import static com.javarush.games.minesweeper.Util.inside;

public class ControlStrategyFactory {
    public ControlStrategy createStrategy(Phase phase) {
        switch (phase) {
            case ABOUT:
                return new ControlStrategy() {
                    @Override
                    public void leftClick(int x, int y) {
                        if (Button.cache.get(ButtonType.GENERAL_CLOSE).checkLeftTouch(x, y)) {
                            Phase.setActive(Phase.MAIN);
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
                        Phase.setActive(Phase.MAIN);
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
                            Phase.setActive(Phase.GAME_OVER);
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
                            Phase.setActive(Phase.GAME_OVER);
                            return;
                        }
                        game.setFlag(gridX, gridY, true);              // works only on closed tiles
                        game.openSurroundingCells(gridX, gridY);       // works only on open tiles
                        game.shop.deactivateExpiredItems();
                    }

                    @Override
                    @DeveloperOption
                    public void pressLeft() {
                        game.autoFlag();
                    }

                    @Override
                    @DeveloperOption
                    public void pressRight() {
                        game.autoOpen();
                    }

                    @Override
                    @DeveloperOption
                    public void pressDown() {
                        game.autoScan();
                    }

                    @Override
                    @DeveloperOption
                    public void pressUp() {
                        game.skipEasyPart();
                    }

                    @Override
                    public void pressSpace() {
                        if (!game.isStopped) {
                            Phase.setActive(Phase.SHOP);
                        } else {
                            Phase.setActive(Phase.GAME_OVER);
                        }
                    }

                    @Override
                    public void pressEsc() {
                        if (game.isStopped) {
                            Phase.setActive(Phase.GAME_OVER);
                        } else {
                            Phase.setActive(Phase.MAIN);
                        }
                    }

                    @Override
                    public void pressPause() {
                        pressEsc();
                    }

                    @Override
                    public void pressOther() {
                        if (game.isStopped) {
                            Phase.setActive(Phase.GAME_OVER);
                        }
                    }
                };

            case GAME_OVER:
                return new ControlStrategy() {
                    final private MinesweeperGame game = MinesweeperGame.getInstance();

                    @Override
                    public void leftClick(int x, int y) {
                        if (Button.cache.get(ButtonType.GAME_OVER_HIDE).checkLeftTouch(x, y)) {
                            Phase.setActive(Phase.BOARD);
                        } else if (Button.cache.get(ButtonType.GAME_OVER_RETURN).checkLeftTouch(x, y)) {
                            Phase.setActive(Phase.MAIN);
                        } else if (Button.cache.get(ButtonType.GAME_OVER_QUESTION).checkLeftTouch(x, y)) {
                            Phase.setActive(Phase.SCORE);
                        } else if (Button.cache.get(ButtonType.GAME_OVER_AGAIN).checkLeftTouch(x, y)) {
                            game.startNewGame();
                        }
                    }

                    @Override
                    public void pressSpace() {
                        Phase.setActive(Phase.SCORE);
                    }

                    @Override
                    public void pressEsc() {
                        Phase.setActive(Phase.BOARD);
                    }
                };

            case ITEM_HELP:
                return new ControlStrategy() {
                    @Override
                    public void leftClick(int x, int y) {
                        if (Button.cache.get(ButtonType.GENERAL_CLOSE).checkLeftTouch(x, y)) {
                            Phase.setActive(Phase.SHOP);
                        }
                    }

                    @Override
                    public void pressEsc() {
                        Phase.setActive(Phase.SHOP);
                    }
                };

            case MAIN:
                return new ControlStrategy() {
                    final private MinesweeperGame game = MinesweeperGame.getInstance();

                    @Override
                    @DeveloperOption
                    public void leftClick(int x, int y) {
                        if (Button.cache.get(ButtonType.MAIN_MENU_START).checkLeftTouch(x, y)) {
                            game.startNewGame();
                        } else if (Button.cache.get(ButtonType.MAIN_MENU_OPTIONS).checkLeftTouch(x, y)) {
                            Phase.setActive(Phase.OPTIONS);
                        } else if (Button.cache.get(ButtonType.MAIN_MENU_ABOUT).checkLeftTouch(x, y)) {
                            Phase.setActive(Phase.ABOUT);
                        } else if (Button.cache.get(ButtonType.MAIN_MENU_RECORDS).checkLeftTouch(x, y)) {
                            Phase.setActive(Phase.RECORDS);
                        } else if (Image.cache.get(ImageType.FLO_LOGO).checkLeftTouch(x, y)) {
                            if (Options.developerCounter < 9) {
                                Options.developerCounter++;
                                PopUpMessage.show("Версия: " + Strings.VERSION);
                            } else {
                                Options.developerMode = true;
                                PopUpMessage.show("Вы разработчик!");
                            }
                        }
                    }

                    @Override
                    public void pressEsc() {
                        if (!game.isStopped) {
                            Phase.setActive(Phase.BOARD);
                        }
                    }
                };

            case OPTIONS:
                return new ControlStrategy() {
                    @Override
                    public void leftClick(int x, int y) {
                        if (Button.cache.get(ButtonType.GENERAL_CLOSE).checkLeftTouch(x, y)) {
                            Phase.setActive(Phase.MAIN);
                            PopUpMessage.show("Сохранено");
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
                        Phase.setActive(Phase.MAIN);
                        PopUpMessage.show("Сохранено");
                    }
                };

            case RECORDS:
                return new ControlStrategy() {
                    @Override
                    public void leftClick(int x, int y) {
                        if (Button.cache.get(ButtonType.GENERAL_CLOSE).checkLeftTouch(x, y)) {
                            Phase.setActive(Phase.MAIN);
                        }
                    }

                    @Override
                    public void pressEsc() {
                        Phase.setActive(Phase.MAIN);
                    }
                };

            case SCORE:
                return new ControlStrategy() {
                    @Override
                    public void leftClick(int x, int y) {
                        if (Button.cache.get(ButtonType.GENERAL_CONFIRM).checkLeftTouch(x, y)) {
                            Phase.setActive(Phase.GAME_OVER);
                        }
                        Score.Table.pageSelector.checkLeftTouch(x, y);
                    }

                    @Override
                    public void pressSpace() {
                        Phase.setActive(Phase.GAME_OVER);
                    }

                    @Override
                    public void pressEsc() {
                        Phase.setActive(Phase.GAME_OVER);
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
                            Phase.setActive(Phase.BOARD);
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
                        Phase.setActive(Phase.BOARD);
                    }

                    @Override
                    public void pressEsc() {
                        Phase.setActive(Phase.BOARD);
                    }

                    @Override
                    @DeveloperOption
                    public void pressUp() {
                        game.cheatMoreMoney();
                    }

                    @Override
                    @DeveloperOption
                    public void pressDown() {
                        game.cheatMoreTools();
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
