package com.javarush.games.minesweeper.gui.interactive;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.model.InteractiveObject;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Simple page selector that looks like this: <   1/12   >
 * Arrow buttons change current page, getCurrentPage method returns current page to be used elsewhere.
 * Constructor accepts maximum number of pages, although they're counted from 0 to MAX-1 to complement arrays.
 */

public class PageSelector extends InteractiveObject {
    public static final Set<PageSelector> allSelectors = new HashSet<>();
    private static final int DEFAULT_PAGE = 0;
    private final int maxPage;
    private int currentPage;
    private final Arrow prevPageArrow;
    private final Arrow nextPageArrow;

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

    public void selectPrevPage() {
        prevPageArrow.animate();
        if (currentPage > 0) {
            currentPage--;
        }
    }

    public void selectNextPage() {
        nextPageArrow.animate();
        if (currentPage < maxPage) {
            currentPage++;
        }
    }

    @Override
    public void draw() {
        printPageIndex();
        drawPageArrows();
    }

    private void printPageIndex() {
        String indexText = ((currentPage + 1) + " / " + (maxPage + 1));
        int textHorizontalPosition = getTextHorizontalPosition(indexText);
        Printer.print(indexText, Color.WHITE, textHorizontalPosition, y - 1);
    }

    private int getTextHorizontalPosition(String text) {
        int textWidth = Printer.calculateWidth(text);
        return getMiddlePoint() - (textWidth / 2);
    }

    private void drawPageArrows() {
        prevPageArrow.draw();
        nextPageArrow.draw();
    }

    @Override
    public void onLeftClick() {
        if (latestClickX < getMiddlePoint()) {
            selectPrevPage();
        } else {
            selectNextPage();
        }
    }

    private int getMiddlePoint() {
        return x + (width / 2);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setDefaultPage() {
        currentPage = DEFAULT_PAGE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageSelector that = (PageSelector) o;
        return maxPage == that.maxPage
                && currentPage == that.currentPage
                && Objects.equals(prevPageArrow, that.prevPageArrow)
                && Objects.equals(nextPageArrow, that.nextPageArrow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxPage, currentPage, prevPageArrow, nextPageArrow);
    }
}
