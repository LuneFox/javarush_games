package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.model.player.Score;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.view.graphics.*;

public final class ViewGameOver extends View {
    public Area scoreArea = new Area(18, 37, 60, 64);
    private int showDelay;

    /**
     * Displays the result of the game over the board.
     */

    public ViewGameOver() {
        this.screen = Screen.GAME_OVER;
    }

    public void update() {
        super.update();

        if (showDelay > 0) {
            Screen.board.update();
            showDelay--;
            return;
        }

        if (game.isVictory) {
            Cache.get(VisualElement.WIN_VICTORY).draw(Image.CENTER, Image.CENTER);
            Cache.get(VisualElement.FACE_HAPPY).draw(Image.CENTER, Image.CENTER);
            Printer.print("победа!", Color.YELLOW, 18, 33);
        } else {
            Cache.get(VisualElement.WIN_GAME_OVER).draw(Image.CENTER, Image.CENTER);
            Cache.get(VisualElement.FACE_SAD).draw(Image.CENTER, Image.CENTER);
            Printer.print("не повезло!", Color.YELLOW, 18, 33);
        }
        Printer.print("счёт: " + Score.Table.total, Color.LIGHTGOLDENRODYELLOW, 18, 57);
        Cache.get(Button.ButtonID.GAME_OVER_AGAIN).draw();
        Cache.get(Button.ButtonID.GAME_OVER_RETURN).draw();
        Cache.get(Button.ButtonID.GAME_OVER_HIDE).draw();
    }

    public void setDelay() {
        this.showDelay = 30;
    }

    public int getShowDelay() {
        return showDelay;
    }
}
