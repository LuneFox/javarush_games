package com.javarush.games.racer.road;

import com.javarush.games.racer.HitBox;
import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.ShapeMatrix;

public class Puddle extends RoadObject {
    private Arrow arrow = new Arrow(RacerGame.WIDTH - ShapeMatrix.YELLOW_ARROW[0].length - 1, 0,
            ShapeMatrix.YELLOW_ARROW);

    public Puddle(double x, double y) {
        super(RoadObjectType.PUDDLE, x, y);
        this.speed = 0;
        this.hitBox = new HitBox(0, 0, 8, 15);
    }

    @Override
    public void draw(RacerGame game) {
        if (this.x >= RacerGame.WIDTH) {
            arrow.y = (int) (this.y + this.matrix.length / 2 - arrow.matrix.length / 2);
            if (!game.isStopped) {
                arrow.draw(game);
            }
        } else {
            super.draw(game);
        }
    }
}
