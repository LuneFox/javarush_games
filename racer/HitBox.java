package com.javarush.games.racer;

public class HitBox {
    public int startY;
    public int startX;
    public int endY;
    public int endX;

    public HitBox(int sY, int sX, int eY, int eX) {
        this.startY = sY;
        this.startX = sX;
        this.endY = eY;
        this.endX = eX;
    }

    public boolean isCollision(GameObject object) {


        return false;
    }
}
