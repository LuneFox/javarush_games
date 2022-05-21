package com.javarush.games.racer.model.gameobjects;

public class HitBox {
    private final double startY;
    private final double startX;
    private final double width;
    private final double height;

    public HitBox(double startX, double startY, double endX, double endY) {
        this.startX = startX;
        this.startY = startY;
        this.width = endX - startX;
        this.height = endY - startY;
    }

    public static boolean checkFullOverlap(GameObject thisObject, GameObject otherObject) {
        return checkHorizontalOverlap(thisObject, otherObject) && checkVerticalOverlap(thisObject, otherObject);
    }

    public static boolean checkVerticalOverlap(GameObject thisObject, GameObject otherObject) {
        final double thisStart = thisObject.hitBox.startY + thisObject.y;
        final double otherStart = otherObject.hitBox.startY + otherObject.y;
        final double thisEnd = thisStart + thisObject.hitBox.height;
        final double otherEnd = otherStart + otherObject.hitBox.height;

        return thisStart <= otherEnd && otherStart <= thisEnd;
    }

    public static boolean checkHorizontalOverlap(GameObject thisObject, GameObject otherObject) {
        final double thisStart = thisObject.hitBox.startX + thisObject.x;
        final double otherStart = otherObject.hitBox.startX + otherObject.x;
        final double thisEnd = thisStart + thisObject.hitBox.width;
        final double otherEnd = otherStart + otherObject.hitBox.width;

        return thisStart <= otherEnd && otherStart <= thisEnd;
    }
}
