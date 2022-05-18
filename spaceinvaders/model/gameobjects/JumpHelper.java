package com.javarush.games.spaceinvaders.model.gameobjects;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;

public class JumpHelper {
    private final GameObject object;
    private int floorLevel;
    private int ceilingLevel;
    private int raiseSpeed;
    private int descendSpeed;
    private int jumpEnergy;
    private int maxJumpEnergy;
    private long jumpCount;

    public JumpHelper(GameObject object) {
        this.object = object;
        this.floorLevel = SpaceInvadersGame.HEIGHT - 1;
        this.raiseSpeed = 1;
        this.descendSpeed = 1;
        this.jumpEnergy = 0;
        this.maxJumpEnergy = 3;
        this.jumpCount = 0;
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

    public void setCeilingLevel(int ceilingLevel) {
        this.ceilingLevel = ceilingLevel;
    }

    public void setRaiseSpeed(int raiseSpeed) {
        this.raiseSpeed = raiseSpeed;
    }

    public void setDescendSpeed(int descendSpeed) {
        this.descendSpeed = descendSpeed;
    }

    public void setMaxJumpEnergy(int maxJumpEnergy) {
        this.maxJumpEnergy = maxJumpEnergy;
    }

    public long getJumpCount() {
        return jumpCount;
    }
}
