package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.view.graphics.*;

public final class ViewGameOver extends View {
    public int popUpTimer;  // defines how soon will game over screen show up
    public Area scoreArea = new Area(18, 37, 60, 64);

    /**
     * Displays the result of the game over the board.
     */

    public ViewGameOver(MinesweeperGame game) {
        this.game = game;
        this.screen = Screen.GAME_OVER;
    }

    @Override
    public void display() {
        display(game.lastResultIsVictory, 0);
    }

    public void display(boolean victory, int popUpDelay) {
        if (popUpTimer-- > 0) return;
        View.board.refresh();
        super.display();
        popUpTimer = popUpDelay;

        if (popUpTimer > 0) return;
        if (victory) {
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

}
