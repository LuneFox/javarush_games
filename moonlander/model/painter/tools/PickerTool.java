package com.javarush.games.moonlander.model.painter.tools;

import com.javarush.engine.cell.Color;
import com.javarush.games.moonlander.model.painter.Canvas;
import com.javarush.games.moonlander.model.painter.Click;
import com.javarush.games.moonlander.model.painter.Painter;

class PickerTool implements PaintTool {
    @Override
    public void use(Painter painter, int x, int y, Click click) {
        Canvas canvas = painter.canvas;
        int[][] colorMatrix = canvas.getColorMatrix();
        x -= canvas.posX;
        y -= canvas.posY;

        painter.selectedTool = PaintToolManager.getPreviousTool();
        if (colorMatrix[y][x] == Color.NONE.ordinal()) return;

        if (click == Click.LEFT) {
            painter.primaryColor = colorMatrix[y][x];
        } else {
            painter.secondaryColor = colorMatrix[y][x];
        }

    }

    @Override
    public String getDescription() {
        return "Инструмент: ПИПЕТКА\n\nКопирует цвет пикселя и\nпереключается на предыдущий инструмент.\n" +
                "Быстрый выбор - клавиша SPACE.";
    }

    @Override
    public String getIcon() {
        return "\uD83E\uDDEA";
    }
}
