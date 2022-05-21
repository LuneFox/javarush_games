package com.javarush.games.racer.view.printer;

import com.javarush.engine.cell.Color;
import com.javarush.games.racer.RacerGame;

public class SymbolImage {
    private static RacerGame game;
    private static final Color[] colors = new Color[]{Color.NONE, Color.WHITE};
    private int x;
    private int y;
    private final int[][] matrix;

    public static void setGame(RacerGame game) {
        SymbolImage.game = game;
    }

    SymbolImage(Symbol type) {
        this.matrix = type.getMatrix();
    }

    void draw(int x, int y) {
        setPosition(x, y);
        draw();
    }

    private void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void draw() {
        for (int j = 0; j < getHeight(); j++) {
            for (int i = 0; i < getWidth(); i++) {

                int pixel = matrix[j][i];
                if (isPixelTransparent(pixel)) continue;

                int drawX = x + i;
                int drawY = y + j;

                game.display.drawPixel(drawX, drawY, colors[pixel]);
            }
        }
    }

    int getHeight(){
        return matrix.length;
    }

    int getWidth(){
        return matrix[0].length;
    }

    private boolean isPixelTransparent(int pixel) {
        final int TRANSPARENT = 0;
        return (pixel == TRANSPARENT || colors[pixel] == Color.NONE);
    }

    void changeColor(Color color) {
        try {
            colors[1] = color;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Could not change color! " + e.getMessage());
        }
    }
}
