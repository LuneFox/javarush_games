package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.view.Shapes;

/**
 * A single lane dash painted on the road. The shape is one pixel high, so {@link #LENGTH} is both its
 * pixel width and the unit used to space the dashed lines.
 */
public class RoadMarking extends GameObject {
    static final double LENGTH = Shapes.ROAD_MARKING[0].length;

    /**
     * Creates a dash at the given position and installs the one-row dash shape.
     *
     * @param x left edge in screen pixels
     * @param y top edge in screen pixels
     */
    RoadMarking(double x, double y) {
        super(x, y);
        setStaticView(Shapes.ROAD_MARKING);
    }

    /**
     * Tells whether the dash has moved completely past the left edge, using its own length as the
     * threshold, so it is recycled once its right edge clears x = 0.
     *
     * @return {@code true} when {@code x < -LENGTH}
     */
    boolean isOutsideScreen() {
        return x < -LENGTH;
    }
}
