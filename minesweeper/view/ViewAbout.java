package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.Strings;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Button;
import com.javarush.games.minesweeper.graphics.Image;

public final class ViewAbout extends View {
    public static int currentAboutPage = 0;
    public Area prevPageArrowArea = new Area(new int[]{5, 27, 88, 99});
    public Area nextPageArrowArea = new Area(new int[]{31, 54, 88, 99});

    public ViewAbout(MinesweeperGame game) {
        this.game = game;
        this.screenType = Screen.ScreenType.ABOUT;
    }

    @Override
    public void display() {
        super.display();
        IMAGES.get(Bitmap.WINDOW_MENU).draw();
        game.print(Strings.ABOUT_HEAD[currentAboutPage], Color.YELLOW, 24, 2);
        game.print(Strings.ABOUT_BODY[currentAboutPage], 3, 13);
        BUTTONS.get(Button.ButtonID.BACK).draw();
        IMAGES.get(Bitmap.MENU_ARROW).drawAt(7, 89, Image.Mirror.HORIZONTAL);
        IMAGES.get(Bitmap.MENU_ARROW).drawAt(47, 89);
        game.print((currentAboutPage + 1) + " / " + Strings.ABOUT_HEAD.length, 20, 88);
    }

    public final void prevPage() {
        currentAboutPage = currentAboutPage <= 0 ? 0 : currentAboutPage - 1;
        display();
    }

    public final void nextPage() {
        int lastPage = Strings.ABOUT_HEAD.length - 1;
        currentAboutPage = currentAboutPage >= lastPage ? lastPage : currentAboutPage + 1;
        display();
    }
}
