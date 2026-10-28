package com.javarush.games.spaceinvaders.model.gameobjects.physics;

import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;

/**
 * Collects the jump settings before the immutable {@link JumpPhysics} is created.
 */
public class JumpPhysicsBuilder {
    private static final int DEFAULT_MAX_JUMP_ENERGY = 3;

    private final GameObject object;
    private int floorLevel = GameField.HEIGHT - 1;
    private int ceilingLevel;
    private int raiseSpeed = 1;
    private int descendSpeed = 1;
    private int maxJumpEnergy = DEFAULT_MAX_JUMP_ENERGY;

    public JumpPhysicsBuilder(GameObject object) {
        this.object = object;
    }

    public JumpPhysicsBuilder setFloorLevel(int floorLevel) {
        this.floorLevel = floorLevel;
        return this;
    }

    public JumpPhysicsBuilder setCeilingLevel(int ceilingLevel) {
        this.ceilingLevel = ceilingLevel;
        return this;
    }

    public JumpPhysicsBuilder setRaiseSpeed(int raiseSpeed) {
        this.raiseSpeed = raiseSpeed;
        return this;
    }

    public JumpPhysicsBuilder setDescendSpeed(int descendSpeed) {
        this.descendSpeed = descendSpeed;
        return this;
    }

    public JumpPhysicsBuilder setMaxJumpEnergy(int maxJumpEnergy) {
        this.maxJumpEnergy = maxJumpEnergy;
        return this;
    }

    public JumpPhysics build() {
        return new JumpPhysics(object, floorLevel, ceilingLevel, raiseSpeed, descendSpeed, maxJumpEnergy);
    }
}
