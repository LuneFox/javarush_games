package com.javarush.games.moonlander.model.painter.tools;

import com.javarush.engine.cell.Color;
import com.javarush.games.moonlander.model.painter.Canvas;
import com.javarush.games.moonlander.model.painter.Click;
import com.javarush.games.moonlander.model.painter.Painter;

public class EraserTool implements PaintTool {
    @Override
    public void use(Painter painter, int x, int y, Click click) {
        Canvas canvas = painter.canvas;
        int[][] colorMatrix = canvas.getColorMatrix();
        x -= canvas.posX;
        y -= canvas.posY;
        colorMatrix[y][x] = Color.NONE.ordinal();
    }

    @Override
    public String getDescription() {
        return "Инструмент: ЛАСТИК\n\nДелает указанный пиксель прозрачным.\n" +
                "Быстрый выбор - клавиша ESC.";
    }

    @Override
    public String getIcon() {
        return "\uD83E\uDDFC";
    }
}
