package com.javarush.games.minesweeper;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.graphics.*;

/**
 * Logical game element and a cell for drawing sprites inside.
 * Each tile is 10x10 pixels. Each tile can have a single sprite assigned to it that can be revealed.
 */

class Cell extends Image {
    int x;
    int y;                          // logical position
    private Image sprite;          // a sprite can be assigned to a tile to be drawn over it
    private VisualElement visualElement;
    boolean isMined;                // this tile contains mine
    boolean isOpen;                 // this tile has been revealed
    boolean isShielded;             // this tile has been blocked with shield
    boolean isScanned;              // this tile has been revealed with scanner
    boolean isFlagged;              // this tile was flagged by player
    boolean isDestroyed;            // who did this? :(
    int countMinedNeighbors;        // how many neighboring tiles have mines

    Cell(VisualElement visualElement, int x, int y, boolean isMined) {
        super(visualElement, x * 10, y * 10);
        this.visualElement = visualElement;
        this.x = x;
        this.y = y;
        this.isMined = isMined;
        this.sprite = (isMined) ? getSprite(VisualElement.SPR_BOARD_MINE) : getSprite(VisualElement.SPR_BOARD_NONE);
        fullRecolor();
        draw();
    }

    // draws a button in a pushed state and reveals its sprite (number or icon), used while opening a tile
    public void push() {
        matrix = (isDestroyed) ? assignMatrix(VisualElement.CELL_DESTROYED) : assignMatrix(VisualElement.CELL_OPENED);
        if (isFlaggedCorrectly()) {
            replaceColor(Color.GREEN, 3);
        } else if (isShielded) {
            replaceColor(Color.YELLOW, 3);
        } else if (isScanned) {
            replaceColor(Theme.CELL_SCANNED.getColor(), 3);
        } else if (isDestroyed) {
            replaceColor(Color.DARKSLATEGRAY, 3);
        } else {
            replaceColor(Theme.CELL_BG_DOWN.getColor(), 3);
        }
        draw();
        drawSprite();
    }

    public void fullRecolor() {
        colors = new ImageStorage(visualElement).getColors();
        if (isOpen) push();
    }


    Image getSprite(VisualElement visualElement) {
        return new Image(visualElement, x * 10, y * 10);
    }

    // assigns a sprite to this tile by its name
    void assignSprite(VisualElement visualElement) {
        this.sprite = getSprite(visualElement);
    }

    // assigns a number sprite to this tile, used to draw the number of mines nearby
    void assignSprite(int number) {
        this.sprite = new Image(VisualElement.getSpriteByNumber(number), x * 10, y * 10);
    }

    void eraseSprite() {
        this.sprite = new Image(VisualElement.SPR_BOARD_NONE, x * 10, y * 10);
        draw();
    }

    void makeSpriteYellow() {
        sprite.replaceColor(Color.YELLOW, 1);
    }

    void drawSprite() {
        sprite.draw();
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
        // Cell contains unrevealed mine
        return (isMined && !isOpen);
    }

    public boolean isScored() {
        return (isOpen && !isMined && !isDestroyed);
    }
}
