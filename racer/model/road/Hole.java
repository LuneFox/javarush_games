package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.HitBox;
import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.view.Shapes;

public class Hole extends RoadObject {
    private Arrow arrow = new Arrow(RacerGame.WIDTH - Shapes.RED_ARROW[0].length - 1, 0,
            Shapes.RED_ARROW);

    public Hole(double x, double y) {
        super(RoadObjectType.HOLE, x, y);
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