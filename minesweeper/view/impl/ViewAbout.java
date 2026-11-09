package com.javarush.games.minesweeper.view.impl;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.gui.interactive.PageSelector;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.View;

/** Экран с информацией об игре. */
public class ViewAbout extends View {
    /** Переключатель страниц экрана. */
    public static final PageSelector pageSelector = new PageSelector(27, 89, 44, 5);

    /** Кнопка закрытия экрана. */
    private final Button closeButton = new Button(88, 2, "x", this) {
        /** Обрабатывает левый клик. */
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.MAIN);
        }
    };
    /** Фоновое изображение. */
    final Image background = Image.cache.get(ImageType.GUI_BACKGROUND);

    /** Создаёт экземпляр ViewAbout. */
    public ViewAbout(MinesweeperGame game) {
        super(game);
        linkObject(pageSelector);
    }

    /** Обновляет состояние. */
    @Override
    public void update() {
        background.draw();
        pageSelector.draw();
        drawPageContainer();
        closeButton.draw();
        super.update();
    }

    /** Рисует содержимое страницы. */
    private void drawPageContainer() {
        switch (pageSelector.getCurrentPage()) {
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
                                "\nоткроет прилегающие\nячейки автоматом,\nаналогично парному\nклику в Windows.");
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

    /** Печатает страницу. */
    private void printPage(String title, String contents) {
        Printer.print(title, Theme.LABEL.getColor(), Printer.CENTER, 2);
        Printer.print(contents, 3, 13);
    }
}
