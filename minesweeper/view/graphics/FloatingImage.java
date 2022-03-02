package com.javarush.games.minesweeper.view.graphics;

public class FloatingImage extends Image {
    private float shift;     // difference between the anchor and the current position
    private boolean phase;

    public FloatingImage(VisualElement visualElement) {
        this(visualElement, -1, -1);
    }

    public FloatingImage(VisualElement visualElement, int drawX, int drawY) {
        super(visualElement, drawX, drawY);
        shift = 0;
        phase = true;
    }

    public void draw(double height, int x, int y) {
        this.setPosition(x, y);
        shift += (phase ? 0.2 : -0.2);
        if (Math.abs(shift) > height)
            phase = !phase;
        this.drawY = (int) (y + shift);
        super.draw();
    }
}
