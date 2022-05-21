package com.javarush.games.racer.model;

import com.javarush.games.racer.view.Shapes;

public class TireFlame extends GameObject {
    private final Side side;

    public enum Side {
        LEFT, RIGHT
    }

    public TireFlame(Side side) {
        this.side = side;
        setAnimatedView(Sprite.Loop.ENABLED, 3,
                Shapes.TIRE_FLAME_0,
                Shapes.TIRE_FLAME_1);
    }

    @Override
    public void draw() {
        if (!game.isStopped) return;
        align();
        maskOut(3);
        super.draw();
    }

    private void align() {
        DeLorean deLorean = game.delorean;
        this.x = deLorean.x;
        this.y = deLorean.y + deLorean.getHeight() - this.getHeight() - ((side == Side.RIGHT) ? 0 : 10);
    }
}
