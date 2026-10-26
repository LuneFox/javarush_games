package com.javarush.games.racer.model.gameobjects;

/**
 * A tool for sprite that gradually hides it from right to left or reveals it from left to right.
 * Step parameter decides how many pixel columns get hidden/revealed at each iteration.
 * Despite the names, {@link #startIn(int)} hides the sprite (the covered width grows from 0) while
 * {@link #startOut(int, int)} reveals it (the covered width shrinks from the full sprite width).
 * The mask only stores how many of the sprite's columns are covered; it is advanced once per
 * sprite draw call and stops by itself at the sprite bounds.
 */

class SpriteMask {
    private int step;
    private State state;
    private int width;

    /**
     * Mask animation state: {@code READY} before the first start, {@code IN} while the covered width
     * grows (hiding the sprite), {@code OUT} while it shrinks (revealing the sprite), and
     * {@code STOP} once a bound has been reached.
     */
    private enum State {
        IN, OUT, READY, STOP
    }

    /**
     * Creates a mask in the {@code READY} state with a width of 0, so it covers none of the sprite.
     */
    SpriteMask() {
        this.state = State.READY;
    }

    /**
     * Advances the mask by one step and clamps it to the sprite bounds. Expected to be called once
     * per draw call; does nothing while the state is {@code READY} or {@code STOP}.
     *
     * @param spriteWidth width of the owning sprite in pixels, used as the upper bound
     */
    void nextStep(int spriteWidth) {
        changeWidth();
        limitGrowth(spriteWidth);
    }

    /**
     * Grows or shrinks the mask width by the configured step, depending on the current direction.
     */
    private void changeWidth() {
        if (state == State.IN) {
            width += step;
        } else if (state == State.OUT) {
            width -= step;
        }
    }

    /**
     * Clamps the mask width to {@code [0, spriteWidth]} and moves the mask to {@code STOP} when a
     * bound is hit, which makes the animation one-shot.
     *
     * @param spriteWidth width of the owning sprite in pixels
     */
    private void limitGrowth(int spriteWidth) {
        if (width < 0) {
            width = 0;
            state = State.STOP;
        } else if (width > spriteWidth) {
            width = spriteWidth;
            state = State.STOP;
        }
    }

    /**
     * Starts hiding the sprite: the covered width grows from 0 by {@code step} columns per step, so
     * the sprite disappears from its right edge toward the left. Ignored unless the mask is still
     * {@code READY}, so this is a one-shot animation.
     *
     * @param step number of pixel columns covered per step
     */
    void startIn(int step) {
        if (state != State.READY) return;

        this.step = step;
        this.width = 0;
        this.state = State.IN;
    }

    /**
     * Starts revealing the sprite: the covered width starts at the full sprite width and shrinks by
     * {@code step} columns per step, so the sprite reappears from its left edge. Ignored unless the
     * mask is still {@code READY}, so this is a one-shot animation.
     *
     * @param step        number of pixel columns uncovered per step
     * @param spriteWidth width of the owning sprite in pixels, used as the starting mask width
     */
    void startOut(int step, int spriteWidth) {
        if (state != State.READY) return;

        this.step = step;
        this.width = spriteWidth;
        this.state = State.OUT;
    }

    /**
     * Returns how many of the sprite's columns are currently covered, counted from the right edge.
     *
     * @return mask width in pixel columns, never negative nor greater than the sprite width
     */
    int getWidth() {
        return width;
    }
}
