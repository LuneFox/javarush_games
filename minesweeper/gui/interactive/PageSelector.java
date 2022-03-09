package com.javarush.games.minesweeper.gui.interactive;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.model.DrawableObject;

import java.util.*;

/**
 * Simple page selector that looks like this: <   1/12   >
 * Arrow buttons change current page, getCurrentPage method returns current page to be used elsewhere.
 * Constructor accepts maximum number of pages, although they're counted from 0 to MAX-1 to complement arrays.
 */

public class PageSelector extends DrawableObject {
    public static final List<PageSelector> allSelectors = new LinkedList<>();
    private final int maxPage;
    private int currentPage;
    private final Arrow prevPageArrow;
    private final Arrow nextPageArrow;

    public PageSelector(int x, int y, int width, int maxPage) {
        super(x, y);
        this.width = width;
        this.maxPage = maxPage - 1;
        currentPage = 0;
        prevPageArrow = new Arrow(x, y, false);
        nextPageArrow = new Arrow(x + width - prevPageArrow.width, y, true);
        this.height = prevPageArrow.height;
        allSelectors.add(this);
    }

    public void prevPage() {
        prevPageArrow.onLeftTouch();
        if (currentPage > 0)
            currentPage--;
    }

    public void nextPage() {
        nextPageArrow.onLeftTouch();
        if (currentPage < maxPage)
            currentPage++;
    }

    @Override
    public void draw() {
        // Page index
        String text = ((currentPage + 1) + " / " + (maxPage + 1));
        int textLength = Printer.calculateWidth(text);
        int textPosition = x + (width / 2) - (textLength / 2);
        Printer.print(text, Color.WHITE, textPosition, y - 1, false);

        // Arrow buttons
        prevPageArrow.draw();
        nextPageArrow.draw();
    }

    @Override
    protected void onLeftTouch() {
        // Click on left half = page back, click on right half = page forward
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageSelector that = (PageSelector) o;
        return maxPage == that.maxPage && currentPage == that.currentPage && Objects.equals(prevPageArrow, that.prevPageArrow) && Objects.equals(nextPageArrow, that.nextPageArrow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxPage, currentPage, prevPageArrow, nextPageArrow);
    }
}
