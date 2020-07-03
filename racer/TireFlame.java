package com.javarush.games.racer;

import com.javarush.engine.cell.Color;

public class TireFlame extends GameObject {

    private int drawWidth;

    public TireFlame() {
        super(0, 0, ShapeMatrix.TIRE_FLAME_0);
        setAnimation(ShapeMatrix.TIRE_FLAME_0, ShapeMatrix.TIRE_FLAME_1);
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
                game.display.setCellColor((int) x + i, (int) y + j, Color.values()[colorIndex]);
                game.display.setCellColor((int) x + i, (int) y + j - 10, Color.values()[colorIndex]);
            }
        }
    }
}
