package com.javarush.games.racer.model.car;

import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.view.Shapes;

public class StopSignal extends GameObject {
    private final DeLorean deLorean;

    public StopSignal(DeLorean deLorean) {
        this.deLorean = deLorean;
        setStaticView(Shapes.STOP_SIGNAL);
    }

    void alignToDelorean() {
        setPosition(deLorean.x + 3, deLorean.y + deLorean.getHeight() - this.getHeight() - 5);
    }

    @Override
    public void draw() {
        alignToDelorean();
        super.draw();
    }
}
