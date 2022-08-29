package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.controller.Click;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.gui.Display;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.*;
import com.javarush.games.minesweeper.model.board.BoardManager;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.player.Player;
import com.javarush.games.minesweeper.model.player.Score;
import com.javarush.games.minesweeper.model.shop.Shop;
import com.javarush.games.minesweeper.model.shop.items.Bomb;
import com.javarush.games.minesweeper.model.shop.items.Scanner;
import com.javarush.games.minesweeper.view.View;

import java.util.List;
import java.util.function.Predicate;

/**
 * Main game class. Part of the model.
 * Cannot remove it from root because it is required by JavaRush Game Engine to compile the game.
 * Also plays role the Facade for Controller and View (central node to get to all possible game elements).
 */
public class MinesweeperGame extends Game {
    public static final String VERSION = "1.25";

    private Controller controller;
    private Display display;
    private BoardManager boardManager;
    private Shop shop;
    private Player player;
    private Result result;
    private boolean isStopped;

    /**
     * Everything that happens during the launch and before the user starts interacting.
     * Creates all important instances and sets the game field parameters.
     */
    @Override
    public void initialize() {
        super.showGrid(false);
        super.setScreenSize(Display.SIZE, Display.SIZE);
        super.setTurnTimer(30);

        Phase.setUp(this);
        InteractiveObject.setGame(this);
        GameStatistics.setGame(this);
        Score.setGame(this);
        display = new Display();
        controller = new Controller(this);
        boardManager = new BoardManager(this);
        shop = new Shop(this);
        player = new Player(this);
        isStopped = true;

        Phase.setActive(Phase.MAIN);
    }

    /**
     * Defines what happens at every tick.
     * In this particular case it updates the view related to current game phase
     * and draws pixels from the invisible Display on real game screen.
     *
     * @param step count of frames since the launch of the application.
     */
    @Override
    public void onTurn(int step) {
        Phase.updateView();
        display.draw();
    }

    public void startNewGame() {
        Options.apply();
        resetAssets();
        Phase.setActive(Phase.BOARD);
        PopUpMessage.show("Новая игра");
    }

    private void resetAssets() {
        boardManager.reset();
        player.reset();
        shop.reset();
        isStopped = false;
        result = Result.UNDEFINED;
    }

    public void win() {
        finish(Result.WIN);
        player.getScore().registerTopScore();
    }

    public void lose() {
        finish(Result.LOSE);
        shop.getDice().hide();
        boardManager.revealMines();
    }

    private void finish(Result result) {
        this.isStopped = true;
        this.result = result;
        View.setGameOverShowDelay(30);
        Phase.setActive(Phase.GAME_OVER);
        GameStatistics.update();
    }

    /*
     * Facade
     */

    public void leftClickOnBoard(int x, int y) {
        boardManager.interactWithLeftClick(x, y);
        shop.checkExpiredItems();
    }

    public void rightClickOnBoard(int x, int y) {
        boardManager.interactWithRightClick(x, y);
        shop.checkExpiredItems();
    }

    public void aimWithScannerOrBomb(Cell cell) {
        if (isStopped()) return;
        shop.aimWithScanner(cell);
        shop.aimWithBomb(cell);
    }

    public void destroyCell(Cell cell) {
        boardManager.destroyCell(cell);
        boardManager.cleanUpAfterMineDestruction();
    }

    public boolean isScannerOrBombActivated() {
        Scanner scanner = shop.getScanner();
        Bomb bomb = shop.getBomb();
        return scanner.isActivated() || bomb.isActivated();
    }

    /*
     * Method shortcuts
     */

    public Cell getCell(int x, int y) {
        return boardManager.getCell(x, y);
    }

    public void refreshOpenedCellsGraphics() {
        boardManager.refreshOpenedCellsGraphics();
    }

    public void scanNeighbors(Cell cell) {
        boardManager.scanNeighbors(cell);
    }

    public void drawGameBoard() {
        boardManager.draw();
    }

    @DeveloperOption
    public void autoSolve() {
        boardManager.autoSolve();
    }

    @DeveloperOption
    public void autoFlag() {
        boardManager.autoFlag();
    }

    @DeveloperOption
    public void autoOpen() {
        boardManager.autoOpen();
    }

    @DeveloperOption
    public void autoScan() {
        boardManager.autoScan();
    }

    public int countPlayerFlags() {
        return player.countFlags();
    }

    public void stopRecursion() {
        boardManager.setRecursiveMove(false);
    }

    public void restrictFlagExplosion() {
        boardManager.setFlagExplosionAllowed(false);
    }

    /*
     * Plain getters
     */

    public List<Cell> getAllCells(Predicate<Cell> predicate) {
        return boardManager.getAllCells(predicate);
    }

    public Shop getShop() {
        return shop;
    }

    public boolean shovelIsActivated() {
        return shop.getShovel().isActivated();
    }

    public Player getPlayer() {
        return player;
    }

    public Score getScore() {
        return player.getScore();
    }

    public boolean isStopped() {
        return isStopped;
    }

    public int getTimerBonus() {
        return boardManager.getTimer().getScoreBonus();
    }

    public boolean isWon() {
        return result == Result.WIN;
    }

    public boolean isRecursiveMove() {
        return boardManager.isRecursiveMove();
    }

    public boolean isFlagExplosionAllowed() {
        return boardManager.isFlagExplosionAllowed();
    }

    public boolean isFirstMove() {
        return boardManager.isFirstMove();
    }

    /*
     * Plain setters
     */

    public void setInterlacedEffect(boolean enabled) {
        display.setInterlaceEnabled(enabled);
    }

    public void drawPixel(int x, int y, Color color) {
        display.drawPixel(x, y, color);
    }


    /*
     * Controls
     */

    @Override
    public void onMouseLeftClick(int x, int y) {
        controller.click(x, y, Click.LEFT);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        controller.click(x, y, Click.RIGHT);
    }

    @Override
    public void onKeyPress(Key key) {
        controller.pressKey(key);
    }
}