package com.javarush.games.minesweeper.gui.image;

public class FloatingImage extends Image {
    private float shift;     // difference between the anchor and the current position
    private boolean phase;

    public FloatingImage(ImageID imageID) {
        this(imageID, -1, -1);
    }

    public FloatingImage(ImageID imageID, int drawX, int drawY) {
        super(imageID, drawX, drawY);
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
