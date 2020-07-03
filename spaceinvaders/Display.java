package com.javarush.games.spaceinvaders;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

public class Display {
    private Game game;
    private Pixel[][] matrix;

    Display(Game game) {
        this.game = game;
        this.matrix = new Pixel[100][100];
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                matrix[y][x] = new Pixel();
            }
        }
    }

    public void draw() {
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                game.setCellColor(x, y, matrix[y][x].cellColor);
            }
        }
    }

    // OVERRIDES

    public void setCellColor(int x, int y, Color color) {
        if (x >= 0 && y >= 0 && x < SpaceInvadersGame.WIDTH && y < SpaceInvadersGame.HEIGHT) {
            if (color != Color.NONE) {
                this.matrix[y][x].cellColor = color;
            }
        }
    }

    public void setCellValue(int x, int y, String value) {
        if (x >= 0 && y >= 0 && x < SpaceInvadersGame.WIDTH && y < SpaceInvadersGame.HEIGHT) {
            this.matrix[y][x].value = value;
        }
    }

    public void setCellNumber(int x, int y, int value) {
        if (x >= 0 && y >= 0 && x < SpaceInvadersGame.WIDTH && y < SpaceInvadersGame.HEIGHT) {
            this.matrix[y][x].number = value;
        }
    }

    public void setCellTextColor(int x, int y, Color color) {
        if (x >= 0 && y >= 0 && x < SpaceInvadersGame.WIDTH && y < SpaceInvadersGame.HEIGHT) {
            if (color != Color.NONE) {
                this.matrix[y][x].textColor = color;
            }
        }
    }

    public void setCellTextSize(int x, int y, int size) {
        if (x >= 0 && y >= 0 && x < SpaceInvadersGame.WIDTH && y < SpaceInvadersGame.HEIGHT) {
            this.matrix[y][x].textSize = size;
        }
    }

    public void setCellValueEx(int x, int y, Color cellColor, String value) {
        if (x >= 0 && y >= 0 && x < SpaceInvadersGame.WIDTH && y < SpaceInvadersGame.HEIGHT) {
            if (cellColor != Color.NONE) {
                this.matrix[y][x].cellColor = cellColor;
                this.matrix[y][x].value = value;
            }
        }
    }

    public void setCellValueEx(int x, int y, Color cellColor, String value, Color textColor) {
        if (x >= 0 && y >= 0 && x < SpaceInvadersGame.WIDTH && y < SpaceInvadersGame.HEIGHT) {
            if (cellColor != Color.NONE) {
                this.matrix[y][x].cellColor = cellColor;
                this.matrix[y][x].value = value;
                this.matrix[y][x].textColor = textColor;
            }
        }
    }

    public void setCellValueEx(int x, int y, Color cellColor, String value, Color textColor, int textSize) {
        if (x >= 0 && y >= 0 && x < SpaceInvadersGame.WIDTH && y < SpaceInvadersGame.HEIGHT) {
            if (cellColor != Color.NONE) {
                this.matrix[y][x].cellColor = cellColor;
                this.matrix[y][x].value = value;
                this.matrix[y][x].textColor = textColor;
                this.matrix[y][x].textSize = textSize;
            }
        }
    }

    public Color getCellColor(int x, int y) {
        return matrix[y][x].cellColor;
    }

    public String getCellValue(int x, int y) {
        return matrix[y][x].value;
    }

    public int getCellNumber(int x, int y) {
        return this.matrix[y][x].number;
    }

    public Color getCellTextColor(int x, int y) {
        return this.matrix[y][x].textColor;
    }

    public int getCellTextSize(int x, int y) {
        return this.matrix[y][x].textSize;
    }


    // INNER CLASSES

    public static class Pixel {
        Color cellColor;
        Color textColor;
        String value;
        int textSize;
        int number;

        Pixel() {
            this.cellColor = Color.NONE;
            this.textColor = Color.NONE;
            this.value = "";
            this.textSize = 0;
            this.number = 0;
        }
    }
}
