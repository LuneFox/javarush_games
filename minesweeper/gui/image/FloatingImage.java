package com.javarush.games.minesweeper.gui.image;

import com.javarush.games.minesweeper.view.View;

public class FloatingImage extends Image {
    private static final float STEP = 0.2f;
    private float shift;
    private boolean directionDown;

    public FloatingImage(ImageType imageType, View view) {
        super(imageType, view);
    }

    public void draw(double maxDistance, int x, int y) {
        this.setPosition(x, y);
        shiftVertically(maxDistance, y);
        super.draw();
    }

    private void shiftVertically(double maxDistance, int y) {
        shift += (directionDown ? STEP : -STEP);
        if (Math.abs(shift) > maxDistance) {
            reverseDirection();
        }
        this.y = (int) (y + shift);
    }

    private void reverseDirection() {
        directionDown = !directionDown;
    }
}
