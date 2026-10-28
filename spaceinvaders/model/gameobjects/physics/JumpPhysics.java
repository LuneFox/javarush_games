package com.javarush.games.spaceinvaders.model.gameobjects.physics;

import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;

/**
 * Gives an object the ability to jump over the field and to fall down to a floor level.
 */
public class JumpPhysics {
    private final GameObject object;
    private int floorLevel;
    private final int ceilingLevel;
    private final int raiseSpeed;
    private int descendSpeed;
    private int jumpEnergy;
    private final int maxJumpEnergy;
    private long completedJumps;

    JumpPhysics(GameObject object,
               int floorLevel,
               int ceilingLevel,
               int raiseSpeed,
               int descendSpeed,
               int maxJumpEnergy) {
        this.object = object;
        this.floorLevel = floorLevel;
        this.ceilingLevel = ceilingLevel;
        this.raiseSpeed = raiseSpeed;
        this.descendSpeed = descendSpeed;
        this.maxJumpEnergy = maxJumpEnergy;
    }

    public void startJump() {
        if (isInAir()) return;

        jumpEnergy = maxJumpEnergy;
    }

    /**
     * One step of the jump: rises while the energy lasts, then falls down to the floor.
     */
    public void update() {
        if (jumpEnergy > 0) {
            rise();
            jumpEnergy--;
        } else if (isInAir()) {
            descend();
            countLanding();
        }
    }

    private void rise() {
        object.setPosition(object.getX(), object.getY() - raiseSpeed);
        if (object.getY() < ceilingLevel) {
            object.setPosition(object.getX(), ceilingLevel);
        }
    }

    private void descend() {
        object.setPosition(object.getX(), object.getY() + descendSpeed);
        if (object.getY() > floorLevel) {
            object.setPosition(object.getX(), floorLevel);
        }
    }

    private void countLanding() {
        if (isOnFloor()) {
            completedJumps++;
        }
    }

    /**
     * Makes the object fall to the given floor, for example from a brick down to the ground.
     */
    public void fallTo(int floorLevel, int descendSpeed) {
        this.floorLevel = floorLevel;
        this.descendSpeed = descendSpeed;
    }

    public boolean isInAir() {
        return object.getY() < floorLevel;
    }

    private boolean isOnFloor() {
        return object.getY() == floorLevel;
    }

    public long getCompletedJumps() {
        return completedJumps;
    }
}
