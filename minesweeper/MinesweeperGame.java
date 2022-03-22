package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.gui.Display;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.board.BoardManager;
import com.javarush.games.minesweeper.model.player.Player;
import com.javarush.games.minesweeper.model.player.Score;
import com.javarush.games.minesweeper.model.shop.Shop;
import com.javarush.games.minesweeper.view.View;

/**
 * Main game class
 */

public class MinesweeperGame extends Game {
    private static MinesweeperGame instance;
    private Controller controller;
    public Display display;
    public BoardManager boardManager;
    public Shop shop;
    public Player player;
    public boolean isStopped;
    public boolean isResultVictory;

    // Universal accessor
    public static MinesweeperGame getInstance() {
        return instance;
    }

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(100, 100);
        setTurnTimer(30);

        instance = this;
        display = new Display();
        controller = new Controller(this);
        boardManager = new BoardManager(this);
        shop = new Shop(this);
        player = new Player();
        isStopped = true;

        Phase.setActive(Phase.MAIN);
    }

    @Override
    public void onTurn(int step) {
        Phase.updateView();
        display.draw();
    }

    public void startNewGame() {
        Options.apply();
        resetValues();
        Phase.setActive(Phase.BOARD);
        PopUpMessage.show("Новая игра");
    }

    private void resetValues() {
        boardManager.reset();
        player.reset();
        shop.reset();
        isStopped = false;
        isResultVictory = false;
        setScore(0);
    }

    public void win() {
        player.score.registerTopScore();
        finish(true);
    }

    public void lose() {
        boardManager.getDice().hide();
        boardManager.getField().revealMines();
        finish(false);
    }

    private void finish(boolean isVictory) {
        isStopped = true;
        isResultVictory = isVictory;
        View.setGameOverShowDelay(30);
        Phase.setActive(Phase.GAME_OVER);
        setScore(player.score.getTotalScore());
        Score.Table.update();
    }

    /**
     * Controls
     */

    @Override
    public void onMouseLeftClick(int x, int y) {
        controller.leftClick(x, y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        controller.rightClick(x, y);
    }

    @Override
    public void onKeyPress(Key key) {
        controller.pressKey(key);
    }
}