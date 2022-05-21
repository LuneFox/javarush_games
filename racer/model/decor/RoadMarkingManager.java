package com.javarush.games.racer.model.decor;

import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.model.road.RoadManager;

import java.util.ArrayList;
import java.util.List;

public class RoadMarkingManager {
    private final List<RoadMarking> markings = new ArrayList<>();

    public RoadMarkingManager() {
        createMarkings();
    }

    private void createMarkings() {
        final double len = RoadMarking.LENGTH;

        for (double i = RacerGame.WIDTH + len; i >= -len; i -= (4 * len)) {

            final double screenHalf = RacerGame.HEIGHT / 2.0;
            final double laneHalf = RoadManager.ROAD_WIDTH / 4.0;

            RoadMarking upperMarking = new RoadMarking(i, screenHalf - laneHalf);
            markings.add(upperMarking);

            RoadMarking lowerMarking = new RoadMarking(i + len, screenHalf + laneHalf);
            markings.add(lowerMarking);
        }
    }

    public void move(double boost) {
        for (RoadMarking marking : markings) {
            if (marking.isOutsideScreen()) {
                transferBehindRightBorder(marking);
            } else {
                marking.x -= boost;
            }
        }
    }

    private void transferBehindRightBorder(RoadMarking marking) {
        double rightBorder = getRightBorder();
        marking.x = Math.ceil(rightBorder + RoadMarking.LENGTH * 2);
    }

    private double getRightBorder() {
        return markings.stream()
                .mapToDouble(marking -> marking.x)
                .max()
                .orElse(0);
    }

    public void drawMarkings() {
        markings.forEach(RoadMarking::draw);
    }
}