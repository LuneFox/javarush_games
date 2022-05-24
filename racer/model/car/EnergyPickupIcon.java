package com.javarush.games.racer.model.car;

import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.view.Shapes;

public class EnergyPickupIcon extends GameObject {
    int time;

    public EnergyPickupIcon(double x, double y) {
        super(x, y);
        setStaticView(Shapes.ENERGY_ICON);
    }

    @Override
    public void draw() {
        if (time > 10) return;

        super.draw(x, y - time);
        time++;
    }
}
