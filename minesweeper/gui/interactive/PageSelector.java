package com.javarush.games.minesweeper.gui.interactive;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.model.InteractiveObject;

import java.util.ArrayList;
import java.util.List;

/** Переключатель страниц с двумя стрелками. */
public class PageSelector extends InteractiveObject {
    /** Все переключатели страниц. */
    public static final List<PageSelector> allSelectors = new ArrayList<>();
    /** Страница по умолчанию. */
    private static final int DEFAULT_PAGE = 0;
    /** Максимальная страница. */
    private final int maxPage;
    /** Текущая страница. */
    private int currentPage;
    /** Стрелка предыдущей страницы. */
    private final Arrow prevPageArrow;
    /** Стрелка следующей страницы. */
    private final Arrow nextPageArrow;

    /** Сбрасывает все переключатели страниц. */
    public static void resetAllSelectors() {
        allSelectors.forEach(PageSelector::setDefaultPage);
    }

    /** Создаёт экземпляр PageSelector. */
    public PageSelector(int x, int y, int width, int maxPage) {
        super(x, y);
        this.maxPage = maxPage - 1;
        currentPage = DEFAULT_PAGE;
        prevPageArrow = Arrow.createLeftArrow(x, y);
        nextPageArrow = Arrow.createRightArrow(x + width - prevPageArrow.width, y);
        this.width = width;
        this.height = prevPageArrow.height;
        allSelectors.add(this);
    }

    /** Переключает на предыдущую страницу. */
    public void selectPrevPage() {
        prevPageArrow.animate();
        if (currentPage > 0) {
            currentPage--;
        }
    }

    /** Переключает на следующую страницу. */
    public void selectNextPage() {
        nextPageArrow.animate();
        if (currentPage < maxPage) {
            currentPage++;
        }
    }

    /** Рисует объект на экране. */
    @Override
    public void draw() {
        printPageIndex();
        drawPageArrows();
    }

    /** Печатает номер страницы. */
    private void printPageIndex() {
        String indexText = ((currentPage + 1) + " / " + (maxPage + 1));
        int textHorizontalPosition = getTextHorizontalPosition(indexText);
        Printer.print(indexText, Color.WHITE, textHorizontalPosition, y - 1);
    }

    /** Возвращает горизонтальную позицию текста. */
    private int getTextHorizontalPosition(String text) {
        int textWidth = Printer.calculateWidth(text);
        return getMiddlePoint() - (textWidth / 2);
    }

    /** Рисует стрелки страниц. */
    private void drawPageArrows() {
        prevPageArrow.draw();
        nextPageArrow.draw();
    }

    /** Обрабатывает левый клик. */
    @Override
    public void onLeftClick() {
        if (latestClickX < getMiddlePoint()) {
            selectPrevPage();
        } else {
            selectNextPage();
        }
    }

    /** Возвращает середину элемента. */
    private int getMiddlePoint() {
        return x + (width / 2);
    }

    /** Возвращает текущую страницу. */
    public int getCurrentPage() {
        return currentPage;
    }

    /** Сбрасывает страницу на первую. */
    private void setDefaultPage() {
        currentPage = DEFAULT_PAGE;
    }
}
