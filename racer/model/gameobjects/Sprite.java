package com.javarush.games.racer.model.gameobjects;

import com.javarush.engine.cell.Color;
import com.javarush.games.racer.view.Display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A pixel sprite drawn into the off-screen display buffer.
 * The pixel matrix stores indices into {@code Color.values()}; index 0 is drawn as
 * {@code Color.NONE}, which the display treats as transparent. Handles animation playback,
 * horizontal mirroring, one-shot masking and flicker.
 */
public class  Sprite {
    private final Display display;
    private int[][] matrix;
    int width;
    int height;

    private List<int[][]> frames;
    private int currentFrame;
    private int nextFrameDelay;
    private int nextFrameTimer;
    private Loop loop;
    private final SpriteMask mask;

    private boolean flicker;
    private boolean flickerPhase;

    /**
     * Playback mode: either restart from the first frame after the last one, or hold the last frame.
     */
    public enum Loop {
        ENABLED, DISABLED
    }

    /**
     * Creates an empty sprite with a frame delay of one draw call and a mask in its initial state.
     *
     * @param display the pixel buffer that receives the sprite's pixels
     */
    public Sprite(Display display) {
        this.display = display;
        this.nextFrameDelay = 1;
        this.mask = new SpriteMask();
    }

    /**
     * Advances the animation and the mask by one step, then writes the visible pixels into the
     * display buffer. Only the columns left of the mask are drawn, so an active mask erases the
     * sprite from its right edge. On a flicker-off phase the whole drawing is skipped, but the
     * animation timer has already advanced. Pixels written here appear only after the engine
     * flushes the buffer with {@code Display.draw()}.
     *
     * @param x      horizontal cell coordinate of the sprite's top-left corner
     * @param y      vertical cell coordinate of the sprite's top-left corner
     * @param mirror {@code Mirror.HORIZONTAL} to flip left-to-right, {@code Mirror.NONE} to draw as-is
     */
    void draw(double x, double y, Mirror mirror) {
        nextFrame();
        mask.nextStep(width);

        if (flickers()) return;

        for (int i = 0; i < (width - mask.getWidth()); i++) {
            for (int j = 0; j < height; j++) {
                Color color = Color.values()[matrix[j][i]];
                double drawX = (mirror == Mirror.HORIZONTAL) ? (x + width - 1 - i) : (x + i);
                double drawY = (y + j);
                display.drawPixel((int) drawX, (int) drawY, color);
            }
        }
    }

    /**
     * Advances the flicker phase and reports whether the current draw call should be skipped.
     * The phase flips on every call while flicker is active, which produces the blinking.
     *
     * @return true if this draw call must be skipped, false otherwise
     */
    private boolean flickers() {
        if (flicker) {
            changeFlickerPhase();
            return flickerPhase;
        }
        return false;
    }

    /**
     * Moves to the next animation frame once the per-frame delay has elapsed. The timer counts
     * draw calls, so the delay is expressed in frames rather than milliseconds. At the end of a
     * looping animation it jumps back to frame 0; otherwise it stays on the last frame.
     */
    void nextFrame() {
        if (nextFrameTimer++ % nextFrameDelay != 0) return;

        if (!isAnimationFinished()) {
            this.matrix = frames.get(currentFrame++);
        } else if (loop == Loop.ENABLED) {
            currentFrame = 0;
            this.matrix = frames.get(currentFrame++);
        }
    }

    /**
     * Tells whether the animation has run past its last frame.
     *
     * @return true if all frames have been consumed and playback is at or beyond the end
     */
    boolean isAnimationFinished() {
        return currentFrame >= frames.size();
    }

    /**
     * Replaces the animation with a single static frame and resets playback to the beginning.
     * Note that the playback mode is left untouched, so a previous {@code Loop} setting survives.
     *
     * @param frame pixel matrix whose first row is the top row of the sprite
     */
    void setStaticView(int[][] frame) {
        this.frames = new ArrayList<>();
        this.frames.add(frame);
        this.currentFrame = 0;
        this.setMatrix(frame);
    }

    /**
     * Sets up an animation from the given frames. As a quirk, the matrix is seeded with the last
     * frame while the frame counter is reset to 0; since the timer also starts at 0, the first
     * draw already advances to frame 0. The sprite size is derived from the seeded last frame.
     *
     * @param loop           whether playback restarts after the last frame or holds it
     * @param nextFrameDelay number of draw calls each frame stays visible
     * @param frames         animation frames, each a pixel matrix indexed into {@code Color.values()}
     */
    void setAnimatedView(Loop loop, int nextFrameDelay, int[][]... frames) {
        this.loop = loop;
        this.nextFrameDelay = nextFrameDelay;
        this.frames = Arrays.asList(frames);
        this.currentFrame = 0;
        int lastFrameIndex = frames.length - 1;
        this.setMatrix(frames[lastFrameIndex]);
    }

    /**
     * Stores the pixel matrix and derives the sprite size from it: the first row's length becomes
     * the width in columns and the row count becomes the height. The matrix is kept by reference.
     *
     * @param matrix pixel matrix indexed into {@code Color.values()}, 0 being transparent
     */
    void setMatrix(int[][] matrix) {
        this.matrix = matrix;
        this.width = matrix[0].length;
        this.height = matrix.length;
    }

    /**
     * Changes how many draw calls each animation frame stays visible.
     *
     * @param nextFrameDelay delay in draw calls per frame
     */
    public void setAnimationDelay(int nextFrameDelay) {
        this.nextFrameDelay = nextFrameDelay;
    }

    /**
     * Starts the one-shot "mask in" animation: the masked width grows by {@code step} per draw call,
     * so the sprite is erased from its right edge toward the left. Despite the name it hides the
     * sprite, it does not reveal it.
     *
     * @param step number of pixel columns covered per draw call
     */
    void maskIn(int step) {
        mask.startIn(step);
    }

    /**
     * Starts the one-shot "mask out" animation: the masked width starts at the full sprite width and
     * shrinks by {@code step} per draw call, so the sprite is revealed from its left edge.
     *
     * @param step number of pixel columns uncovered per draw call
     */
    void maskOut(int step) {
        mask.startOut(step, width);
    }

    /**
     * Enables blinking, so the sprite is skipped on alternating draw calls.
     */
    void startFlicker() {
        this.flicker = true;
    }

    /**
     * Disables blinking, so the sprite is drawn on every subsequent draw call.
     */
    void stopFlicker() {
        this.flicker = false;
    }

    /**
     * Inverts the flicker phase, switching between drawing and skipping on the next draw call.
     */
    private void changeFlickerPhase() {
        flickerPhase = !flickerPhase;
    }
}
