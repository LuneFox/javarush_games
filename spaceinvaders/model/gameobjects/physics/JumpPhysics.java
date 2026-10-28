package com.javarush.games.spaceinvaders.model.gameobjects.physics;

import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;

/** Прыжки и падения объекта. */
public class JumpPhysics {
    /** Объект, к которому применяется физика. */
    private final GameObject object;
    /** Уровень пола. */
    private int floorLevel;
    /** Уровень потолка. */
    private final int ceilingLevel;
    /** Скорость подъёма. */
    private final int raiseSpeed;
    /** Скорость падения. */
    private int descendSpeed;
    /** Оставшаяся энергия прыжка. */
    private int jumpEnergy;
    /** Максимальная энергия прыжка. */
    private final int maxJumpEnergy;
    /** Число завершённых прыжков. */
    private long completedJumps;

    /** Создаёт физику прыжка для объекта. */
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

    /** Начинает прыжок. */
    public void startJump() {
        if (isInAir()) return;

        jumpEnergy = maxJumpEnergy;
    }

    /** Делает шаг прыжка или падения. */
    public void update() {
        if (jumpEnergy > 0) {
            rise();
            jumpEnergy--;
        } else if (isInAir()) {
            descend();
            countLanding();
        }
    }

    /** Поднимает объект. */
    private void rise() {
        object.setPosition(object.getX(), object.getY() - raiseSpeed);
        if (object.getY() < ceilingLevel) {
            object.setPosition(object.getX(), ceilingLevel);
        }
    }

    /** Опускает объект. */
    private void descend() {
        object.setPosition(object.getX(), object.getY() + descendSpeed);
        if (object.getY() > floorLevel) {
            object.setPosition(object.getX(), floorLevel);
        }
    }

    /** Считает приземление. */
    private void countLanding() {
        if (isOnFloor()) {
            completedJumps++;
        }
    }

    /** Задаёт пол и скорость падения. */
    public void fallTo(int floorLevel, int descendSpeed) {
        this.floorLevel = floorLevel;
        this.descendSpeed = descendSpeed;
    }

    /** Сообщает, в воздухе ли объект. */
    public boolean isInAir() {
        return object.getY() < floorLevel;
    }

    /** Сообщает, на полу ли объект. */
    private boolean isOnFloor() {
        return object.getY() == floorLevel;
    }

    /** Возвращает число завершённых прыжков. */
    public long getCompletedJumps() {
        return completedJumps;
    }
}
