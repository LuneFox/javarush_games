package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.MinesweeperGame;

/**
 * Images of numbers and icons to draw over tiles, and not only tiles if needed.
 * Each sprite is 10x10 pixels.
 */

public class Sprite extends Image {
    public Sprite(Bitmap bitmap, MinesweeperGame game, int x, int y) { // creates a sprite in a logical position
        super(bitmap, game, x, y);
    }

    protected int[][] assignBitmap(Bitmap bitmap) {
        switch (bitmap) {
            case BOARD_1: {
                colors[1] = Color.DARKGREEN;
                return new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},

                };
            }
            case BOARD_2: {
                colors[1] = Color.DARKBLUE;
                return new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 1, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                };
            }
            case BOARD_3: {
                colors[1] = Color.DARKRED;
                return new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 1, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                };
            }
            case BOARD_4: {
                colors[1] = Color.PURPLE;
                return new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 1, 0, 0},
                        {0, 0, 0, 0, 1, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 1, 1, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                };
            }
            case BOARD_5: {
                colors[1] = Color.DARKSLATEGRAY;
                return new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 1, 1, 0, 0},
                        {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 1, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                };
            }
            case BOARD_6: {
                colors[1] = Color.DARKSLATEBLUE;
                return new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 1, 0, 0},
                        {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 0, 1, 0, 0},
                        {0, 0, 0, 0, 1, 1, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                };
            }
            case BOARD_7: {
                colors[1] = Color.FORESTGREEN;
                return new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                };
            }
            case BOARD_8: {
                colors[1] = Color.RED;
                return new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 0, 1, 0, 0},
                        {0, 0, 0, 0, 1, 1, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                };
            }
            case BOARD_9: {
                colors[1] = Color.WHITE;
                return new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 1, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 1, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                };
            }
            case BOARD_0: {
                colors[1] = Color.WHITE;
                return new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 1, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 1, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 1, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 1, 0, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                };
            }
            case BOARD_FLAG: {
                colors = new Color[]{Color.NONE, Color.BLACK, Color.RED, Color.DARKRED, Color.YELLOW};
                return new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 1, 2, 2, 0, 0, 0, 0},
                        {0, 0, 0, 1, 4, 2, 2, 2, 0, 0},
                        {0, 0, 0, 1, 2, 2, 3, 3, 0, 0},
                        {0, 0, 0, 1, 3, 3, 0, 0, 0, 0},
                        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                };
            }
            case BOARD_MINE: {
                colors = new Color[]{
                        Color.NONE,
                        Color.BLACK,
                        Color.RED,
                        Color.DARKGRAY,
                        Color.DARKSLATEGRAY,
                        Color.DARKRED,
                        Color.DIMGRAY
                };
                return new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 1, 1, 0, 0},
                        {0, 0, 0, 1, 3, 4, 4, 6, 1, 0},
                        {0, 0, 0, 1, 4, 2, 5, 4, 1, 1},
                        {0, 0, 1, 1, 4, 5, 5, 4, 1, 0},
                        {0, 0, 0, 1, 6, 4, 4, 6, 1, 0},
                        {0, 0, 0, 0, 1, 1, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                };
            }
            default: {
                return new int[1][1];
            }
        }
    }
}
