package com.javarush.games.spaceinvaders.model.gameobjects.jumphelper;

import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;

public class JumpHelper {
    private final GameObject object;
    private int floorLevel;
    private final int ceilingLevel;
    private final int raiseSpeed;
    private int descendSpeed;
    private int jumpEnergy;
    private final int maxJumpEnergy;
    private long jumpCount;

    JumpHelper(GameObject object,
                      int floorLevel,
                      int ceilingLevel,
                      int raiseSpeed,
                      int descendSpeed,
                      int jumpEnergy,
                      int maxJumpEnergy,
                      long jumpCount) {
        this.object = object;
        this.floorLevel = floorLevel;
        this.ceilingLevel = ceilingLevel;
        this.raiseSpeed = raiseSpeed;
        this.descendSpeed = descendSpeed;
        this.jumpEnergy = jumpEnergy;
        this.maxJumpEnergy = maxJumpEnergy;
        this.jumpCount = jumpCount;
    }

    public void initJump() {
        if (isAboveFloor()) return;
        jumpEnergy = maxJumpEnergy;
    }

    public void progressJump() {
        if (jumpEnergy > 0) {
            raise();
            loseJumpEnergy();
        } else if (isAboveFloor()) {
            descend();
            countJumpsAfterLanding();
        }
    }

    private void countJumpsAfterLanding() {
        if (isOnFloor()) {
            jumpCount++;
        }
    }

    protected void raise() {
        object.y -= raiseSpeed;
        if (object.y < ceilingLevel) {
            object.y = ceilingLevel;
        }
    }

    private void descend() {
        object.y += descendSpeed;
        if (object.y > floorLevel) {
            object.y = floorLevel;
        }
    }

    private void loseJumpEnergy() {
        jumpEnergy--;
    }

    public boolean isAboveFloor() {
        return object.y < floorLevel;
    }

    private boolean isOnFloor() {
        return object.y == floorLevel;
    }

    public void setFloorLevel(int floorLevel) {
        this.floorLevel = floorLevel;
    }

    public void setDescendSpeed(int descendSpeed) {
        this.descendSpeed = descendSpeed;
    }

    public long getJumpCount() {
        return jumpCount;
    }
}
