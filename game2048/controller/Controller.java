package com.javarush.games.game2048.controller;

import com.javarush.engine.cell.Key;
import com.javarush.games.game2048.Game2048;
import com.javarush.games.game2048.model.Result;

/**
 * Receives keyboard and mouse events from the {@link Game2048} engine callbacks,
 * dispatches them to the active {@link ControlStrategy} and checks after every
 * event whether any move is still possible (loss condition).
 */
public class Controller {
    private final ControlStrategy controlStrategy;
    private final Game2048 game;

    /**
     * Creates a controller backed by a {@link MainControlStrategy}.
     *
     * @param game the game instance to control
     */
    public Controller(Game2048 game) {
        this.game = game;
        this.controlStrategy = new MainControlStrategy(game);
    }

    /**
     * Dispatches a left mouse click to the control strategy.
     *
     * @param x the column of the clicked cell
     * @param y the row of the clicked cell
     */
    public final void leftClick(int x, int y) {
        controlStrategy.leftClick(x, y);
    }

    /**
     * Dispatches a right mouse click to the control strategy.
     *
     * @param x the column of the clicked cell
     * @param y the row of the clicked cell
     */
    public final void rightClick(int x, int y) {
        controlStrategy.rightClick(x, y);
    }

    /**
     * Dispatches a key press to the control strategy and checks whether the
     * game has ended.
     *
     * @param key the pressed key
     */
    public final void pressKey(Key key) {
        if (key == Key.UP) controlStrategy.pressUp();
        else if (key == Key.DOWN) controlStrategy.pressDown();
        else if (key == Key.LEFT) controlStrategy.pressLeft();
        else if (key == Key.RIGHT) controlStrategy.pressRight();
        else if (key == Key.SPACE) controlStrategy.pressSpace();
        else if (key == Key.ENTER) controlStrategy.pressEnter();
        else controlStrategy.pressAnyOtherKey();

        checkGameOver();
    }

    /**
     * Dispatches a key release to the control strategy and checks whether the
     * game has ended.
     *
     * @param key the released key
     */
    public final void releaseKey(Key key) {
        if (key == Key.UP) controlStrategy.releaseUp();
        else if (key == Key.DOWN) controlStrategy.releaseDown();
        else if (key == Key.LEFT) controlStrategy.releaseLeft();
        else if (key == Key.RIGHT) controlStrategy.releaseRight();
        else if (key == Key.SPACE) controlStrategy.releaseSpace();
        else if (key == Key.ENTER) controlStrategy.releaseEnter();
        else controlStrategy.pressAnyOtherKey();

        checkGameOver();
    }

    /**
     * Marks the game as lost and shows the result if no move is possible anymore.
     */
    private void checkGameOver() {
        if (game.isMovePossible()) return;

        game.setResult(Result.LOSE, "Невозможно совершить ход!");
        game.finishIfResultIsAchieved();
    }
}
