package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Cache;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.FloatingImage;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageID;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.gui.interactive.ButtonID;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.Strings;
import com.javarush.games.minesweeper.model.player.Score;
import com.javarush.games.minesweeper.model.shop.ShopItem;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

import static com.javarush.games.minesweeper.model.player.Score.Table.*;

public class ViewFactory {
    public View createView(Phase phase) {
        switch (phase) {
            case ABOUT:
                return new View(phase) {
                    @Override
                    public void update() {
                        Cache.get(ImageID.WIN_MENU).draw();
                        Options.aboutPageSelector.draw();

                        switch (Options.aboutPageSelector.getCurrentPage()) {
                            case 0:
                                printPage("Информация",
                                        "В моей версии игры\nесть магазин вещей.\nОни помогут меньше\nполагаться на удачу," +
                                                "\nбольше планировать\nкаждый свой ход.\nЦены зависят от\nсложности уровня.");
                                break;
                            case 1:
                                printPage("О магазине",
                                        "Во время игры\nклавиша [пробел]\nоткроет или закроет\nмагазин.\n" +
                                                "\nПравый клик по вещи\nпокажет её описание.");
                                break;
                            case 2:
                                printPage("Удобства",
                                        "Если около ячейки\nуже стоит равное ей\nколичество флажков,\nправый клик по ней" +
                                                "\nоткроет прилегающие\nячейки автоматом,\nаналогично двойному\nклику в Windows.");
                                break;
                            case 3:
                                printPage("О счёте",
                                        "Чтобы посмотреть\nподробности счёта\nв конце игры,\nнажмите на знак" +
                                                "\nвопроса рядом со\nсмайликом или на\nклавишу [пробел].\n");
                                break;
                            case 4:
                                printPage("На время",
                                        "В игре на время\nуспевайте открывать\nновые ячейки, пока\nшкала вверху экрана" +
                                                "\nне закончилась. Если\nвремя выйдет, мины\nвзорвутся! Скорость\nдаёт больше очков.");
                                break;
                            case 5:
                                printPage("Рекорды",
                                        "Чтобы попасть на\nстраницу рекордов,\nсделайте скриншот\nс вашим результатом" +
                                                "\nи прикрепите его\nв комментариях.\n\n...и спасибо за игру!");
                                break;
                            default:
                                printPage("404",
                                        "Страница не найдена.");
                                break;
                        }

                        Cache.get(ButtonID.GENERAL_CLOSE).draw();
                        super.update();
                    }

                    private void printPage(String title, String contents) {
                        Printer.print(title, Color.YELLOW, Printer.CENTER, 2);
                        Printer.print(contents, 3, 13);
                    }
                };

            case BOARD:
                return new View(phase) {
                    @Override
                    public void update() {
                        if (!(Phase.isActive(Phase.SHOP))) game.display.setInterlaceEnabled(true);
                        game.checkTimeOut();
                        game.field.draw();
                        if (game.shop.allItems.get(1).isActivated()) {
                            Cache.get(ImageID.WIN_BOARD_TRANSPARENT_FRAME).replaceColor(Color.BLUE, 3);
                            Cache.get(ImageID.WIN_BOARD_TRANSPARENT_FRAME).draw();
                        } else if (game.shop.allItems.get(5).isActivated()) {
                            Cache.get(ImageID.WIN_BOARD_TRANSPARENT_FRAME).replaceColor(Color.RED, 3);
                            Cache.get(ImageID.WIN_BOARD_TRANSPARENT_FRAME).draw();
                        }
                        game.timer.draw();
                        game.shop.goldenShovel.statusBar.draw();
                        game.shop.luckyDice.statusBar.draw();
                        super.update();
                    }
                };

            case GAME_OVER:
                return new View(phase) {
                    @Override
                    public void update() {
                        if (game.gameOverShowDelay > 0) {
                            game.field.draw();
                            game.gameOverShowDelay--;
                            super.update();
                            return;
                        }

                        game.field.draw();
                        if (game.isVictory) {
                            Cache.get(ImageID.WIN_VICTORY).draw(Image.CENTER, Image.CENTER);
                            Cache.get(ImageID.FACE_HAPPY).draw(Image.CENTER, Image.CENTER);
                            Printer.print("победа!", Color.YELLOW, 18, 33);
                        } else {
                            Cache.get(ImageID.WIN_GAME_OVER).draw(Image.CENTER, Image.CENTER);
                            Cache.get(ImageID.FACE_SAD).draw(Image.CENTER, Image.CENTER);
                            Printer.print("не повезло!", Color.YELLOW, 18, 33);
                        }
                        Printer.print("счёт: " + total, Options.developerMode ? Color.RED : Color.LIGHTGOLDENRODYELLOW, 28, 57);
                        Cache.get(ButtonID.GAME_OVER_AGAIN).draw();
                        Cache.get(ButtonID.GAME_OVER_RETURN).draw();
                        Cache.get(ButtonID.GAME_OVER_HIDE).draw();
                        Cache.get(ButtonID.GAME_OVER_QUESTION).draw();
                        super.update();
                    }
                };

            case ITEM_HELP:
                return new View(phase) {
                    @Override
                    public void update() {
                        ShopItem displayItem = game.shop.helpDisplayItem;
                        Strings.generateNewShieldDescription();
                        Cache.get(ImageID.WIN_MENU).draw();
                        displayItem.icon.setPosition(5, 10);
                        displayItem.icon.draw();
                        if (displayItem.id == ShopItem.ID.SHIELD) {
                            displayItem.description = Strings.generateNewShieldDescription().toString();
                        }
                        Printer.print("[" + displayItem.name + "]", Color.YELLOW, 25, 14);
                        Printer.print(displayItem.description, 4, 30);
                        Cache.get(ButtonID.GENERAL_CLOSE).draw();
                        super.update();
                    }
                };

            case MAIN:
                return new View(phase) {
                    private String quote;
                    private Date lastQuoteDate;
                    private final LinkedList<String> QUOTES = new LinkedList<>();

                    {
                        Collections.addAll(QUOTES, Strings.QUOTES);
                        lastQuoteDate = new Date();
                        quote = QUOTES.get(0);
                    }

                    @Override
                    public void update() {
                        Cache.get(ImageID.WIN_MENU).draw();
                        Printer.print("JavaRush", Theme.MAIN_MENU_VERSION.getColor(), Image.CENTER, 2);
                        ((FloatingImage) Cache.get(ImageID.FLO_LOGO)).draw(2.8, Image.CENTER, 8);
                        Cache.get(ButtonID.MAIN_MENU_OPTIONS).draw();
                        Cache.get(ButtonID.MAIN_MENU_ABOUT).draw();
                        Cache.get(ButtonID.MAIN_MENU_RECORDS).draw();
                        printTopScore();

                        Button startButton = Cache.get(ButtonID.MAIN_MENU_START);
                        if (game.isStopped || game.isFirstMove) {
                            printRandomQuote();
                            if (!startButton.getText().equals("старт")) startButton.replaceText(36, "старт");
                        } else {
                            printResumeGame();
                            if (!startButton.getText().equals("заново")) startButton.replaceText(36, "заново");
                        }

                        Cache.get(ButtonID.MAIN_MENU_START).draw();
                        super.update();
                    }

                    private void printRandomQuote() {
                        if (new Date().getTime() - lastQuoteDate.getTime() > 30000) {
                            quote = QUOTES.get(game.getRandomNumber(QUOTES.size()));
                            lastQuoteDate = new Date();
                        }
                        Printer.print(quote, Theme.MAIN_MENU_QUOTE_BACK.getColor(), 5, 44); // shadow
                        Printer.print(quote, Theme.MAIN_MENU_QUOTE_FRONT.getColor(), 4, 44);
                    }

                    private void printResumeGame() {
                        String resume = "Игра приостановлена\nESC - продолжить";
                        Printer.print(resume, Theme.MAIN_MENU_QUOTE_BACK.getColor(), 5, 44); // shadow
                        Printer.print(resume, Theme.LABEL.getColor(), 4, 44);
                    }

                    private void printTopScore() {
                        if (game.player.score.getTopScore() > 0) {
                            Printer.print("счёт: " + game.player.score.getTopScore() + "\n" + game.player.getTitle(),
                                    Options.developerMode ? Color.RED : Color.LIGHTGOLDENRODYELLOW, 4, 65);
                        }
                    }
                };

            case OPTIONS:
                return new View(phase) {
                    @Override
                    public void update() {
                        Cache.get(ImageID.WIN_MENU).draw();
                        Printer.print("настройки", Color.YELLOW, Printer.CENTER, 2);

                        Printer.print("сложность", 2, Options.difficultySelector.y - 1);
                        Options.difficultySelector.draw();

                        Printer.print("покупка флажков", 2, Options.autoBuyFlagsSelector.y - 1);
                        Options.autoBuyFlagsSelector.draw();

                        Printer.print("игра на время", 2, Options.timerEnabledSelector.y - 1);
                        Options.timerEnabledSelector.draw();

                        Printer.print("Сообщения:", 2, Options.displayMessageSelector.y - 1);
                        Options.displayMessageSelector.draw();

                        Printer.print("тема: " + Theme.getCurrentName(), 2, Options.themeSelector.y);
                        Options.themeSelector.draw();

                        Cache.get(ButtonID.GENERAL_CLOSE).draw();
                        super.update();
                    }
                };

            case RECORDS:
                return new View(phase) {
                    @Override
                    public void update() {
                        Cache.get(ImageID.WIN_MENU).draw();
                        Printer.print(Strings.RECORDS[0], Color.YELLOW, Printer.CENTER, 2);
                        Cache.get(ButtonID.GENERAL_CLOSE).draw();
                        drawPrizeCups();
                        drawEntries();
                    }

                    private void drawPrizeCups() {
                        Image prizeCup = Cache.get(ImageID.MENU_CUP);
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
                            prizeCup.draw(2, 20 + (20 * i));
                        }
                    }

                    private void drawEntries() {
                        Color[] colors = new Color[]{Color.WHITE, Color.GOLD, Color.SILVER, Color.PALEGOLDENROD};
                        Printer.print(Strings.RECORDS[1], colors[1], 18, 18);
                        Printer.print(Strings.RECORDS[2], colors[0], 94, 27, true);
                        Printer.print(Strings.RECORDS[3], colors[2], 18, 38);
                        Printer.print(Strings.RECORDS[4], colors[0], 94, 47, true);
                        Printer.print(Strings.RECORDS[5], colors[3], 18, 58);
                        Printer.print(Strings.RECORDS[6], colors[0], 94, 67, true);
                        super.update();
                    }
                };

            case SCORE:
                return new View(phase) {
                    @Override
                    public void update() {
                        Cache.get(ImageID.WIN_MENU).draw();
                        Score.Table.pageSelector.draw();

                        String minesScoreDetail = minesCount + "*" + 20 * difficulty + " = ";
                        String luckDetail = diceAvgLuck + "*" + diceLuckyCells + "*" + difficulty + " = " + scoreDice;
                        String moneyScoreDetail = moneyLeftOver + "*" + difficulty + " = ";
                        String cellScoreDetail = cellsCount + "*" + Options.difficulty + " = ";
                        String shieldScoreDetail = penaltyShields == 0 ? "" : penaltyShields + "*-" + (150 * (difficulty / 5)) + " = ";
                        String youLost = "не учтено";

                        switch (Score.Table.pageSelector.getCurrentPage()) {
                            case 0:
                                Printer.print("подробности счёта", Color.YELLOW, Printer.CENTER, 2);
                                Printer.print("ячейки:\nкубик:\nмины:\nзолото:\nщиты:\nскорость:\n\nитого:", 3, 13);

                                Printer.print(
                                        (total +
                                                "\n\n" + scoreTimer +
                                                "\n" + (shieldScoreDetail + scoreLost) +
                                                "\n" + (victory ? (moneyScoreDetail + scoreMoney) : youLost) +
                                                "\n" + (victory ? (minesScoreDetail + scoreMines) : youLost) +
                                                "\n" + scoreDice +
                                                "\n" + cellScoreDetail + cellsCount * difficulty),
                                        Options.developerMode ? Color.RED : Color.LIGHTGOLDENRODYELLOW, 94, 13, true);
                                break;
                            case 1:
                                Printer.print("очки кубика удачи", Color.YELLOW, Printer.CENTER, 2);
                                Printer.print("средняя удача:\nзатронуто ячеек:\nбонус сложности:\n\nсуммарно:", 3, 13);
                                Printer.print((luckDetail +
                                                "\n\n\n" + difficulty +
                                                "\n" + diceLuckyCells +
                                                "\n" + diceAvgLuck),
                                        Options.developerMode ? Color.RED : Color.LIGHTGOLDENRODYELLOW, 94, 13, true);
                                break;
                            default:
                                break;
                        }
                        Cache.get(ButtonID.GENERAL_CONFIRM).draw();
                        super.update();
                    }
                };

            case SHOP:
                return new View(phase) {
                    @Override
                    public void update() {
                        game.field.draw();
                        game.checkTimeOut();
                        game.shop.showCase.draw();
                        game.timer.draw();
                        game.shop.goldenShovel.statusBar.draw();
                        game.shop.luckyDice.statusBar.draw();
                        super.update();
                    }
                };

            default:
                return new View(phase) {
                    @Override
                    public void update() {
                        super.update();
                    }
                };
        }
    }
}
