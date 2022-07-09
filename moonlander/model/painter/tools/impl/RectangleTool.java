package com.javarush.games.moonlander.model.painter.tools.impl;

import com.javarush.games.moonlander.model.painter.Canvas;
import com.javarush.games.moonlander.model.painter.Click;
import com.javarush.games.moonlander.model.painter.Painter;
import com.javarush.games.moonlander.model.painter.tools.PaintTool;

public class RectangleTool implements PaintTool {
    private int x1;
    private int y1;
    private boolean isAwaitingSecondClick;

    @Override
    public void use(Painter painter, int x, int y, Click click) {
        Canvas canvas = painter.canvas;
        int[][] colorMatrix = canvas.getColorMatrix();
        int color = click == Click.LEFT ? painter.primaryColor : painter.secondaryColor;
        x -= canvas.posX;
        y -= canvas.posY;

        if (!isAwaitingSecondClick) {
            colorMatrix[y][x] = color;
            x1 = x;
            y1 = y;
            isAwaitingSecondClick = true;
        } else {
            if (x > x1) {
                if (y > y1) {
                    drawRectangle(colorMatrix, color, x1, y1, x, y);
                } else {
                    drawRectangle(colorMatrix, color, x1, y, x, y1);
                }
            } else {
                if (y > y1) {
                    drawRectangle(colorMatrix, color, x, y1, x1, y);

                } else {
                    drawRectangle(colorMatrix, color, x, y, x1, y1);
                }
            }
            isAwaitingSecondClick = false;
        }
    }

    private void drawRectangle(int[][] colorMatrix, int color, int x1, int y1, int x2, int y2) {
        for (int i = x1; i <= x2; i++) {
            colorMatrix[y1][i] = color;
        }
        for (int i = x1; i <= x2; i++) {
            colorMatrix[y2][i] = color;
        }
        for (int i = y1; i <= y2; i++) {
            colorMatrix[i][x1] = color;
        }
        for (int i = y1; i <= y2; i++) {
            colorMatrix[i][x2] = color;
        }
    }

    @Override
    public String getDescription() {
        return "Инструмент: ПРЯМОУГОЛЬНИК" +
                "\n\nРисует прямоугольник между двумя точками." +
                "\nНужно поставить две точки в противоположных углах.";
    }

    @Override
    public String getIcon() {
        return "□";
    }

    public boolean isAwaitingSecondClick() {
        return isAwaitingSecondClick;
    }

    public void setAwaitingSecondClick(boolean awaitingSecondClick) {
        isAwaitingSecondClick = awaitingSecondClick;
    }
}
