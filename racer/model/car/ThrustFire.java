package com.javarush.games.racer.model.car;

import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.model.gameobjects.Sprite;
import com.javarush.games.racer.view.Shapes;

public class ThrustFire extends GameObject {
    private final DeLorean deLorean;

    ThrustFire(DeLorean deLorean) {
        this.deLorean = deLorean;
        setAnimatedView(Sprite.Loop.ENABLED, 1,
                Shapes.THRUST_FIRE_1,
                Shapes.THRUST_FIRE_2,
                Shapes.THRUST_FIRE_3,
                Shapes.THRUST_FIRE_4
        );
    }

    void alignToDelorean() {
        setPosition(deLorean.x, deLorean.y + deLorean.getHeight() - this.getHeight() - 4);
    }

    @Override
    public void draw() {
        alignToDelorean();
        super.draw();
    }
}
