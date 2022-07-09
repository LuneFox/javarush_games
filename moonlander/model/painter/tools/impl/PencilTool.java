package com.javarush.games.moonlander.model.painter.tools.impl;

import com.javarush.games.moonlander.model.painter.Canvas;
import com.javarush.games.moonlander.model.painter.Click;
import com.javarush.games.moonlander.model.painter.Painter;
import com.javarush.games.moonlander.model.painter.tools.PaintTool;

public class PencilTool implements PaintTool {
    @Override
    public void use(Painter painter, int x, int y, Click click) {
        Canvas canvas = painter.canvas;
        int[][] colorMatrix = canvas.getColorMatrix();
        int color = click == Click.LEFT ? painter.primaryColor : painter.secondaryColor;
        x -= canvas.posX;
        y -= canvas.posY;
        colorMatrix[y][x] = color;
    }

    @Override
    public String getDescription() {
        return "Инструмент: КАРАНДАШ" +
                "\n\nПерекрашивает пиксель выбранным цветом." +
                "\nБыстрый выбор - клавиша ENTER.";
    }

    @Override
    public String getIcon() {
        return "\uD83D\uDD8D️";
    }
}
