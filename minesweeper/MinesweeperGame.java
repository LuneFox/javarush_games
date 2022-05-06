package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.gui.Display;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.board.*;
import com.javarush.games.minesweeper.model.player.Inventory;
import com.javarush.games.minesweeper.model.player.Player;
import com.javarush.games.minesweeper.model.Results;
import com.javarush.games.minesweeper.model.player.Score;
import com.javarush.games.minesweeper.model.shop.Shop;
import com.javarush.games.minesweeper.model.shop.item.Bomb;
import com.javarush.games.minesweeper.model.shop.item.Scanner;
import com.javarush.games.minesweeper.view.View;

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
    private boolean isStopped;
    private boolean isResultVictory;

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
        Results.setGame(this);
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
        resetValues();
        Phase.setActive(Phase.BOARD);
        PopUpMessage.show("Новая игра");
    }

    private void resetValues() {
        boardManager.reset();
        shop.reset();
        player.reset();
        isStopped = false;
        isResultVictory = false;
    }

    public void win() {
        player.getScore().registerTopScore();
        finish(true);
    }

    public void lose() {
        shop.getDice().hide();
        boardManager.getField().revealMines();
        finish(false);
    }

    private void finish(boolean isVictory) {
        isStopped = true;
        isResultVictory = isVictory;
        View.setGameOverShowDelay(30);
        Phase.setActive(Phase.GAME_OVER);
        Results.update();
    }

    /*
     * Facade
     */

    public void setInterlacedEffect(boolean enabled) {
        display.setInterlaceEnabled(enabled);
    }

    public void drawPixel(int x, int y, Color color) {
        display.setCellColor(x, y, color);
    }

    public void drawGameBoard() {
        boardManager.drawGameBoard();
    }

    public Cell getCellByLogicalPosition(int x, int y) {
        Field field = boardManager.getField();
        return field.getCell(x, y);
    }

    public Cell getCellByCoordinates(int x, int y) {
        Field field = boardManager.getField();
        return field.getCell(x / 10, y / 10);
    }


    public int countCells(CellFilter filter) {
        return boardManager.getField().countAllCells(filter);
    }

    public void open(Cell cell) {
        boardManager.openCell(cell.x, cell.y);
    }

    public void useItem(Cell cell) {
        boardManager.useItemOnCell(cell.x, cell.y);
    }

    public void openSurrounding(Cell cell) {
        boardManager.openSurroundingCells(cell.x, cell.y);
    }

    public void swapFlag(Cell cell) {
        boardManager.swapFlag(cell.x, cell.y);
    }

    public void useScanner(Cell cell) {
        boardManager.scanNeighbors(cell.x, cell.y);
    }

    public void useMiniBomb(Cell cell) {
        boardManager.destroyCell(cell.x, cell.y);
        boardManager.cleanUpAfterMineDestruction();
    }

    public void skipInventoryMoneyAnimation() {
        Inventory inventory = player.getInventory();
        inventory.skipMoneyAnimation();
    }

    public boolean isScannerOrBombActivated() {
        Scanner scanner = shop.getScanner();
        Bomb bomb = shop.getBomb();
        return scanner.isActivated() || bomb.isActivated();
    }

    public void checkExpiredItems() {
        shop.checkExpiredItems();
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

    @DeveloperOption
    public void autoSolve() {
        boardManager.autoSolve();
    }

    public boolean isFirstMove() {
        return boardManager.isFirstMove();
    }

    public boolean isRecursiveMove() {
        return boardManager.isRecursiveMove();
    }

    public void setRecursiveMove(boolean enable) {
        boardManager.setRecursiveMove(enable);
    }

    public boolean isFlagExplosionAllowed() {
        return boardManager.isFlagExplosionAllowed();
    }

    public void setFlagExplosionAllowed(boolean enable) {
        boardManager.setFlagExplosionAllowed(enable);
    }

    public void applyThemeChangeToOpenedCells() {
        boardManager.reapplyOpenedCellsVisuals();
    }

    public Timer getTimer() {
        return boardManager.getTimer();
    }

    public Shop getShop() {
        return shop;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public boolean isResultVictory() {
        return isResultVictory;
    }


    /*
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