package com.javarush.games.minesweeper;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.graphics.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Logical game element and a cell for drawing sprites inside.
 * Each tile is 10x10 pixels. Each tile can have a single sprite assigned to it that can be revealed.
 */

public class Cell {
    private static final List<VisualElement> SPRITES = VisualElement.getElementsByPrefixes("SPR_");

    protected final int x;                    // logical position on a 10x10 grid
    protected final int y;
    protected boolean isMined;                // contains a mine
    protected boolean isOpen;                 // revealed
    protected boolean isScanned;              // revealed using a scanner
    protected boolean isShielded;             // revealed using a shield
    public boolean isFlagged;              // flagged by player
    protected boolean isDestroyed;            // blown up by the bomb
    protected int countMinedNeighbors;        // number of adjacent mines

    private final VisualElement visualElement;// visual element assigned to the body: opened, closed or destroyed
    private final Image background;           // the "body" of the cell
    private Image sprite;                     // foreground image (number, flag or mine)

    public enum Filter {CLOSED, DANGEROUS, MINED, NONE, NUMERABLE, OPEN, SAFE, SUSPECTED, EMPTY, SCORED, FLAGGED}

    protected Cell(VisualElement visualElement, int x, int y, boolean isMined) {
        this.background = new Image(visualElement, x * 10, y * 10);
        this.visualElement = visualElement;
        this.x = x;
        this.y = y;
        this.isMined = isMined;
        this.sprite = (isMined) ? createSprite(VisualElement.SPR_BOARD_MINE) : createSprite(VisualElement.SPR_BOARD_NONE);
        fullRecolor();
        background.draw();
    }

    // draws a button in a pushed state and reveals its sprite (number or icon), used while opening a tile
    public void push() {
        background.matrix = (isDestroyed) ? background.getMatrix(VisualElement.CELL_DESTROYED) : background.getMatrix(VisualElement.CELL_OPENED);
        if (isFlaggedCorrectly()) {
            drawBackground(Color.GREEN);
        } else if (isShielded) {
            drawBackground(Color.YELLOW);
        } else if (isScanned) {
            drawBackground(Theme.CELL_SCANNED.getColor());
        } else if (isDestroyed) {
            drawBackground(Color.DARKSLATEGRAY);
        } else {
            drawBackground(Theme.CELL_BG_DOWN.getColor());
        }
        drawSprite();
    }

    public void fullRecolor() {
        background.colors = new ImageStorage(visualElement).getColors();
        if (isOpen) push();
    }

    // Return a new sprite at the physical position of the cell
    protected Image createSprite(VisualElement visualElement) {
        return new Image(visualElement, x * 10, y * 10);
    }

    // Assign a sprite to this tile by its name
    protected void setSprite(VisualElement visualElement) {
        this.sprite = createSprite(visualElement);
    }

    // Assign a number sprite to this tile
    protected void setSprite(int number) {
        this.sprite = new Image(getSpriteByNumber(number), x * 10, y * 10);
    }

    // Actually replace it with a 1x1 transparent sprite
    protected void eraseSprite() {
        this.sprite = new Image(VisualElement.SPR_BOARD_NONE, x * 10, y * 10);
        background.draw();
    }

    protected void drawBackground(Color color) {
        if (color != Color.NONE) background.replaceColor(color, 3);
        background.draw();
    }

    // Display the sprite over the cell
    protected void drawSprite() {
        sprite.draw();
    }

    protected void changeSpriteColor(Color color) {
        sprite.replaceColor(color, 1);
    }

    public static VisualElement getSpriteByNumber(int number) {
        return (number == 0) ? VisualElement.SPR_BOARD_NONE : SPRITES.get(number);
    }

    public boolean isEmpty() {
        // Cell is not mined and doesn't contain a number
        return (!isMined && countMinedNeighbors == 0);
    }

    public boolean isNumerable() {
        // A number can be assigned to this cell
        return (!isMined && !isDestroyed && !isFlagged);
    }

    public boolean isFlaggedCorrectly() {
        // Cell is mined, marked with a flag and is not destroyed
        return (isFlagged && isMined && !isDestroyed);
    }

    public boolean isSuspected() {
        // Cell is flagged or revealed after shield
        return (isFlagged || (isOpen && isMined));
    }

    public boolean isSafe() {
        // Cell is safe to click and open
        return (!isOpen && !isMined);
    }

    public boolean isDangerous() {
        // Cell contains unrevealed mine, opposite of "safe", added just for semantics
        return (isMined && !isOpen);
    }

    public boolean isScored() {
        return (isOpen && !isMined && !isDestroyed);
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
                case DANGEROUS:
                    if (cell.isDangerous()) result.add(cell);
                    break;
                case NUMERABLE:
                    if (cell.isNumerable()) result.add(cell);
                    break;
                case SUSPECTED:
                    if (cell.isSuspected()) result.add(cell);
                    break;
                case SAFE:
                    if (cell.isSafe()) result.add(cell);
                    break;
                case EMPTY:
                    if (cell.isEmpty()) result.add(cell);
                    break;
                case SCORED:
                    if (cell.isScored()) result.add(cell);
                    break;
                case FLAGGED:
                    if (cell.isFlagged) result.add(cell);
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
