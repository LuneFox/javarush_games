package com.javarush.games.game2048.model;

/**
 * A billiards pocket at a fixed position on the field border. Accumulates the
 * score of pocketed balls; a pocket is "open" only while its score is zero.
 */
public class Pocket {
    public final static String ICON = "⬤";
    public int x;
    public int y;
    private int score;
    private boolean isOpen;

    /**
     * Creates an open pocket at the given board position.
     *
     * @param x the pocket column
     * @param y the pocket row
     */
    public Pocket(int x, int y) {
        this.x = x;
        this.y = y;
        isOpen = true;
    }

    /**
     * Adds the given amount to the pocket score; the pocket becomes closed as
     * soon as the score turns positive.
     *
     * @param amount the score of the pocketed ball
     */
    public void addScore(int amount) {
        score += amount;

        if (score > 0) {
            isOpen = false;
        }
    }

    /**
     * Subtracts the given amount from the pocket score; the pocket becomes
     * open again once the score reaches zero.
     *
     * @param amount the amount to remove
     */
    public void removeScore(int amount) {
        score -= amount;
        if (score == 0) {
            isOpen = true;
        }
    }

    /**
     * @return the score currently accumulated in the pocket
     */
    public int getScore() {
        return score;
    }

    /**
     * @return whether the pocket is open (holds no balls yet)
     */
    public boolean isOpen() {
        return isOpen;
    }

}
