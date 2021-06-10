package com.javarush.games.minesweeper;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Image;
import com.javarush.games.minesweeper.graphics.Sprite;

import java.util.HashMap;

/**
 * Logical game element and a cell for drawing sprites inside.
 * Each tile is 10x10 pixels. Each tile can have a single sprite assigned to it that can be revealed.
 */

class Cell extends Image {
    private static HashMap<Integer, Bitmap> spriteNumbers = new HashMap<>();
    int x;
    int y;                          // logical position
    private Sprite sprite;          // a sprite can be assigned to a tile to be drawn over it
    boolean isMined;                // this tile contains mine
    boolean isOpen;                 // this tile has been revealed
    boolean isShielded;             // this tile has been blocked with shield
    boolean isScanned;              // this tile has been revealed with scanner
    boolean isFlagged;              // this tile was flagged by player
    boolean isDestroyed;            // who did this? :(
    int countMinedNeighbors;        // how many neighboring tiles have mines

    static {
        spriteNumbers.put(0, Bitmap.BOARD_NONE);
        spriteNumbers.put(1, Bitmap.BOARD_1);
        spriteNumbers.put(2, Bitmap.BOARD_2);
        spriteNumbers.put(3, Bitmap.BOARD_3);
        spriteNumbers.put(4, Bitmap.BOARD_4);
        spriteNumbers.put(5, Bitmap.BOARD_5);
        spriteNumbers.put(6, Bitmap.BOARD_6);
        spriteNumbers.put(7, Bitmap.BOARD_7);
        spriteNumbers.put(8, Bitmap.BOARD_8);
        spriteNumbers.put(9, Bitmap.BOARD_9);
    }

    Cell(Bitmap bitmap, MinesweeperGame game, int x, int y, boolean isMined) {
        super(bitmap, game, x * 10, y * 10);
        this.x = x;
        this.y = y;
        this.isMined = isMined;
        if (isMined) {
            assignSprite(Bitmap.BOARD_MINE);
        } else {
            assignSprite(Bitmap.BOARD_NONE);
        }
        colors = new Color[]{Color.NONE, Color.SADDLEBROWN, Color.BLANCHEDALMOND, Color.BURLYWOOD, Color.BLACK, Color.GRAY};
        draw();
    }

    // draws a button in a pushed state and reveals its sprite (number or icon), used while opening a tile
    void push() {
        if (isDestroyed) {
            bitmapData = assignBitmap(Bitmap.CELL_DESTROYED);
        } else {
            bitmapData = assignBitmap(Bitmap.CELL_OPENED);
        }
        if (isFlagged && isMined && !isDestroyed) {
            replaceColor(Color.GREEN, 3); // marks right guesses on game over
        } else if (isShielded) {
            replaceColor(Color.YELLOW, 3); // shielded remain yellow on push
        } else if (isScanned) {
            replaceColor(Color.ORANGE, 3); // scanned remain light blue
        } else if (isDestroyed) {
            replaceColor(Color.DARKSLATEGRAY, 3); // boom
        } else {
            replaceColor(Color.SANDYBROWN, 3);
        }
        draw();
        drawSprite();
    }

    // assigns a sprite to this tile by its name
    void assignSprite(Bitmap bitmap) {
        this.sprite = new Sprite(bitmap, game, x * 10, y * 10);
    }

    // assigns a number sprite to this tile, used to draw the number of mines nearby
    void assignSprite(int number) {
        this.sprite = new Sprite(spriteNumbers.get(number), game, x * 10, y * 10);
    }

    // draws an assigned sprite over the tile
    void drawSprite() {
        sprite.draw();
    }

    void changeSpriteColor(Color color) {
        sprite.replaceColor(color, 1);
    }

    // assigns an empty sprite and draws the tile again to remove whatever is drawn over it
    void eraseSprite() {
        this.sprite = new Sprite(Bitmap.BOARD_NONE, game, x * 10, y * 10);
        draw();
    }

    protected int[][] assignBitmap(Bitmap bitmap) {
        switch (bitmap) {
            case CELL_CLOSED:
                return new int[][]{
                        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                        {2, 3, 3, 3, 3, 3, 3, 3, 3, 1},
                        {2, 3, 3, 3, 3, 3, 3, 3, 3, 1},
                        {2, 3, 3, 3, 3, 3, 3, 3, 3, 1},
                        {2, 3, 3, 3, 3, 3, 3, 3, 3, 1},
                        {2, 3, 3, 3, 3, 3, 3, 3, 3, 1},
                        {2, 3, 3, 3, 3, 3, 3, 3, 3, 1},
                        {2, 3, 3, 3, 3, 3, 3, 3, 3, 1},
                        {2, 3, 3, 3, 3, 3, 3, 3, 3, 1},
                        {2, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                };
            case CELL_OPENED:
                return new int[][]{
                        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                        {2, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {2, 1, 3, 3, 3, 3, 3, 3, 3, 3},
                        {2, 1, 3, 3, 3, 3, 3, 3, 3, 3},
                        {2, 1, 3, 3, 3, 3, 3, 3, 3, 3},
                        {2, 1, 3, 3, 3, 3, 3, 3, 3, 3},
                        {2, 1, 3, 3, 3, 3, 3, 3, 3, 3},
                        {2, 1, 3, 3, 3, 3, 3, 3, 3, 3},
                        {2, 1, 3, 3, 3, 3, 3, 3, 3, 3},
                        {2, 1, 3, 3, 3, 3, 3, 3, 3, 3},
                };
            case CELL_DESTROYED:
                return new int[][]{
                        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                        {2, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {2, 1, 5, 3, 3, 3, 5, 3, 3, 5},
                        {2, 1, 3, 5, 4, 3, 5, 3, 5, 3},
                        {2, 1, 5, 3, 3, 4, 4, 3, 4, 3},
                        {2, 1, 5, 4, 5, 4, 4, 3, 5, 3},
                        {2, 1, 3, 5, 3, 5, 3, 5, 3, 3},
                        {2, 1, 3, 5, 4, 3, 5, 4, 5, 5},
                        {2, 1, 3, 5, 3, 3, 3, 5, 3, 3},
                        {2, 1, 5, 3, 3, 3, 3, 5, 3, 3},
                };
            default:
                return new int[10][10];
        }
    }
}
