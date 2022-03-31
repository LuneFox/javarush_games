package com.javarush.games.moonlander.model.painter.tools;

import com.javarush.games.moonlander.model.painter.Canvas;
import com.javarush.games.moonlander.model.painter.Click;
import com.javarush.games.moonlander.model.painter.Painter;

class ReplacerTool implements PaintTool {
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
        int baseColor = matrix[y][x];
        for (int j = 0; j < matrix.length; j++) {
            for (int i = 0; i < matrix[0].length; i++) {
                if (i >= canvas.editAreaX || j >= canvas.editAreaY) continue;
                if (matrix[j][i] == baseColor){
                    matrix[j][i] = usedColor;
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "Инструмент: ЗАМЕНА ЦВЕТА\n\nЗаменяет все пиксели\nуказанного цвета на выбранный.";
    }
}
