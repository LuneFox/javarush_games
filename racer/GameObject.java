package com.javarush.games.racer;

import com.javarush.engine.cell.*;

public class GameObject {
    public double x;
    public double y;
    public int width;
    public int height;
    public int[][] matrix;
    public int[][] hitBoxMatrix;
    public HitBox hitBox;

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public GameObject(double x, double y, int[][] matrix) {
        this.x = x;
        this.y = y;
        this.matrix = matrix;
        width = matrix[0].length;
        height = matrix.length;
    }

    public void draw(RacerGame game) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int colorIndex = matrix[j][i];
                game.display.setCellColor((int) x + i, (int) y + j, Color.values()[colorIndex]);
            }
        }
    }
}