package com.javarush.games.racer.model.gameobjects;

import com.javarush.games.racer.RacerGame;

/**
 * Base class for every object drawn on the 100x100 cell field.
 * Holds the top-left cell position, an optional sprite and an optional hit box.
 * The sprite stays null until a view is assigned, and the hit box stays null unless a subclass
 * sets one. The shared engine reference must be installed once at startup with
 * {@link #setGame(RacerGame)}.
 */
public class GameObject {
    protected static RacerGame game;
    private Sprite sprite;
    public double x;
    public double y;
    protected HitBox hitBox;

    /**
     * Stores the engine reference shared by all game objects, used to reach the display.
     *
     * @param game the running game instance
     */
    public static void setGame(RacerGame game) {
        GameObject.game = game;
    }

    /**
     * Creates an object at the top-left corner of the field. The sprite is still null, so it
     * cannot be drawn or measured until a view is assigned.
     */
    public GameObject() {
        this(0, 0);
    }

    /**
     * Creates an object at the given cell position. The sprite is still null, so it cannot be
     * drawn or measured until a view is assigned.
     *
     * @param x horizontal cell coordinate of the sprite's top-left corner
     * @param y vertical cell coordinate of the sprite's top-left corner (grows downward)
     */
    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates and assigns a one-frame sprite built from the given pixel matrix.
     * Matrix values index {@code Color.values()}, and 0 is transparent. Requires
     * {@link #setGame(RacerGame)} to have been called, since a sprite needs the display.
     *
     * @param frame pixel matrix whose first row is the top row of the sprite
     */
    public void setStaticView(int[][] frame) {
        sprite = new Sprite(game.getDisplay());
        sprite.setStaticView(frame);
    }

    /**
     * Creates and assigns an animated sprite. The sprite's matrix is seeded with the final frame
     * while playback starts at frame 0, so all frames should share the same pixel dimensions.
     *
     * @param loop           whether playback restarts from the first frame or holds the last one
     * @param nextFrameDelay number of draw calls each frame is shown for
     * @param frames         animation frames, each a pixel matrix indexed into {@code Color.values()}
     */
    public void setAnimatedView(Sprite.Loop loop, int nextFrameDelay, int[][]... frames) {
        sprite = new Sprite(game.getDisplay());
        sprite.setAnimatedView(loop, nextFrameDelay, frames);
    }

    /**
     * Draws the sprite at the object's own position without mirroring.
     * Does nothing internally until the frame is flushed by {@code Display.draw()}; throws
     * {@link NullPointerException} if no view has been assigned yet.
     */
    public void draw() {
        draw(x, y, Mirror.NONE);
    }

    /**
     * Draws the sprite at an explicit cell position, leaving {@code x} and {@code y} unchanged.
     * Useful when the sprite must appear somewhere other than the object's own position.
     *
     * @param x horizontal cell coordinate of the sprite's top-left corner
     * @param y vertical cell coordinate of the sprite's top-left corner
     */
    public void draw(double x, double y) {
        draw(x, y, Mirror.NONE);
    }

    /**
     * Draws the sprite at the object's own position, optionally flipped left-to-right.
     *
     * @param mirror {@code Mirror.HORIZONTAL} to flip the sprite, {@code Mirror.NONE} to draw as-is
     */
    public void draw(Mirror mirror) {
        draw(x, y, mirror);
    }

    /**
     * Draws the sprite at an explicit cell position, optionally flipped left-to-right. The other
     * overloads delegate here, and this one forwards to the sprite.
     *
     * @param x      horizontal cell coordinate of the sprite's top-left corner
     * @param y      vertical cell coordinate of the sprite's top-left corner
     * @param mirror {@code Mirror.HORIZONTAL} to flip the sprite, {@code Mirror.NONE} to draw as-is
     */
    public void draw(double x, double y, Mirror mirror) {
        sprite.draw(x, y, mirror);
    }

    /**
     * Starts erasing the sprite from its right edge toward the left: the masked width grows by
     * {@code step} per draw call. Despite the name, the sprite is hidden, not revealed. The mask is
     * one-shot: calls after the mask has started are ignored, even once the animation has finished.
     * Throws {@link NullPointerException} without a view.
     *
     * @param step number of pixel columns covered per draw call
     */
    public void maskIn(int step) {
        sprite.maskIn(step);
    }

    /**
     * Starts revealing the sprite from its left edge by shrinking the masked width by {@code step}
     * per draw call. Like {@link #maskIn(int)}, this is one-shot and ignored once the mask has
     * started.
     *
     * @param step number of pixel columns uncovered per draw call
     */
    public void maskOut(int step) {
        sprite.maskOut(step);
    }

    /**
     * Starts blinking: the sprite is skipped on every other draw call until flicker is stopped.
     */
    public void startFlicker() {
        sprite.startFlicker();
    }

    /**
     * Stops blinking and returns the sprite to being drawn on every call.
     */
    public void stopFlicker() {
        sprite.stopFlicker();
    }

    /**
     * Returns the sprite width in cells, as taken from the pixel matrix.
     *
     * @return sprite width in cells
     */
    public int getWidth() {
        return sprite.width;
    }

    /**
     * Returns the sprite height in cells, as taken from the pixel matrix.
     *
     * @return sprite height in cells
     */
    public int getHeight() {
        return sprite.height;
    }

    /**
     * Moves the object by assigning a new top-left cell position. Speed is kept separately by
     * subclasses, so this only relocates the sprite.
     *
     * @param x horizontal cell coordinate of the sprite's top-left corner
     * @param y vertical cell coordinate of the sprite's top-left corner
     */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Changes how long each animation frame is shown. A delay of {@code Integer.MAX_VALUE}
     * effectively freezes the animation, since the frame counter would never reach it.
     *
     * @param delay number of draw calls per animation frame
     */
    public void setAnimationDelay(int delay) {
        sprite.setAnimationDelay(delay);
    }
}