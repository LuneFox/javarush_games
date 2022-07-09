package com.javarush.games.moonlander.model.painter.tools;

import com.javarush.games.moonlander.model.painter.Canvas;
import com.javarush.games.moonlander.model.painter.Click;
import com.javarush.games.moonlander.model.painter.Painter;

public class CircleTool implements PaintTool {
    private Canvas canvas;
    private int[][] colorMatrix;
    private int color;
    private int x1;
    private int y1;
    private boolean isAwaitingSecondClick;

    @Override
    public void use(Painter painter, int x, int y, Click click) {
        canvas = painter.canvas;
        colorMatrix = canvas.getColorMatrix();
        color = click == Click.LEFT ? painter.primaryColor : painter.secondaryColor;
        x -= canvas.posX;
        y -= canvas.posY;

        if (!isAwaitingSecondClick) {
            colorMatrix[y][x] = color;
            x1 = x;
            y1 = y;
            isAwaitingSecondClick = true;
        } else {
            if (x1 != x || y1 != y) {
                double r = Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2));
                circleBresenham(x1, y1, (int) Math.round(r));
            }
            isAwaitingSecondClick = false;
        }
    }

    private void drawCircle(int xc, int yc, int x, int y) {
        drawPixel(xc + x, yc + y);
        drawPixel(xc - x, yc + y);
        drawPixel(xc + x, yc - y);
        drawPixel(xc - x, yc - y);

        drawPixel(xc + y, yc + x);
        drawPixel(xc - y, yc + x);
        drawPixel(xc + y, yc - x);
        drawPixel(xc - y, yc - x);
    }

    private void circleBresenham(int xc, int yc, int r) {
        int x = 0;
        int y = r;
        int d = 3 - 2 * r;
        drawCircle(xc, yc, x, y);

        while (y >= x) {
            x++;
            if (d > 0) {
                y--;
                d = d + 4 * (x - y) + 10;
            } else
                d = d + 4 * x + 6;
            drawCircle(xc, yc, x, y);
        }
    }

    private void drawPixel(int x, int y) {
        if (x < 0 || x >= canvas.editAreaX || y < 0 || y >= canvas.editAreaY) {
            return;
        }
        colorMatrix[y][x] = color;
    }

    @Override
    public String getDescription() {
        return "Инструмент: ОКРУЖНОСТЬ\n\nРисует окружность с указанным радиусом.\n" +
                "Нужно поставить две точки. Первая - центр окружности.\n" +
                "Вторая - длина радиуса окружности по отношению к центру.\n" +
                "Если не нужна точка в центре - перед установкой второй точки надо нажать UNDO.";
    }

    @Override
    public String getIcon() {
        return "◯";
    }

    public boolean isAwaitingSecondClick() {
        return isAwaitingSecondClick;
    }

    public void setAwaitingSecondClick(boolean awaitingSecondClick) {
        isAwaitingSecondClick = awaitingSecondClick;
    }
}
