package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;

import static com.javarush.games.minesweeper.model.player.Score.Table.total;

public class ViewGameOver extends View {
    private final Button againButton = new Button(57, 69, 0, 0, "снова", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            game.startNewGame();
        }
    };
    private final Button returnToMenuButton = new Button(15, 69, 0, 0, "меню", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.MAIN);
        }
    };
    private final Button hideOverlayButton = new Button(73, 35, 0, 0, "x", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.BOARD);
        }
    };
    private final Button showScoreDetailButton = new Button(18, 55, 0, 0, "?", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.SCORE);
        }
    };
    private final Image victoryWindow = new Image(ImageType.GUI_VICTORY_WINDOW);
    private final Image happyFace = new Image(ImageType.PICTURE_YELLOW_CAT_SMILE);
    private final Image sadFace = new Image(ImageType.PICTURE_YELLOW_CAT_SAD);

    public ViewGameOver(Phase phase) {
        super(phase);
    }

    @Override
    public void update() {
        if (isDelayed()) return;

        game.field.draw();
        victoryWindow.draw(Image.CENTER, Image.CENTER);

        Image face = game.isVictory ? happyFace : sadFace;
        String result = game.isVictory ? "<победа!>" : "<не повезло!>";
        face.draw(Image.CENTER, Image.CENTER);
        Printer.print(result, Color.YELLOW, 18, 33);
        Printer.print("счёт: " + total, Options.developerMode ? Color.RED : Color.LIGHTGOLDENRODYELLOW, 29, 57);

        againButton.draw();
        returnToMenuButton.draw();
        hideOverlayButton.draw();
        showScoreDetailButton.draw();

        super.update();
    }

    private boolean isDelayed() {
        if (game.gameOverShowDelay > 0) {
            game.gameOverShowDelay--;
            game.field.draw();
            super.update();
            return true;
        }
        return false;
    }
}
