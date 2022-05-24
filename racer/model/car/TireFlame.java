package com.javarush.games.racer.model.car;

import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.model.gameobjects.Sprite;
import com.javarush.games.racer.view.Shapes;

public class TireFlame extends GameObject {
    private final Side side;

    enum Side {
        LEFT, RIGHT
    }

    TireFlame(Side side) {
        this.side = side;
        setAnimatedView(Sprite.Loop.ENABLED, 3,
                Shapes.TIRE_FLAME_0,
                Shapes.TIRE_FLAME_1);
    }

    @Override
    public void draw() {
        if (!game.isStopped) return;

        alignToDeLorean();
        maskOut(3);
        super.draw();
    }

    private void alignToDeLorean() {
        DeLorean deLorean = game.delorean;
        int shift = (side == Side.RIGHT) ? 0 : 10;
        x = deLorean.x;
        y = deLorean.y + deLorean.getHeight() - this.getHeight() - shift;
    }
}
