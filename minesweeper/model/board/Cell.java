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

    public boolean isMined;                // contains a mine
    public boolean isOpen;                 // revealed
    public boolean isScanned;              // revealed using a scanner
    public boolean isShielded;             // revealed using a shield
    public boolean isGameOverCause;        // was clicked last before game over
    public boolean isFlagged;              // flagged by player
    public boolean isDestroyed;            // blown up by the bomb
    public int countMinedNeighbors;        // number of adjacent mines

    private final Image background;        // square "body" of the cell
    private Image sprite;                  // foreground image (number, flag or mine)

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
        if (isNumerable() && game.shop.goldenShovel.isActivated()) {
            if (countMinedNeighbors > 0) {
                sprite.replaceColor(Color.YELLOW, 1);
            }
        }
    }

    // Combined states

    public boolean isEmpty() {
        return (!isMined && countMinedNeighbors == 0);   // Cell is not mined and doesn't contain a number
    }

    public boolean isNumerable() {
        return (!isMined && !isDestroyed && !isFlagged); // A number can be assigned to this cell
    }

    public boolean isIndestructible() {                  // Cannot be exploded with a bomb
        boolean activated = isOpen || isDestroyed;
        boolean noFlagDestruction = isFlagged && !game.allowFlagExplosion;
        return game.isStopped || activated || noFlagDestruction;
    }

    /**
     * Any list of cells can be filtered by some criteria.
     */

    public enum Filter {CLOSED, DANGEROUS, EMPTY, FLAGGED, MINED, NONE, NUMERABLE, OPEN, SAFE, SCORED, SUSPECTED}

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
                    // Cell that must have number over it
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
}
