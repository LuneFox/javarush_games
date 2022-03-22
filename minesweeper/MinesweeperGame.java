package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.gui.Display;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.board.FieldManager;
import com.javarush.games.minesweeper.model.board.Timer;
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
    public Shop shop;
    public Player player;
    public Timer timer;
    public FieldManager fieldManager;
    public boolean isStopped;
    public boolean isFirstMove;
    public boolean isResultVictory;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(100, 100);
        setTurnTimer(30);

        instance = this;        // must come first
        Options.initialize();
        display = new Display();
        controller = new Controller();
        fieldManager = new FieldManager(this);
        timer = new Timer();
        shop = new Shop();
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
        resetValues();
        Phase.setActive(Phase.BOARD);
        PopUpMessage.show("Новая игра");
    }

    private void resetValues() {
        Options.apply();
        fieldManager.createField();
        player.reset();
        shop.reset();
        timer.reset();
        isStopped = false;
        isResultVictory = false;
        isFirstMove = true;
        setScore(player.score.getCurrentScore());
    }

    public void win() {
        finish(true);
        player.score.registerTopScore();
    }

    public void lose() {
        finish(false);
        fieldManager.getField().dice.hide();
        fieldManager.getField().revealMines();
    }

    private void finish(boolean isVictory) {
        this.isStopped = true;
        this.isResultVictory = isVictory;
        View.setGameOverShowDelay(30);
        Phase.setActive(Phase.GAME_OVER);
        setScore(player.score.getTotalScore());
        Score.Table.update();
    }

    // CONTROLS

    @Override
    public void onMouseLeftClick(int x, int y) {
        fieldManager.setRecursiveMove(false);
        fieldManager.setFlagExplosionAllowed(false);
        controller.leftClick(x, y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        fieldManager.setRecursiveMove(false);
        controller.rightClick(x, y);
    }

    @Override
    public void onKeyPress(Key key) {
        controller.pressKey(key);
    }

    // GETTERS

    public static MinesweeperGame getInstance() {
        return instance;
    }
}