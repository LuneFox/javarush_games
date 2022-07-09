package com.javarush.games.moonlander.model.painter.tools;

import com.javarush.games.moonlander.model.painter.Canvas;
import com.javarush.games.moonlander.model.painter.Click;
import com.javarush.games.moonlander.model.painter.Painter;

public class LineTool implements PaintTool {
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
            drawLine(colorMatrix, color, x1, y1, x, y);
            colorMatrix[y][x] = color;
            isAwaitingSecondClick = false;
        }
    }

    private void drawLineLow(int[][] colorMatrix, int color, int x1, int y1, int x2, int y2) {
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;
        int error = 0;
        int yi = 1;
        if (deltaY < 0) {
            yi = -1;
            deltaY = -deltaY;
        }

        int y = y1;
        for (int x = x1; x < x2; x++) {
            colorMatrix[y][x] = color;
            error = error + deltaY;
            if (2 * error >= deltaX) {
                y = y + yi;
                error = error - deltaX;
            }
        }
    }

    private void drawLineHigh(int[][] colorMatrix, int color, int x1, int y1, int x2, int y2) {
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;
        int error = 0;
        int xi = 1;

        if (deltaX < 0) {
            xi = -1;
            deltaX = -deltaX;
        }

        int x = x1;
        for (int y = y1; y < y2; y++) {
            colorMatrix[y][x] = color;
            error = error + deltaX;
            if (2 * error >= deltaY) {
                x = x + xi;
                error = error - deltaY;
            }
        }
    }

    private void drawLine(int[][] colorMatrix, int color, int x1, int y1, int x2, int y2) {
        if (Math.abs(y2 - y1) < Math.abs(x2 - x1)) {
            if (x1 > x2) {
                drawLineLow(colorMatrix, color, x2, y2, x1, y1);
            } else {
                drawLineLow(colorMatrix, color, x1, y1, x2, y2);
            }
        } else {
            if (y1 > y2) {
                drawLineHigh(colorMatrix, color, x2, y2, x1, y1);
            } else {
                drawLineHigh(colorMatrix, color, x1, y1, x2, y2);
            }
        }
    }


    @Override
    public String getDescription() {
        return "Инструмент: ЛИНИЯ\n\nРисует прямую линию между двумя точками.\n" +
                "Сначала нужно поставить первую точку, затем вторую.";
    }

    @Override
    public String getIcon() {
        return "/";
    }

    public boolean isAwaitingSecondClick() {
        return isAwaitingSecondClick;
    }

    public void setAwaitingSecondClick(boolean awaitingSecondClick) {
        isAwaitingSecondClick = awaitingSecondClick;
    }
}
