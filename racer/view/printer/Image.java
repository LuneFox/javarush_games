package com.javarush.games.racer.view.printer;

import com.javarush.engine.cell.Color;
import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.view.Display;

public class Image {
    public static final int CENTER = Integer.MIN_VALUE;
    protected static RacerGame game;

    private static final Color[] colors = new Color[]{Color.NONE, Color.WHITE};
    public int x;
    public int y;
    public int width;
    public int height;
    public int[][] matrix;

    public static void setGame(RacerGame game) {
        Image.game = game;
    }

    public Image(Symbol type) {
        this(type, 0, 0);
    }

    public Image(Symbol symbol, int x, int y) {
        setPosition(x, y);
        this.matrix = SymbolDataStorage.getData(symbol);
        this.height = matrix.length;
        this.width = matrix[0].length;
    }

    public final void setPosition(int drawX, int drawY) {
        this.x = drawX == CENTER ? getCenterH() : drawX;
        this.y = drawY == CENTER ? getCenterV() : drawY;
    }

    private int getCenterH() {
        return (Display.SIZE / 2) - (matrix[0].length / 2);
    }

    private int getCenterV() {
        return (Display.SIZE / 2) - (matrix.length / 2);
    }

    public final void draw(int x, int y) {
        setPosition(x, y);
        draw();
    }

    public void draw() {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                int pixel = matrix[j][i];
                if (isPixelTransparent(pixel)) continue;

                int drawX = x + i;
                int drawY = y + j;

                game.display.drawPixel(drawX, drawY, colors[pixel]);
            }
        }
    }

    private boolean isPixelTransparent(int pixel) {
        final int TRANSPARENT = 0;
        return (pixel == TRANSPARENT || colors[pixel] == Color.NONE);
    }

    public final void changeColor(Color color, int number) {
        try {
            colors[number] = color;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Could not change color! " + e.getMessage());
        }
    }
}
