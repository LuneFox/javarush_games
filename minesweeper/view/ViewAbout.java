package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.model.Strings;
import com.javarush.games.minesweeper.model.options.Options;
import com.javarush.games.minesweeper.model.options.PageSelector;
import com.javarush.games.minesweeper.view.graphics.*;

/**
 * Shows the ABOUT section of the game.
 */

public final class ViewAbout extends View {
    public PageSelector pageSelector;

    public ViewAbout() {
        this.screen = Screen.ABOUT;
        this.pageSelector = new PageSelector(7, 89, 45, 6);
    }

    @Override
    public void update() {
        Cache.get(VisualElement.WIN_MENU).draw();
        pageSelector.draw();

        switch (pageSelector.getCurrentPage()) {
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

        Cache.get(Button.ButtonID.GENERAL_BACK).draw();
        super.update();
    }

    private void printPage(String title, String contents) {
        Printer.print(title, Color.YELLOW, Printer.CENTER, 2);
        Printer.print(contents, 3, 13);
    }
}
