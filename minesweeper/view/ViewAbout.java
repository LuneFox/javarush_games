package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.Strings;
import com.javarush.games.minesweeper.view.graphics.*;

public final class ViewAbout extends View {
    public static int currentAboutPage = 0;
    public Area prevPageArrowArea = new Area(5, 27, 88, 99);
    public Area nextPageArrowArea = new Area(31, 54, 88, 99);

    /**
     * Shows the ABOUT section of the game.
     */

    public ViewAbout(MinesweeperGame game) {
        this.game = game;
        this.screen = Screen.ABOUT;
    }

    @Override
    public void display() {
        super.display();
        IMAGES_CACHE.get(VisualElement.WIN_MENU).draw();
        Printer.print(Strings.ABOUT_HEAD[currentAboutPage], Color.YELLOW, -1, 2);
        Printer.print(Strings.ABOUT_BODY[currentAboutPage], 3, 13);
        BUTTONS_CACHE.get(Button.ButtonID.BACK).draw();
        Image arrowButton = IMAGES_CACHE.get(VisualElement.MENU_ARROW);

        arrowButton.drawAt((ViewOptions.clickedArrowTimeoutL-- > 0) ? 6 : 7, 89, Image.Mirror.HORIZONTAL);
        arrowButton.drawAt((ViewOptions.clickedArrowTimeoutR-- > 0) ? 48 : 47, 89);

        Printer.print((currentAboutPage + 1) + " / " + Strings.ABOUT_HEAD.length, 20, 88);
    }

    public void prevPage() {
        currentAboutPage = currentAboutPage <= 0 ? 0 : currentAboutPage - 1;
        View.options.animateLeftArrow();
        display();
    }

    public void nextPage() {
        int lastPage = Strings.ABOUT_HEAD.length - 1;
        currentAboutPage = currentAboutPage >= lastPage ? lastPage : currentAboutPage + 1;
        View.options.animateRightArrow();
        display();
    }
}
