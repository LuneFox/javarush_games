package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Button;

public final class ViewGameOver extends View {
    public int displayDelay;  // defines how soon will game over screen show up
    public Area scoreArea = new Area(new int[]{18, 37, 60, 64});

    public ViewGameOver(MinesweeperGame game) {
        this.game = game;
        this.screenType = Screen.ScreenType.GAME_OVER;
        this.displayDelay = 0;
    }

    @Override
    public void display() {

    }

    public void display(boolean victory, int delay) {
        super.display();
        displayDelay = delay;
        if (displayDelay > 0) return;
        if (victory) {
            IMAGES.get(Bitmap.WINDOW_VICTORY).drawAt(-1, -1);
            IMAGES.get(Bitmap.FACE_HAPPY).drawAt(-1, -1);
            game.print("победа!", Color.YELLOW, 18, 33);
        } else {
            IMAGES.get(Bitmap.WINDOW_GAME_OVER).drawAt(-1, -1);
            IMAGES.get(Bitmap.FACE_SAD).drawAt(-1, -1);
            game.print("не повезло!", Color.YELLOW, 18, 33);
        }
        game.print("счёт: " + game.player.score, Color.LIGHTGOLDENRODYELLOW, 18, 57);
        BUTTONS.get(Button.ButtonID.AGAIN).draw();
        BUTTONS.get(Button.ButtonID.RETURN).draw();
        BUTTONS.get(Button.ButtonID.CLOSE).draw();
    }

}
