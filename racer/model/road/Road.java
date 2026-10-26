package com.javarush.games.racer.model.road;

import com.javarush.engine.cell.Color;
import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.view.Display;

/**
 * Paints the static road background into the display buffer: grass verges above and below the drivable
 * strip, a centre line and the asphalt itself. The whole frame is repainted from scratch every turn.
 */
public class Road {
    private final Display display;

    /**
     * Stores the display buffer that this road paints into.
     *
     * @param display frame buffer shared by all game objects
     */
    Road(Display display) {
        this.display = display;
    }

    /**
     * Repaints every cell of the 100x100 field: Sienna outside the drivable strip, Snow on the centre
     * row ({@code y == 50}) and DarkGray for the rest of the road. Pixels are buffered only; the game
     * flushes the buffer once per frame.
     */
    void draw() {
        for (int x = 0; x < RacerGame.WIDTH; x++) {
            for (int y = 0; y < RacerGame.HEIGHT; y++) {
                Color color;

                if (y < RoadManager.UPPER_BORDER || y >= RoadManager.LOWER_BORDER) {
                    color = Color.SIENNA;
                } else if (y == RacerGame.HEIGHT / 2) {
                    color = Color.SNOW;
                } else {
                    color = Color.DARKGRAY;
                }

                display.drawPixel(x, y, color);
            }
        }
    }
}
