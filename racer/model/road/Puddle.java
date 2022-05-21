package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.HitBox;
import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.view.Shapes;

public class Puddle extends RoadObject {
    private Arrow arrow = new Arrow(RacerGame.WIDTH - Shapes.YELLOW_ARROW[0].length - 1, 0,
            Shapes.YELLOW_ARROW);

    public Puddle(double x, double y) {
        super(RoadObjectType.PUDDLE, x, y);
        this.speed = 0;
        this.hitBox = new HitBox(0, 0, 8, 15);
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
}
