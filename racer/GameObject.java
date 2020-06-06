package com.javarush.games.racer;

import com.javarush.engine.cell.*;

public class GameObject {
    public double x;
    public double y;
    public int width;
    public int height;
    public int[][] matrix;
    public int[][] hitBox;

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

    public boolean isCollisionPossible(GameObject otherGameObject) {
        if (x > otherGameObject.x + otherGameObject.width || x + width < otherGameObject.x) {
            return false;
        }

        if (y > otherGameObject.y + otherGameObject.height || y + height < otherGameObject.y) {
            return false;
        }

        return true;
    }

    public boolean isCollisionWithHitBox(GameObject gameObject) {
        if (!isCollisionPossible(gameObject)) {
            return false;
        }

        for (int carY = 0; carY < gameObject.height; carY++) {
            for (int carX = 0; carX < gameObject.width; carX++) {
                // проходим по непустым ячейкам машины
                if (gameObject.hitBox[carY][carX] != 0) {
                    if (isCollision(carX + (int) gameObject.x, carY + (int) gameObject.y)) {
                        // координаты непустых пикселей хитбокса машины совпадают с координатами непустых пикселей объекта?
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isCollision(int x, int y) {
        for (int matrixY = 0; matrixY < height; matrixY++) {
            for (int matrixX = 0; matrixX < width; matrixX++) {
                if (matrix[matrixY][matrixX] != 0 && matrixX + (int) this.x == x && matrixY + (int) this.y == y) {
                    return true;
                }
            }
        }
        return false;
    }

}