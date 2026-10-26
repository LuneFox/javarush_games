package com.javarush.games.racer.model.road;

import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.model.GameObjectManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds and updates the dashed lane markings. It owns two rows of dashes (upper at y = 30, lower at
 * y = 70, the lower row shifted right by one dash length) with a pitch of four dash lengths, and
 * recycles dashes that scroll off the left edge back to the right side so the pattern never runs out.
 */
public class RoadMarkingManager implements GameObjectManager {
    private final List<RoadMarking> markings = new ArrayList<>();

    /**
     * Creates the manager and immediately seeds both rows of dashes.
     */
    public RoadMarkingManager() {
        createMarkings();
    }

    /**
     * Seeds the two dash rows from x = {@code WIDTH + LENGTH} down to {@code -LENGTH} in steps of
     * {@code 4 * LENGTH}. The upper row sits a quarter of the road width above the centre line and the
     * lower row the same distance below it, offset one dash length to the right.
     */
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

    /**
     * Draws every dash into the display buffer in list order.
     */
    public void drawObjects() {
        markings.forEach(RoadMarking::draw);
    }

    /**
     * Moves every dash left by {@code boost} and recycles the ones that have already left the screen.
     * The off-screen test runs before the move, so a dash can overshoot the left edge by up to one
     * frame of movement. A negative boost (the car driving backwards) would push dashes to the right
     * with nothing bringing them back.
     *
     * @param boost world pixels per frame, normally the car's current speed
     */
    public void moveObjects(double boost) {
        for (RoadMarking marking : markings) {
            if (marking.isOutsideScreen()) {
                transferBehindRightBorder(marking);
            } else {
                marking.x -= boost;
            }
        }
    }

    /**
     * Recycles a dash by placing it past the current rightmost dash: the largest x among all dashes plus
     * two dash lengths, rounded up to a whole pixel. It is off-screen again immediately, but the exact
     * four-length pitch used when seeding is not restored.
     *
     * @param marking dash that has left the left edge
     */
    private void transferBehindRightBorder(RoadMarking marking) {
        double rightBorder = getRightBorder();
        marking.x = Math.ceil(rightBorder + RoadMarking.LENGTH * 2);
    }

    /**
     * Finds the largest x among all dashes, used as the anchor for recycling.
     *
     * @return the rightmost dash position, or 0 when there are no dashes
     */
    private double getRightBorder() {
        return markings.stream()
                .mapToDouble(marking -> marking.x)
                .max()
                .orElse(0);
    }
}