package com.javarush.games.racer.model.car;

/**
 * The four movement directions plus {@code NONE}. The same enum drives both axes: RIGHT and LEFT
 * mean accelerate and brake, while UP and DOWN mean steer up and down.
 */
public enum Direction {
    UP, DOWN, RIGHT, LEFT, NONE
}
