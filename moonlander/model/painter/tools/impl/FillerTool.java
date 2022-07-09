package com.javarush.games.moonlander.model.painter.tools.impl;

import com.javarush.games.moonlander.model.painter.Canvas;
import com.javarush.games.moonlander.model.painter.Click;
import com.javarush.games.moonlander.model.painter.Painter;
import com.javarush.games.moonlander.model.painter.tools.PaintTool;

public class FillerTool implements PaintTool {
    private int usedColor;
    private Canvas canvas;
    private int[][] matrix;

    @Override
    public void use(Painter painter, int x, int y, Click click) {
        canvas = painter.canvas;
        matrix = canvas.getColorMatrix();
        usedColor = click == Click.LEFT ? painter.primaryColor : painter.secondaryColor;
        x -= canvas.posX;
        y -= canvas.posY;
        fill(x, y);
    }

    private void fill(int x, int y) {
        if (x >= canvas.editAreaX || y >= canvas.editAreaY) return;

        int baseColor = matrix[y][x];
        if (baseColor == usedColor) return;
        matrix[y][x] = usedColor;

        if (x > 0 && matrix[y][x - 1] == baseColor) {
            fill(x - 1, y);
        }
        if (x < matrix[0].length - 1 && matrix[y][x + 1] == baseColor) {
            fill(x + 1, y);
        }
        if (y > 0 && matrix[y - 1][x] == baseColor) {
            fill(x, y - 1);
        }
        if (y < matrix.length - 1 && matrix[y + 1][x] == baseColor) {
            fill(x, y + 1);
        }
    }

    @Override
    public String getDescription() {
        return "Инструмент: ЗАЛИВКА" +
                "\n\nЗаливает ограниченную область цветом.";
    }

    @Override
    public String getIcon() {
        return "\uD83E\uDD43";
    }
}
