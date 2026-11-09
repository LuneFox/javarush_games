package com.javarush.games.minesweeper.view.impl;

import com.javarush.games.minesweeper.Text;

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

/** Экран завершения партии. */
public class ViewGameOver extends View {
    /** Кнопка «Снова». */
    private final Button againButton = new Button(57, 69, Text.BUTTON_AGAIN.getText(), this) {
        /** Обрабатывает левый клик. */
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            game.startNewGame();
        }
    };
    /** Кнопка возврата в меню. */
    private final Button returnToMenuButton = new Button(15, 69, Text.BUTTON_MENU.getText(), this) {
        /** Обрабатывает левый клик. */
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.MAIN);
        }
    };
    /** Кнопка скрытия оверлея. */
    private final Button hideOverlayButton = new Button(73, 35, Text.BUTTON_CLOSE.getText(), this) {
        /** Обрабатывает левый клик. */
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.BOARD);
        }
    };
    /** Кнопка детализации счёта. */
    private final Button showScoreDetailButton = new Button(18, 55, Text.BUTTON_HELP.getText(), this) {
        /** Обрабатывает левый клик. */
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.SCORE);
        }
    };
    /** Окно результата партии. */
    private final Image victoryWindow = new Image(ImageType.GUI_VICTORY_WINDOW);
    /** Изображение довольного кота. */
    private final Image happyFace = new Image(ImageType.PICTURE_YELLOW_CAT_SMILE);
    /** Изображение грустного кота. */
    private final Image sadFace = new Image(ImageType.PICTURE_YELLOW_CAT_SAD);
    /** Победный результат текущего баннера. */
    private boolean won;

    /** Создаёт экземпляр ViewGameOver. */
    public ViewGameOver(MinesweeperGame game) {
        super(game);
    }

    /** Обновляет состояние. */
    @Override
    public void update() {
        if (waitForDelay()) return;
        game.drawGameBoard();
        drawBanner();
        drawButtons();
        super.update();
    }

    /** Рисует кнопки экрана. */
    private void drawButtons() {
        againButton.draw();
        returnToMenuButton.draw();
        hideOverlayButton.draw();
        showScoreDetailButton.draw();
    }

    /** Рисует баннер результата. */
    private void drawBanner() {
        if (game.isStopped()) won = game.isWon();
        victoryWindow.draw(Image.CENTER, Image.CENTER);
        Image face = won ? happyFace : sadFace;
        face.draw(Image.CENTER, Image.CENTER);
        Printer.print(GameStatistics.get("result"), Theme.LABEL.getColor(), 18, 33);
        Printer.print(Text.SCORE_PREFIX.getText() + GameStatistics.get("total"), Options.isDeveloperModeEnabled() ? Color.RED : Color.LIGHTGOLDENRODYELLOW, 29, 57);
    }

    /** Ждёт окончания задержки перед показом экрана. */
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
