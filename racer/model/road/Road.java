package com.javarush.games.racer.model.road;

import com.javarush.engine.cell.Color;
import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.view.Display;

public class Road {
    private final Display display;

    Road(Display display) {
        this.display = display;
    }

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
