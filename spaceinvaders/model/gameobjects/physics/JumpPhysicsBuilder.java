package com.javarush.games.spaceinvaders.model.gameobjects.physics;

import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;

/** Собирает настройки физики прыжка. */
public class JumpPhysicsBuilder {
    /** Энергия прыжка по умолчанию. */
    private static final int DEFAULT_MAX_JUMP_ENERGY = 3;

    /** Объект для физики. */
    private final GameObject object;
    /** Уровень пола. */
    private int floorLevel = GameField.HEIGHT - 1;
    /** Уровень потолка. */
    private int ceilingLevel;
    /** Скорость подъёма. */
    private int raiseSpeed = 1;
    /** Скорость падения. */
    private int descendSpeed = 1;
    /** Максимальная энергия прыжка. */
    private int maxJumpEnergy = DEFAULT_MAX_JUMP_ENERGY;

    /** Создаёт строитель для объекта. */
    public JumpPhysicsBuilder(GameObject object) {
        this.object = object;
    }

    /** Задаёт уровень пола. */
    public JumpPhysicsBuilder setFloorLevel(int floorLevel) {
        this.floorLevel = floorLevel;
        return this;
    }

    /** Задаёт уровень потолка. */
    public JumpPhysicsBuilder setCeilingLevel(int ceilingLevel) {
        this.ceilingLevel = ceilingLevel;
        return this;
    }

    /** Задаёт скорость подъёма. */
    public JumpPhysicsBuilder setRaiseSpeed(int raiseSpeed) {
        this.raiseSpeed = raiseSpeed;
        return this;
    }

    /** Задаёт скорость падения. */
    public JumpPhysicsBuilder setDescendSpeed(int descendSpeed) {
        this.descendSpeed = descendSpeed;
        return this;
    }

    /** Задаёт силу прыжка. */
    public JumpPhysicsBuilder setMaxJumpEnergy(int maxJumpEnergy) {
        this.maxJumpEnergy = maxJumpEnergy;
        return this;
    }

    /** Создаёт физику прыжка. */
    public JumpPhysics build() {
        return new JumpPhysics(object, floorLevel, ceilingLevel, raiseSpeed, descendSpeed, maxJumpEnergy);
    }
}
