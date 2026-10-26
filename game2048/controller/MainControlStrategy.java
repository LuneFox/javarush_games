package com.javarush.games.game2048.controller;

import com.javarush.games.game2048.Game2048;
import com.javarush.games.game2048.model.Direction;

/**
 * Default {@link ControlStrategy}: arrow keys move the balls, ENTER performs a
 * random move, SPACE restarts after the game ends. Left clicks on the border
 * trigger the corresponding move, right clicks on pockets empty them, and right
 * clicks inside the board place or remove the white (cue) ball.
 */
public class MainControlStrategy implements ControlStrategy {
    private final Game2048 game;

    /**
     * @param game the game instance to control
     */
    public MainControlStrategy(Game2048 game) {
        this.game = game;
    }

    /**
     * Handles a left mouse click: border cells perform a move in the
     * corresponding direction, the top-left corner opens the help dialog.
     * Ignored when the game is over.
     *
     * @param x the column of the clicked cell
     * @param y the row of the clicked cell
     */
    @Override
    public void leftClick(int x, int y) {
        if (game.isStopped()) return;

        if (y == 0 && x > 0 && x < 6) releaseUp();
        else if (y == 6 && x > 0 && x < 6) releaseDown();
        else if (x == 0 && y > 0 && y < 6) releaseLeft();
        else if (x == 6 && y > 0 && y < 6) releaseRight();
        else if (x == 0 && y == 0) game.showHelpDialog();
    }

    /**
     * Handles a right mouse click: pocket cells empty the corresponding
     * pocket, inner cells place or remove the white ball. Ignored when the
     * game is over.
     *
     * @param x the column of the clicked cell
     * @param y the row of the clicked cell
     */
    @Override
    public void rightClick(int x, int y) {
        if (game.isStopped()) return;

        if (x == 0 && y == 1) game.emptyPocket(0);
        else if (x == 0 && y == 5) game.emptyPocket(1);
        else if (x == 6 && y == 1) game.emptyPocket(2);
        else if (x == 6 && y == 5) game.emptyPocket(3);
        else if (x == 3 && y == 0) game.emptyPocket(4);
        else if (x == 3 && y == 6) game.emptyPocket(5);
        else if (x > 0 && x < 6 && y > 0 && y < 6) {
            game.placeOrRemoveWhiteBall(x, y);
        }
    }

    /**
     * Moves all balls up if the game is not over.
     */
    @Override
    public void releaseUp() {
        if (game.isStopped()) return;
        game.move(Direction.UP);
    }

    /**
     * Moves all balls down if the game is not over.
     */
    @Override
    public void releaseDown() {
        if (game.isStopped()) return;
        game.move(Direction.DOWN);
    }

    /**
     * Moves all balls right if the game is not over.
     */
    @Override
    public void releaseRight() {
        if (game.isStopped()) return;
        game.move(Direction.RIGHT);
    }

    /**
     * Moves all balls left if the game is not over.
     */
    @Override
    public void releaseLeft() {
        if (game.isStopped()) return;
        game.move(Direction.LEFT);
    }

    /**
     * Starts a new game if the previous one is over.
     */
    @Override
    public void pressSpace() {
        if (!game.isStopped()) return;
        game.createNewGame();
    }

    /**
     * Shows the result dialog if a game result has been achieved.
     */
    @Override
    public void pressAnyOtherKey() {
        game.finishIfResultIsAchieved();
    }

    /**
     * Performs a random move in one of the four directions.
     */
    @Override
    public void pressEnter() {
        performRandomMove();
    }

    /**
     * Picks a uniformly random direction and moves the balls in it.
     */
    private void performRandomMove() {
        double random = Math.random();

        if (random < 0.25) {
            releaseUp();
        } else if (random < 0.5) {
            releaseDown();
        } else if (random < 0.75) {
            releaseLeft();
        } else {
            releaseRight();
        }
    }
}
