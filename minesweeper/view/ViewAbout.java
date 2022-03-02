package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.model.Strings;
import com.javarush.games.minesweeper.view.graphics.*;

public final class ViewAbout extends View {
    public static int currentAboutPage = 0;
    public Area prevPageArrowArea = new Area(5, 27, 88, 99);
    public Area nextPageArrowArea = new Area(31, 54, 88, 99);

    /**
     * Shows the ABOUT section of the game.
     */

    public ViewAbout() {
        this.screen = Screen.ABOUT;
    }

    @Override
    public void update() {
        super.update();
        Cache.get(VisualElement.WIN_MENU).draw();
        Printer.print(Strings.ABOUT_HEAD[currentAboutPage], Color.YELLOW, -1, 2);
        Printer.print(Strings.ABOUT_BODY[currentAboutPage], 3, 13);
        Cache.get(Button.ButtonID.GENERAL_BACK).draw();
        Image arrowButton = Cache.get(VisualElement.MENU_ARROW);

        arrowButton.draw((ViewOptions.clickedArrowTimeoutL-- > 0) ? 6 : 7, 89, Image.Mirror.HORIZONTAL);
        arrowButton.draw((ViewOptions.clickedArrowTimeoutR-- > 0) ? 48 : 47, 89);

        Printer.print((currentAboutPage + 1) + " / " + Strings.ABOUT_HEAD.length, 20, 88);
    }

    public void prevPage() {
        currentAboutPage = currentAboutPage <= 0 ? 0 : currentAboutPage - 1;
        Screen.options.animateLeftArrow();
        update();
    }

    public void nextPage() {
        int lastPage = Strings.ABOUT_HEAD.length - 1;
        currentAboutPage = currentAboutPage >= lastPage ? lastPage : currentAboutPage + 1;
        Screen.options.animateRightArrow();
        update();
    }
}
