package com.javarush.games.moonlander.model.painter;

public abstract class DrawableObject implements Drawable {
    public int posX;
    public int posY;
    public int width;
    public int height;

    public DrawableObject(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }
}
