package com.javarush.games.minesweeper.model.options;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.model.DrawableObject;
import com.javarush.games.minesweeper.view.graphics.Printer;

public class PageSelector extends DrawableObject {
    private final int maxPage;
    private int currentPage;
    private final MenuArrow prevPageArrow;
    private final MenuArrow nextPageArrow;

    public PageSelector(int x, int y, int width, int maxPage) {
        super(x, y);
        this.width = width;
        this.maxPage = maxPage - 1;
        currentPage = 0;
        prevPageArrow = new MenuArrow(x, y, false);
        nextPageArrow = new MenuArrow(x + width - prevPageArrow.width, y, true);
        this.height = prevPageArrow.height;
    }

    public void prevPage() {
        prevPageArrow.onTouch();
        if (currentPage > 0)
            currentPage--;
    }

    public void nextPage() {
        nextPageArrow.onTouch();
        if (currentPage < maxPage)
            currentPage++;
    }

    @Override
    public void draw() {
        String text = ((currentPage + 1) + " / " + (maxPage + 1));
        int textLength = Printer.calculateWidth(text);
        int textPosition = x + (width / 2) - (textLength / 2);
        Printer.print(text, Color.WHITE, textPosition, y - 1, false);
        prevPageArrow.draw();
        nextPageArrow.draw();
    }

    @Override
    protected void onTouch() {
        if (lastClickX < x + width / 2) {
            prevPage();
        } else {
            nextPage();
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void reset() {
        currentPage = 0;
    }
}
