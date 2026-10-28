package com.javarush.games.spaceinvaders.model;

/**
 * Horizontal mirroring of a sprite. Mario is drawn mirrored when he looks left.
 */
public enum Mirror {
    HORIZONTAL, NONE;

    public static Mirror of(Direction facing) {
        return (facing == Direction.RIGHT) ? NONE : HORIZONTAL;
    }
}
