package com.javarush.games.racer.model.gameobjects;

public class HitBox {
    public int startY;
    public int startX;
    public int endY;
    public int endX;
    public int width;
    public int height;

    public HitBox(int startY, int startX, int endY, int endX) {
        this.startY = startY;
        this.startX = startX;
        this.endY = endY;
        this.endX = endX;
        this.width = endX - startX;
        this.height = endY - startY;
    }

    public static boolean isCollision(GameObject thisObject, GameObject otherObject) {
        // координаты начал хитбоксов
        int thisX = thisObject.hitBox.startX + (int) thisObject.x;
        int thisY = thisObject.hitBox.startY + (int) thisObject.y;
        int otherX = otherObject.hitBox.startX + (int) otherObject.x;
        int otherY = otherObject.hitBox.startY + (int) otherObject.y;

        // ширина и высота хитбоксов
        int thisWidth = thisObject.hitBox.width;
        int thisHeight = thisObject.hitBox.height;
        int otherWidth = otherObject.hitBox.width;
        int otherHeight = otherObject.hitBox.height;

        // если хитбоксы не имеют общую высоту, то они не пересекаются
        if (thisY > otherY + otherHeight || thisY + thisHeight < otherY) {
            return false;
        }

        // если хитбоксы имеют общую высоту, но не имеют общую ширину, то они не пересекаются
        if (thisX > otherX + otherWidth || thisX + thisWidth < otherX) {
            return false;
        }

        // хитбоксы имеют общие зоны
        return true;
    }

    public static boolean isCollisionY(GameObject thisObject, GameObject otherObject) {
        int thisY = thisObject.hitBox.startY + (int) thisObject.y;
        int otherY = otherObject.hitBox.startY + (int) otherObject.y;
        int thisHeight = thisObject.hitBox.height;
        int otherHeight = otherObject.hitBox.height;

        return thisY <= otherY + otherHeight && thisY + thisHeight >= otherY;
    }
}
