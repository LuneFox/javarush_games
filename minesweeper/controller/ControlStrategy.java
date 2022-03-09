package com.javarush.games.minesweeper.controller;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Message;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.options.Options;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Cache;

import java.util.stream.Stream;

import static com.javarush.games.minesweeper.Util.inside;

/**
 * Strategy pattern for different controls on different screens.
 */

public interface ControlStrategy {
    default void leftClick(int x, int y) {
    }

    default void rightClick(int x, int y) {
    }

    default void pressUp() {
    }

    default void pressDown() {
    }

    default void pressRight() {
    }

    default void pressLeft() {
    }

    default void pressEnter() {
    }

    default void pressPause() {
    }

    default void pressSpace() {
    }

    default void pressEsc() {
    }

    default void pressOther() {
    }
}

class ControlAbout implements ControlStrategy {

    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GENERAL_CLOSE).checkLeftTouch(x, y)) {
            Screen.setActive(Screen.MAIN);
        }
        Screen.about.pageSelector.checkLeftTouch(x, y);
    }

    @Override
    public void pressRight() {
        Screen.about.pageSelector.nextPage();
    }

    @Override
    public void pressLeft() {
        Screen.about.pageSelector.prevPage();
    }

    @Override
    public void pressEsc() {
        Screen.setActive(Screen.MAIN);
    }
}

class ControlBoard implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        int gridX = x / 10;
        int gridY = y / 10;

        if (game.isStopped) {
            Screen.setActive(Screen.GAME_OVER);
            return;
        }
        Cell cell = game.field.get()[gridY][gridX];
        if (!cell.isFlagged || game.shop.scanner.isActivated()) {
            game.openCell(gridX, gridY);
        }
        game.shop.deactivateExpiredItems();
    }

    @Override
    public void rightClick(int x, int y) {
        int gridX = x / 10;
        int gridY = y / 10;

        if (game.isStopped) {
            Screen.setActive(Screen.GAME_OVER);
            return;
        }
        game.setFlag(gridX, gridY, true);  // works only on closed tiles
        game.openSurroundingCells(gridX, gridY);       // works only on open tiles
        game.shop.deactivateExpiredItems();
    }

    @Override
    public void pressSpace() {
        if (!game.isStopped) {
            Screen.setActive(Screen.SHOP);
        } else {
            Screen.setActive(Screen.GAME_OVER);
        }
    }

    @Override
    public void pressEsc() {
        if (game.isStopped) {
            Screen.setActive(Screen.GAME_OVER);
        } else {
            Screen.setActive(Screen.MAIN);
        }
    }

    @Override
    public void pressOther() {
        if (game.isStopped) {
            Screen.setActive(Screen.GAME_OVER);
        }
    }
}

class ControlGameOver implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GAME_OVER_HIDE).checkLeftTouch(x, y)) {
            Screen.setActive(Screen.BOARD);
        } else if (Cache.get(Button.ButtonID.GAME_OVER_RETURN).checkLeftTouch(x, y)) {
            Screen.setActive(Screen.MAIN);
        } else if (Cache.get(Button.ButtonID.GAME_OVER_QUESTION).checkLeftTouch(x, y)) {
            Screen.setActive(Screen.SCORE);
        } else if (Cache.get(Button.ButtonID.GAME_OVER_AGAIN).checkLeftTouch(x, y)) {
            game.startNewGame();
        }
    }

    @Override
    public void pressSpace() {
        Screen.setActive(Screen.SCORE);
    }

    @Override
    public void pressEsc() {
        Screen.setActive(Screen.BOARD);
    }
}

class ControlItemHelp implements ControlStrategy {

    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GENERAL_CLOSE).checkLeftTouch(x, y)) {
            Screen.setActive(Screen.SHOP);
        }
    }

    @Override
    public void pressEsc() {
        Screen.setActive(Screen.SHOP);
    }
}

class ControlMain implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.MAIN_MENU_START).checkLeftTouch(x, y)) {
            game.startNewGame();
        } else if (Cache.get(Button.ButtonID.MAIN_MENU_OPTIONS).checkLeftTouch(x, y)) {
            Screen.setActive(Screen.OPTIONS);
        } else if (Cache.get(Button.ButtonID.MAIN_MENU_ABOUT).checkLeftTouch(x, y)) {
            Screen.setActive(Screen.ABOUT);
        } else if (Cache.get(Button.ButtonID.MAIN_MENU_RECORDS).checkLeftTouch(x, y)) {
            Screen.setActive(Screen.RECORDS);
        }
    }

    @Override
    public void pressEsc() {
        if (!game.isStopped) {
            Screen.setActive(Screen.BOARD);
        }
    }
}

class ControlOptions implements ControlStrategy {
    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GENERAL_CLOSE).checkLeftTouch(x, y)) {
            Screen.setActive(Screen.MAIN);
            Message.show("Сохранено");
        }

        Stream.of(
                Options.difficultySelector,
                Options.autoBuyFlagsSelector,
                Options.timerEnabledSelector,
                Options.displayMessageSelector,
                Options.themeSelector
        ).forEach(drawableObject -> drawableObject.checkLeftTouch(x, y));
    }

    @Override
    public void pressRight() {
        Options.difficultySelector.difficultyUp();
    }

    @Override
    public void pressLeft() {
        Options.difficultySelector.difficultyDown();
    }

    @Override
    public void pressEsc() {
        Screen.setActive(Screen.MAIN);
        Message.show("Сохранено");
    }
}

class ControlRecords implements ControlStrategy {
    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GENERAL_CLOSE).checkLeftTouch(x, y)) {
            Screen.setActive(Screen.MAIN);
        }
    }

    @Override
    public void pressEsc() {
        Screen.setActive(Screen.MAIN);
    }

}

class ControlScore implements ControlStrategy {

    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GENERAL_CONFIRM).checkLeftTouch(x, y)) {
            goBack();
        }
        Screen.score.pageSelector.checkLeftTouch(x, y);
    }

    @Override
    public void pressSpace() {
        goBack();
    }

    @Override
    public void pressEsc() {
        goBack();
    }

    @Override
    public void pressRight() {
        Screen.score.pageSelector.nextPage();
    }

    @Override
    public void pressLeft() {
        Screen.score.pageSelector.prevPage();
    }

    private void goBack() {
        Screen.board.update();
        Screen.setActive(Screen.GAME_OVER);
    }
}

class ControlShop implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (clickedOutsideShopWindow(x, y)) {
            Screen.setActive(Screen.BOARD);
            return;
        }
        game.shop.showCase.checkLeftTouch(x, y);
    }

    @Override
    public void rightClick(int x, int y) {
        game.shop.showCase.checkRightTouch(x, y);
    }

    @Override
    public void pressSpace() {
        Screen.setActive(Screen.BOARD);
    }

    @Override
    public void pressEsc() {
        Screen.setActive(Screen.BOARD);
    }

    private boolean clickedOutsideShopWindow(int x, int y) {
        boolean horizontal = inside(x, 0, 9) || inside(x, 90, 99);
        boolean vertical = inside(y, 0, 10) || inside(y, 91, 99);
        return (horizontal || vertical);
    }
}