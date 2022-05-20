package com.javarush.games.racer.model;

import com.javarush.engine.cell.Color;
import com.javarush.games.racer.view.Shapes;

public class TireFlame extends GameObject {
    private int drawWidth;

    public TireFlame() {
        super(0, 0);
        setAnimatedView(Sprite.Loop.ENABLED, 3,
                Shapes.TIRE_FLAME_0,
                Shapes.TIRE_FLAME_1);
    }

    @Override
    public void draw() {
        // TODO: Make animation work again
        if (!game.isStopped) return;

        align(game.delorean);
        drawWidth += 3;
        if (drawWidth > getMatrix()[0].length) {
            drawWidth = getMatrix()[0].length;
        }
        for (int i = 0; i < drawWidth; i++) {
            for (int j = 0; j < getHeight(); j++) {
                int colorIndex = getMatrix()[j][i];
                game.display.drawPixel((int) x + i, (int) y + j, Color.values()[colorIndex]);
                game.display.drawPixel((int) x + i, (int) y + j - 10, Color.values()[colorIndex]);
            }
        }
    }

    public void align(DeLorean deLorean) {
        this.x = deLorean.x;
        this.y = deLorean.y + deLorean.getHeight() - this.getHeight();
    }
}
