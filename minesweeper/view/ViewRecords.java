package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.Strings;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Button;
import com.javarush.games.minesweeper.graphics.Image;

public final class ViewRecords extends View {

    public ViewRecords(MinesweeperGame game) {
        this.game = game;
        this.screenType = Screen.ScreenType.RECORDS;
    }

    @Override
    public void display(){
        super.display();
        IMAGES.get(Bitmap.WINDOW_MENU).draw();
        game.print(Strings.RECORDS[0], Color.YELLOW, 17, 2);
        BUTTONS.get(Button.ButtonID.BACK).draw();
        drawPrizeCups();
        drawEntries();
    }

    final void drawPrizeCups() {
        Image prizeCup = IMAGES.get(Bitmap.CUP);
        Color[] colors;
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    colors = new Color[]{Color.GOLD, Color.WHITE};
                    break;
                case 1:
                    colors = new Color[]{Color.SILVER, Color.WHITE};
                    break;
                case 2:
                    colors = new Color[]{Color.DARKGOLDENROD, Color.PALEGOLDENROD};
                    break;
                default:
                    colors = new Color[]{Color.BLACK, Color.WHITE};
            }
            prizeCup.replaceColor(colors[0], 1);
            prizeCup.replaceColor(colors[1], 2);
            prizeCup.drawAt(2, 20 + (20 * i));
        }
    }

    final void drawEntries() {
        Color[] colors = new Color[]{Color.WHITE, Color.GOLD, Color.SILVER, Color.PALEGOLDENROD};
        game.print(Strings.RECORDS[1], colors[1], 18, 18);
        game.print(Strings.RECORDS[2], colors[0], 94, 27, true);
        game.print(Strings.RECORDS[3], colors[2], 18, 38);
        game.print(Strings.RECORDS[4], colors[0], 94, 47, true);
        game.print(Strings.RECORDS[5], colors[3], 18, 58);
        game.print(Strings.RECORDS[6], colors[0], 94, 67, true);
    }
}
