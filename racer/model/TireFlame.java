package com.javarush.games.racer.model;

import com.javarush.engine.cell.Color;
import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.view.Shapes;

public class TireFlame extends GameObject {

    private int drawWidth;

    public TireFlame() {
        super(0, 0, Shapes.TIRE_FLAME_0);
        setAnimation(Shapes.TIRE_FLAME_0, Shapes.TIRE_FLAME_1);
        drawWidth = 0;
    }

    public void align(DeLorean deLorean) {
        this.x = deLorean.x;
        this.y = deLorean.y + deLorean.height - this.height;
    }

    public void animate(RacerGame game, DeLorean delorean) {
        if (game.isStopped) {
            align(delorean);
            super.animate(game, 3);
        }
    }

    @Override
    public void draw(RacerGame game) {
        drawWidth += 3;
        if (drawWidth > matrix[0].length) {
            drawWidth = matrix[0].length;
        }
        for (int i = 0; i < drawWidth; i++) {
            for (int j = 0; j < height; j++) {
                int colorIndex = matrix[j][i];
                game.display.drawPixel((int) x + i, (int) y + j, Color.values()[colorIndex]);
                game.display.drawPixel((int) x + i, (int) y + j - 10, Color.values()[colorIndex]);
            }
        }
    }
}
