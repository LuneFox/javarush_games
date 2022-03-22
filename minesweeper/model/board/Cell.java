package com.javarush.games.minesweeper.model.board;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.model.InteractiveObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Logical game element and a cell for drawing sprites inside.
 * Each tile is 10x10 pixels. Each tile can have a single sprite assigned to it that can be revealed.
 */

public class Cell extends InteractiveObject {
    private static final Map<Integer, ImageType> sprites = new HashMap<>();
    private boolean isMined;
    private boolean isOpen;
    private boolean isScanned;
    private boolean isShielded;
    private boolean isGameOverCause;
    private boolean isFlagged;
    private boolean isDestroyed;
    private int countMinedNeighbors;  // number of adjacent mines
    private final Image background;
    private Image sprite;

    public enum Filter {CLOSED, DANGEROUS, EMPTY, FLAGGED, MINED, NONE, NUMERABLE, OPEN, SAFE, SCORED, SUSPECTED}

    static {
        for (int i = 0; i < 10; i++) {
            sprites.put(i, ImageType.valueOf("BOARD_" + i));
        }
    }

    public Cell(ImageType imageType, int x, int y, boolean isMined) {
        this.background = new Image(imageType, x * 10, y * 10);
        this.x = x;
        this.y = y;
        this.isMined = isMined;
    }

    @Override
    public void draw() {
        background.draw();
        if (isFlagged || isOpen) {
            sprite.draw();
        }
    }

    public void open() {
        isOpen = true;
        background.matrix = background.getMatrixFromStorage(ImageType.CELL_OPENED);
        if (isGameOverCause) {
            setBackgroundColor(Color.RED);
        } else {
            setBackgroundColor(Theme.CELL_BG_DOWN.getColor());
        }
    }

    public void destroy() {
        isOpen = true;
        isDestroyed = true;
        isMined = false;
        background.matrix = background.getMatrixFromStorage(ImageType.CELL_DESTROYED);
        setBackgroundColor(Color.DARKSLATEGRAY);
        setSprite(ImageType.NONE);
    }

    public void setScanned() {
        isScanned = true;
        setBackgroundColor(Theme.CELL_SCANNED.getColor());
    }

    // Update colors for opened state
    public void updateOpenedColors() {
        if (!isOpen) return;

        background.matrix = background.getMatrixFromStorage(ImageType.CELL_OPENED);
        if (isOpen) setBackgroundColor(Theme.CELL_BG_DOWN.getColor());

        if (isGameOverCause) setBackgroundColor(Color.RED);
        else if (isShielded) setBackgroundColor(Color.YELLOW);
        else if (isScanned) setBackgroundColor(Theme.CELL_SCANNED.getColor());
        else if (isDestroyed) {
            background.matrix = background.getMatrixFromStorage(ImageType.CELL_DESTROYED);
            setBackgroundColor(Color.DARKSLATEGRAY);
        }
    }

    public void setBackgroundColor(Color color) {
        background.replaceColor(color, 1);
    }

    public void setSprite(ImageType imageType) {
        this.sprite = new Image(imageType, x * 10, y * 10);
    }

    public void setSprite(int number) {
        if (number < 0 || number > 9) throw new IllegalArgumentException("Sprites numbers must be from 0 to 9");
        this.sprite = new Image(sprites.get(number), x * 10, y * 10);
    }

    public void makeNumberYellow() {
        if (isNumerable() && countMinedNeighbors > 0) {
            sprite.replaceColor(Color.YELLOW, 1);
        }
    }

    public static List<Cell> filterCells(List<Cell> list, Filter filter) {
        List<Cell> result = new ArrayList<>();
        list.forEach(cell -> {
            switch (filter) {
                case CLOSED:
                    if (!cell.isOpen) result.add(cell);
                    break;
                case OPEN:
                    if (cell.isOpen) result.add(cell);
                    break;
                case MINED:
                    if (cell.isMined) result.add(cell);
                    break;
                case FLAGGED:
                    if (cell.isFlagged) result.add(cell);
                    break;
                case SAFE:
                    // Unrevealed cell that is safe to click
                    if (!cell.isOpen && !cell.isMined) result.add(cell);
                    break;
                case DANGEROUS:
                    // Unrevealed cell that is not safe to click
                    if (!cell.isOpen & cell.isMined) result.add(cell);
                    break;
                case NUMERABLE:
                    // Cell that can have number over it
                    if (cell.isNumerable()) result.add(cell);
                    break;
                case SUSPECTED:
                    // Is flagged or revealed with shield (for auto-opening surrounding cells)
                    if (cell.isFlagged || (cell.isOpen && cell.isMined)) result.add(cell);
                    break;
                case EMPTY:
                    // Does not contain mines or numbers
                    if (cell.isEmpty()) result.add(cell);
                    break;
                case SCORED:
                    // Is counted when calculating score
                    if (cell.isOpen && !cell.isMined && !cell.isDestroyed) result.add(cell);
                    break;
                case NONE:
                default:
                    result.add(cell);
                    break;
            }
        });
        return result;
    }

    // Combined states

    public boolean isEmpty() {
        return (!isMined && countMinedNeighbors == 0);
    }

    public boolean isNumerable() {
        return (!isMined && !isDestroyed && !isFlagged);
    }

    public boolean isIndestructible() {
        boolean isActivated = isOpen || isDestroyed;
        boolean isUnableToDestroyFlag = isFlagged && !game.boardManager.isFlagExplosionAllowed();
        return game.isStopped || isActivated || isUnableToDestroyFlag;
    }

    // Getters, setters

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isMined() {
        return isMined;
    }

    public void setMined(boolean mined) {
        isMined = mined;
    }

    public void setShielded(boolean shielded) {
        isShielded = shielded;
    }

    public void setGameOverCause(boolean gameOverCause) {
        isGameOverCause = gameOverCause;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public int getCountMinedNeighbors() {
        return countMinedNeighbors;
    }

    public void setCountMinedNeighbors(int countMinedNeighbors) {
        this.countMinedNeighbors = countMinedNeighbors;
    }
}
