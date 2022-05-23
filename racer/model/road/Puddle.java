package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.gameobjects.HitBox;
import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.view.Shapes;

public class Puddle extends RoadObject {
    private final Arrow arrow = new Arrow(RacerGame.WIDTH - Shapes.YELLOW_ARROW[0].length - 1, 0,
            Shapes.YELLOW_ARROW);

    public Puddle() {
        super(RoadObjectType.PUDDLE);
        this.speed = 0;
        this.hitBox = new HitBox(0, 0, 15, 8);
    }

    @Override
    public void draw() {
        if (this.x >= RacerGame.WIDTH) {
            arrow.y = (int) (this.y + this.getHeight() / 2 - arrow.getHeight() / 2);
            if (!game.isStopped) {
                arrow.draw();
            }
        } else {
            super.draw();
        }
    }

    @Override
    public void onContact(DeLorean deLorean) {
        deLorean.setSpeed((deLorean.getSpeed() / 100) * 95);
    }
}