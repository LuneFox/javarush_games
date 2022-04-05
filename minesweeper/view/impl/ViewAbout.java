package com.javarush.games.minesweeper.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.gui.interactive.PageSelector;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.View;

public class ViewAbout extends View {
    private final Button closeButton = new Button(88, 2, 0, 0, "x", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.MAIN);
        }
    };
    final Image background = Image.cache.get(ImageType.GUI_BACKGROUND);
    final PageSelector pageSelector = Options.aboutPageSelector.linkView(this);

    public ViewAbout(MinesweeperGame game) {
        super(game);
    }

    @Override
    public void update() {
        background.draw();
        pageSelector.draw();
        drawPageContainer();
        closeButton.draw();
        super.update();
    }

    private void drawPageContainer() {
        switch (Options.aboutPageSelector.getCurrentPage()) {
            case 0:
                printPage("<Информация>",
                        "В моей версии игры\nесть магазин вещей.\nОни помогут меньше\nполагаться на удачу," +
                                "\nбольше планировать\nкаждый ход. Без них\nбудет трудновато на\nбольшой сложности!");
                break;
            case 1:
                printPage("<О магазине>",
                        "Во время игры\nклавиша [пробел]\nоткроет или закроет\nмагазин. Также вход\nпоявляется на поле.\n" +
                                "\nПравый клик по вещи\nпокажет её описание.");
                break;
            case 2:
                printPage("<Удобства>",
                        "Если около ячейки\nуже стоит равное ей\nколичество флажков,\nправый клик по ней" +
                                "\nоткроет прилегающие\nячейки автоматом,\nаналогично двойному\nклику в Windows.");
                break;
            case 3:
                printPage("<На время>",
                        "В игре на время\nуспевайте открывать\nновые ячейки, пока\nшкала вверху экрана" +
                                "\nне закончилась. Если\nвремя выйдет, мины\nвзорвутся! Скорость\nдаёт больше очков.");
                break;
            case 4:
                printPage("<Рекорды>",
                        "Чтобы попасть на\nстраницу рекордов,\nсделайте скриншот\nс вашим результатом" +
                                "\nи прикрепите его\nв комментариях.\n\n...и спасибо за игру!");
                break;
            default:
                printPage("<404>",
                        "Страница не найдена.");
                break;
        }
    }

    private void printPage(String title, String contents) {
        Printer.print(title, Color.YELLOW, Printer.CENTER, 2);
        Printer.print(contents, 3, 13);
    }
}
