package com.javarush.games.spaceinvaders.model.gameobjects;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;

public class JumpHelper {
    private final GameObject object;
    private int baseLine;
    private int topLine;
    private int raiseSpeed;
    private int descendSpeed;
    private int jumpEnergy;
    private int maxJumpEnergy;
    private boolean firstJumpFinished;

    public JumpHelper(GameObject object) {
        this.object = object;
        this.baseLine = SpaceInvadersGame.HEIGHT - 1;
        this.raiseSpeed = 1;
        this.descendSpeed = 1;
        this.jumpEnergy = 0;
        this.maxJumpEnergy = 3;
        this.firstJumpFinished = false;
    }

    public void initJump() {
        if (isAboveBaseLine()) return;
        jumpEnergy = maxJumpEnergy;
    }

    public void progressJump() {
        if (jumpEnergy > 0) {
            loseJumpEnergy();
            raise();
        } else if (isAboveBaseLine()) {
            descend();
            checkLanding();
        }
    }

    private void checkLanding() {
        if (isOnBaseLine()) {
            firstJumpFinished = true;
        }
    }

    protected void raise() {
        object.y -= raiseSpeed;
        if (object.y < topLine) {
            object.y = topLine;
        }
    }

    private void descend() {
        object.y += descendSpeed;
        if (object.y > baseLine) {
            object.y = baseLine;
        }
    }

    private void loseJumpEnergy() {
        jumpEnergy--;
    }

    public boolean isAboveBaseLine() {
        return object.y < baseLine;
    }

    private boolean isOnBaseLine() {
        return object.y == baseLine;
    }

    public void setBaseLine(int baseLine) {
        this.baseLine = baseLine;
    }

    public void setTopLine(int topLine) {
        this.topLine = topLine;
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

    public boolean isFirstJumpFinished() {
        return firstJumpFinished;
    }
}
