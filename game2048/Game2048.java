package com.javarush.games.game2048;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.game2048.controller.Controller;
import com.javarush.games.game2048.model.Direction;
import com.javarush.games.game2048.model.Field;
import com.javarush.games.game2048.model.Result;
import com.javarush.games.game2048.view.View;

/**
 * Entry point of the "2048 billiards" game: an American pool-themed variant of 2048
 * played on a 7x7 field with six pockets.
 *
 * <p>Owns the game state (field, result, score, turn count), delegates key/mouse
 * events to the {@link Controller} and rendering to the {@link View}.
 * The goal is to pocket the 8-ball into the last open pocket; the score is
 * (sum of pocket scores * 100) / number of turns.
 */
public class Game2048 extends Game {
    public static final String VERSION = "v0.97";
    private static final int SIDE = Field.SIDE;

    private final Controller controller = new Controller(this);
    private final View view = new View(this);
    private Field field;
    private Result result;
    private String resultMessage;

    private int score;
    private int turnCount;
    private boolean isStopped;

    /**
     * Called by the engine on game start: configures the screen and starts a new game.
     */
    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(SIDE, SIDE);
        createNewGame();
    }

    /**
     * Starts a new game with a fresh field and reset game state.
     */
    public void createNewGame() {
        field = new Field(this);
        resetValues();
        drawScene();
    }

    /**
     * Resets the result, score, turn counter and stopped flag to their initial values.
     */
    private void resetValues() {
        result = Result.NONE;
        isStopped = false;
        score = 0;
        turnCount = 0;
    }

    /**
     * Redraws the whole scene: background, field and the info overlay.
     */
    private void drawScene() {
        view.drawBackground();
        field.draw();
        view.drawInfo();
    }

    /**
     * Shows the result dialog and stops the game if a win or loss has been achieved.
     */
    public void finishIfResultIsAchieved() {
        if (result == Result.WIN) {
            win(resultMessage);
        } else if (result == Result.LOSE) {
            lose(resultMessage);
        }
    }

    /**
     * Stops the game as a win and shows the result dialog with the final score
     * (sum of pocket scores * 100 / number of turns).
     */
    private void win(String message) {
        result = Result.WIN;
        isStopped = true;
        score = (field.sumPocketsScore() * 100) / turnCount;
        view.showResultDialog(result, message + "\nСчёт: " + score);
    }

    /**
     * Stops the game as a loss and shows the result dialog with the given message.
     */
    private void lose(String message) {
        result = Result.LOSE;
        isStopped = true;
        view.showResultDialog(result, "Вы проиграли!\n" + message);
    }

    /**
     * Stores the achieved result and its accompanying message; displayed by
     * {@link #finishIfResultIsAchieved()}.
     *
     * @param result        the achieved result
     * @param resultMessage the message shown in the result dialog
     */
    public void setResult(Result result, String resultMessage) {
        this.result = result;
        this.resultMessage = resultMessage;
    }

    /**
     * Increments the turn counter. Called after every successful move.
     */
    public void increaseTurnCount() {
        turnCount++;
    }

    /**
     * Moves all balls in the given direction and redraws the scene.
     *
     * @param direction the direction to move the balls
     */
    public void move(Direction direction) {
        if (direction == Direction.UP) field.moveUp();
        else if (direction == Direction.RIGHT) field.moveRight();
        else if (direction == Direction.DOWN) field.moveDown();
        else if (direction == Direction.LEFT) field.moveLeft();
        drawScene();
    }

    /**
     * Returns the balls stored in the given pocket back to the field and redraws the scene.
     *
     * @param pocketNumber the pocket index (0-5)
     */
    public void emptyPocket(int pocketNumber) {
        field.emptyPocket(pocketNumber);
        drawScene();
    }

    /**
     * Places or removes the white (cue) ball at the given cell and redraws the scene.
     *
     * @param x the column of the cell
     * @param y the row of the cell
     */
    public void placeOrRemoveWhiteBall(int x, int y) {
        field.placeOrRemoveWhiteBall(x, y);
        drawScene();
    }

    /**
     * @return whether at least one move is still possible on the field
     */
    public boolean isMovePossible() {
        return field.isMovePossible();
    }

    /**
     * Shows the help dialog explaining how to play.
     */
    public void showHelpDialog() {
        view.showHelpDialog();
    }

    /**
     * @return whether the game is over (won or lost)
     */
    public boolean isStopped() {
        return isStopped;
    }

    /**
     * @return the number of moves made in the current game
     */
    public int getTurnCount() {
        return turnCount;
    }

    /*
     * Controls
     */

    /**
     * Forwards a key press to the controller.
     *
     * @param key the pressed key
     */
    @Override
    public void onKeyPress(Key key) {
        controller.pressKey(key);
    }

    /**
     * Forwards a key release to the controller.
     *
     * @param key the released key
     */
    @Override
    public void onKeyReleased(Key key) {
        controller.releaseKey(key);
    }

    /**
     * Forwards a left mouse click to the controller.
     *
     * @param x the column of the clicked cell
     * @param y the row of the clicked cell
     */
    @Override
    public void onMouseLeftClick(int x, int y) {
        controller.leftClick(x, y);
    }

    /**
     * Forwards a right mouse click to the controller.
     *
     * @param x the column of the clicked cell
     * @param y the row of the clicked cell
     */
    @Override
    public void onMouseRightClick(int x, int y) {
        controller.rightClick(x, y);
    }
}