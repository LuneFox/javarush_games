package com.javarush.games.minesweeper.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.GameStatistics;
import com.javarush.games.minesweeper.view.View;


public class ViewGameOver extends View {
    private final Button againButton = new Button(57, 69, "снова", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            game.startNewGame();
        }
    };
    private final Button returnToMenuButton = new Button(15, 69, "меню", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.MAIN);
        }
    };
    private final Button hideOverlayButton = new Button(73, 35, "x", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.BOARD);
        }
    };
    private final Button showScoreDetailButton = new Button(18, 55, "?", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.SCORE);
        }
    };
    private final Image victoryWindow = new Image(ImageType.GUI_VICTORY_WINDOW);
    private final Image happyFace = new Image(ImageType.PICTURE_YELLOW_CAT_SMILE);
    private final Image sadFace = new Image(ImageType.PICTURE_YELLOW_CAT_SAD);

    public ViewGameOver(MinesweeperGame game) {
        super(game);
    }

    @Override
    public void update() {
        if (waitForDelay()) return;
        game.drawGameBoard();
        drawBanner();
        drawButtons();
        super.update();
    }

    private void drawButtons() {
        againButton.draw();
        returnToMenuButton.draw();
        hideOverlayButton.draw();
        showScoreDetailButton.draw();
    }

    private void drawBanner() {
        victoryWindow.draw(Image.CENTER, Image.CENTER);
        Image face = game.isWon() ? happyFace : sadFace;
        face.draw(Image.CENTER, Image.CENTER);
        Printer.print(GameStatistics.get("result"), Theme.LABEL.getColor(), 18, 33);
        Printer.print("счёт: " + GameStatistics.get("total"), Options.isDeveloperModeEnabled() ? Color.RED : Color.LIGHTGOLDENRODYELLOW, 29, 57);
    }

    private boolean waitForDelay() {
        if (View.getGameOverShowDelay() > 0) {
            View.decreaseGameOverShowDelay();
            game.drawGameBoard();
            super.update();
            return true;
        }
        return false;
    }
}
