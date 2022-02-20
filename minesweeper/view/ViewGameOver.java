package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.Screen;
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
            IMAGES_CACHE.get(VisualElement.WIN_VICTORY).drawAt(-1, -1);
            IMAGES_CACHE.get(VisualElement.PIC_FACE_HAPPY).drawAt(-1, -1);
            Printer.print("победа!", Color.YELLOW, 18, 33);
        } else {
            IMAGES_CACHE.get(VisualElement.WIN_GAME_OVER).drawAt(-1, -1);
            IMAGES_CACHE.get(VisualElement.PIC_FACE_SAD).drawAt(-1, -1);
            Printer.print("не повезло!", Color.YELLOW, 18, 33);
        }
        Printer.print("счёт: " + game.player.score.getTotalScore(), Color.LIGHTGOLDENRODYELLOW, 18, 57);
        BUTTONS_CACHE.get(Button.ButtonID.AGAIN).draw();
        BUTTONS_CACHE.get(Button.ButtonID.RETURN).draw();
        BUTTONS_CACHE.get(Button.ButtonID.CLOSE).draw();
    }

    public void setShowDelay() {
        this.showDelay = 30;
    }

    public int getShowDelay() {
        return showDelay;
    }
}
