package com.javarush.games.minesweeper.model.board;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.model.DrawableObject;
import com.javarush.games.minesweeper.view.graphics.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Logical game element and a cell for drawing sprites inside.
 * Each tile is 10x10 pixels. Each tile can have a single sprite assigned to it that can be revealed.
 */

public class Cell extends DrawableObject {
    private static final Map<Integer, VisualElement> sprites = new HashMap<>();

    public boolean isMined;                // contains a mine
    public boolean isOpen;                 // revealed
    public boolean isScanned;              // revealed using a scanner
    public boolean isShielded;             // revealed using a shield
    public boolean isGameOverCause;        // was clicked last before game over
    public boolean isFlagged;              // flagged by player
    public boolean isDestroyed;            // blown up by the bomb
    public int countMinedNeighbors;        // number of adjacent mines

    private final VisualElement visualElement; // visual element assigned to the body: opened, closed or destroyed
    private final Image background;            // the "body" of the cell
    private Image sprite;                      // foreground image (number, flag or mine)

    static {
        sprites.put(0, VisualElement.NONE);
        sprites.put(1, VisualElement.SPR_BOARD_1);
        sprites.put(2, VisualElement.SPR_BOARD_2);
        sprites.put(3, VisualElement.SPR_BOARD_3);
        sprites.put(4, VisualElement.SPR_BOARD_4);
        sprites.put(5, VisualElement.SPR_BOARD_5);
        sprites.put(6, VisualElement.SPR_BOARD_6);
        sprites.put(7, VisualElement.SPR_BOARD_7);
        sprites.put(8, VisualElement.SPR_BOARD_8);
        sprites.put(9, VisualElement.SPR_BOARD_9);
    }

    public Cell(VisualElement visualElement, int x, int y, boolean isMined) {
        this.background = new Image(visualElement, x * 10, y * 10);
        this.visualElement = visualElement;
        this.x = x;
        this.y = y;
        this.isMined = isMined;
        updateColors();
    }

    @Override
    public void draw() {
        if (isOpen) {
            background.matrix = isDestroyed ?
                    background.getMatrixFromStorage(VisualElement.CELL_DESTROYED) :
                    background.getMatrixFromStorage(VisualElement.CELL_OPENED);
            background.replaceColor(Theme.CELL_BG_DOWN.getColor(), 3);
        }

        if (isFlaggedCorrectly() && game.isStopped && !game.isVictory) {
            background.replaceColor(Color.GREEN, 3);
            setSprite(VisualElement.SPR_BOARD_MINE);
        } else if (isShielded) {
            background.replaceColor(Color.YELLOW, 3);
        } else if (isScanned) {
            background.replaceColor(Theme.CELL_SCANNED.getColor(), 3);
        } else if (isDestroyed) {
            background.replaceColor(Color.DARKSLATEGRAY, 3);
        } else if (isGameOverCause) {
            background.replaceColor(Color.RED, 3);
        }
        background.draw();

        if (isFlagged || isOpen) {
            sprite.draw();
        }
    }

    public void updateColors() {
        background.colors = new ImageStorage(visualElement).getColors();
    }

    public void setSprite(VisualElement visualElement) {
        this.sprite = new Image(visualElement, x * 10, y * 10);
    }

    public void setSprite(int number) {
        if (number < 0 || number > 9) throw new IllegalArgumentException("Sprites numbers must be from 0 to 9");
        this.sprite = new Image(sprites.get(number), x * 10, y * 10);
    }

    public void makeNumberGold() {
        if (isNumerable() && game.shop.goldenShovel.isActivated()) {
            sprite.replaceColor(Color.YELLOW, 1);
        }
    }

    // Combined states

    public boolean isEmpty() {
        return (!isMined && countMinedNeighbors == 0);   // Cell is not mined and doesn't contain a number
    }

    public boolean isNumerable() {
        return (!isMined && !isDestroyed && !isFlagged); // A number can be assigned to this cell
    }

    public boolean isFlaggedCorrectly() {
        return (isFlagged && isMined && !isDestroyed);   // Is mined, marked with a flag and is not destroyed
    }

    public boolean isIndestructible() {                  // Cannot be exploded with a bomb
        boolean activated = (isOpen || isDestroyed);
        boolean noFlagDestruction = (isFlagged && !game.allowFlagExplosion);
        return (game.isStopped || activated || noFlagDestruction);
    }

    /**
     * Any list of cells can be filtered by some criteria.
     */

    public enum Filter {CLOSED, DANGEROUS, MINED, NONE, NUMERABLE, OPEN, SAFE, SUSPECTED, EMPTY, SCORED, FLAGGED}

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
