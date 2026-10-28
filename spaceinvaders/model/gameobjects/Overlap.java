package com.javarush.games.spaceinvaders.model.gameobjects;

/**
 * Shape of the area where two sprites overlap. It tells in which direction a bouncing object
 * should reflect: a wide overlap means a top/bottom contact, a tall one means a side contact.
 */
public enum Overlap {
    /**
     * The overlap is wider than tall: the objects meet with their top or bottom sides.
     */
    HORIZONTAL,

    /**
     * The overlap is taller than wide: the objects meet with their left or right sides.
     */
    VERTICAL,

    /**
     * The overlap is a square: the objects meet with a corner.
     */
    SQUARE,

    /**
     * The sprites do not touch.
     */
    NONE
}
