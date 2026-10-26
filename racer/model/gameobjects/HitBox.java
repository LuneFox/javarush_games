package com.javarush.games.racer.model.gameobjects;

/**
 * An axis-aligned rectangular hit area stored as an offset from its owner's position plus a size.
 * The offsets are relative to the owning {@link GameObject}'s {@code x} and {@code y}, not absolute
 * cell coordinates. The static helpers compare two hit boxes and assume both objects have one.
 */
public class HitBox {
    private final double startY;
    private final double startX;
    private final double width;
    private final double height;

    /**
     * Creates a hit box from two opposite corners, both given relative to the owning object's
     * position. The width and height are simple differences, so swapping the corners yields
     * negative sizes rather than an error.
     *
     * @param startX horizontal offset of one corner from the object's x
     * @param startY vertical offset of that corner from the object's y
     * @param endX   horizontal offset of the opposite corner from the object's x
     * @param endY   vertical offset of the opposite corner from the object's y
     */
    public HitBox(double startX, double startY, double endX, double endY) {
        this.startX = startX;
        this.startY = startY;
        this.width = endX - startX;
        this.height = endY - startY;
    }

    /**
     * Tests two objects for a full axis-aligned bounding-box overlap by combining the horizontal
     * and vertical interval tests. Both objects must have a hit box, otherwise this throws
     * {@link NullPointerException}.
     *
     * @param thisObject  the first object, with a non-null hit box
     * @param otherObject the second object, with a non-null hit box
     * @return true if the two hit boxes overlap on both axes
     */
    public static boolean checkFullOverlap(GameObject thisObject, GameObject otherObject) {
        return checkHorizontalOverlap(thisObject, otherObject) && checkVerticalOverlap(thisObject, otherObject);
    }

    /**
     * Tests whether two objects' hit boxes overlap on the vertical axis. The intervals are
     * inclusive, so boxes that merely touch edges count as overlapping. Both objects must have a
     * hit box, otherwise this throws {@link NullPointerException}.
     *
     * @param thisObject  the first object, with a non-null hit box
     * @param otherObject the second object, with a non-null hit box
     * @return true if the vertical intervals intersect, including shared edges
     */
    public static boolean checkVerticalOverlap(GameObject thisObject, GameObject otherObject) {
        final double thisStart = thisObject.hitBox.startY + thisObject.y;
        final double otherStart = otherObject.hitBox.startY + otherObject.y;
        final double thisEnd = thisStart + thisObject.hitBox.height;
        final double otherEnd = otherStart + otherObject.hitBox.height;

        return thisStart <= otherEnd && otherStart <= thisEnd;
    }

    /**
     * Tests whether two objects' hit boxes overlap on the horizontal axis. The intervals are
     * inclusive, so boxes that merely touch edges count as overlapping. Both objects must have a
     * hit box, otherwise this throws {@link NullPointerException}.
     *
     * @param thisObject  the first object, with a non-null hit box
     * @param otherObject the second object, with a non-null hit box
     * @return true if the horizontal intervals intersect, including shared edges
     */
    public static boolean checkHorizontalOverlap(GameObject thisObject, GameObject otherObject) {
        final double thisStart = thisObject.hitBox.startX + thisObject.x;
        final double otherStart = otherObject.hitBox.startX + otherObject.x;
        final double thisEnd = thisStart + thisObject.hitBox.width;
        final double otherEnd = otherStart + otherObject.hitBox.width;

        return thisStart <= otherEnd && otherStart <= thisEnd;
    }
}
