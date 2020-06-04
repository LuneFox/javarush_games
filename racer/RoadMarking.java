package com.javarush.games.racer;

import com.javarush.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class RoadMarking {

    private List<GameObject> roadMarking = new ArrayList<>();

    public RoadMarking() {
        for (int i = RacerGame.WIDTH + 8; i >= -8; i -= 16) {
            roadMarking.add(new GameObject(i, (RacerGame.HEIGHT / 2) - (RoadManager.ROAD_WIDTH / 4), ShapeMatrix.ROAD_MARKING));
            roadMarking.add(new GameObject(i + 8, (RacerGame.HEIGHT / 2) + (RoadManager.ROAD_WIDTH / 4), ShapeMatrix.ROAD_MARKING));
        }
    }

    public void move(double step) {
        for (GameObject item : roadMarking) {
            if (item.x <= -8) {
                double mostRightX = 0;
                for (GameObject dash : roadMarking) {
                    if (dash.x > mostRightX) {
                        mostRightX = dash.x;
                    }
                }
                item.x = Math.ceil(mostRightX + 8);
            } else {
                item.x -= step;
            }
        }
    }

    public void draw(RacerGame game) {
        for (GameObject item : roadMarking) {
            item.draw(game);
        }
    }
}