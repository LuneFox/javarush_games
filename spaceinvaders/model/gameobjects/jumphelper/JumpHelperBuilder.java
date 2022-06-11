package com.javarush.games.spaceinvaders.model.gameobjects.jumphelper;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;

public class JumpHelperBuilder {
    private final GameObject object;
    private int floorLevel;
    private int ceilingLevel;
    private int raiseSpeed;
    private int descendSpeed;
    private final int jumpEnergy;
    private int maxJumpEnergy;
    private final long jumpCount;

    public JumpHelperBuilder(GameObject object) {
        this.object = object;
        this.floorLevel = SpaceInvadersGame.HEIGHT - 1;
        this.raiseSpeed = 1;
        this.descendSpeed = 1;
        this.jumpEnergy = 0;
        this.maxJumpEnergy = 3;
        this.jumpCount = 0;
    }

    public JumpHelperBuilder setFloorLevel(int floorLevel) {
        this.floorLevel = floorLevel;
        return this;
    }

    public JumpHelperBuilder setCeilingLevel(int ceilingLevel) {
        this.ceilingLevel = ceilingLevel;
        return this;
    }

    public JumpHelperBuilder setRaiseSpeed(int raiseSpeed) {
        this.raiseSpeed = raiseSpeed;
        return this;
    }

    public JumpHelperBuilder setDescendSpeed(int descendSpeed) {
        this.descendSpeed = descendSpeed;
        return this;
    }

    public JumpHelperBuilder setMaxJumpEnergy(int maxJumpEnergy) {
        this.maxJumpEnergy = maxJumpEnergy;
        return this;
    }

    public JumpHelper build() {
        return new JumpHelper(
                object,
                floorLevel,
                ceilingLevel,
                raiseSpeed,
                descendSpeed,
                jumpEnergy,
                maxJumpEnergy,
                jumpCount
        );
    }
}