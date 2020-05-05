package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.MinesweeperGame;

public class Button {
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private Image body;
    private Text textArtist;
    private Color textColor;
    private String text;
    private int textOffset = 2;

    public Button(MinesweeperGame game, int posX, int posY, int sizeX, int sizeY, String text) {
        this.textArtist = new Text(Bitmap.BOARD_NONE, game);
        this.x1 = posX;
        this.y1 = posY;
        this.x2 = posX + sizeX;
        this.y2 = posY + sizeY;
        this.text = text;
        int textLength = textArtist.calculateLength(text);
        this.textOffset = ((sizeX - textLength) / 2) + 1;
        this.textColor = Color.WHITE;
        this.body = new Image(Bitmap.MENU_BUTTON, game, posX, posY) {
            @Override
            protected int[][] assignBitmap(Bitmap bitmap) {
                return createWindowBitmap(x2 - x1, y2 - y1, true, true);
            }
        };
        this.body.colors = new Color[]{Color.NONE, Color.DARKRED, Color.BLACK, Color.SALMON};
        body.assignBitmap(Bitmap.MENU_BUTTON);
    }

    public Button(MinesweeperGame game, int posX, int posY, String text) {
        this.textArtist = new Text(Bitmap.BOARD_NONE, game);
        this.x1 = posX;
        this.y1 = posY;
        this.x2 = posX + textArtist.calculateLength(text) + 3;
        this.y2 = posY + 9;
        this.text = text;
        this.textColor = Color.WHITE;
        this.body = new Image(Bitmap.MENU_BUTTON, game, posX, posY) {
            @Override
            protected int[][] assignBitmap(Bitmap bitmap) {
                return createWindowBitmap(x2 - x1, y2 - y1, true, true);
            }
        };
        this.body.colors = new Color[]{Color.NONE, Color.DARKRED, Color.BLACK, Color.SALMON};
        body.assignBitmap(Bitmap.MENU_BUTTON);
    }

    public void draw() {
        this.body.draw();
        textArtist.write(text, textColor, x1 + textOffset, y1, false);
    }

    public boolean has(int x, int y) {
        return (x >= x1 && x <= x2 && y >= y1 && y <= y2);
    }
}
