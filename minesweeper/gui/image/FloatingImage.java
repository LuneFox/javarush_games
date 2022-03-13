package com.javarush.games.minesweeper.gui.image;

import com.javarush.games.minesweeper.view.View;

public class FloatingImage extends Image {
    private float shift;     // difference between the anchor and the current position
    private boolean phase;

    public FloatingImage(ImageType imageType) {
        this(imageType, CENTER, CENTER);
    }

    public FloatingImage(ImageType imageType, View view) {
        super(imageType, view);
    }

    public FloatingImage(ImageType imageType, int drawX, int drawY) {
        super(imageType, drawX, drawY);
        shift = 0;
        phase = true;
    }

    public void draw(double height, int x, int y) {
        this.setPosition(x, y);
        shift += (phase ? 0.2 : -0.2);
        if (Math.abs(shift) > height)
            phase = !phase;
        this.y = (int) (y + shift);
        super.draw();
    }
}
