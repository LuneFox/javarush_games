package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.GameObject;
import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.view.Shapes;

import java.util.ArrayList;
import java.util.List;

public class RoadMarking {

    private List<GameObject> roadMarking = new ArrayList<>();

    public RoadMarking() {
        for (int i = RacerGame.WIDTH + 8; i >= -8; i -= 16) {
            GameObject marking1 = new GameObject(i, (RacerGame.HEIGHT / 2.0) - (RoadManager.ROAD_WIDTH / 4.0));
            marking1.setStaticView(Shapes.ROAD_MARKING);
            roadMarking.add(marking1);

            GameObject marking2 = new GameObject(i + 8, (RacerGame.HEIGHT / 2.0) + (RoadManager.ROAD_WIDTH / 4.0));
            marking2.setStaticView(Shapes.ROAD_MARKING);
            roadMarking.add(marking2);
        }
    }

    public void move(double boost) {
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
                item.x -= boost;
            }
        }
    }

    public void draw() {
        for (GameObject item : roadMarking) {
            item.draw();
        }
    }
}