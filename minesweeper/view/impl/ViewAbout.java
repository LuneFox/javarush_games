package com.javarush.games.minesweeper.view.impl;

import com.javarush.games.minesweeper.Text;

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
    private final Button closeButton = new Button(88, 2, Text.BUTTON_CLOSE.getText(), this) {
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
                printPage(Text.ABOUT_INFO_TITLE.getText(), Text.ABOUT_INFO_TEXT.getText());
                break;
            case 1:
                printPage(Text.ABOUT_SHOP_TITLE.getText(), Text.ABOUT_SHOP_TEXT.getText());
                break;
            case 2:
                printPage(Text.ABOUT_CONVENIENCE_TITLE.getText(), Text.ABOUT_CONVENIENCE_TEXT.getText());
                break;
            case 3:
                printPage(Text.ABOUT_TIMER_TITLE.getText(), Text.ABOUT_TIMER_TEXT.getText());
                break;
            case 4:
                printPage(Text.ABOUT_RECORDS_TITLE.getText(), Text.ABOUT_RECORDS_TEXT.getText());
                break;
            default:
                printPage(Text.ABOUT_404_TITLE.getText(), Text.ABOUT_404_TEXT.getText());
                break;
        }
    }

    /** Печатает страницу. */
    private void printPage(String title, String contents) {
        Printer.print(title, Theme.LABEL.getColor(), Printer.CENTER, 2);
        Printer.print(contents, 3, 13);
    }
}
