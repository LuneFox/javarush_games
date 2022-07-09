package com.javarush.games.moonlander.model.painter.tools.impl;

import com.javarush.games.moonlander.model.painter.Canvas;
import com.javarush.games.moonlander.model.painter.Click;
import com.javarush.games.moonlander.model.painter.Painter;
import com.javarush.games.moonlander.model.painter.tools.PaintTool;

public class ReplacerTool implements PaintTool {

    @Override
    public void use(Painter painter, int x, int y, Click click) {
        Canvas canvas = painter.canvas;

        x -= canvas.posX;
        y -= canvas.posY;

        int[][] matrix = canvas.getColorMatrix();
        int colorToReplace = matrix[y][x];
        int targetColor = (click == Click.LEFT)
                ? painter.primaryColor
                : painter.secondaryColor;

        for (int j = 0; j < matrix.length; j++) {
            for (int i = 0; i < matrix[0].length; i++) {
                if (i >= canvas.editAreaX || j >= canvas.editAreaY) continue;
                if (matrix[j][i] != colorToReplace) continue;
                matrix[j][i] = targetColor;
            }
        }
    }

    @Override
    public String getDescription() {
        return "Инструмент: ЗАМЕНА ЦВЕТА" +
                "\n\nЗаменяет все пиксели" +
                "\nуказанного цвета на выбранный.";
    }

    @Override
    public String getIcon() {
        return "\uD83C\uDFA8";
    }
}
