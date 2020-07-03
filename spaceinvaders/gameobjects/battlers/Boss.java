package com.javarush.games.spaceinvaders.gameobjects.battlers;

import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.gameobjects.ammo.Ammo;
import com.javarush.games.spaceinvaders.gameobjects.ammo.Bullet;
import com.javarush.games.spaceinvaders.shapes.ObjectShape;

public class Boss extends EnemyShip {
    private int frameCount = 0;

    public Boss(double x, double y) {
        super(x, y);
        setAnimatedView(true,
                ObjectShape.BOSS_TANK_1, ObjectShape.BOSS_TANK_2);
        score = 100;
    }

    @Override
    public void nextFrame() {
        frameCount++;
        if (frameCount % 10 == 0 || !isAlive) {
            super.nextFrame();
        }
    }

    @Override
    public Bullet fire() {
        if (!isAlive) {
            return null;
        }
        return new Ammo(x + 6, y + height, Direction.DOWN);
    }

    @Override
    public void kill() {
        if (isAlive) {
            isAlive = false;
            setAnimatedView(false,
                    ObjectShape.BOSS_TANK_KILL_1,
                    ObjectShape.BOSS_TANK_KILL_2,
                    ObjectShape.BOSS_TANK_KILL_3,
                    ObjectShape.BOSS_TANK_KILL_4,
                    ObjectShape.BOSS_TANK_KILL_4);
        }
    }
}
